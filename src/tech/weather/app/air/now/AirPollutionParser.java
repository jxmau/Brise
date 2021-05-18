package tech.weather.app.air.now;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import tech.weather.app.tools.GPSCoordParser;
import tech.weather.settings.SettingsFileController;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class AirPollutionParser {


    public static String fetchAirPollutionInfosForSavedCity(){
        Map<String, String> coord = SettingsFileController.getCoord();

        if (coord.get("latitude").length() < 1){
            return """
                    You didn't save a city !
                    If you want to save a city, please add "-s"!
                    Example : weather today Washington US DC -s
                    """;
        }

        String location = cityParser(coord.get("city"));
        if (!coord.get("state").equals("N/A")){
            location += ", " + coord.get("state");
        }

        return generateBulletin(coord, location);
    }

    // Fetch Air Informations
    public static String fetchAirPollutionInformations(String city, String country, String state, String command) {
        if (country.equals("-s") || state.equals("-s")){
            command = "-s";
            state = "N/A";
            country = "N/A";
        }
        // Fetch the coordinates of the city
        Map<String, String> coordinates = GPSCoordParser.getCoordinates(city, country, state);
        // Will make sure that there's no 404 error
        if (!coordinates.get("cod").equals("200")){
            return "Sorry, the city couldn't be found.";
        }
        // Save the city's location infos
        if (command.equals("-s")) {
            SettingsFileController.saveCoord(city, country, state, coordinates.get("latitude"), coordinates.get("longitude"));
        }

        String location = cityParser(city);
        if (!state.equals("N/A")){
            location += ", " + state;
        }

        return generateBulletin(coordinates, location);
    }


    private static String generateBulletin(Map<String, String> coordinates, String location){
        try {

            String appId = SettingsFileController.getAppId();
            if (appId.equals("Error")){
                return "An error has occurred.";
            }

            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet request = new HttpGet("http://api.openweathermap.org/data/2.5/air_pollution?lat=" + coordinates.get("latitude")
                    + "&lon=" + coordinates.get("longitude") + "&appid=" + appId);

            HttpResponse response = httpClient.execute(request);
            String responseBody =  EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);

            JSONParser parser = new JSONParser();
            Map<String, Map<String, Object>> jsonResponseForAirPollution = (Map<String, Map<String, Object>>) parser.parse(responseBody);
            return AirPollutionAssembler.generateInformations(jsonResponseForAirPollution, location);

        } catch (IOException | ParseException e) {
            System.out.println("Coordinates couldn't be fetched.");
            return null;
        }

    }

    private static String cityParser(String city){
        return city.replace("+", " ");
    }

}