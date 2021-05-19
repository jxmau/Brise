package tech.weather.controller;

import org.json.simple.parser.ParseException;
import tech.weather.app.air.now.AirPollutionParser;
import tech.weather.app.hello.HelloParser;
import tech.weather.ressource.help.Help;
import tech.weather.app.weather.day.WeatherDayRequest;
import tech.weather.app.weather.now.WeatherNowParser;
import tech.weather.settings.SettingsController;
import tech.weather.settings.SettingsKey;

import java.io.IOException;
import java.util.*;

public class CLIController {


    public String test(String command) throws IOException, ParseException {
        String[] commands = command.split(" ");
        List<String> commandsParsed = new ArrayList<>();
        commandsParsed.addAll(Arrays.asList(commands));

        while (commandsParsed.size() < 6) {
            commandsParsed.add("N/A");
        }

        Map<String, String> commandLine = new HashMap<>();
        commandLine.put("command", commandsParsed.get(0));
        commandLine.put("selection", commandsParsed.get(1));
        commandLine.put("city", commandsParsed.get(2));
        commandLine.put("country", commandsParsed.get(3));
        commandLine.put("state", commandsParsed.get(4));
        commandLine.put("option", commandsParsed.get(5));

        return switch (commandLine.get("command")) {
            case "hello" -> helloController(commandLine);
            case "air" -> airPollutionController(commandLine);
            case "weather" -> weatherController(commandLine);
            case "help" -> Help.generalHelp();
            case "key" -> SettingsKey.modifyOpeanWeatherMapKey(commandLine.get("selection")); // The key will hosted in the second key (selection)
            case "settings" -> SettingsController.enterSettingsShell();
            case "exit" -> "Brise is closing.";
            default -> """
                    Invalid Command.
                    Type help for more informations.""";
        };

    }

    // As the hello method doesn't use selection command,
    // the localization information are shifted minus one index
    // Example : selection is city
    private String helloController(Map<String, String> commandLine) throws IOException, ParseException {

        return HelloParser
                .fetchHelloInformations(commandLine.get("selection"), commandLine.get("city"), commandLine.get("country"));
    }

    // As the hello method doesn't use selection command yet,
    // the localization information are shifted minus one index
    // Example : selection is city
    private String airPollutionController(Map<String, String> commandLine) {

        return switch (commandLine.get("selection")) {
            case "N/A" -> AirPollutionParser.fetchAirPollutionInfosForSavedCity();
            case "-limits" -> Help.limitsHelp();
            case "-pollutants" -> Help.pollutantsHelp();
            default -> AirPollutionParser.fetchAirPollutionInformations
                    (commandLine.get("selection"), commandLine.get("city"), commandLine.get("country"), commandLine.get("state"));
        };
    }

    private String weatherController(Map<String, String> commandLine) {

        if (commandLine.get("city").equals("N/A")) {
            return switch (commandLine.get("selection")) {
                case "now" -> WeatherNowParser.fetchWeatherInfosForSavedCity();
                case "today" -> WeatherDayRequest.weatherTodayForSavedCity();
                case "tomorrow" -> WeatherDayRequest.weatherTomorrowForSavedCity();
                default -> Help.weatherhelp();
            };
        }
        else {
                return switch (commandLine.get("selection")){
                    case "now" -> WeatherNowParser .fetchWeatherInformations(commandLine.get("city"),
                            commandLine.get("country"), commandLine.get("state"), commandLine.get("option"));
                    case "today" -> WeatherDayRequest.weatherTodayForCity(commandLine.get("city"),
                            commandLine.get("country"), commandLine.get("state"), commandLine.get("option"));
                    case "tomorrow" -> WeatherDayRequest.weatherTomorrowForCity(commandLine.get("city"),
                            commandLine.get("country"), commandLine.get("state"), commandLine.get("option"));
                    default -> Help.weatherhelp();
                };
        }

    }

}
