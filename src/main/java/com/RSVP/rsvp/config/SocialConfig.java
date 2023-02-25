package com.RSVP.rsvp.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.security.AuthenticationNameUserIdSource;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;

import javax.sql.DataSource;

@Configuration
@EnableSocial
@Import(PropertyPlaceholderConfig.class)
public class SocialConfig implements SocialConfigurer {

    @Autowired
    private DataSource dataSource;

    @Value("${twitter.consumer.key}")
    private String twitterKey;

    @Value("${twitter.consumer.secret}")
    private String twitterSecret;

    @Value("${facebook.app.id}")
    private String facebookKey;

    @Value("${facebook.app.secret}")
    private String facebookSecret;

    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer cfc, Environment env) {
        cfc.addConnectionFactory(new TwitterConnectionFactory(twitterKey, twitterSecret));
        cfc.addConnectionFactory(new FacebookConnectionFactory(facebookKey, facebookSecret));
    }

    @Bean
    @Override
    public UserIdSource getUserIdSource() {
        return new AuthenticationNameUserIdSource();
    }

    @Bean
    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator cfl) {
        return new JdbcUsersConnectionRepository(dataSource, cfl, Encryptors.noOpText());
    }

}
