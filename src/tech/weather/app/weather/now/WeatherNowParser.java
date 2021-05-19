package tech.weather.app.weather.now;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import tech.weather.app.tools.GPSCoordParser;
import tech.weather.settings.SettingsFileController;
import tech.weather.settings.SettingsKey;
import tech.weather.settings.SettingsLocation;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class WeatherNowParser {


    // Fetch Json For city
    public static String fetchWeatherInformations(String city, String country, String state, String command) {
        if (country.equals("-s") || state.equals("-s")){
            command = "-s";
            country = "N/A";
            state = "N/A";
        }

        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet request = new HttpGet(urlAssemblerForWeather(city,country, state));

            HttpResponse response = httpClient.execute(request);
            String responseBody =  EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);

            JSONParser parser = new JSONParser();
            Map<String, Map<String, Object>> jsonResponse = (Map<String, Map<String, Object>>) parser.parse(responseBody);

            if (command.equals("-s")) {
                Map<String, String> coordinates = GPSCoordParser.jsonParserToGetCoordInfos(jsonResponse);
                SettingsLocation.saveLocation(city, country, state, coordinates.get("latitude"), coordinates.get("longitude"));
            }

            return generateBulletin(city, state, jsonResponse);

        } catch (IOException | ParseException e) {
            System.out.println("Coordinates couldn't be fetched.");
            return null;
        }



    }


    //Fetch weather informations for saved city
    public static String fetchWeatherInfosForSavedCity(){
        Map<String, String> coord = SettingsLocation.getLocation();
        String city = coord.get("city");
        String state = coord.get("state");

        if (city.equals("")){
            return """
                    You didn't save a city !
                    If you want to save a city, please add "-s"!
                    Example : weather now Washington US DC -s
                    """;
        }

        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet request = new HttpGet(urlAssemblerForWeather(city, coord.get("country"), state));

            HttpResponse response = httpClient.execute(request);
            String responseBody =  EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);

            JSONParser parser = new JSONParser();
            Map<String, Map<String, Object>> jsonResponse = (Map<String, Map<String, Object>>) parser.parse(responseBody);

            // Will make sure that there's no 404 error
            Map<String, Object> rawResponse = (Map<String, Object>) parser.parse(responseBody);
            if (!rawResponse.get("cod").toString().equals("200")){
                return "Sorry, the city couldn't be found.";
            }
            return generateBulletin(city, state, jsonResponse);

        } catch (IOException | ParseException e) {
            System.out.println("Coordinates couldn't be fetched.");
            return null;
        }


    }

    // Generate bulletin
    public static String generateBulletin(String city, String state, Map<String, Map<String, Object>> jsonResponse ){



        String localisation = cityParser(city);

        if (!state.equals("N/A")){
            localisation += ", " + state;
        }

        return WeatherNowAssembler.generateInformations(jsonResponse, localisation);

    }


    private static String urlAssemblerForWeather(String city, String country, String state){

        String localisationInfos = city;

        if (!state.equals("N/A")) {
            localisationInfos += "," + country + "," + state;
        } else if (!country.equals("N/A")){
            localisationInfos += "," + country;
        }

        return "https://api.openweathermap.org/data/2.5/weather?q=" + localisationInfos
                + "&appid=" + SettingsKey.getOpenWeatherMapKey();

    }

    // Will replace "+" with space
    private static String cityParser(String city){
        return city.replace("+", " ");
    }



}
