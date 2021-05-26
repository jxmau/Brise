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
import java.util.Scanner;

import static tech.weather.settings.SettingsFileController.createSettingsFile;
import static tech.weather.settings.SettingsFileController.getSettingsFileName;

public class SettingsKey {

    public static String getOpenWeatherMapKey(){
        try {
            JSONParser parser = new JSONParser();
            Map<String, Map<String, String>> settings = (Map<String, Map<String, String>>) parser.parse(new FileReader(getSettingsFileName()));
            Map<String, String> key = settings.get("key");
            String appId = key.get("openWeatherMap");
            if (appId.length() < 1){
                System.out.println("No appId saved. Please, enter an OpenWeatherMap appId Key. ");
                Scanner scanner = new Scanner(System.in);
                String newKey = scanner.nextLine();
                System.out.println("Thank you!\nIf it doesn't work, don't worry, you can always modify by using the 'key' command.\n" +
                        "Brise is now starting! \n");
                return modifyOpeanWeatherMapKey(newKey);
            } else {
                return appId;
            }

        } catch (FileNotFoundException | NoSuchFileException e) {
            createSettingsFile();
            return getOpenWeatherMapKey();
        } catch (IOException | ParseException e) {
            throw new IllegalStateException("An issue has occurred.");
        }
    }


    // Modify the AppId
    public static String modifyOpeanWeatherMapKey(String openWeatherMapKey){

        try {
            JSONParser parser = new JSONParser();
            JSONObject settings = (JSONObject) parser.parse(new FileReader(getSettingsFileName()));

            Map<String,String> key = (Map<String, String>) settings.get("key");
            key.put("openWeatherMap", openWeatherMapKey);

            settings.put("key", key);
            FileWriter file = new FileWriter(getSettingsFileName());
            file.write(settings.toJSONString());
            file.flush();
            return "Your key has been saved.";
        } catch (FileNotFoundException e) {
            createSettingsFile();
            return modifyOpeanWeatherMapKey(openWeatherMapKey);
        } catch (IOException | ParseException e) {
            throw new IllegalStateException("An issue as occurred.");
        }
    }
}
