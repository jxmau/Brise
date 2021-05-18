package tech.weather;

import org.json.simple.parser.ParseException;
import tech.weather.app.tools.Screen;
import tech.weather.controller.CLIController;
import tech.weather.ressource.Banner;
import tech.weather.settings.SettingsFileController;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException, ParseException {
        CLIController cliController = new CLIController();

        System.out.println(Banner.getBanner());

        SettingsFileController.verificationUponStart();
        String cli = "N/A";
        String prompt = " Brise > ";
        while (!cli.equals("exit")){

            Scanner scan = new Scanner(System.in);
            System.out.print(prompt);
            cli = scan.nextLine();
            Screen.clear();
            System.out.println(cliController.test(cli));

        }
    }

}
