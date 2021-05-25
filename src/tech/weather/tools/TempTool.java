package tech.weather.tools;

import tech.weather.settings.SettingsUnit;

public class TempTool {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";

    // Temp is received in Kelvin by default
    public static String getTemp(Double temp){
        return switch (SettingsUnit.getUnit("temp")){
            case "celcius" -> getCelcius(temp); // metric
            case "farenheit" -> getFarenheit(temp); // imperial
            default -> temp + "°K"; // scientific / by default
        };
    }

    private static String getCelcius(Double tempK){
        Double temp = Math.round( (tempK - 273.16) * 10.0) / 10.0;
        if (temp <= 0) {
            return ANSI_BLUE + temp + "°C" + ANSI_RESET;
        } else if (temp >= 30.0) {
            return ANSI_RED + temp + "°C" + ANSI_RESET;
        } else if (temp >= 20.0) {
            return ANSI_YELLOW + temp + "°C" + ANSI_RESET;
        } else {
            return temp + "°C";
        }
    }

    private static String getFarenheit(Double tempK){
        Double temp = Math.round(((tempK - 273.15) * 9/5 + 32) * 10.0) / 10.0;
        if (temp <= 32) {
            return ANSI_BLUE + temp + "°F" + ANSI_RESET;
        } else if (temp >= 86.0) {
            return ANSI_RED + temp + "°F" + ANSI_RESET;
        } else if (temp >= 68.0) {
            return ANSI_YELLOW + temp + "°F" + ANSI_RESET;
        } else {
            return temp + "°F";
        }
    }
}
