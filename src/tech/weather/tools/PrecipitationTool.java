package tech.weather.tools;

import tech.weather.settings.SettingsService;
import tech.weather.settings.SettingsUnit;

import java.util.Map;

public class PrecipitationTool {

    public static String getPrecipitation(Map<String, Map<String, Object>> jsonRepsonse){
        Map<String, Object> rainMap = jsonRepsonse.get("rain");
        Map<String, Object> snowMap = jsonRepsonse.get("snow");

        if (rainMap != null) {
            if (rainMap.get("1h") != null) {
                Double precipitation = Double.valueOf(rainMap.get("1h").toString());
                return "Rain in the last hour : " + getRainUnit(precipitation);
            } else if (rainMap.get("3h") != null) {
                Double precipitation = Double.valueOf(rainMap.get("3h").toString());
                return "Rain in the last 3 hours : " + getRainUnit(precipitation);
            }

        } else if (snowMap != null){
            if (snowMap.get("1h") != null){
                Double snowFall = Double.valueOf(snowMap.get("1h").toString());
                return "Snow falls in the last hour : " + getSnowUnit(snowFall);
            } else if (snowMap.get("3h") != null){
                Double snowFall = Double.valueOf(snowMap.get("1h").toString());
                return "Snow falls in the last 3 hours : " + getSnowUnit(snowFall);
            }
        }

        return "No precipitation in the last hour.";
    }

    public static String getRainUnit(Double rain){
        return switch(SettingsUnit.getUnit("precipitation")){
            case "imperial" -> Math.round((rain / 25.4) * 100.0) / 100.0 + " in";
            case "metric" -> Math.round(rain * 100.0) / 100.0 + " mm";
            case "scientific" -> Math.round(rain * 100.0) / 100.0 + " mm";
            default -> "N/A ";
        };
    }

    public static String getSnowUnit(Double snowFall){
        return switch (SettingsUnit.getUnit("precipitation")){
          case "imperial" -> Math.round((snowFall / 25.4) * 100.0) / 100.0 + " in";
          case "metric" -> snowFall + " mm";
          case "scientific" -> snowFall + " mm";
          default -> "N/A ";
        };
    }
}
