package com.github.maciejkrolpl.controller;

import com.github.maciejkrolpl.model.PasswordSafe;
import org.junit.Test;

import static org.junit.Assert.*;

public class PasswordManagerControllerTest {

    @Test
    public void givenPasswordWithoutAnyUppercase_WhenVerifyingPassword_ThenShouldBeNOK() {
        //given
        PasswordManagerController passwordManagerController = new PasswordManagerController();
        char[] password = "dj8d&kd3j6k1!".toCharArray();

        //when
        boolean verificationResult = passwordManagerController.verifyPassword(password);

        //then
        assertFalse(verificationResult);
    }

    @Test
    public void givenPasswordWithoutAnyLowercase_WhenVeryfyingPassword_ThenShouldBeNOK() {
        //given
        PasswordManagerController passwordManagerController = new PasswordManagerController();
        char[] password = "DG8D&KD3B6K1!".toCharArray();

        //when
        boolean verificationResult = passwordManagerController.verifyPassword(password);

        //then
        assertFalse(verificationResult);
    }

    @Test
    public void givenPasswordWithoutAnyDigit_WhenVerifyingPassword_ThenShouldBeNOK() {
        //given
        PasswordManagerController passwordManagerController = new PasswordManagerController();
        char[] password = "DjtD&KDmjyKz!".toCharArray();

        //when
        boolean verificationResult = passwordManagerController.verifyPassword(password);

        //then
        assertFalse(verificationResult);
    }


    @Test
    public void givenPasswordWithoutAnySpecial_WhenVerifyingPassword_ThenShouldBeNOK() {
        //given
        PasswordManagerController passwordManagerController = new PasswordManagerController();
        char[] password = "DjtD4KDmjyKz8".toCharArray();

        //when
        boolean verificationResult = passwordManagerController.verifyPassword(password);

        assertFalse(verificationResult);
    }

    @Test
    public void givenPasswordShorterThan8_WhenVerifyingPassword_ThenShouldBeNOK() {
        //given
        PasswordManagerController passwordManagerController = new PasswordManagerController();
        char[] password = "5hA4(z!".toCharArray();

        //when
        boolean verificationResult = passwordManagerController.verifyPassword(password);

        //then
        assertFalse(verificationResult);
    }


    @Test
    public void givenPasswordExactly8charsLenght_WhenVerifyingPassword_ThenShouldBeOK() {
        //given
        PasswordManagerController passwordManagerController = new PasswordManagerController();
        char[] password = "5hA4(z!s".toCharArray();

        //when
        boolean verificationResult = passwordManagerController.verifyPassword(password);

        //then
        assertTrue(verificationResult);
    }


    @Test
    public void givenPasswordExactly9charsLenght_WhenVerifyingPassword_ThenShouldBeOK() {
        //given
        PasswordManagerController passwordManagerController = new PasswordManagerController();
        char[] password = "5hA#4(z!s".toCharArray();

        //when
        boolean verificationResult =passwordManagerController.verifyPassword(password);

        //then
        assertTrue(verificationResult);
    }

    @Test
    public void givenCorrectEntry_WhenAddingToPasswordSafe_ThenShouldBeAdded() {
        // given
        PasswordManagerController passwordManagerController = new PasswordManagerController();
        PasswordSafe passwordSafe = new PasswordSafe();

        //when
        passwordManagerController.verifyAndAddEntry(passwordSafe, "facebook.com",
                "maciejkrol", "qweRTY123$%^".toCharArray());
        boolean verifyingResult = passwordSafe.entryExists("facebook.com", "maciejkrol");

        //then
        assertTrue(verifyingResult);
    }

    @Test
    public void givenEntryWithIncorrectPassword_WhenAddingToPasswordSafe_ThenExpectionShouldBeThrown() {
        // given
        PasswordManagerController passwordManagerController = new PasswordManagerController();
        PasswordSafe passwordSafe = new PasswordSafe();
        boolean thrown = false;

        //when
        try {
            passwordManagerController.verifyAndAddEntry(passwordSafe, "facebook.com",
                    "maciejkrol", "qweRTY123".toCharArray());
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        boolean verifyingResult = passwordSafe.entryExists("facebook.com", "maciejkrol");

        //then
        assertTrue(thrown);
    }

    @Test
    public void givenCorrectId_WhenPromptingToPrintPassword_ThenPasswordShouldBePrinted() {
        //given
        PasswordSafe passwordSafe = new PasswordSafe();
        passwordSafe.addEntry("facebook.com", "maciejkrol", "qweRTY!@#456".toCharArray());
        int id = 1;

        //when
        String pass = new PasswordManagerController().showPassword(passwordSafe, id);

        //then
        assertEquals("qweRTY!@#456", pass);
    }

    @Test
    public void givenIncorrectId_WhenPromptingToPrintPassword_ThenExpectionShouldBeThrown() {
        //given
        PasswordSafe passwordSafe = new PasswordSafe();
        passwordSafe.addEntry("facebook.com", "maciejkrol", "qweRTY!@#456".toCharArray());
        int id = 2;
        boolean thrown = false;

        //when
        try {
            String pass = new PasswordManagerController().showPassword(passwordSafe, id);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }

        //then
        assertTrue(thrown);
    }

    @Test
    public void givenCorrectServiceAndLogin_WhenVerifying_ThenEntryShouldBeDeleted() {
        //given
        PasswordSafe passwordSafe = new PasswordSafe();
        String service = "facebook.com";
        String login = "maciejkrol";
        passwordSafe.addEntry(service, login, "QWErty!@#456".toCharArray());

        //when
        new PasswordManagerController().verifyAndDeleteEntry(passwordSafe, service, login);
        boolean verificationResult = passwordSafe.entryExists(service, login);

        //then
        assertFalse(verificationResult);
    }

}
