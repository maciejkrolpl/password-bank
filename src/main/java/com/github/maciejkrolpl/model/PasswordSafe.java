package com.github.maciejkrolpl.model;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * Ta klasa powinna przechowywać PasswordEntries w wybranej przez Ciebie strukturze. Do wyboru masz: List, Set, Map.
 * Zanim podejmiesz decyzję, poświęć 5 minut na wypisanie zalet i wad każdej ze struktur. Powstrzymaj się od strzelania!
 */
public class PasswordSafe {

    private Integer nextId = 1;
    private Map<Integer, PasswordEntry> passwordSafe = new HashMap<>();

    public char[] getPassword(int id) {

        return passwordSafe.values().stream()
                .filter(pe -> pe.getId() == id)
                .findFirst()
                .orElseThrow(RuntimeException::new)
                .getPassword();
    }

    public void addEntry(String service, String login, char[] password) {
        if (this.entryExists(service, login)) {
            throw new RuntimeException("Entry already exists");
        }

        int id = nextId++;


            passwordSafe.put(id, PasswordEntry.Builder.create()
                    .withId(id)
                    .withLogin(login)
                    .withPassword(password)
                    .withService(service)
                    .build());
        }




    public void addEntry(PasswordEntry passwordEntry) {
        nextId = passwordEntry.getId() + 1;
        passwordSafe.put(passwordEntry.getId(), passwordEntry);
    }

    public void delEntry(String service) {

        passwordSafe.entrySet().removeIf(e -> e.getValue().getService().equals(service));

    }

    public boolean entryExists(int idToFind) {
        for (int id: passwordSafe.keySet()) {
            if (id == idToFind) {
                return true;
            }
        }
        return false;
    }

    public boolean entryExists(String service, String login) {
        for (PasswordEntry passwordEntry: passwordSafe.values()) {
            if (passwordEntry.getService().equals(service) && passwordEntry.getLogin().equals(login)) {
                return true;
            }
        }
        return false;
    }

    public String getJsons() {

        StringBuilder sb = new StringBuilder();
        Gson gson = new Gson();
        passwordSafe.values().forEach(v-> sb.append(gson.toJson(v)).append("\n"));

        return sb.toString();
    }

    public String getAllEntries() {

        StringBuilder sb = new StringBuilder();

        for (PasswordEntry passwordEntry : passwordSafe.values()) {
            sb.append(passwordEntry.toString()).append("\n");

        }

        return sb.toString();
    }
}
