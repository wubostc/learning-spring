package com.scs.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scs.common.resp.MyBizRespJson;
import com.scs.security.filter.LoginFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.HeaderWriterFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.web.filter.CharacterEncodingFilter;


@Configuration
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {


    /**
     * 全局默认加密方式
     */
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

    // 替换自动配置 UserDetailsServiceAutoConfiguration
//    @Bean
//    public UserDetailsService userDetailsService() {
//        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
//        inMemoryUserDetailsManager.createUser(
//                User.withUsername("aaa").password("{noop}123").roles("ADMIN", "SYS").build());
//        return inMemoryUserDetailsManager;
//    }

    private final MyUserDetailService myUserDetailService;

    @Autowired
    public WebSecurityConfigurer(MyUserDetailService myUserDetailService) {
        this.myUserDetailService = myUserDetailService;
    }

    // 自定义 AuthenticationManager
    @Override
    public void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(myUserDetailService);
    }


    // 提供自动注入能力，把自定义的 AuthenticationManager，暴露在bean工厂
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    // 自定义loginFilter
    @Bean
    public LoginFilter loginFilter() throws Exception {
        LoginFilter loginFilter = new LoginFilter();
        loginFilter.setUsernameParameter("uname");
        loginFilter.setPasswordParameter("passwd");
        loginFilter.setAuthenticationManager(authenticationManagerBean());
        loginFilter.setRequiresAuthenticationRequestMatcher(new OrRequestMatcher(
                new AntPathRequestMatcher("/doLogin", "POST")));

        // 验证登陆handler
        loginFilter.setAuthenticationSuccessHandler(new DefaultAuthenticationSuccessHandler());
        loginFilter.setAuthenticationFailureHandler(new DefaultAuthenticationFailureHandler());

        return loginFilter;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 开启认证，除了/index /login.html以外的都需要认证
        http.authorizeRequests()
                .mvcMatchers("/index", "/login.html", "/vc.jpg").permitAll()
                .anyRequest().authenticated();

        http.formLogin()
                .loginPage("/login.html")

                // 登陆成功后自动跳转
//                .defaultSuccessUrl("/index", true)
                // 处理登陆的接口
//                .loginProcessingUrl("/doLogin")
                // 自定义登陆成功后的handler
//                .successHandler(new DefaultAuthenticationSuccessHandler())
//                .failureHandler()
//                .failureUrl("/login.html") // 认证失败时 redirect
                .failureForwardUrl("/login.html") // 认证失败时 redirect
//                .failureHandler(new DefaultAuthenticationFailureHandler())

                // 登出逻辑
                .and()
                .logout()
//                .logoutUrl("/mylogout") // 登出接口
//                .logoutSuccessUrl("/login.html") // 登出成功后，跳转界面
                .logoutRequestMatcher(new OrRequestMatcher(
                        new AntPathRequestMatcher("/aa_logout", "GET"),
                        new AntPathRequestMatcher("/bb_logout", "GET")
                ))

                // 登出时，清除session 和 auth
                .invalidateHttpSession(true)
                .clearAuthentication(true)

                // 退出成功后handler
                .logoutSuccessHandler(new DefaultLogoutSuccessHandler())

                // 登入认证/授权异常处理
                .and()
                .exceptionHandling()
                .authenticationEntryPoint((req, resp, ex) -> {
                    resp.setStatus(HttpStatus.UNAUTHORIZED.value());
                    resp.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                    String s = new ObjectMapper().writeValueAsString(MyBizRespJson.fail("认证异常！"));
                    resp.getWriter().println(s);
                })

                // 禁用csrf
                .and()
                .csrf().disable();


        // loginFilter 替换原先的 UsernamePasswordAuthenticationFilter 过滤器
        http.addFilterAt(loginFilter(), UsernamePasswordAuthenticationFilter.class);

        // security filter proxy 添加一个 filter utf-8
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        http.addFilterBefore(filter, HeaderWriterFilter.class);
    }
}


