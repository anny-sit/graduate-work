package ru.skypro.homework.config;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import ru.skypro.homework.dto.Role;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private static final String[] AUTH_WHITELIST = {
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v3/api-docs",
            "/webjars/**",
            "/login",
            "/register"
    };

    @Bean
    public JdbcUserDetailsManager userDetailsService(DataSource dataSource, PasswordEncoder passwordEncoder) {
        JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource);
        // Кастомные SQL-запросы для user_table
        manager.setUsersByUsernameQuery(
                "SELECT email, password, true AS enabled FROM user_table WHERE email = ?"
        );
        manager.setAuthoritiesByUsernameQuery(
                "SELECT email, role AS authority FROM user_table WHERE email = ?"
        );
        // Настройка создания/обновления пользователей (для /register)
        manager.setCreateUserSql(
                "INSERT INTO user_table (email, password, role, first_name, last_name, phone, image) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)"
        );
        manager.setUpdateUserSql(
                "UPDATE user_table SET password = ?, role = ? WHERE email = ?"
        );
        return manager;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(AUTH_WHITELIST).permitAll()
                        .requestMatchers("/users/me").authenticated() // Доступ для текущего пользователя
                        .requestMatchers("/ads/**", "/users/**").hasAnyRole(Role.USER.name(), Role.ADMIN.name())
                        .requestMatchers("/admin/**").hasRole(Role.ADMIN.name()) // Админ-эндпоинты
                        .anyRequest().authenticated()
                )
                .cors(cors -> cors.configurationSource(request ->
                        new org.springframework.web.cors.CorsConfiguration().applyPermitDefaultValues()))
                .httpBasic(httpBasic -> httpBasic.realmName("Ads API"));

        // Примечание: Для редактирования/удаления объявлений (/ads/{id}) и комментариев (/comments/{id})
        // добавьте проверки в контроллерах или сервисах:
        // - Проверяйте, что текущий пользователь (Principal.getName()) является автором (author_id).
        // - Для админов (ROLE_ADMIN) разрешайте доступ без проверки авторства.
        // Пример: В AdsController или AdsService для PUT/DELETE /ads/{id}
        // if (!user.getEmail().equals(ad.getAuthor().getEmail()) && !user.hasRole("ROLE_ADMIN")) {
        //     throw new AccessDeniedException("Only the author or admin can modify this ad");
        // }
        // Аналогично для CommentController/CommentService.

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}