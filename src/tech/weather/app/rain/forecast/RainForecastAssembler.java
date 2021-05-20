package tech.weather.app.rain.forecast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RainForecastAssembler {

    public static String getBulletin(List<Map<String, String>> forecastHarvested, String location, String period){

        Map<String, String> forecastFormated = getFormat(forecastHarvested);
        return """
                Hour          : %s
                Precipitation : %s
                Volume        : %s
                
                Precipitation forecast for %s for %s.
                """.formatted(forecastFormated.get("hour"),
                        forecastFormated.get("precipitation"),
                forecastFormated.get("volume"),
                        location, period);
    }

    private static Map<String, String> getFormat(List<Map<String, String>> forecastHarvested){
        String hour = "";
        String precipitation = "";
        String volume = "";
        for (Map forecast : forecastHarvested) {
            hour += String.format("%7s", forecast.get("hour"));
            precipitation += String.format("%7s", forecast.get("precipitation"));
            volume += String.format("%7s", forecast.get("volume"));
        }

        Map<String, String> forecastFormated = new HashMap<>();
        forecastFormated.put("hour", hour);
        forecastFormated.put("precipitation", precipitation);
        forecastFormated.put("volume", volume);
        return forecastFormated;
    }
}
