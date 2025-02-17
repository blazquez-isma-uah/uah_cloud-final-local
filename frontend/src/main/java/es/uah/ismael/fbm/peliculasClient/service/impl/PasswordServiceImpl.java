package es.uah.ismael.fbm.peliculasClient.service.impl;

import es.uah.ismael.fbm.peliculasClient.service.IPasswordService;
import es.utils.RestUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PasswordServiceImpl implements IPasswordService {

    @Autowired
    RestTemplate restTemplate;

    @Value("${api.password.generator.url}")
    private String passwordGeneratorUrl;

    @Override
    public String generarPassword(int length, boolean useUpper, boolean useNumbers, boolean useSpecials) {
        String url = String.format("%s/generate?length=%d&useUpper=%b&useNumbers=%b&useSpecials=%b",
        passwordGeneratorUrl, length, useUpper, useNumbers, useSpecials);
        return RestUtils.getResponseWithHeaders(restTemplate, url, HttpMethod.GET, String.class);
    }

}
