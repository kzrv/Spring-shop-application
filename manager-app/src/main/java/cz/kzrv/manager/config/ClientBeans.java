package cz.kzrv.manager.config;


import cz.kzrv.manager.client.ProductsRestClient;
import cz.kzrv.manager.client.ProductsRestClientImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.web.client.RestClient;

@Configuration
public class ClientBeans {

    @Bean
    public ProductsRestClientImpl productsRestClient(
            @Value("${selmag.services.catalogue.uri:http://localhost:8081}") String catalogueBaseUri,
            @Value("${selmag.services.catalogue.username}") String username,
            @Value("${selmag.services.catalogue.password}") String password) {
        return new ProductsRestClientImpl(RestClient.builder()
                .baseUrl(catalogueBaseUri)
                .requestInterceptor(
                        new BasicAuthenticationInterceptor(username,password))
                .build());
    }
}
