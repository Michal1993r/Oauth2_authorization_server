package com.mickl.rest.rest_server.security.config;

import com.mickl.rest.rest_server.security.model.User;
import com.mickl.rest.rest_server.security.services.ClientService;
import com.mickl.rest.rest_server.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class Oauth2Config extends AuthorizationServerConfigurerAdapter {

    private final UserService userService;
    private final ClientService clientService;

    @Value("3600")
    private int expiration;

    @Autowired
    public Oauth2Config(@Qualifier("userService") UserService userService,
                        @Qualifier("clientService") ClientService clientService) {
        this.userService = userService;
        this.clientService = clientService;
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .authenticationManager(authentication -> {
                    String name = authentication.getName();
                    String password = authentication.getCredentials().toString();
                    User user = userService.getByUsername(name)
                            .orElseThrow(() -> new BadCredentialsException("User Not Found!"));

                    if (passwordsMatch(password, user)) {
                        return new UsernamePasswordAuthenticationToken(
                                authentication.getPrincipal(),
                                authentication.getCredentials(),
                                user.getAuthorities());
                    }

                    throw new BadCredentialsException("Password incorrect!");
                })
                .accessTokenConverter(accessTokenConverter())
                .userDetailsService(userService)
                .tokenStore(tokenStore());
    }

    private boolean passwordsMatch(String password, User user) {
        return user.getPassword().equals(password);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.checkTokenAccess("permitAll()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients
                .withClientDetails(clientService)
                .clients(clientService);
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("123");
        return converter;
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setSupportRefreshToken(true);
        defaultTokenServices.setAccessTokenValiditySeconds(expiration);
        return defaultTokenServices;
    }

}
