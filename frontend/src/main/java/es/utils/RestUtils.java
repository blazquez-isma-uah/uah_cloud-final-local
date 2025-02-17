package es.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

public class RestUtils {
    
    public static <T> T getResponseWithHeaders(RestTemplate restTemplate, String url, HttpMethod method, Class<T> responseType) {
        // Obtener la cabecera X-Version del request original
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String xVersion = request.getHeader("X-Version");
        // Crear las cabeceras HTTP y añadir X-Version si está presente
        HttpHeaders headers = new HttpHeaders();
        if (xVersion != null) {
            headers.set("X-Version", xVersion);
        } 
        // else {
        //     headers.set("X-Version", "v1");
        // }
        
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<T> response = restTemplate.exchange(url, method, entity, responseType);

        return response.getBody();
    }
}
