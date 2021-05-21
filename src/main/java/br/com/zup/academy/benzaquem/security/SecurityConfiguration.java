package br.com.zup.academy.benzaquem.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests(authorizeRequests ->{

                    try {
                        authorizeRequests
                                .antMatchers(HttpMethod.GET, "/actuator/**").hasAuthority("SCOPE_benzaquem-proposta-scope")
                                .antMatchers(HttpMethod.GET, "/propostas/**").hasAuthority("SCOPE_benzaquem-proposta-scope")
                                .antMatchers(HttpMethod.GET, "/biometrias/**").hasAuthority("SCOPE_benzaquem-proposta-scope")
                                .antMatchers(HttpMethod.GET, "/cartoes/**").hasAuthority("SCOPE_benzaquem-proposta-scope")
                                .antMatchers(HttpMethod.POST, "/cartoes/**").hasAuthority("SCOPE_benzaquem-proposta-scope")
                                .antMatchers(HttpMethod.POST, "/propostas/**").hasAuthority("SCOPE_benzaquem-proposta-scope")
                                .antMatchers(HttpMethod.POST, "/biometrias/**").hasAuthority("SCOPE_benzaquem-proposta-scope")
                                .antMatchers(HttpMethod.PUT, "/bloqueios/**").hasAuthority("SCOPE_benzaquem-proposta-scope")
                                .antMatchers(HttpMethod.PUT, "/avisos/**").hasAuthority("SCOPE_benzaquem-proposta-scope")
                                .anyRequest().authenticated().and().csrf().disable();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        )
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);


    }

}
