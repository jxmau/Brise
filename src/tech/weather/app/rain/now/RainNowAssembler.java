package tech.weather.app.rain.now;

import tech.weather.tools.PrecipitationTool;

import java.util.Map;

// As there isn't much to parse,
// This layer is also a parser layer.
public class RainNowAssembler {

    public static String rainAssembler(Map<String, Map<String, Object>> weatherInformation, String location){

        if (weatherInformation.get("rain") != null){
            Map<String, Object> rain = weatherInformation.get("rain");
            if (rain.get("1h") != null){
                Double rainQty = Double.valueOf(rain.get("1h").toString());
                return "It has been raining for %s in the last hour for %s"
                        .formatted(PrecipitationTool.getRainUnit(rainQty), location);
            } else if (rain.get("3h") != null){
                Double rainQty = Double.valueOf(rain.get("3h").toString());
                return "It has been raining for %s in the last three hours for %s"
                        .formatted(PrecipitationTool.getRainUnit(rainQty), location);
            } else {
                return "Sorry, an issue as occurred.";
            }
        } else if (weatherInformation.get("snow") != null){
            Map<String, Object> snow = weatherInformation.get("snow");
            if (snow.get("1h") != null){
                Double rainQty = Double.valueOf(snow.get("1h").toString());
                return "It has been snowing for %s in the last hour for %s"
                        .formatted(PrecipitationTool.getRainUnit(rainQty), location);
            } else if (snow.get("3h") != null){
                Double rainQty = Double.valueOf(snow.get("3h").toString());
                return "It has been snowing for %s in the last three hours for %s"
                        .formatted(PrecipitationTool.getRainUnit(rainQty), location);
            } else {
                return "Sorry, an issue as occurred.";
            }
        } else {
            return "Currently, it is not raining, nor snowing, on " + location + "!";
        }

    }
}
