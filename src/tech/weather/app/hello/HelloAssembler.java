package tech.weather.app.hello;

import tech.weather.tools.TempTool;
import tech.weather.tools.WindTool;


import java.util.List;
import java.util.Map;

public class HelloAssembler {


    public static String generateInformations(Map<String, Map<String, Object>> jsonResponse, String localisation) {
        Map<String, Object> airInfos = jsonResponse.get("main");
        Map<String, Object> windInfos = jsonResponse.get("wind");
        List<Map<String, Object>> weatherList = (List<Map<String, Object>>) jsonResponse.get("weather");
        Map<String, Object> weatherInfos = weatherList.get(0);

        return """
                Hello from %s!
                We have a %s.
                It's currently %s outside, with an humidity level of %s.
                The winds blows from the %s at %s.
                See you soon!
                """.formatted(localisation, weatherInfos.get("description"),
                TempTool.getTemp(Double.valueOf(airInfos.get("temp").toString())), airInfos.get("humidity").toString(),
                WindTool.getWindOrigin(Double.valueOf(windInfos.get("deg").toString())),
                WindTool.getWindSpeed(Double.valueOf(windInfos.get("speed").toString())));
    }

}
