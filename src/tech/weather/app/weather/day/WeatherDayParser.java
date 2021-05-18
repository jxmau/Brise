package tech.weather.app.weather.day;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeatherDayParser {


    // Will parse the json and create a map will today's weather informations
    public static String jsonParserForToday(Map<String, Map<String, Object>> jsonResponse, String location){
        // Parsing of the city to get the timezone information
        // and to take the list containing all forecasts' map
        Map<String, Object> cityInfos = (Map<String, Object>) jsonResponse.get("city");
        List<Map<String, Object>> forecastInfo = (List<Map<String, Object>>) jsonResponse.get("list");

        // Will iniate the map using the first Map in the list
        Map<String, Object> map = initizalizeMainInformations((Map<String, Object>) forecastInfo.get(0));

        // Scale the time zone information on the location
        Long timeZone = (Long) cityInfos.get("timezone");
        LocalDate TODAY = LocalDateTime.now(Clock.systemUTC()).plusSeconds(timeZone).toLocalDate();


        for (Map<String, Object> forecast : forecastInfo) {
            if (getForecastDate((Long) forecast.get("dt"), timeZone).isBefore(TODAY.plusDays(1))){
                actualizationMap(forecast, map);
            } else {
                break;
            }
        }
        return WeatherDayAssembler.generateBulletin(map, "Today", location);
    }

    // Will parse the json and create a map will today's weather informations
    public static String jsonParserForTomorrow(Map<String, Map<String, Object>> jsonResponse, String location){
        // Parsing of the city to get the timezone information
        // and to take the list containing all forecasts' map
        Map<String, Object> cityInfos = (Map<String, Object>) jsonResponse.get("city");
        List<Map<String, Object>> forecastInfo = (List<Map<String, Object>>) jsonResponse.get("list");

        // To avoid getting the information of today, and getting stuck with it,
        // the token system permit to generate the Map
        // when the script reach the first Map with tomorrow's information

        int token = 0;

        // Scale the time zone information on the location
        Long timeZone = (Long) cityInfos.get("timezone");
        LocalDate TODAY = LocalDateTime.now(Clock.systemUTC()).plusSeconds(timeZone).toLocalDate();

        Map<String, Object> tomorrowForecast = new HashMap<>();

        for (Map<String, Object> forecast : forecastInfo) {
            Long unixSeconds = (Long) forecast.get("dt");
            if (getForecastDate(unixSeconds, timeZone).isAfter(TODAY)
                    && getForecastDate(unixSeconds, timeZone).isBefore(TODAY.plusDays(2))){
                if (token ==  0){
                    tomorrowForecast = initizalizeMainInformations(forecast);
                    token++;
                }
                actualizationMap(forecast, tomorrowForecast);
            }
        }
        //return JsonDayParser.generateMapOfDay(tomorrowForecast).toString();
        return WeatherDayAssembler.generateBulletin(tomorrowForecast, "Tomorrow", location);
    }


    // dt corresponds to the unix time of the forecast
    private static LocalDate getForecastDate(Long dt, Long timeZone){
        return LocalDateTime.of(1970, 01, 01, 0, 0, 0)
                .plusSeconds(dt).plusSeconds(timeZone).toLocalDate();
    }

    private static LocalTime getForecastTime(Long dt, Long timeZone){
        return LocalDateTime.of(1970, 01, 01, 0, 0, 0)
                .plusSeconds(dt + timeZone).toLocalTime();
    }


    private static Map<String, Object> initizalizeMainInformations(Map<String, Object> mapReceived){
        Map<String, Object> main = (Map<String, Object>) mapReceived.get("main");
        Map<String, Object> wind = (Map<String, Object>) mapReceived.get("wind");
        Map<String, Object> map = new HashMap<>();
        map.put("tempMIN", main.get("temp"));
        map.put("tempMAX", main.get("temp"));
        map.put("humidityMIN", main.get("humidity"));
        map.put("humidityMAX", main.get("humidity"));
        map.put("pressureMIN", main.get("pressure"));
        map.put("pressureMAX", main.get("pressure"));
        map.put("windSpeedMIN", wind.get("speed"));
        map.put("windSpeedMAX", wind.get("speed"));
        return map;
    }

    private static void actualizationMap(Map<String, Object> mapReceived, Map<String, Object> map){
        Map<String, Object> main = (Map<String, Object>) mapReceived.get("main");
        Map<String, Object> wind = (Map<String, Object>) mapReceived.get("wind");

        // To avoid getting a cast error if the wind speed is an Integer.
        Double temp = Double.valueOf(main.get("temp").toString());

        if (temp < (Double) map.get("tempMIN")){
            map.put("tempMIN", temp);
        } else if (temp > (Double) map.get("tempMIN")){
            map.put("tempMAX",  temp);
        }

        if ((Long) main.get("humidity") < (Long) map.get("humidityMIN")){
            map.put("humidityMIN",  main.get("humidity"));
        } else if ((Long) main.get("humidity") > (Long) map.get("humidityMAX")){
            map.put("humidityMAX",  main.get("humidity"));
        }

        if ((Long) main.get("pressure") < (Long) map.get("pressureMIN")){
            map.put("pressureMIN",  main.get("pressure"));
        } else if ((Long) main.get("pressure") > (Long) map.get("pressureMAX")){
            map.put("pressureMAX",  main.get("pressure"));
        }

        // To avoid getting a cast error if the wind speed is an Integer.
        Double windSpeed = Double.valueOf(wind.get("speed").toString());

        if (windSpeed < (Double) map.get("windSpeedMIN")){
            map.put("windSpeedMIN",  windSpeed);
        } else if (windSpeed > (Double) map.get("windSpeedMAX")){
            map.put("windSpeedMAX", windSpeed);
        }

    }
}
