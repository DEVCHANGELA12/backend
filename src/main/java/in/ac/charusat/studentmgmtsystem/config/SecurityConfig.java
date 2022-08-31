package in.ac.charusat.studentmgmtsystem.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.jdbcAuthentication().dataSource(dataSource)
//                .withDefaultSchema()
//                .withUser(
//                        User.withUsername("admin")
//                                .password("admin")
//                                .roles("ADMIN")
//                )
//                .withUser(
//                        User.withUsername("user")
//                                .password("user")
//                                .roles("USER")
//                );
       auth.inMemoryAuthentication()
               .withUser("admin")
               .password("admin")
               .roles("ADMIN")
               .and()
               .withUser("user")
               .password("user")
               .roles("USER");
    }

    //    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/login").permitAll()
//                .anyRequest().authenticated().and()
//                .formLogin();
//        http.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
        http.authorizeRequests()
                .antMatchers("/login").permitAll()
                .anyRequest().authenticated()
//                .and()
//                .formLogin()
        ;
        http.csrf().disable();
        http.headers().frameOptions().disable();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}
