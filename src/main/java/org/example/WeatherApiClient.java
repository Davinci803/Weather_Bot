package org.example;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


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

        OkHttpClient jokeClient = new OkHttpClient();

        Request jokeRequest = new Request.Builder()
                .url("https://www.anekdot.ru/random/anekdot/")  // Используем другой источник русскоязычных анекдотов
                .get()
                .build();

        try (Response jokeResponse = jokeClient.newCall(jokeRequest).execute()) {
            if (jokeResponse.isSuccessful()) {
                String jokeHtml = jokeResponse.body().string();
                String joke = parseAnekdotRu(jokeHtml);
                System.out.println("Анекдот дня: " + joke);
            } else {
                System.out.println("Ошибка при получении анекдота: " + jokeResponse.code());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Здесь заканчивается код для получения анекдотов
    }

    private static String parseAnekdotRu(String html) {
        Document document = Jsoup.parse(html);
        Elements elements = document.select(".text");

        if (!elements.isEmpty()) {
            Element anekdotElement = elements.get(0);
            return anekdotElement.text();
        } else {
            return "Не удалось получить анекдот.";
        }
    }

}
