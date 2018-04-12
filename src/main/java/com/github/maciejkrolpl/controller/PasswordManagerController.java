package com.github.maciejkrolpl.controller;

import com.github.maciejkrolpl.model.PasswordSafe;

import java.util.Arrays;

/**
 * Ta klasa ma zarządzać dodawaniem nowych haseł, usuwaniem istniejących, pobieraniem istniejących. Dla nowego hasła,
 * powinna być opcja przyjęcia go od usera oraz w przyszłości wygenerowania nowego hasła.
 */
public class PasswordManagerController {


    public String showPassword(PasswordSafe passwordSafe, int id) {
        if (passwordSafe.entryExists(id)) {
            return  Arrays.toString(passwordSafe.getPassword(id))
                    .replace(",", "")
                    .replace("[", "")
                    .replace("]", "")
                    .replace(" ", "");

        } else {
            throw new IllegalArgumentException("No entry!");
        }

    }

    public void verifyAndDeleteEntry(PasswordSafe passwordSafe, String serviceToDelete, String loginToDelete) {
        if (passwordSafe.entryExists(serviceToDelete, loginToDelete)) {
            passwordSafe.delEntry(serviceToDelete);
        } else {
            throw new IllegalArgumentException("Entry doesn't exist!");
        }
    }

    public void verifyAndAddEntry(PasswordSafe passwordSafe, String service, String login, char[] password) {
        if (passwordSafe.entryExists(service, login)) {
            throw new IllegalArgumentException("Entry exists!");
        }

        if (verifyPassword(password)) {
            passwordSafe.addEntry(service, login, password);
        } else {
            throw new IllegalArgumentException("Password shoud have at least 1 digit, 1 uppercase letter,"
                    + " 1 lowercase letter and 1 special character! And be at least 8 chars long!");
        }

    }

    public boolean verifyPassword(char[] password) {
        boolean lowercase = false;
        boolean uppercase = false;
        boolean digit = false;
        boolean specialChar = false;

        for (char c : password) {
            if (!lowercase) {
                if (c >= 'a' && c <= 'z') {
                    lowercase = true;
                }
            }
            if (!uppercase) {
                if (c >= 'A' && c <= 'Z') {
                    uppercase = true;
                }
            }
            if (!digit) {
                if (c >= '0' && c <= '9') {
                    digit = true;
                }
            }
            if (!specialChar) {
                if ((c >= 33 && c <= 47) || (c >= 58) && (c <= 64)) {
                    specialChar = true;
                }
            }
        }

        return (digit) && (uppercase) && (lowercase) && (specialChar) && (password.length >= 8);

    }
}
