package tech.weather.settings;

import tech.weather.app.tools.Screen;

import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

// Do not close Scanners!
// Closing secondary scanners
// Willer create a NoSuchElementException
// For the enterSettingsShell method's
// And Main's scanners.
public class SettingsController {

    public static String enterSettingsShell(){

        Scanner scanner = new Scanner(System.in);
        String cli = "N/A";

        while (!cli.equals("exit")){
            Screen.clear();
            System.out.println(printSettings());
            System.out.print("Brise - Settings > ");
            cli = scanner.nextLine();
            parser(cli);
        }
        return "Settings' shell exited.";
    }

    private static String printSettings(){
        Map<String, Map<String, String>> settings = SettingsFileController.getAllSettings();
        return """
                    > City Saved :
                City : %s  | Country : %s  | State : %s
                Latitude : %s | Longitude : %s
                
                    > Key Saved :
                OpenWeatherMap : %s
                
                    > Unit System : %s
                Temperature : %s    | Air : %s
                Speed : %s          | Precipitation : %s
                
                """.formatted(settings.get("location").get("city"), settings.get("location").get("country"),
                settings.get("location").get("state"),
                settings.get("location").get("latitude"), settings.get("location").get("longitude"),
                settings.get("key").get("openWeatherMap"),
                settings.get("unit").get("system"),
                settings.get("unit").get("temp"), settings.get("unit").get("air"),
                settings.get("unit").get("speed"), settings.get("unit").get("precipitation"));
    }

    private static void parser(String cli){

        switch (cli) {
            case "help" :
                System.out.println("""
                        key   -> To modify your OWM key
                        unit  -> To mofidy your Unit Preference
                        exit  -> Will exit the shell
                        """);
                break;
            case "key" :
                modifyKey();
                break;
            case "unit" :
                modifyUnit();
                break;
            case "exit" :
                break;
            default:
                System.out.println("Invalid command - Type help if you're lost.");
        }

    }

    // Modification of Unit Preference
    private static void modifyUnit(){
        String unitHelpString = """
                    > For updating to Unit System altogether :
                imperial   -> Speed : mph | Temperature : °F
                metric     -> Speed : kph | Temperature : °C
                scientific -> Speed : m/s | Temperature : °K
                    > To updating a unit.
                temperature : celcius (°C) - farenheit (°F) - kelvin (°K)
                speed : kph (kilometers per hour) - mph (miles per hour) - ms (meters per second)
                precipitation : metric (meter and millimeters) - imperial (foot and inches) 
                """;
        System.out.println(unitHelpString);
        System.out.print("Make a choice : ");
        Scanner scan = new Scanner(System.in);
        String unit = scan.nextLine();
        String[] cli = unit.split(" ");
        // Use a token system in lieu of listing all valid keywords.
        Boolean correct = false;
        while (!correct){

            if (cli[0].equals("exit")) {
                correct = true;
            } else if (cli[0].equals("imperial") || cli[0].equals("metric") || cli[0].equals("scientific")){
                SettingsUnit.modifyUnitSystem(cli[0]);
                correct = true;
            } else if (cli[0].equals("temperature") &&
                    (cli[1].equals("celcius") || cli[1].equals("farenheit") || cli[1].equals("kelvin"))){
                SettingsUnit.modifyUnit(cli[0], cli[1]);
                correct = true;
            } else if (cli[0].equals("speed") &&
                    (cli[1].equals("kph") || cli[1].equals("mph") || cli[1].equals("ms"))){
                SettingsUnit.modifyUnit(cli[0], cli[1]);
                correct = true;
            } else if (cli[0].equals("precipitation") && (cli[1].equals("metric") || cli[1].equals("imperial"))){
                SettingsUnit.modifyUnit(cli[0], cli[1]);
                correct = true;
            }

            if (!correct){
                System.out.print(" Brise - Settings - Unit > ");
                String line = scan.nextLine();
                cli = line.split(" ");
            }

        }

    }

    // Modification of your OPW's key
    private static void modifyKey(){
        System.out.print("Please, enter your new key : ");
        Scanner scan = new Scanner(System.in);
        SettingsKey.modifyOpeanWeatherMapKey(scan.nextLine());

    }

}
