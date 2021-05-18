package tech.weather.settings;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class SettingsService {

    public static String getUnit(){
        try {
            JSONParser parser = new JSONParser();
            Map<String, String> settingsMap = (Map<String, String>) parser.parse(new FileReader("settings.json"));

            String unit = settingsMap.get("unit");
            if (unit.length() < 1){
                return "metric"; // Metric is returned by default in case of a problem in the settings file.
            } else {
                return unit;
            }
        } catch (FileNotFoundException e) {
            SettingsFileController.createSettingsFile();
            return getUnit();
        } catch (IOException | ParseException e) {
            throw new IllegalStateException("There has been an issue");
        }
    }

    public static void modifityUnit(String unit){
        try {
            JSONParser parser = new JSONParser();
            JSONObject settings = (JSONObject) parser.parse(new FileReader("settings.json"));
            settings.put("unit", unit);
            FileWriter fileWriter = new FileWriter("settings.json");
            fileWriter.write(settings.toJSONString());
            fileWriter.flush();
        } catch (FileNotFoundException e){
            SettingsFileController.createSettingsFile();
            modifityUnit(unit);
        } catch (IOException | ParseException e){
            throw new IllegalStateException("There's been an issue");
        }
    }

    public static Map<String, String> getAllSettings(){
        try {
            JSONParser parser = new JSONParser();
            return (Map<String, String>) parser.parse(new FileReader("settings.json"));
        } catch (FileNotFoundException e) {
            SettingsFileController.createSettingsFile();
            return getAllSettings();
        } catch (IOException | ParseException e){
            throw new IllegalStateException("There has been an issue");
        }
    }
}
