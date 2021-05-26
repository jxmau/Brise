package tech.weather.ressource.help;

public class Help {
    public static String generalHelp(){
        return """
                    Main commands :
        
                hello <city name>   -> Fetch quick weather information from a major city.
                
                weather now <city name> <country ISO code> <state ISO code>      -> Fetch complete current weather information.
                weather today <city name> <country ISO code> <state ISO code>    -> Fetch complete weather information for today.
                weather tomorrow <city name> <country ISO code> <state ISO code> -> Fetch complete weather information for tomorrow.
                
                air <city name> <country ISO code> <state ISO code>   -> Fetch current air quality information.
                
                rain now <city name> <country ISO code> <state ISO code>      -> Fetch complete current precipitation information.
                rain today <city name> <country ISO code> <state ISO code>    -> Fetch complete precipitation information for today.
                rain tomorrow <city name> <country ISO code> <state ISO code> -> Fetch complete precipitation information for tomorrow.
                
                    Hints :
                To save a city : Add "-s" at the end of the line. It doesn't work with the hello command.
                If your city has a multiple words name, replace the space by "+".
                
                    Help commands :
                
                air -limits -> Will show you the limit and the colour code.
                air -pollutants -> Will show you the names of the pollutants listed.
                """;
    }

    public static String limitsHelp(){
        return """
        LIMITS : For more information, please read the LIMITS.MD on Brise's repository.
        
            > Molecules :
        CO -> ORANGE : for an exposure of 8 hours (10 ppm) - RED : for less than an hour of exposure (25 ppm) 
        NO -> N/A
        NO2 -> ORANGE : limit fo yearly exposure ( 0.053 ppm) - RED : for a single exposure of 8h over 3 yrs (0.1 ppm)
        O3 -> RED : limit of a single exposure of 8h over a year (0.070 ppm)
        SO2 -> ORANGE : for a single exposure over 1 hour for three years (0.075 ppm) - RED : for a single 3h exposure over a year (0.5 ppm)
        NH3 -> N/A
        
            > Particulates :
        PM 2.5 -> RED : for a single 24h exposure over a year (35 μg/m3) 
        PM 10 -> RED : for a single 24h exposure over a year (150 μg/m3) 
        
        The colour code shows only critical limits -
        For more information, please read the LIMITS.MD on Brise's repository.
        """;
    }

    public static String pollutantsHelp(){
        return """
                    > Molecules :
                CO   -> Carbon Monoxide     | NO   -> Nitric Oxide
                NO2  -> Nitrogen Dioxide    | O3   -> Ozone
                SO2  -> Sulfur Dioxide      | NH3  -> Ammonia
                
                    > Particulates :
                PM 2.5 & 10 -> The number corresponds to the diameter of the particulates.
                """;
    }

    public static String weatherhelp(){

        return """
                    > To get current weather conditions -
                weather now <city name> <country ISO code> <state ISO code>     
                    > To get today's conditions forecast - 
                weather today <city name> <country ISO code> <state ISO code>    
                    > To get tomorrow's conditions forecast -
                weather today <city name> <country ISO code> <state ISO code>
                
                <Hint>
                Add "-s" at the end of the command to save the location.
                """;

    }

    public static String rainHelp(){
        return """
                Will get precipitation information of a location (rain/snow)
                     > To get current precipitation information -
                rain now <city name> <country ISO code> <state ISO code>     
                    > To get today's precipitation information - 
                rain today <city name> <country ISO code> <state ISO code>    
                    > To get tomorrow's precipitation information -
                rain today <city name> <country ISO code> <state ISO code>
                
                <Hint>
                Add "-s" at the end of the command to save the location.
                """;
    }

    public static String unitHelp(){
        return """
                    > To update the Unit System :
                imperial   -> Speed : mph | Temperature : °F | Precipitation : in | Air Pollution : μgm3
                metric     -> Speed : kph | Temperature : °C | Precipitation : mm | Air Pollution : μgm3
                scientific -> Speed : m/s | Temperature : °K | Precipitation : mm | Air Pollution : ppm
                
                    > To update a single unit :
                temperature   -> celcius (°C) - farenheit (°F) - kelvin (°K)
                speed         -> kph (kilometers per hour) - mph (miles per hour) - ms (meters per second)
                precipitation -> metric (meter and millimeters) - imperial (foot and inches)
                air           -> μgm3 (micrograms per cube meters) - ppm (parts per million)
                """;
    }


}
