package tech.weather.app.weather.now;


import tech.weather.tools.PrecipitationTool;
import tech.weather.tools.TempTool;
import tech.weather.tools.WindTool;

import java.util.List;
import java.util.Map;

public class WeatherNowAssembler {


    public static String generateInformations(Map<String, Map<String, Object>> jsonResponse, String localisation) {


        Map<String, Object> airInfos = jsonResponse.get("main");
        Map<String, Object> windInfos = jsonResponse.get("wind");
        List<Map<String, Object>> weatherList = (List<Map<String, Object>>) jsonResponse.get("weather");
        Map<String, Object> weatherInfos = weatherList.get(0);


        return """
                > Current Weather Condition : %s
                > Air Condition :
                    Temperature : %s    | Humidity : %s
                    Atmospheric Pressure : %s hPa
                > Wind Condition :
                    Speed : %s  | Degree : %s
                > Precipitations :
                    %s
                    
                Current condition for %s
                """.formatted(weatherInfos.get("description"),
                TempTool.getTemp(Double.valueOf(airInfos.get("feels_like").toString())), airInfos.get("humidity"),
                airInfos.get("pressure"),
                WindTool.getWindSpeed(Double.valueOf(windInfos.get("speed").toString())),
                WindTool.getWindOriginAbvWithSpeed(Double.valueOf(windInfos.get("deg").toString())),
                PrecipitationTool.getPrecipitation(jsonResponse),
                localisation);
    }


}
