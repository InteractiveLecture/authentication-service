package org.lecture.config;

import nats.client.Nats;
import nats.client.NatsConnector;
import org.lecture.model.User;
import org.lecture.repository.UserRepository;
import org.lecture.service.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by rene on 09.10.15.
 */
@Configuration
public class OauthServerConfig {


  @Bean
  public Nats nats() {
    return new NatsConnector().addHost("nats://nats:4222").connect();
  }

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
          .csrf().disable()
          .authorizeRequests()
          .antMatchers(HttpMethod.POST,"/users").permitAll()
          .antMatchers("/users","/me","/authorities").authenticated()
          .antMatchers("/users/**/authorities","/authorities").hasRole("admin");
      // @formatter:on
    }

  }

  @Configuration
  @EnableAuthorizationServer
  public static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {



    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    //TODO externe konfiguration des keystores.
    //keystore kann mit keytool erzeugt werden:
    //keytool -genkey -alias authentication-service -keyalg RSA -keystore authentication-service-keystore.jks -keysize 2048
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
      JwtAccessTokenConverter converter = new CustomTokenConverter();
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
              "password")
          .scopes("openid");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints)
        throws Exception {
      endpoints.authenticationManager(authenticationManager)
          .accessTokenConverter(jwtAccessTokenConverter())
          .userDetailsService(new UserDetailServiceImpl(userRepository));
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer)
        throws Exception {

      oauthServer.allowFormAuthenticationForClients() //curl -v  http://localhost:8080/oauth/token -d username=user -d password="im log nachschauen" -d grant_type=password -d client_id=user-web-client -d client_secret=user-web-client-secret -H "Accept: application/json"
          .tokenKeyAccess("permitAll()").checkTokenAccess(
          "isAuthenticated()");

    }
  }

  protected static class CustomTokenConverter extends JwtAccessTokenConverter {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken,
                                     OAuth2Authentication authentication) {

      User user = (User) authentication.getPrincipal();
      Map<String, Object> info = new LinkedHashMap<String, Object>(
          accessToken.getAdditionalInformation());

      info.put("id", user.getId());

      DefaultOAuth2AccessToken customAccessToken = new DefaultOAuth2AccessToken(accessToken);
      customAccessToken.setAdditionalInformation(info);
      return super.enhance(customAccessToken, authentication);

    }
  }


}
