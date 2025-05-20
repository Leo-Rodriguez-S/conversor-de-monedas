package com.leorodriguezs.conversordemonedas.api;

import java.util.Map;

// Record para la respuesta del endpoint /latest
public record CurrencyResponse(Map<String, Double> conversion_rates) {
    // Genera automáticamente el método conversion_rates()
}

// Record para la respuesta del endpoint /codes
record CodesResponse(String[][] supported_codes) {
    // Genera automáticamente el método supported_codes()
}