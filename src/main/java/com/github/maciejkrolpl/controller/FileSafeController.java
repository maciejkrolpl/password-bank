package com.github.maciejkrolpl.controller;

import com.github.maciejkrolpl.model.PasswordEntry;
import com.github.maciejkrolpl.model.PasswordSafe;
import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * Odpowiedzialnością tej klasy, niech będzie zapisywanie do pliku oraz odczytywanie pliku.
 * Pierwotnie, plik nie będzie zabezpieczony w żadnen sposób.
 * Drugie podejście będzie uwzględniało ochronę zahardocde'owanym hasłem.
 * Ostatecznie, hasło powinno być pobierane od użytkownika oraz być używane do szyfrowania pliku.
 */


public class FileSafeController {

    private final static String FILE_NAME = "passwordbankfile.pbf";
    private FileEncryptionDecryption ske;

    public void saveToFile(PasswordSafe passwordSafe) {


        try {
            ske = new FileEncryptionDecryption("!@#$MySecr3tPassw0rd", 16, "AES", FILE_NAME);
        } catch (UnsupportedEncodingException ex) {
            System.err.println("Couldn't create key: " + ex.getMessage());
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            System.err.println(e.getMessage());
        }

        String passwordSafeStringRepresentation = passwordSafe.getJsons();

        File file = new File(FILE_NAME);

        try {
            FileUtils.writeStringToFile(file, "ODSZYFROWANY\n" + passwordSafeStringRepresentation, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            ske.encryptFile(file);
        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException
                | IOException e) {
            System.err.println("Couldn't encrypt " + file.getName() + ": " + e.getMessage());
        }


    }

    public void readFromFile(PasswordSafe passwordSafe) {

        try {
            ske = new FileEncryptionDecryption("!@#$MySecr3tPassw0rd", 16, "AES", FILE_NAME);
        } catch (UnsupportedEncodingException ex) {
            System.err.println("Couldn't create key: " + ex.getMessage());
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            System.err.println(e.getMessage());
        }

        File file = new File(FILE_NAME);

        try {
            ske.decryptFile(file);
        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException
                | IOException e) {
            System.err.println("Couldn't decrypt " + file.getName() + ": " + e.getMessage());
        }


        List<String> readedFile = null;
        PasswordEntry passwordEntry;

        try {
            readedFile = FileUtils.readLines(file, "UTF-8");

        } catch (IOException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();

        for (String readedLine : readedFile) {
            if (!readedLine.equals("ODSZYFROWANY")) {
                passwordEntry = gson.fromJson(readedLine, PasswordEntry.class);
                passwordSafe.addEntry(passwordEntry);
            }

        }

    }

}
