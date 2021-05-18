package tech.weather.app.help;

public class Help {
    public static String generalHelp(){
        return """
                    Main commands :
        
                hello <city name>                                                -> Fetch quick weather information from a major city.
                weather now <city name> <country ISO code> <state ISO code>      -> Fetch complete current weather information.
                weather today <city name> <country ISO code> <state ISO code>    -> Fetch complete weather information for today.
                air <city name> <country ISO code> <state ISO code>             -> Fetch current air quality information.
                
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
        LIMITS : (According to WHO)
        
            > Molecules :
        CO -> RED : above 8 hours exposure (10310 μg/m3) - ORANGE : above 24 hours exposure (6874 μg/m3)
        NO -> N/A
        NO2 -> RED : above annual mean exposure (40 μg/m3)
        O3 -> RED : above annual mean exposure (100 μg/m3)
        SO2 -> RED : above 24 hours mean exposure (20 μg/m3)
        NH3 -> N/A
        
            > Particulates :
        PM 2.5 -> RED : above 24 hours mean exposure (10 μg/m3) - ORANGE : above annual mean exposure (25 μg/m3)
        PM 10 -> RED : above 24 hours mean exposure (20 μg/m3) - ORANGE : above annual mean exposure (50 μg/m3)
        """;
    }

    public static String pollutantsHelp(){
        return """
                    > Molecules :
                CO   -> Carbon Monoxyde     | NO   -> Nitric Oxyde
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
                Add "-s" at the end of the command to save the city.
                """;

    }
}
