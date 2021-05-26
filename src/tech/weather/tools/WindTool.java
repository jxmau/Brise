package tech.weather.tools;


import tech.weather.settings.SettingsUnit;

public class WindTool {

    private static boolean isInBetween(Double lowLimit, Double windDirection, Double highLimit){
        return windDirection >= lowLimit && windDirection < highLimit;
    }

    public static String getWindOrigin(Double windDirection) {
        if (isInBetween(337.5, windDirection, 360.1) || isInBetween(0.0, windDirection, 22.5)) {
            return "North";
        } else if (isInBetween(22.5, windDirection, 67.5)) {
            return "North-West";
        } else if (isInBetween(65.5, windDirection, 112.5)) {
            return "West";
        } else if (isInBetween(112.5, windDirection, 157.5)) {
            return "South-West";
        } else if (isInBetween(157.5, windDirection, 202.5)) {
            return "South";
        } else if (isInBetween(202.5, windDirection, 247.5)) {
            return "South-East";
        } else if (isInBetween(247.5, windDirection, 292.5)) {
            return "East";
        } else if (isInBetween(292.5, windDirection, 337.5)) {
            return "North-East";
        } else {
            return "There's no wind";
        }

    }

    public static String getWindOriginAbvWithSpeed(Double windDirection) {

        if (windDirection > 337.5 || windDirection < 22.5) {
            return windDirection + " N";
        } else if (isInBetween(22.5, windDirection, 67.5)) {
            return windDirection + " NW";
        } else if (isInBetween(65.5, windDirection, 112.5)) {
            return windDirection + " W";
        } else if (isInBetween(112.5, windDirection, 157.5)) {
            return windDirection + " SW";
        } else if (isInBetween(157.5, windDirection, 202.5)) {
            return windDirection + " S";
        } else if (isInBetween(202.5, windDirection, 247.5)) {
            return windDirection + " SE";
        } else if (isInBetween(247.5, windDirection, 292.5)) {
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
