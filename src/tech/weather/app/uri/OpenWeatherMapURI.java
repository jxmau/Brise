package tech.weather.app.uri;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import tech.weather.settings.SettingsKey;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class OpenWeatherMapURI {

    private static String getLocation(String city, String country, String state){
        if (!state.equals("N/A")){
            return city + "," + country + "," + state;
        } else if (!country.equals("N/A")){
            return city + "," + country;
        } else {
            return city;
        }
    }

    public static Map<String, Map<String, Object>> currentWeatherForLocation(String city, String country, String state){
        try {

            String location = getLocation(city, country, state);
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet request = new HttpGet(getCurrentWeatherURI(location));

            HttpResponse response = httpClient.execute(request);
            String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);

            JSONParser parser = new JSONParser();
            return (Map<String, Map<String, Object>>) parser.parse(responseBody);
        } catch (IOException | ParseException e){
            throw new IllegalStateException("Fatal Error.");
        }
    }

    private static String getCurrentWeatherURI(String location){
        return "https://api.openweathermap.org/data/2.5/weather?q=" + location
                + "&appid=" + SettingsKey.getOpenWeatherMapKey();
    }

    public static Map<String, Map<String, Object>> forecastFiveDays(String city, String country, String state){

        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet request = new HttpGet(getForecastFiveDaysURI(getLocation(city, country, state)));
            HttpResponse response = httpClient.execute(request);
            String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);

            JSONParser parser = new JSONParser();
            return (Map<String, Map<String, Object>>) parser.parse(responseBody);

        } catch (IOException | ParseException e) {
            e.printStackTrace();
            throw new IllegalStateException();
        }

    }

    private static String getForecastFiveDaysURI(String location){
        return "https://api.openweathermap.org/data/2.5/forecast?q=" + location +
                "&appid=" + SettingsKey.getOpenWeatherMapKey();
    }

}
