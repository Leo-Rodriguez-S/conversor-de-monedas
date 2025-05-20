package com.leorodriguezs.conversordemonedas.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.leorodriguezs.conversordemonedas.modelos.Transaction;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import com.google.gson.reflect.TypeToken;

public class CurrencyApiClient {

    private final String transactionsFile = "transactions.json";
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    // Cargar las transacciones desde el archivo JSON
    private List<Transaction> loadTransactions() {
        File file = new File(transactionsFile);
        if (file.exists()) {
            try (FileReader reader = new FileReader(file)) {
                Type listType = new TypeToken<ArrayList<Transaction>>() {}.getType();
                List<Transaction> transactions = gson.fromJson(reader, listType);
                return transactions != null ? transactions : new ArrayList<>();
            } catch (IOException e) {
                System.err.println("Error reading transactions file: " + e.getMessage());
            }
        }
        return new ArrayList<>();
    }

    // Guardar una transacción en el archivo JSON
    private void saveTransaction(Transaction transaction) {
        List<Transaction> transactions = loadTransactions();
        transactions.add(transaction);
        try (FileWriter writer = new FileWriter(transactionsFile)) {
            gson.toJson(transactions, writer);
        } catch (IOException e) {
            System.err.println("Error writing to transactions file: " + e.getMessage());
        }
    }

    // Convertir las monedas usando la API
    public double convertCurrencies(String localCurrency, String destinationCurrency, double amount) {
        Dotenv dotenv = Dotenv.load();
        String apiKey = dotenv.get("API_KEY");
        String url = "https://v6.exchangerate-api.com/v6/" + apiKey + "/latest/" + localCurrency;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String json = response.body();
            CurrencyResponse currencyResponse = gson.fromJson(json, CurrencyResponse.class);

            // Aquí accedemos al método conversion_rates() para obtener las tasas de conversión
            Map<String, Double> rates = currencyResponse.conversion_rates();

            Double rate = rates.get(destinationCurrency);
            if (rate != null) {
                double converted = amount * rate;
                Transaction transaction = new Transaction(localCurrency, destinationCurrency, amount, converted);
                saveTransaction(transaction);
                return converted;
            } else {
                System.out.println("The destination currency couldn't be found.");
                return -1;
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Error connecting to the API: " + e.getMessage());
            return -1;
        }
    }

    // Obtener las monedas válidas disponibles para la conversión desde la API
    public Set<String> getValidCurrencies() {
        Dotenv dotenv = Dotenv.load();
        String apiKey = dotenv.get("API_KEY");
        // Usamos el endpoint de codes para obtener todas las monedas disponibles
        String url = "https://v6.exchangerate-api.com/v6/" + apiKey + "/codes";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String json = response.body();

            // Para el endpoint /codes, la respuesta es diferente
            CodesResponse codesResponse = gson.fromJson(json, CodesResponse.class);

            if (codesResponse.supported_codes() != null) {
                Set<String> currencies = new HashSet<>();
                // supported_codes es una lista de arrays donde cada array tiene [código, nombre]
                for (String[] currencyInfo : codesResponse.supported_codes()) {
                    currencies.add(currencyInfo[0]); // El código está en la posición 0
                }
                return currencies;
            } else {
                System.out.println("No currencies found in API response.");
                return new HashSet<>();
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Error connecting to the API: " + e.getMessage());
            return new HashSet<>();
        }
    }
}