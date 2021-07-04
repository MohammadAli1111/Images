package main_Images.Security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = {"main_Images.*"})
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    //تشفير كلمة السر
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Qualifier("dataSource")
    @Autowired
    private DataSource dataSource;

    private String usersQuery="select email, password, active from user where email=?";
    private String rolesQuery="select email, role from user where email=?";

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().usersByUsernameQuery(usersQuery).authoritiesByUsernameQuery(rolesQuery)
                .dataSource(dataSource).passwordEncoder(bCryptPasswordEncoder);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/index").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/register").permitAll()
                .antMatchers("/Download").permitAll()
                .antMatchers("/Home").hasAnyAuthority("ADMIN","User")
                .antMatchers( "/AddImage/**").hasAnyAuthority("ADMIN","User")
                .antMatchers("/UpdateImage").hasAnyAuthority("ADMIN","User")
                .antMatchers("/Delete").hasAnyAuthority("ADMIN","User")
                .and().csrf().disable().formLogin()
                .loginPage("/login").failureUrl("/login?error=true")
                .defaultSuccessUrl("/Home")
                .usernameParameter("email")
                .passwordParameter("password")
                .and()
                .logout().logoutSuccessUrl("/index")
                .and().httpBasic();



    }

    @Override
    public void configure(WebSecurity web)  {
        web.ignoring().antMatchers("/resources/**","static/**"
                ,"static/assets/**","templates/**");
    }

}