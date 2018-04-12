package com.github.maciejkrolpl.view;

import com.github.maciejkrolpl.controller.PasswordManagerController;
import com.github.maciejkrolpl.model.PasswordSafe;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

public class ConsoleView {

    private PasswordSafe passwordSafe;
    private PasswordManagerController passwordManagerController;


    private ConsoleView(PasswordSafe passwordSafe, PasswordManagerController passwordManagerController) {
        this.passwordSafe = passwordSafe;
        this.passwordManagerController = passwordManagerController;
    }


    public void runConsole() {
        TextIO textIO = TextIoFactory.getTextIO();
        TextTerminal terminal = textIO.getTextTerminal();
        boolean quitProgram = false;


        while (!quitProgram) {
            terminal.println("\n\n M A N A G E R   H A S E Ł");
            terminal.println("1. Wyświetl wszystkie wpisy");
            terminal.println("2. Dodaj wpis");
            terminal.println("3. Usuń wpis");
            terminal.println("4. Pokaż hasło");
            terminal.println("0. Wyjście z programu");
            int select = textIO.newIntInputReader()
                    .withMaxVal(4)
                    .withMinVal(0)
                    .read("Twój wybór");
            switch (select) {

                case 1:
                    terminal.println(passwordSafe.getAllEntries());
                    break;
                case 2:
                    this.getAddEntryFromUser(terminal, textIO);
                    break;
                case 3:
                    this.getDeleteEntryFromUser(terminal, textIO);
                    break;
                case 4:
                    this.getPasswordToGetFromUser(terminal, textIO);
                    break;
                case 0:
                    quitProgram = true;
            }

        }

    }

    private void getPasswordToGetFromUser(TextTerminal terminal, TextIO textIO) {
        terminal.println(passwordSafe.getAllEntries());
        int id = textIO.newIntInputReader()
                .read("Wybierz hasło do pokazania");
        terminal.println(passwordManagerController.showPassword(passwordSafe, id));
    }

    private void getDeleteEntryFromUser(TextTerminal terminal, TextIO textIO) {
        terminal.println(passwordSafe.getAllEntries());
        String serviceToDelete = textIO.newStringInputReader()
                .read("Wpisz serwis wpisu do usunięcia");
        String loginToDelete = textIO.newStringInputReader()
                .read("Wpisz login wpisu do usunięcia");
        passwordManagerController.verifyAndDeleteEntry(passwordSafe, serviceToDelete, loginToDelete);
    }

    private void getAddEntryFromUser(TextTerminal terminal, TextIO textIO) {

        terminal.println("\n**WPROWADŹ NOWE DANE\n");
        String service = textIO.newStringInputReader()
                .read("Serwis: ");
        String login = textIO.newStringInputReader()
                .read("Login");
        char[] password = textIO.newStringInputReader()
                .withMinLength(8)
                .withInputMasking(true)
                .read("Hasło (min. 8 znaków): ")
                .toCharArray();

        passwordManagerController.verifyAndAddEntry(passwordSafe, service, login, password);
    }

    public static class Builder {

        private PasswordSafe passwordSafe;
        private PasswordManagerController passwordManagerController;

        private Builder() {
        }

        public static Builder create() {
            return new Builder();
        }

        public Builder withPasswordSafe(PasswordSafe passwordSafe) {
            this.passwordSafe = passwordSafe;
            return this;
        }


        public Builder withPasswordManagerController(PasswordManagerController passwordManagerController) {
            this.passwordManagerController = passwordManagerController;
            return this;
        }

        public ConsoleView build() {
            return new ConsoleView(passwordSafe, passwordManagerController);
        }
    }


}
