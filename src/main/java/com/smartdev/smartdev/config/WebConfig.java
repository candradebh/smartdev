package com.smartdev.smartdev.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Value("${github.token}")
    private String githubToken;

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                // Permitir CORS para todos os endpoints (/**) e de um domínio específico
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:9500") // Domínio permitido
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Métodos HTTP permitidos
                        .allowedHeaders("Content-Type", "Authorization") // Cabeçalhos permitidos
                        .allowCredentials(true); // Permitir envio de cookies
            }
        };
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public RestTemplate restTemplateGithub() {
        RestTemplate restTemplate = new RestTemplate();
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(new ClientHttpRequestInterceptor() {
            @Override
            public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
                request.getHeaders().add("Authorization", "token " + githubToken);
                request.getHeaders().add("Accept", "application/vnd.github.v3+json");
                return execution.execute(request, body);
            }
        });
        restTemplate.setInterceptors(interceptors);
        return restTemplate;
    }

}
