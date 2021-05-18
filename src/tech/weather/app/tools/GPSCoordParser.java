package tech.weather.app.tools;

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
import java.util.HashMap;
import java.util.Map;

public class GPSCoordParser {

    public static Map<String, String> getCoordinates(String city, String country, String state) {

        Map<String, Map<String, Object>> jsonResponseForCoordinates = new HashMap<>();

        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet request = new HttpGet(urlAssembler(city, country, state));
            HttpResponse response = httpClient.execute(request);
            String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            JSONParser parser = new JSONParser();
            // Creation of the returned object
            Map<String, String> coordinates = new HashMap<>();

            // Parsing to get the status request
            Map<String, Object> rawResponse = (Map<String, Object>) parser.parse(responseBody);
            // If the status is not 200, it will be aborted
            if (!rawResponse.get("cod").toString().equals("200")){
                coordinates.put("cod",rawResponse.get("cod").toString());
                return coordinates;
            }
            // Prepare the Map for the parser layer
            jsonResponseForCoordinates = (Map<String, Map<String, Object>>) parser.parse(responseBody);
            // Create the new map with the coordinates
            coordinates = jsonParserToGetCoordInfos(jsonResponseForCoordinates);
            coordinates.put("cod", "200"); //
            return coordinates;

        } catch (IOException | ParseException e) {
            System.out.println("Coordinates couldn't be fetched.");
            return null;
        }

    }

    // Must remain public as the Weather Now API uses it when saving a city.
    public static Map<String, String> jsonParserToGetCoordInfos(Map<String, Map<String, Object>> jsonResponse) {
        Map<String, Object> coordinatesReceived = jsonResponse.get("coord");

        Map<String, String> coordinates = new HashMap<>();
        coordinates.put("longitude", coordinatesReceived.get("lon").toString());
        coordinates.put("latitude", coordinatesReceived.get("lat").toString());
        return coordinates;
    }

    private static String urlAssembler(String city, String country, String state) {

        String location = city;

        if (!state.equals("N/A")){
            location += "," + country + "," + state;
        } else if (!country.equals("N/A")){
            location += "," + country;
        }

        return "https://api.openweathermap.org/data/2.5/weather?q=" + location + "&appid=" + SettingsFileController.getAppId();

    }
}