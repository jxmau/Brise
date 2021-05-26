package tech.weather.settings;

import java.io.File;
import java.util.Scanner;

public class SettingsExceptionHandler {

    protected static void parsingHandler(){
        System.out.println("Brise wasn't able to read correctly the settings file.");
        new File(SettingsFileController.getSettingsFileName()).delete();
        System.out.println("Settings file has been deleted - Recreating a settings file.");
        SettingsFileController.createSettingsFile();
        System.out.println("Settings file has been correctly created.");
        System.out.print("Please, enter the OpenWeatherMap AppId key : ");
        Scanner scan = new Scanner(System.in);
        SettingsKey.modifyOpeanWeatherMapKey(scan.nextLine());
        System.out.println("Brise is ready!");
    }
}
