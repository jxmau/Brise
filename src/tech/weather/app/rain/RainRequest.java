package tech.weather.app.rain;

import tech.weather.app.rain.forecast.RainForecastParser;
import tech.weather.app.rain.now.RainNowAssembler;
import tech.weather.app.uri.OpenWeatherMapURI;
import tech.weather.settings.SettingsLocation;

import java.util.Map;

public class RainRequest {

    public static String getHourlyPrecipitationForSavedCity(String day){
        Map<String, String> locationSaved = SettingsLocation.getLocation();


        String location = getLocation(locationSaved.get("city"), locationSaved.get("state"));
        return switch (day){
          case "now" -> RainNowAssembler.rainAssembler(
                  OpenWeatherMapURI.currentWeatherForLocation(
                          locationSaved.get("city"), locationSaved.get("country"), locationSaved.get("state")), location);
          case "today" -> RainForecastParser.parseResponse(OpenWeatherMapURI.forecastFiveDays(
                  locationSaved.get("city"), locationSaved.get("country"), locationSaved.get("state")), location, "today");
          case "tomorrow" -> RainForecastParser.parseResponse(OpenWeatherMapURI.forecastFiveDays(
                  locationSaved.get("city"), locationSaved.get("country"), locationSaved.get("state")), location, "tomorrow");
          default -> "default";
        };
    }

    public static String getHourlyPrecipitation(String city, String country, String state, String command, String day){

        if (country.equals("-s")){
            command = "-s";
            country = "N/A";
        } else if (state.equals("-s")){
            command = "-s";
            state = "N/A";
        }
        if (command.equals("-s")){
            SettingsLocation.saveLocationWithoutGPS(city, country, state);
        }

        String location = getLocation(city, state);

        return switch (day){
          case "now" -> RainNowAssembler.rainAssembler(OpenWeatherMapURI.currentWeatherForLocation(city, country, state), location);
          case "today" ->  RainForecastParser.parseResponse(OpenWeatherMapURI.forecastFiveDays(city, country, state), location, "today");
          case "tomorrow" -> RainForecastParser.parseResponse(OpenWeatherMapURI.forecastFiveDays(city, country, state), location, "tomorrow");
          default -> "Invalid command.";
        };
    }

    private static String getLocation(String city, String state){
        if (!state.equals("N/A")){
            return cityParser(city) + ", " + state;
        }
        return cityParser(city);
    }
    private static String cityParser(String city){
        return city.replace("+", " ");
    }
}
