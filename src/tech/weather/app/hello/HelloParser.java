package tech.weather.app.hello;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import tech.weather.settings.SettingsFileController;
import tech.weather.settings.SettingsKey;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class HelloParser {



    // Fetch Hello Informations
    public static String fetchHelloInformations(String city, String country, String state) throws IOException, ParseException {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(urlAssemblerForWeather(city, country, state));
        HttpResponse response = httpClient.execute(request);
        String responseBody =  EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
        JSONParser parser = new JSONParser();

        Map<String, Object> rawResponse = (Map<String, Object>) parser.parse(responseBody);
        if (!rawResponse.get("cod").toString().equals("200")){
            return "Sorry, the city couldn't be found.";
        }

        Map<String, Map<String, Object>> jsonResponse = (Map<String, Map<String, Object>>) parser.parse(responseBody);


        String localisation = city;

        if (!state.equals("N/A")) {
            localisation += ", " + state;
        }

        return HelloAssembler.generateInformations(jsonResponse, localisation);
    }

    private static String urlAssemblerForWeather(String city, String country, String state){
        if (!country.equals("N/A") && state.equals("N/A")){

            return "https://api.openweathermap.org/data/2.5/weather?q=" + city + "," + country
                    + "&appid=" + SettingsKey.getOpenWeatherMapKey();
        } else if (!country.equals("N/A") && !state.equals("N/A")){

            return "https://api.openweathermap.org/data/2.5/weather?q=" + city + "," + state
                    + "," + country + "&appid=" + SettingsKey.getOpenWeatherMapKey();
        } else {
            return "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + SettingsKey.getOpenWeatherMapKey();
        }
    }


}
