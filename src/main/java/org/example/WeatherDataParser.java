package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class WeatherDataParser {
    public static void parseWeatherData(String city, String jsonData) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonData);
            JsonNode weatherList = rootNode.get("list");

            System.out.println("Погода для города " + city + ":");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDate today = LocalDate.now();

            for (JsonNode weatherItem : weatherList) {
                String dateTimeStr = weatherItem.get("dt_txt").asText();
                LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, formatter);

                if (dateTime.toLocalDate().isEqual(today)) {
                    JsonNode mainData = weatherItem.get("main");
                    JsonNode weatherData = weatherItem.get("weather");
                    JsonNode weather = weatherData.get(0);

                    double temperatureKelvin = mainData.get("temp").asDouble();
                    double temperatureCelsius = temperatureKelvin - 273.15;
                    String weatherDescription = weather.get("description").asText();
                    int pressure = mainData.get("pressure").asInt();

                    System.out.println("Дата и время: " + dateTimeStr);
                    System.out.println("Температура: " + (int)temperatureCelsius + "°C");
                    System.out.println("Погода: " + weatherDescription);
                    // System.out.println("Давление: " + pressure + " гПа");
                    System.out.println("---------------------------");
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
