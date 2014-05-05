package demo;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@Configuration
@ComponentScan
@EnableGlobalMethodSecurity(securedEnabled = true)
@EnableAutoConfiguration
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Configuration
	public static class RepositoryRestMvnConfig extends RepositoryRestMvcConfiguration {

		@Override
		protected void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
			config.setBaseUri(URI.create("repo"));
		}
	}

	@Configuration
	static class AuthenticationSecurity extends GlobalAuthenticationConfigurerAdapter {

		@Override
		public void init(AuthenticationManagerBuilder auth) throws Exception {
			auth.inMemoryAuthentication()
					.withUser("hero").password("hero").roles("HERO", "USER").and()
					.withUser("user").password("user").roles("USER");
		}
	}

	@Bean
	HealthIndicator<Map<String,Object>> health() {
		return () -> {
			Map<String, Object> result = new HashMap<>();
			result.put("foo", "bar");
			result.put("counter", 23);
			return result;
		};
	}
}
