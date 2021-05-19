package tech.weather.settings;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.Map;

import static tech.weather.settings.SettingsExceptionHandler.parsingHandler;

public class SettingsUnit {


    // Modify System Unit
    public static void modifyUnitSystem(String unitSystemChoosed){
        try {
            // Fetch the settings saved
            JSONParser parser = new JSONParser();
            JSONObject settings = (JSONObject) parser.parse(new FileReader("settings.json"));
            Map<String, String> unit = (Map<String, String>) settings.get("unit");

            switch (unitSystemChoosed){
                case "imperial" :
                    unit.put("system", "imperial");
                    unit.put("temp", "farenheit");
                    unit.put("speed", "mph");
                    unit.put("precipitation", "imperial");
                    break;
                case "metric" :
                    unit.put("system", "metric");
                    unit.put("temp", "celcius");
                    unit.put("speed", "kph");
                    unit.put("precipitation", "metric");
                    break;
                case "scientific" :
                    unit.put("system", "scientific");
                    unit.put("temp", "kelvin");
                    unit.put("speed", "ms");
                    unit.put("precipitation", "scientific");
                    break;
                default:
                    System.out.println("Error - Invalid Choice.");
            }
            settings.put("unit", unit);
            FileWriter file = new FileWriter("settings.json");
            file.write(settings.toJSONString());
            file.flush();
        } catch (NoSuchFileException e){
            SettingsFileController.createSettingsFile();
            modifyUnitSystem(unitSystemChoosed);
        } catch (IOException | ParseException e){
            parsingHandler(); // from SettingsExceptionHandler
            modifyUnitSystem(unitSystemChoosed);
        }
    }

    // Modify Unit
    protected static void modifyUnit(String unitSelected, String unitChosen){
        try {
            JSONParser parser = new JSONParser();
            JSONObject settings = (JSONObject) parser.parse(new FileReader("settings.json"));

            Map<String, String> unit = (Map<String, String>) settings.get("unit");
            unit.put(unitSelected, unitChosen);
            // Will change the unit system to personalized.
            if (!unit.get("system").equals("personalized")) {
                unit.put("system", "personalized");
            }

            settings.put("unit", unit);
            FileWriter writer = new FileWriter("settings.json");
            writer.write(settings.toJSONString());
            writer.flush();
        } catch (NoSuchFileException e){
            SettingsFileController.createSettingsFile();
            modifyUnit(unitSelected, unitChosen);
        } catch (IOException | ParseException e){
            SettingsExceptionHandler.parsingHandler();
        }
    }



    // Get Unit
    public static String getUnit(String unitNeeded){
        try {
            JSONParser parser = new JSONParser();
            JSONObject settings = (JSONObject) parser.parse(new FileReader("settings.json"));
            Map<String, String> unit = (Map<String, String>) settings.get("unit");
            String unitFetched = unit.get(unitNeeded);
            if (unitFetched.length() < 1){
                return switch (unitNeeded){
                    case "speed" -> "kph";
                    case "temp" -> "celcius";
                    case "air" -> "μgm3";
                    case "precipitation" -> "metric;";
                    default -> "N/A";
                };
            } else {
                return unitFetched;
            }
        } catch (FileNotFoundException e) {
            SettingsFileController.createSettingsFile();
            return getUnit(unitNeeded);
        } catch (IOException | ParseException e) {
            throw new IllegalStateException("There has been an issue");
        }
    }
}
