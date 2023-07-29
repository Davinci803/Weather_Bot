package org.example;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WeatherApiClient {
    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/forecast";
    private static final String API_KEY = "5b296a9d736c80aa66f04a32efb2b607";

    public static void getWeather(String city) {
        OkHttpClient client = new OkHttpClient();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(BASE_URL).newBuilder();
        urlBuilder.addQueryParameter("q", city);
        urlBuilder.addQueryParameter("appid", API_KEY);

        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String jsonData = response.body().string();
                WeatherDataParser.parseWeatherData(city, jsonData); // Вызов метода из нового класса
            } else {
                System.out.println("Ошибка: " + response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}