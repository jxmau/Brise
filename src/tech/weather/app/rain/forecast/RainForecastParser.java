package tech.weather.app.rain.forecast;

import tech.weather.tools.PrecipitationTool;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RainForecastParser {

    public static String parseResponse(Map<String, Map<String, Object>> response, String location, String period){

        // Will check if the query was successful
        Object rawResponse = (Object) response;
        Map<String, Object> rawResponseMap = (Map<String, Object>) rawResponse;
        if (!rawResponseMap.get("cod").toString().equals("200")){
            return "Sorry, the city couldn't be found.";
        }

        Long timeZone = (Long) response.get("city").get("timezone");
        List<Map<String, Object>> forecastList = (List<Map<String, Object>>) response.get("list");
        LocalDate TOMORROW = LocalDateTime.now(Clock.systemUTC()).plusSeconds(timeZone).plusDays(1).toLocalDate();

        List<Map<String, String>> forecastHarvested = new ArrayList<>();
        for (Map forecast : forecastList){
            LocalDateTime localDateTime = getLocalTime((Long) forecast.get("dt"), timeZone);
            if (isInPeriod(period, localDateTime, timeZone)){

                Map<String, String> forecastHourly = new HashMap<>();
                forecastHourly.put("hour", String.format("%s", localDateTime.getHour()));

                if (forecast.get("rain") != null) {
                    forecastHourly.put("precipitation", "rain");
                    Map<String, Object> rain = (Map<String, Object>) forecast.get("rain");
                    if (rain.get("1h") != null){
                        forecastHourly.put("volume", PrecipitationTool.getRainVolume(Double.valueOf(rain.get("1h").toString())));
                    }
                    else if (rain.get("3h") != null){
                        forecastHourly.put("volume", PrecipitationTool.getRainVolume(Double.valueOf(rain.get("3h").toString())));
                    }
                }
                else if (forecast.get("snow") != null) {
                    forecastHourly.put("precipitation", "snow");
                    Map<String, Object> snow = (Map<String, Object>) forecast.get("snow");
                    if (snow.get("1h") != null){
                        forecastHourly.put("volume", PrecipitationTool.getRainVolume(Double.valueOf(snow.get("1h").toString())));
                    }
                    else if (snow.get("3h") != null){
                        forecastHourly.put("volume", PrecipitationTool.getRainVolume(Double.valueOf(snow.get("3h").toString())));
                    }
                } else {
                    forecastHourly.put("precipitation", "rain");
                    forecastHourly.put("volume", "0");
                }
                forecastHarvested.add(forecastHourly);
            }
        }
        return RainForecastAssembler.getBulletin(forecastHarvested, location, period) ;
    }

    private static LocalDateTime getLocalTime(Long dt, Long timeZone){
        return LocalDateTime.of(1970, 1, 1, 0, 0, 0).plusSeconds(dt+timeZone);
    }

    private static boolean isInPeriod(String period, LocalDateTime localDateTime, Long timeZone){
        LocalDate TOMORROW = LocalDateTime.now(Clock.systemUTC()).plusSeconds(timeZone).plusDays(1).toLocalDate();
        if (period.equals("today")){
            return localDateTime.toLocalDate().isBefore(TOMORROW);
        } else if (period.equals("tomorrow")){
            return localDateTime.toLocalDate().isEqual(TOMORROW);
        }
        return false;
    }

}
