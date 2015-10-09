package org.lecture.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;

/**
 * Created by rene on 09.10.15.
 */
@Configuration
public class OauthServerConfig {

  @Configuration
  @EnableResourceServer
  protected static class ResourceServerConfiguration extends
      ResourceServerConfigurerAdapter {

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
      // @formatter:off
      resources
          .resourceId("users-resource");
      // @formatter:on
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
      // @formatter:off
      http
          .authorizeRequests()
          .antMatchers("/users").authenticated();
      // @formatter:on
    }

  }

  @Configuration
  @EnableAuthorizationServer
  public static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    //TODO externe konfiguration des keystores.
    //keystore kann mit keytool erzeugt werden:
    //keytool -genkey -alias authentication-service -keyalg RSA -keystore authentication-service-keystore.jks -keysize 2048
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
      JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
      KeyPair keyPair = new KeyStoreKeyFactory(
          new ClassPathResource("authentication-service-keystore.jks"), //keystorefile in resources
          "authentication-service".toCharArray()) //password of keypair
          .getKeyPair("authentication-service"); //-alias
      converter.setKeyPair(keyPair);
      return converter;
    }

    //beispielrequest:
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
      clients.inMemory()
          .withClient("user-web-client") //name der applikation, bei uns die angular-app
          .autoApprove(true) //redirect verhindern
          .secret("user-web-client-secret")
          .authorizedGrantTypes("authorization_code", "refresh_token",
              "password").scopes("openid");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints)
        throws Exception {
      endpoints.authenticationManager(authenticationManager).accessTokenConverter(
          jwtAccessTokenConverter());
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer)
        throws Exception {
      oauthServer.allowFormAuthenticationForClients() //curl -v  http://localhost:8080/oauth/token -d username=user -d password="im log nachschauen" -d grant_type=password -d client_id=acme -d client_secret=acmesecret -H "Accept: application/json"
          .tokenKeyAccess("permitAll()").checkTokenAccess(
          "isAuthenticated()");

    }
  }


}
