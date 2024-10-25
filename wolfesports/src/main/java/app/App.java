package app;

import app.controller.MenuController;
import app.util.AppContext;
import util.PersistenceUtil;

public class App {
    public static void main(String[] args) {

        // Init expensive operations on startup
        PersistenceUtil.getEntityManagerFactory();
        AppContext.getAppContext();

        MenuController menuController = new MenuController();

        menuController.startMainMenu();
    }
}
