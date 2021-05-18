package tech.weather.settings;

import java.util.Map;
import java.util.Scanner;

public class SettingsController {

    public static String enterSettingsShell(){

        Scanner scanner = new Scanner(System.in);
        String cli = "N/A";

        while (!cli.equals("exit")){
            System.out.println(clearScreen() + printSettings());
            System.out.print("Brise - Settings > ");
            cli = scanner.nextLine();
            parser(cli);
        }
        return "Settings's shell exited.";
    }

    private static String printSettings(){
        Map<String, String> settings = SettingsService.getAllSettings();
        return """
                    > City Saved :
                City : %s  | Country : %s  | State : %s
                Latitute : %s | Longitude : %s
                
                    > Key Saved :
                OpenWeatherMap : %s
                
                    > Preference :
                Unit : %s
                """.formatted(settings.get("city"), settings.get("country"), settings.get("state"),
                settings.get("latitude"), settings.get("longitude"),
                settings.get("appId"),
                settings.get("unit"));
    }

    private static void parser(String cli){

        switch (cli) {
            case "help" :
                System.out.println("""
                        key   -> To modify your OWM key
                        unit  -> To mofidy your Unit Preference
                        exit  -> Will exit the shell
                        """);
            case "key" : modifyKey();
            case "unit" : modifyUnit();
            case "exit" : break;
            default:
                System.out.println("Invalid command - Type help if you're lost.");
        }

    }

    // Modification of Unit Preference
    private static void modifyUnit(){
        System.out.println("""
                imperial   -> Speed : mph | Temperature : °F
                metric     -> Speed : kph | Temperature : °C
                scientific -> Speed : m/s | Temperature : °K
                """);
        System.out.print("Make a choice : ");
        Scanner scan = new Scanner(System.in);
        String unit = scan.nextLine();
        while (!unit.equals("metric") && !unit.equals("imperial") && !unit.equals("scientific") && !unit.equals("exit")){
            System.out.println("""
                    Invalid choice. Available choices are 'imperial', 'metric' and 'scientific'.
                    Type 'exit' to return.""");
            System.out.print("Brise - Settings - Unit > ");
            unit = scan.nextLine();
        }

        if (unit.equals("exit")){
            System.out.println("Exiting the unit menu...");
        } else {
            SettingsService.modifityUnit(unit);
            System.out.println("Your Unit System preference has been modified to " + unit + "!");
        }
    }

    // Modification of your OPW's key
    private static void modifyKey(){
        System.out.print("Please, enter your new key : ");
        Scanner scan = new Scanner(System.in);
        SettingsFileController.modifyAppId(scan.nextLine());
        scan.close();
    }


    // Print ten empty lines to gain visibility
    private static String clearScreen(){
        return "\n\n\n\n\n\n\n\n"; // 7 * \n
    }


}
