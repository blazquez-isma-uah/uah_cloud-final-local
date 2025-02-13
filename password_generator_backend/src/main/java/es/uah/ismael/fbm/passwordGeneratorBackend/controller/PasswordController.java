package es.uah.ismael.fbm.passwordGeneratorBackend.controller;

import es.uah.ismael.fbm.passwordGeneratorBackend.utils.PasswordGenerator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/password")
public class PasswordController {
    
    @Value("${server.version}")
    private String serverVersion;

    @GetMapping("/generate")
    public String generatePassword(
            @RequestParam(defaultValue = "12") int length,
            @RequestParam(defaultValue = "true") boolean useUpper,
            @RequestParam(defaultValue = "true") boolean useNumbers,
            @RequestParam(defaultValue = "true") boolean useSpecials) {

        return serverVersion + "-" + PasswordGenerator.generatePassword(length, useUpper, useNumbers, useSpecials);
    }
}