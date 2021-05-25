package tech.weather.app.air.now;

import tech.weather.settings.SettingsUnit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static tech.weather.tools.AirPollutionTool.*;

public class AirPollutionAssembler {




    public static String generateInformations(Map<String, Map<String, Object>> jsonResponse, String location) {
        List<Map<String, Object>> jsonResponseList = (List<Map<String, Object>>) jsonResponse.get("list");
        Map<String, Object> airQualityInfos = jsonResponseList.get(0);
        Map<String, Object> airPollutantsReceived = (Map<String, Object>) airQualityInfos.get("components");
        Map<String, Double> airPollutants = new HashMap<>();
        for (String key : airPollutantsReceived.keySet()) {
            airPollutants.put(key, Double.valueOf(airPollutantsReceived.get(key).toString()));
        }
        Map<String, Long> main = (Map<String, Long>) airQualityInfos.get("main");
        return """
                %s
                > Air Quality : %s
                
                Air Pollution Information for %s.
                (Colour code is for indication only. Type "air -limits" to know more about it.)
                """.formatted(airPollutantsInformations(airPollutants), airQualityCondition(main.get("aqi")), location);
    }

    // Generate the pollutants bulletin
    // Variables are received as Double, except when equals as zero, then they are Integer.
    private static String airPollutantsInformations(Map<String, Double> airPollutants) {
        return """
                 > Molecules :
                 CO  : %s | NO  : %s 
                 NO2 : %s | O3  : %s 
                 SO2 : %s | NH3 : %s 
                 > Particulates :
                 PM2.5 : %s  | PM10 : %s
                """.formatted(getCO(airPollutants.get("co")), getNO(airPollutants.get("no")),
                getNO2(airPollutants.get("no2")), getO3(airPollutants.get("o3")), getSO2(airPollutants.get("so2")),
                getNH3(airPollutants.get("nh3")), getPM2_5(airPollutants.get("pm2_5")), getPM10(airPollutants.get("pm10"))
                );

    }


}