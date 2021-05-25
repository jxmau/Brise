package tech.weather.tools;


import tech.weather.settings.SettingsUnit;

public class WindTool {

    public static String getWindOrigin(Double windDirection) {
        if (windDirection > 337.5 || windDirection < 22.5) {
            return "North";
        } else if (windDirection >= 22.5 && windDirection < 67.5) {
            return "North-West";
        } else if (windDirection >= 65.5 && windDirection < 112.5) {
            return "West";
        } else if (windDirection >= 112.5 && windDirection < 157.5) {
            return "South-West";
        } else if (windDirection >= 157.5 && windDirection < 202.5) {
            return "South";
        } else if (windDirection >= 202.5 && windDirection < 247.5) {
            return "South-East";
        } else if (windDirection >= 247.5 && windDirection < 292.5) {
            return "East";
        } else if (windDirection >= 292.5 && windDirection < 337.5) {
            return "North-East";
        } else {
            return "There's no wind";
        }

    }

    public static String getWindOriginAbvWithSpeed(Double windDirection) {

        if (windDirection > 337.5 || windDirection < 22.5) {
            return windDirection + " N";
        } else if (windDirection >= 22.5 && windDirection < 67.5) {
            return windDirection + " NW";
        } else if (windDirection >= 65.5 && windDirection < 112.5) {
            return windDirection + " W";
        } else if (windDirection >= 112.5 && windDirection < 157.5) {
            return windDirection + " SW";
        } else if (windDirection >= 157.5 && windDirection < 202.5) {
            return windDirection + " S";
        } else if (windDirection >= 202.5 && windDirection < 247.5) {
            return windDirection + " SE";
        } else if (windDirection >= 247.5 && windDirection < 292.5) {
            return windDirection + " E";
        }  else {
            return windDirection + " NE";
        }

    }

    public static String getWindSpeed(Double windSpeed){
        return switch (SettingsUnit.getUnit("speed")){
            case "m/s" -> windSpeed + " m/s"; // scientific
            case "kph" -> Math.round((windSpeed * 3.6) * 100.0) / 100.0 + " kph"; // metric
            case "mph" -> Math.round((windSpeed * 2.237) * 100.0) / 100.0 + " mph"; // Imperial
            default -> "N/A";
        };
    }

}
