package app.view.menu;

import java.util.Scanner;

import app.view.interfaces.Menu;
import app.util.IO;

public class PlayerMenu implements Menu {


    Scanner in = IO.getScanner();

    @Override
    public int display() {
        int input = -1;

        System.out.println("\n\t\t+---------------------------------------------+");
        System.out.println("\t\t|                 PLAYERS MENU                |");
        System.out.println("\t\t+---------------------------------------------+");
        System.out.println("\t\t|                                             |");
        System.out.println("\t\t|     1- List of players                      |");
        System.out.println("\t\t|     2- Add a player                         |");
        System.out.println("\t\t|     3- Back                                 |");
        System.out.println("\t\t|                                             |");
        System.out.println("\t\t+---------------------------------------------+");
        System.out.print("Pick your choice : ");

        try {
            input = in.nextInt();
            if (input < 1 || input > 3) {
                System.out.println("Please pick a choice between 1 and 3...");
                in.next();
            }
        } catch (Exception e) {
            System.out.println("Please pick a valid number...");
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
