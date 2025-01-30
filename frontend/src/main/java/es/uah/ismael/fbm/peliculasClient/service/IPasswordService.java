package es.uah.ismael.fbm.peliculasClient.service;

public interface IPasswordService {
    String generarPassword(int length, boolean useUpper, boolean useNumbers, boolean useSpecials);
}