package org.example.authproj.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
                        .disable()
                )
                .authorizeHttpRequests(authorize -> authorize
                        //Любой пользователь, даже незарегистрированный, может получить доступ к страницам входа и регистрации.
                        .requestMatchers("/login","/register").permitAll()
                        //Все остальные запросы требуют, чтобы пользователь был аутентифицирован (вошёл в систему).
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        //Указывает пользовательскую страницу для входа.
                        .loginPage("/login")
                        //Перенаправляет пользователя на главную страницу (/) после успешного входа.
                        //Параметр true означает, что перенаправление всегда будет идти на указанный URL, даже если пользователь изначально пытался получить доступ к другой странице.
                        .defaultSuccessUrl("/profile",true)
                        //Делает страницу входа доступной для всех.
                        .permitAll()
                )
                .logout(logout -> logout
                        //Указывает URL, который инициирует выход из системы.
                        .logoutUrl("/logout")
                        //Перенаправляет пользователя на страницу входа с параметром logout, чтобы можно было показать сообщение вроде "Вы успешно вышли из системы"
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );
        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
