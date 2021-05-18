package tech.weather.app.weather.day;

import tech.weather.app.tools.TempTool;
import tech.weather.app.tools.WindTool;

import java.util.Map;

public class WeatherDayAssembler {

    public static String generateBulletin(Map<String, Object> weatherInformations, String day, String location){

        return """
                > Air Condition :         
                    Temperature :   %s - %s
                    Humidity :  %s - %s 
                    Atmospheric Pressure : %s hPa - %s hPa
                > Wind Condition :
                    Speed : %s - %s
                    
                %s's Forecast for %s
                """

                .formatted(TempTool.getTemp((Double) weatherInformations.get("tempMIN")),
                TempTool.getTemp((Double) weatherInformations.get("tempMAX")),
                weatherInformations.get("humidityMIN"), weatherInformations.get("humidityMAX"),
                weatherInformations.get("pressureMIN"), weatherInformations.get("pressureMAX"),
                WindTool.getWindSpeed((Double) weatherInformations.get("windSpeedMIN")),
                WindTool.getWindSpeed((Double) weatherInformations.get("windSpeedMAX")),
                day, location);
    }

}
