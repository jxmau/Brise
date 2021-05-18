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


    //Create Settings Json File
    public static void createSettingsFile(){
        JSONObject settings = new JSONObject();
        settings.put("appId", "");
        settings.put("city", "");
        settings.put("country", "");
        settings.put("state", "");
        settings.put("latitude", "");
        settings.put("longitude", "");
        settings.put("unit", "metric");

        //Write JSON file
        try (FileWriter file = new FileWriter("settings.json")) {
            file.write(settings.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    // Fetch the coord informations
    public static Map<String, String> getCoord(){
        try {
            JSONParser parser = new JSONParser();
            Map<String, String> settings = (Map<String, String>) parser.parse(new FileReader("settings.json"));
            Map<String, String> coord = new HashMap<>();
            coord.put("city", settings.get("city"));
            coord.put("state", settings.get("state"));
            coord.put("country", settings.get("country"));
            coord.put("latitude", settings.get("latitude"));
            coord.put("longitude", settings.get("longitude"));


            return coord;
        } catch (FileNotFoundException e) {
            createSettingsFile();
            getAppId(); // When the script will understand that there's no AppId Key, the user will be asked to enter his.
            throw new IllegalStateException("Error - No Information Saved.");
        } catch (IOException | ParseException e) {
            throw new IllegalStateException("There has been an error");

        }
    }


    public static String getAppId(){
        try {
            JSONParser parser = new JSONParser();
            Map<String, String> settings = (Map<String, String>) parser.parse(new FileReader("settings.json"));
            String appId = settings.get("appId");
            if (appId.length() < 1){
                System.out.println("No appId saved. Please, enter an OpenWeatherMap appId Key. ");
                Scanner scanner = new Scanner(System.in);
                String newKey = scanner.nextLine();
                System.out.println("Thank you!\nIf it doesn't work, don't worry, you can always modify by using the 'key' command.\n" +
                        "Brise is now starting! \n");
                return modifyAppId(newKey);
            } else {
                return settings.get("appId");
            }

        } catch (FileNotFoundException | NoSuchFileException e) {
            createSettingsFile();
            return getAppId();
        } catch (IOException | ParseException e) {
            throw new IllegalStateException("An issue has occured.");
        }
    }


    // Modify the AppId
    public static String modifyAppId(String appId){
        JSONParser parser = new JSONParser();

        try {
            JSONObject settings = (JSONObject) parser.parse(new FileReader("settings.json"));
            settings.put("appId", appId);
            FileWriter file = new FileWriter("settings.json");
            file.write(settings.toJSONString());
            file.flush();
            return "Your key has been saved.";
        } catch (FileNotFoundException e) {
            createSettingsFile();
            System.out.println("Settings file has been created.");
            return modifyAppId(appId);
        } catch (IOException | ParseException e) {
            throw new IllegalStateException("An issue as occurred.");
        }
    }

    public static void saveCoord(String city, String country, String state, String latitude, String longitude){
        try {
            JSONParser parser = new JSONParser();
            JSONObject settings = (JSONObject) parser.parse(new FileReader("settings.json"));

            settings.put("city", city);
            if (!country.equals("-s") && !country.equals("N/A")) {
                settings.put("country", country);
            } else if (country.equals("N/A")){
                settings.put("country", "N/A");
            }
            if (!state.equals("-s") && !state.equals("N/A")) {
                settings.put("state", state);
            }else if (country.equals("N/A")){
                settings.put("state", "N/A");
            }
            settings.put("latitude", latitude);
            settings.put("longitude", longitude);

            FileWriter file = new FileWriter("settings.json");
            file.write(settings.toJSONString());
            file.flush();

        } catch (FileNotFoundException e) {
            createSettingsFile();
            getAppId(); // When the script will understand that there's no AppId Key, the user will be asked to enter his.
            saveCoord(city, country, state, latitude, longitude);
        } catch (IOException | ParseException e) {
            System.out.println("There's been an issue.");
        }

    }


    // Will verify the settings file upon software start
    public static void verificationUponStart(){
        try {
            JSONParser parser = new JSONParser();
            JSONObject settings = (JSONObject) parser.parse(new FileReader("settings.json"));
            if (settings.get("appId").equals("")){
                System.out.println("No OpenWeatherMap appId Key has been saved.");
                System.out.print("Please, enter your key : ");
                Scanner scan = new Scanner(System.in);
                modifyAppId(scan.nextLine());
            }
            System.out.println("Brise is ready!");
        } catch (NoSuchFileException | NoSuchFieldError | FileNotFoundException e ){
            System.out.println("No settings file found.");
            createSettingsFile();
            System.out.println("Settings file has been created.");
            System.out.print("Please, enter the OpenWeatherMap AppId key : ");
            Scanner scan = new Scanner(System.in);
            modifyAppId(scan.nextLine());
            System.out.println("Brise is ready!");
        } catch (ParseException e) {
            System.out.println("Brise wasn't able to read correctly the settings file.");
            new File("settings.json").delete();
            System.out.println("Settings file has been deleted - Recreating a settings file.");
            createSettingsFile();
            System.out.println("Settings file has been correctly created.");
            System.out.print("Please, enter the OpenWeatherMap AppId key : ");
            Scanner scan = new Scanner(System.in);
            modifyAppId(scan.nextLine());
            System.out.println("Brise is ready!");
        } catch (IOException e){
            System.out.println("Fatal Error.");
            System.exit(1);
        }
    }

}
