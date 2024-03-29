package tech.weather.settings;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import tech.weather.app.uri.OpenWeatherMapURI;
import tech.weather.tools.GPSCoordParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import static tech.weather.settings.SettingsFileController.getSettingsFileName;

public class SettingsLocation {

    // Fetch the location information saved
    public static Map<String, String> getLocation(){
        try {
            JSONParser parser = new JSONParser();
            Map<String, Map<String, String>> settings =
                    (Map<String, Map<String, String>>) parser.parse(new FileReader(getSettingsFileName()));
            return settings.get("location");
        } catch (FileNotFoundException e) {
            SettingsFileController.createSettingsFile();
            SettingsKey.getOpenWeatherMapKey(); // When the script will understand that there's no AppId Key, the user will be asked to enter his.
            throw new IllegalStateException("Error - No Information Saved.");
        } catch (IOException | ParseException e) {
            throw new IllegalStateException("There has been an error");
        }
    }


    public static void saveLocationWithoutGPS(String city, String country, String state){
        Map<String, Map<String, Object>> responseBody = OpenWeatherMapURI.currentWeatherForLocation(city, country, state);
        Map<String, Object> coord = responseBody.get("coord");
        saveLocation(city, country, state, coord.get("lat").toString(), coord.get("lon").toString());
    }

    // Will save the location informations.
    public static void saveLocation(String city, String country, String state, String latitude, String longitude){
        try {
            JSONParser parser = new JSONParser();
            JSONObject settings = (JSONObject) parser.parse(new FileReader(getSettingsFileName()));
            Map<String, String> location = (Map<String, String>) settings.get("location");

            location.put("city", city);
            if (!country.equals("-s") && !country.equals("N/A")) {
                location.put("country", country);
            } else if (country.equals("N/A")){
                location.put("country", "N/A");
            }
            if (!state.equals("-s") && !state.equals("N/A")) {
                location.put("state", state);
            }else if (country.equals("N/A")){
                location.put("state", "N/A");
            }

            location.put("latitude", latitude);
            location.put("longitude", longitude);

            settings.put("location", location);
            FileWriter file = new FileWriter(getSettingsFileName());
            file.write(settings.toJSONString());
            file.flush();

        } catch (FileNotFoundException e) {
            SettingsFileController.createSettingsFile();
            SettingsKey.getOpenWeatherMapKey(); // When the script will understand that there's no AppId Key, the user will be asked to enter his.
            saveLocation(city, country, state, latitude, longitude);
        } catch (IOException | ParseException e) {
            System.out.println("There's been an issue.");
        }

    }
}
