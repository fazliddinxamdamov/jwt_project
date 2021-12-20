package pdp.uz.jwtproject.config;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import static java.time.Duration.ofSeconds;

@Configuration
public class RestTemplateConfig {

    @Bean("restTemplate1")
    public RestTemplate restTemplate1(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.requestFactory(this::requestFactory)
                .setReadTimeout(ofSeconds(60))     // read timeout 1 minute
                .setConnectTimeout(ofSeconds(60))  // connect timeout 1 minute
                .build();
    }

    @Bean("restTemplate5")
    public RestTemplate restTemplate5(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.requestFactory(this::requestFactory)
                .setReadTimeout(ofSeconds(5 * 60))     // read timeout 1 minute
                .setConnectTimeout(ofSeconds(60))  // connect timeout 1 minute
                .build();
    }

    private HttpComponentsClientHttpRequestFactory requestFactory() {
        HttpsURLConnection.setDefaultHostnameVerifier((s, sslSession) -> true);
        TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        SSLContext sslContext = null;
        try {
            sslContext = org.apache.http.ssl.SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
            SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());
            CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();
            requestFactory.setHttpClient(httpClient);
            return requestFactory;
        } catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException e) {
            return requestFactory;
        }
    }
}
