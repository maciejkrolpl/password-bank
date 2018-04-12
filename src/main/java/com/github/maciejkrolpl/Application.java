package com.github.maciejkrolpl;

import com.github.maciejkrolpl.controller.FileSafeController;
import com.github.maciejkrolpl.controller.PasswordManagerController;
import com.github.maciejkrolpl.model.PasswordSafe;
import com.github.maciejkrolpl.view.ConsoleView;

/**
 * Główna klasa do odpalenia aplikacji
 */
public class Application {

    public static void main(String[] args) {
        PasswordSafe passwordSafe = new PasswordSafe();
        FileSafeController fileSafeController = new FileSafeController();
        PasswordManagerController passwordManagerController = new PasswordManagerController();

        fileSafeController.readFromFile(passwordSafe);




       ConsoleView.Builder.create()
               .withPasswordManagerController(passwordManagerController)
               .withPasswordSafe(passwordSafe)
               .build()
               .runConsole();



        fileSafeController.saveToFile(passwordSafe);

    }
}
