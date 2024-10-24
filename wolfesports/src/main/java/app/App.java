package app;

import app.controller.MenuController;

public class App {
    public static void main(String[] args) {
        MenuController menuController = new MenuController();

        menuController.startMainMenu();
    }
}
