package com.mickl.rest.rest_server.security.model;

import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

@Document(collection = "Clients")
public class Client implements ClientDetails {

    @Id
    private String id;
    @NotNull
    @Setter
    private String clientId;
    @NotNull
    @Setter
    private String clientSecret;
    @Setter
    private Set<String> resourceIds;
    @Setter
    private Set<String> scope;
    @Setter
    private Set<String> authorizedGrantTypes;
    @Setter
    private Set<String> registeredRedirectUri;
    @Setter
    private Set<Role> authorities;
    @Setter
    private Integer accessTokenValiditySeconds;
    @Setter
    private Integer refreshTokenValiditySeconds;

    @Override
    public String getClientId() {
        return clientId;
    }

    @Override
    public Set<String> getResourceIds() {
        return resourceIds;
    }

    @Override
    public boolean isSecretRequired() {
        return true;
    }

    @Override
    public String getClientSecret() {
        return clientSecret;
    }

    @Override
    public boolean isScoped() {
        return true;
    }

    @Override
    public Set<String> getScope() {
        return scope;
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        return authorizedGrantTypes;
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        return registeredRedirectUri;
    }

    private Set<? extends GrantedAuthority> getRoleAsGA() {
        return authorities;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return (Collection<GrantedAuthority>) getRoleAsGA();
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
        return accessTokenValiditySeconds;
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return refreshTokenValiditySeconds;
    }

    @Override
    public boolean isAutoApprove(String scope) {
        return true;
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        return null;
    }
}
