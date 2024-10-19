package ProjectSpringBoot.Project.configuration;

import ProjectSpringBoot.Project.common.Roles;
import ProjectSpringBoot.Project.utils.AppConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public AuthenticationManager authenticationManager(AuthUserDetailsService userDetailsService) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        return new ProviderManager(authenticationProvider);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers(HttpMethod.POST, AppConstants.API_ADMIN + AppConstants.CREATE_BANKER).hasRole(Roles.ADMIN.name())
                                .requestMatchers(HttpMethod.PUT, AppConstants.API_ADMIN + AppConstants.UPDATE_BANKER).hasRole(Roles.ADMIN.name())
                                .requestMatchers(HttpMethod.DELETE, AppConstants.API_ADMIN + AppConstants.DELETE_BANKER).hasRole(Roles.ADMIN.name())
                                .requestMatchers(HttpMethod.GET, AppConstants.API_BANKER + AppConstants.BANK_ACCOUNTS_GET).hasRole(Roles.BANKER.name())
                                .requestMatchers(HttpMethod.GET, AppConstants.API_BANKER + AppConstants.GET_REQUESTS).hasRole(Roles.BANKER.name())
                                .requestMatchers(HttpMethod.GET, AppConstants.API_BANKER + AppConstants.GET_TRANSACTIONS).hasRole(Roles.BANKER.name())
                                .requestMatchers(HttpMethod.PUT, AppConstants.API_BANKER + AppConstants.UPDATE_REQUESTS).hasRole(Roles.BANKER.name())
                                .requestMatchers(HttpMethod.POST, AppConstants.API_BANKER + AppConstants.CLIENT_CREATE).hasRole(Roles.BANKER.name())
                                .requestMatchers(HttpMethod.DELETE, AppConstants.API_BANKER + AppConstants.DELETE_CLIENT).hasRole(Roles.BANKER.name())
                                .requestMatchers(HttpMethod.POST, AppConstants.API_CLIENT + AppConstants.CLIENT_CARD_CREATE).hasRole(Roles.CLIENT.name())
                                .requestMatchers(HttpMethod.POST, AppConstants.API_CLIENT + AppConstants.BANK_ACCOUNT_CREATE).hasRole(Roles.CLIENT.name())
                                .requestMatchers(HttpMethod.POST, AppConstants.API_CLIENT + AppConstants.CREATE_REQUEST).hasRole(Roles.CLIENT.name())
                                .requestMatchers(HttpMethod.POST, AppConstants.API_CLIENT + AppConstants.GET_TRANSACTION).hasRole(Roles.CLIENT.name())
                                .requestMatchers(HttpMethod.GET, AppConstants.API_CLIENT + AppConstants.BANK_ACCOUNT_GET).hasRole(Roles.CLIENT.name())
                                .requestMatchers(HttpMethod.GET, AppConstants.API_CLIENT + AppConstants.CARDS_GET).hasRole(Roles.CLIENT.name())
                                .requestMatchers(HttpMethod.GET, AppConstants.API_CLIENT + AppConstants.GET_TRANSACTION).hasRole(Roles.CLIENT.name())
                                .anyRequest().authenticated()
                )
                .httpBasic();

        return http.build();
    }
}
