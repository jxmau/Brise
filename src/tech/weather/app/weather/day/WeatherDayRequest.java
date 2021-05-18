package tech.weather.app.weather.day;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import tech.weather.settings.SettingsFileController;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class WeatherDayRequest {

    //// TODAY REQUEST

    // Fetch saved informations
    public static String weatherTodayForSavedCity(){
        Map<String, String> location = SettingsFileController.getCoord();
        if (location.get("city").length() < 1){
            return """
                    You didn't save a city !
                    If you want to save a city, please add "-s"!
                    Example : weather today Washington US DC -s
                    """;
        }
        return generateBulletinForToday(location.get("city"), location.get("country"), location.get("state"), "N/A");
    }

    // Fetch Informations
    public static String weatherTodayForCity(String city, String country, String state, String command){
        if (country.equals("-s") || state.equals("-s")){
            command = "-s";
            country = "N/A";
            state = "N/A";
        }

        return generateBulletinForToday(city, country, state, command);
    }

    // Fetch Json Response
    private static String generateBulletinForToday(String city, String country, String state, String command){
        try {

            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet request = new HttpGet(urlAssembler(city, state, country));

            HttpResponse response = httpClient.execute(request);
            String responseBody =  EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);

            JSONParser parser = new JSONParser();

            Map<String, Object> rawResponse = (Map<String, Object>) parser.parse(responseBody);
            if (!rawResponse.get("cod").toString().equals("200")){
                return "Sorry, the city couldn't be found.";
            }

            Map<String, Map<String, Object>> jsonResponse = (Map<String, Map<String, Object>>) parser.parse(responseBody);

            if (command.equals("-s")) {
                Map<String, Object> cityMap = jsonResponse.get("city");
                Map<String, Object> coordinates = (Map<String, Object>) cityMap.get("coord");
                SettingsFileController.saveCoord(city, country, state,
                        coordinates.get("lat").toString(), coordinates.get("lon").toString());
            }

            String localization = cityParser(city);

            if (!state.equals("N/A")) {
                localization += ", " + state.toUpperCase();
            }

            return  WeatherDayParser.jsonParserForToday(jsonResponse, localization);
        } catch (IOException | ParseException e) {
            System.out.println("Coordinates couldn't be fetched.");
            return null;
        }

    }



    ////// TOMORROW REQUEST


    // Fetch saved informations
    public static String weatherTomorrowForSavedCity(){
        Map<String, String> location = SettingsFileController.getCoord();
        if (location.get("city").length() < 1){
            return """
                    You didn't save a city !
                    If you want to save a city, please add "-s"!
                    Example : weather today Washington US DC -s
                    """;
        }
        return generateBulletinForTomorrow(location.get("city"), location.get("country"), location.get("state"), "N/A");
    }

    // Fetch Informations
    public static String weatherTomorrowForCity(String city, String country, String state, String command){
        if (country.equals("-s") || state.equals("-s")){
            command = "-s";
            country = "N/A";
            state = "N/A";
        }


        return generateBulletinForTomorrow(city, country, state, command);
    }

    // Fetch Json Response
    private static String generateBulletinForTomorrow(String city, String country, String state, String command){

        try {

            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet request = new HttpGet(urlAssembler(city, state, country));

            HttpResponse response = httpClient.execute(request);
            String responseBody =  EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);

            JSONParser parser = new JSONParser();
            Map<String, Map<String, Object>> jsonResponse = (Map<String, Map<String, Object>>) parser.parse(responseBody);

            Map<String, Object> rawResponse = (Map<String, Object>) parser.parse(responseBody);
            if (!rawResponse.get("cod").toString().equals("200")){
                return "Sorry, the city couldn't be found.";
            }

            if (command.equals("-s")) {
                Map<String, Object> cityMap = jsonResponse.get("city");
                Map<String, Object> coordinates = (Map<String, Object>) cityMap.get("coord");
                SettingsFileController.saveCoord(city, country, state,
                        coordinates.get("lat").toString(), coordinates.get("lon").toString());
            }
            String localization = cityParser(city);

            if (!state.equals("N/A")) {
                localization += ", " + state.toUpperCase();
            }

            return WeatherDayParser.jsonParserForTomorrow(jsonResponse, localization);

        } catch (IOException | ParseException e) {
            System.out.println("Coordinates couldn't be fetched.");
            return null;
        }


    }

    private static String urlAssembler(String city, String state, String country){

        String localisation = city;

        if (!state.equals("N/A")){
            localisation += "," + country + "," + state;
        } else if (!country.equals("N/A")){
            localisation += "," + country;
        }

        return "https://api.openweathermap.org/data/2.5/forecast?q=" + localisation + "&appid=" + SettingsFileController.getAppId();

    }

    // Will replace "+" with space
    private static String cityParser(String city){
        return city.replace("+", " ");
    }

}

