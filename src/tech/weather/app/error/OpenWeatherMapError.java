package tech.weather.app.error;

public class OpenWeatherMapError {

    public static String checkCode(String cod){
        return switch (cod){
            case "404" -> """
                    Sorry, OpenWeatherMap couldn't find your location.
                    Please, try again.
                    If your city have a composed name, try replacing the space with a '+' 
                    
                    """;

            case "401" -> """
                    An issue has occurred regarding your OpenWeatherMap key.
                    Please, try to use another one.
                    To change your key from the Main shell, use the command key:
                    key [your key]
                    
                    """;

            case "429"-> """
                    Sorry, you've made too many request in a short lapse of time.
                    Wait a bit before trying again.
                    
                    """;

            default -> """
                    An error has occurred between Brise and OpenWeatherMap.
                    If you can replicate this error, please create an issue at https://www.github.com/jxmau/Brise .
                    
                    """;
        };
    }
}
