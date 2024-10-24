package app.view.menu;

import java.util.Scanner;

import app.view.interfaces.Menu;
import util.IO;

public class MainMenu implements Menu {


    Scanner in = IO.getScanner();

    public MainMenu() {
    }

    @Override
    public int display() {
        int input = -1;

        System.out.println("\n\t\t+---------------------------------------------+");
        System.out.println("\t\t|                  MAIN MENU                  |");
        System.out.println("\t\t+---------------------------------------------+");
        System.out.println("\t\t|                                             |");
        System.out.println("\t\t|     1- Tournaments                          |");
        System.out.println("\t\t|     2- Teams                                |");
        System.out.println("\t\t|     3- Players                              |");
        System.out.println("\t\t|     4- Games                                |");
        System.out.println("\t\t|     5- Exit                                 |");
        System.out.println("\t\t|                                             |");
        System.out.println("\t\t+---------------------------------------------+");
        System.out.print("Pick your choice : ");

        try {
            input = in.nextInt();
            if (input < 1 || input > 5) {
                System.out.println("Please pick a choice between 1 and 5...");
                in.next();
            }
        } catch (Exception e) {
            System.out.println("Please pick a valid number...");
            in.next();
            in.next();
        }
        return input;
    }


    @Override
    public void back() {
        System.out.println("Press Enter to go back...");
        in.next();
    }
}
