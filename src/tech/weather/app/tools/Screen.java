package tech.weather.app.tools;

import java.io.IOException;

public class Screen {



    public static void clear() {

        String os = System.getProperty("os.name");
        boolean problem = false;
        if (!problem){
            try {
                if (os.startsWith("Mac")) {
                    Runtime.getRuntime().exec("clear");
                } else if (os.startsWith("Win")){
                    Runtime.getRuntime().exec("cls");
                } else {
                    System.out.println("\n\n\n\n");
                }
            } catch (IOException e) {
                problem = true;
                clear();
            }
        } else {
            System.out.println("\n\n\n\n");
        }
    }
}
