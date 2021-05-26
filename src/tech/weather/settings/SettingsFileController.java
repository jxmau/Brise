package tech.weather.settings;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.file.NoSuchFileException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SettingsFileController {

    protected static String getSettingsFileName(){
        return System.getProperty("user.home") + File.separator + "Brise" + File.separator + "settings.json";
    }

    private static void createDirectory(){
        File directory = new File(System.getProperty("user.home") + File.separator + "Brise");
        directory.mkdir();
    }


    //Create Settings Json File
    public static void createSettingsFile(){
        // Creation of the location Map
        Map<String, String> location = new HashMap<>();
        location.put("city", "");
        location.put("country", "");
        location.put("state", "");
        location.put("latitude", "");
        location.put("longitude", "");
        // Creation of the key Map
        Map<String, String> key = new HashMap<>();
        key.put("openWeatherMap", "");
        // Creation of the Unit Settings File Map
        Map<String, String> unit = new HashMap<>();
        unit.put("system", "metric");
        unit.put("speed", "kph");
        unit.put("temp", "celcius");
        unit.put("precipitation", "metric");
        unit.put("air", "μgm3");
        JSONObject settings = new JSONObject();
        settings.put("location", location);
        settings.put("key", key);
        settings.put("unit", unit);
        //Write JSON file
        try (
                FileWriter file = new FileWriter(getSettingsFileName())) {
                file.write(settings.toJSONString());
                file.flush();
        } catch (IOException e) {
            // Will create the directory
            createDirectory();
            createSettingsFile();
        }
    }

    // Will verify the settings file upon software start
    public static void verificationUponStart(){
        try {
            JSONParser parser = new JSONParser();
            JSONObject settings = (JSONObject) parser.parse(new FileReader(getSettingsFileName()));
            Map<String, String> key = (Map<String, String>) settings.get("key");
            if (key.get("openWeatherMap").equals("")){
                System.out.println("No OpenWeatherMap appId Key has been saved.");
                System.out.print("Please, enter your key : ");
                Scanner scan = new Scanner(System.in);
                SettingsKey.modifyOpeanWeatherMapKey(scan.nextLine());
            }
            System.out.println("Brise is ready!");
        } catch (NoSuchFileException | NoSuchFieldError | FileNotFoundException e ){
            System.out.println("No settings file found.");
            createSettingsFile();
            System.out.println("Settings file has been created.");
            System.out.print("Please, enter the OpenWeatherMap AppId key : ");
            Scanner scan = new Scanner(System.in);
            SettingsKey.modifyOpeanWeatherMapKey(scan.nextLine());
            System.out.println("Brise is ready!");
        } catch (ParseException e) {
            SettingsExceptionHandler.parsingHandler();
        } catch (IOException e){
            System.out.println("Fatal Error.");
            System.exit(1);
        }
    }

    // Get All Settings
    public static Map<String, Map<String, String>> getAllSettings(){
        try {
            JSONParser parser = new JSONParser();
            return (Map<String, Map<String, String>>) parser.parse(new FileReader(getSettingsFileName()));
        } catch (FileNotFoundException e) {
            SettingsFileController.createSettingsFile();
            return getAllSettings();
        } catch (IOException | ParseException e){
            throw new IllegalStateException("There has been an issue");
        }
    }
}
