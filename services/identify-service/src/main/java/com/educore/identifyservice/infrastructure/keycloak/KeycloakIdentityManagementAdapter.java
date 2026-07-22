package com.educore.identifyservice.infrastructure.keycloak;

import com.educore.identifyservice.application.port.out.IdentityManagementPort;
import com.educore.identifyservice.application.port.out.model.AccountSearchCriteria;
import com.educore.identifyservice.application.port.out.model.CreateIdentityAccount;
import com.educore.identifyservice.application.port.out.model.UpdateIdentityAccount;
import com.educore.identifyservice.domain.exception.*;
import com.educore.identifyservice.domain.model.*;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.RoleScopeResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    21/07/2026 at 11:48
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@Component
public class KeycloakIdentityManagementAdapter implements IdentityManagementPort {
    private final Keycloak keycloak;
    private final KeycloakProperties properties;

    public KeycloakIdentityManagementAdapter(Keycloak keycloak, KeycloakProperties properties) {
        this.keycloak = keycloak;
        this.properties = properties;
    }

    @Override
    public boolean existsByUsername(Username username) {
        return execute(() ->
                users()
                        .searchByUsername(
                                username.value(),
                                true
                        )
                        .stream()
                        .findAny()
                        .isPresent()
        );
    }

    @Override
    public boolean existsByEmail(EmailAddress email) {
        return execute(() ->
                users()
                        .searchByEmail(
                                email.value(),
                                true
                        )
                        .stream()
                        .findAny()
                        .isPresent()
        );
    }

    @Override
    public boolean existsByUsernameOtherThan(
            Username username,
            AccountId accountId
    ) {
        return execute(() ->
                users()
                        .searchByUsername(
                                username.value(),
                                true
                        )
                        .stream()
                        .anyMatch(user ->
                                !accountId.value()
                                        .equals(user.getId())
                        )
        );
    }

    @Override
    public boolean existsByEmailOtherThan(
            EmailAddress email,
            AccountId accountId
    ) {
        return execute(() ->
                users()
                        .searchByEmail(
                                email.value(),
                                true
                        )
                        .stream()
                        .anyMatch(user ->
                                !accountId.value()
                                        .equals(user.getId())
                        )
        );
    }

    @Override
    public Account create(CreateIdentityAccount account) {
        RealmResource realm = realm();

        UserRepresentation representation = new UserRepresentation();

        representation.setUsername(
                account.username().value()
        );
        representation.setEmail(
                account.email().value()
        );
        representation.setFirstName(account.firstName());
        representation.setLastName(account.lastName());
        representation.setEnabled(account.enabled());
        representation.setEmailVerified(false);

        CredentialRepresentation credential = new CredentialRepresentation();

        credential.setType(
                CredentialRepresentation.PASSWORD
        );
        credential.setValue(account.password().value());
        credential.setTemporary(
                account.temporaryPassword()
        );

        representation.setCredentials(
                List.of(credential)
        );

        String createdId;

        try (Response response = realm.users().create(representation)) {

            if (response.getStatus() == Response.Status.CONFLICT.getStatusCode()) {
                throw new AccountAlreadyExistsException("Username or email already exists");
            }

            if (response.getStatus() != Response.Status.CREATED.getStatusCode()) {
                throw new IdentityProviderException(
                        "Could not create account. "
                                + "Keycloak status: "
                                + response.getStatus()
                );
            }

            createdId = CreatedResponseUtil.getCreatedId(response);

        } catch (ProcessingException exception) {
            throw new IdentityProviderUnavailableException(exception.getMessage());
        } catch (ForbiddenException exception) {
            throw new IdentityProviderPermissionException(exception.getMessage());
        }

        AccountId accountId = AccountId.of(createdId);

        try {
            replaceManagedRoles(
                    accountId,
                    account.roles()
            );

            return findById(accountId);

        } catch (RuntimeException exception) {
            compensateDelete(accountId);
            throw exception;
        }
    }

    @Override
    public Account findById(AccountId accountId) {
        return executeForAccount(
                accountId,
                () -> mapAccount(
                        user(accountId)
                                .toRepresentation()
                )
        );
    }

    @Override
    public Page<Account> search(AccountSearchCriteria criteria) {
        return execute(() -> {
            UsersResource usersResource = users();

            List<UserRepresentation> representations;
            long total;

            if (criteria.keyword() == null || criteria.keyword().isBlank()) {
                representations = usersResource.list(
                        criteria.offset(),
                        criteria.size()
                );

                total = usersResource.count();
            } else {
                String keyword = criteria.keyword().trim();

                representations = usersResource.search(
                        keyword,
                        criteria.offset(),
                        criteria.size(),
                        true
                );

                total = usersResource.count(keyword);
            }

            List<Account> accounts = representations.stream()
                    .map(this::mapAccount)
                    .toList();

            Pageable pageable = PageRequest.of(
                    criteria.page(),
                    criteria.size()
            );

            return new PageImpl<>(
                    accounts,
                    pageable,
                    total
            );
        });
    }

    @Override
    public Account update(
            UpdateIdentityAccount account
    ) {
        return executeForAccount(
                account.accountId(),
                () -> {
                    UserResource resource = user(account.accountId());

                    UserRepresentation representation = resource.toRepresentation();

                    representation.setUsername(
                            account.username().value()
                    );
                    representation.setEmail(
                            account.email().value()
                    );
                    representation.setFirstName(
                            account.firstName()
                    );
                    representation.setLastName(
                            account.lastName()
                    );

                    resource.update(representation);

                    return findById(account.accountId());
                }
        );
    }

    @Override
    public Account changeStatus(
            AccountId accountId,
            boolean enabled
    ) {
        return executeForAccount(
                accountId,
                () -> {
                    UserResource resource = user(accountId);

                    UserRepresentation representation = resource.toRepresentation();

                    representation.setEnabled(enabled);
                    resource.update(representation);

                    if (!enabled) {
                        resource.logout();
                    }

                    return findById(accountId);
                }
        );
    }

    @Override
    public Account replaceRoles(
            AccountId accountId,
            Set<AccountRole> roles
    ) {
        return executeForAccount(
                accountId,
                () -> {
                    replaceManagedRoles(accountId, roles);
                    return findById(accountId);
                }
        );
    }

    private <T> T executeForAccount(
            AccountId accountId,
            Supplier<T> action
    ) {
        try {
            return execute(action);

        } catch (NotFoundException exception) {
            throw new AccountNotFoundException(
                    accountId.value()
            );
        }
    }

    private void replaceManagedRoles(
            AccountId accountId,
            Set<AccountRole> targetRoles
    ) {
        RoleScopeResource roleScope = user(accountId)
                .roles()
                .realmLevel();

        List<RoleRepresentation> currentManagedRoles = roleScope.listAll()
                .stream()
                .filter(this::isManagedRole)
                .toList();

        List<RoleRepresentation> targetRepresentations = targetRoles.stream()
                .map(this::resolveRole)
                .toList();

        Set<String> currentNames = currentManagedRoles.stream()
                .map(RoleRepresentation::getName)
                .collect(Collectors.toSet());

        Set<String> targetNames = targetRepresentations.stream()
                .map(RoleRepresentation::getName)
                .collect(Collectors.toSet());

        List<RoleRepresentation> rolesToAdd = targetRepresentations.stream()
                .filter(role ->
                        !currentNames.contains(
                                role.getName()
                        )
                )
                .toList();

        List<RoleRepresentation> rolesToRemove = currentManagedRoles.stream()
                .filter(role ->
                        !targetNames.contains(
                                role.getName()
                        )
                )
                .toList();

        try {
            if (!rolesToAdd.isEmpty()) {
                roleScope.add(rolesToAdd);
            }

            if (!rolesToRemove.isEmpty()) {
                roleScope.remove(rolesToRemove);
            }

        } catch (RuntimeException exception) {
            restoreRoles(
                    roleScope,
                    currentManagedRoles
            );

            throw exception;
        }
    }

    private void restoreRoles(
            RoleScopeResource roleScope,
            List<RoleRepresentation> originalRoles
    ) {
        try {
            List<RoleRepresentation> current = roleScope.listAll()
                    .stream()
                    .filter(this::isManagedRole)
                    .toList();

            if (!current.isEmpty()) {
                roleScope.remove(current);
            }

            if (!originalRoles.isEmpty()) {
                roleScope.add(originalRoles);
            }
        } catch (RuntimeException ignored) {
            // Ghi error log hoặc gửi cảnh báo vận hành tại đây.
        }
    }

    private RoleRepresentation resolveRole(
            AccountRole role
    ) {
        try {
            return realm()
                    .roles()
                    .get(role.name())
                    .toRepresentation();

        } catch (NotFoundException exception) {
            throw new RoleConfigurationException(
                    "Not found role name " + role.name()
            );
        }
    }

    private Account mapAccount(
            UserRepresentation representation
    ) {
        Set<AccountRole> roles = user(AccountId.of(representation.getId()))
                .roles()
                .realmLevel()
                .listAll()
                .stream()
                .map(RoleRepresentation::getName)
                .map(AccountRole::fromKeycloakRole)
                .flatMap(Optional::stream)
                .collect(Collectors.toUnmodifiableSet());

        Instant createdAt = representation.getCreatedTimestamp() == null
                ? null
                : Instant.ofEpochMilli(
                representation
                        .getCreatedTimestamp()
        );

        return new Account(
                AccountId.of(representation.getId()),
                Username.of(representation.getUsername()),
                EmailAddress.of(representation.getEmail()),
                representation.getFirstName(),
                representation.getLastName(),
                AccountStatus.fromEnabled(
                        Boolean.TRUE.equals(
                                representation.isEnabled()
                        )
                ),
                Boolean.TRUE.equals(
                        representation.isEmailVerified()
                ),
                roles,
                createdAt
        );
    }

    private boolean isManagedRole(
            RoleRepresentation role
    ) {
        return AccountRole
                .fromKeycloakRole(role.getName())
                .isPresent();
    }

    private RealmResource realm() {
        return keycloak.realm(properties.realm());
    }

    private UsersResource users() {
        return realm().users();
    }

    private UserResource user(AccountId accountId) {
        return users().get(accountId.value());
    }

    private void compensateDelete(AccountId accountId) {
        try {
            user(accountId).remove();
        } catch (RuntimeException ignored) {
            // Ghi error log hoặc retry bằng job/outbox.
        }
    }


    private <T> T execute(Supplier<T> action) {
        try {
            return action.get();

        } catch (ForbiddenException exception) {
            throw new IdentityProviderPermissionException(exception.getMessage());

        } catch (ProcessingException exception) {
            throw new IdentityProviderUnavailableException(exception.getMessage());

        } catch (WebApplicationException exception) {
            if (exception.getResponse() != null && exception.getResponse().getStatus() == Response.Status.CONFLICT.getStatusCode()) {
                throw new AccountAlreadyExistsException("Username or email already exists");
            }

            throw new IdentityProviderException("Keycloak request failed " + exception.getMessage());
        }
    }
}
