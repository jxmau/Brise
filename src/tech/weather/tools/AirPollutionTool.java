package tech.weather.tools;

import tech.weather.settings.SettingsUnit;

public class AirPollutionTool {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_YELLOW = "\u001B[33m";

    public static String airUnit = SettingsUnit.getUnit("air");

    // get Air Quality Condition
    public static String airQualityCondition(Long condition) {
        return switch (condition.toString()) {
            case "1" -> "Good";
            case "2" -> "Moderate to Good";
            case "3" -> "Moderate";
            case "4" -> "Bad";
            case "5" -> "Dangerous";
            default -> "Information not available";
        };

    }

    // Molecules Info
    // Colour code are enforced with the value converted to ppm.
    // To know more about the limits, please read the LIMITS.MD.

    // Get CO
    public static String getCO(Double co) {
        // To make sure that, if the user changes the air unit,
        // the modification is took in account in this Class.
        airUnit = SettingsUnit.getUnit("air");

        String colour = "";
        Double coPPM = getPPM(co, 28.01);
        if (coPPM >= 25) { // WHO exposure limit for 1 hour
            colour = ANSI_RED;
        } else if (coPPM >= 10) { // WHO exposure limit for 8 hours
            colour = ANSI_YELLOW;
        }

        if (airUnit.equals("ppm")){
            return colour + coPPM + ANSI_RESET + " " + getUnit();
        } else {
            return colour + co + ANSI_RESET + " " + getUnit();
        }
    }

    // Get NO - Limits not implemented -
    public static String getNO(Double no){
        Double noPPM = getPPM(no, 30.01);

        if (airUnit.equals("ppm")){
            return noPPM + " " + getUnit();
        } else {
            return no + " " + getUnit();
        }
    }


    // Get NO2
    public static String getNO2(Double no2) {
        Double no2PPM = getPPM(no2, 46.01);
        String colour = "";
        if (no2PPM >= 0.1){
            colour = ANSI_RED;
        } else if (no2PPM >= 0.053){
            colour = ANSI_YELLOW;
        }

        if (airUnit.equals("ppm")){
            return colour + no2PPM + ANSI_RESET + " " + getUnit();
        } else {
            return colour + no2 + ANSI_RESET + " " + getUnit();
        }

    }

    // Get O3
    public static String getO3(Double o3) {

        Double o3PPM = getPPM(o3, 48.00);

        String colour = "";
        if (o3PPM >= 0.070){
            colour = ANSI_RED;
        }

        if (airUnit.equals("ppm")){
            return colour + getPPM(o3, 48.00) + ANSI_RESET  + " " + getUnit();
        } else {
            return colour + o3 + ANSI_RESET  + " " + getUnit();
        }

    }

    // Get SO2
    public static String getSO2(Double so2) {
        Double so2PPM = getPPM(so2, 64.01);
        String colour = "";
        if (so2PPM >= 0.5){
            colour = ANSI_RED;
        } else if (so2PPM >= 0.075){
            colour = ANSI_YELLOW;
        }

        if (airUnit.equals("ppm")){
            return colour + so2PPM + ANSI_RESET  + " " + getUnit();
        } else {
            return colour + so2 + ANSI_RESET  + " " + getUnit();
        }

    }

    // Get NH3 - LIMITS are not imported -
    public static String getNH3(Double nh3){
        Double nh3PPM = getPPM(nh3, 34.063);
        String colour = "";

        if (airUnit.equals("ppm")){
            return colour + nh3PPM+ ANSI_RESET  + " " + getUnit();
        } else {
            return colour + nh3 + ANSI_RESET  + " " + getUnit();
        }

    }

    // Unlike the Molecules, Particulates' colour code are enforced without converted value.
    // Moreover, the unit cannot be changed.

    // Get PM2_5
    public static String getPM2_5(Double pm2_5) {

        String colour = "";
        if (pm2_5 >= 35){
            colour = ANSI_RED;
        }
            return colour + pm2_5 + ANSI_RESET  + " μg/m3";
    }

    //
    public static String getPM10(Double pm10) {

        String colour = "";
        if (pm10 >= 150){
            colour = ANSI_RED;
        }
            return colour + pm10 + ANSI_RESET  + " μg/m3";
    }

    // Will convert the value from µg.m^-3 to ppm.
    private static Double getPPM(Double quantity, Double molecularWeight){
        return Math.round((24.45 * (quantity / 1000) / molecularWeight) * 100.0) / 100.0;
    }

    private static String getUnit(){
        if (airUnit.equals("μgm3")){
            return "μg/m3";
        } else {
            return airUnit;
        }
    }
}
