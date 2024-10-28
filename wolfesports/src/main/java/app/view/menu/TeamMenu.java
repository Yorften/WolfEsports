package app.view.menu;

import java.util.Scanner;

import app.view.interfaces.Menu;
import app.util.IO;

public class TeamMenu implements Menu {


    Scanner in = IO.getScanner();

    @Override
    public int display() {
        int input = -1;

        System.out.println("\n\t\t+---------------------------------------------+");
        System.out.println("\t\t|                  TEAMS MENU                 |");
        System.out.println("\t\t+---------------------------------------------+");
        System.out.println("\t\t|                                             |");
        System.out.println("\t\t|     1- List of teams                        |");
        System.out.println("\t\t|     2- List of warned teams                 |");
        System.out.println("\t\t|     3- List of non warned teams             |");
        System.out.println("\t\t|     4- Add a team                           |");
        System.out.println("\t\t|     5- Back                                 |");
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
        }
        return input;
    }


    @Override
    public void back() {
        System.out.println("Press Enter to go back...");
        in.next();
    }
}
