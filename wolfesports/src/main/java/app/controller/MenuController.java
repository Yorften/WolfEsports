package app.controller;

import app.util.IO;
import app.view.interfaces.Menu;
import app.view.menu.MainMenu;

public class MenuController {
    private Menu mainMenu = new MainMenu();

    private boolean isRunning = true;

    public void startMainMenu() {
        while (isRunning) {
            IO.clear();
            int choice = mainMenu.display();
            handleChoice(choice);
        }
    }

    private void handleChoice(int choice) {
        switch (choice) {
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                isRunning = false;
                System.out.println("Exiting...");
                break;
            default:
                break;
        }
    }

}
