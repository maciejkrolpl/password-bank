package com.github.maciejkrolpl.model;

import java.util.Arrays;

/**
 * Klasa powinna przechowywać hasło wraz z kluczem (np nazwą serwisu, do którego jest dedykowane).
 * Ponadto, niech pozwala na przechowanie loginu do tego serwisu.
 * Pola:
 * - klucz (nazwa serwisu)
 * - login
 * - hasło
 */
public class PasswordEntry {

    private Integer id;
    private String service;
    private String login;
    private char[] password;


    public PasswordEntry(Integer id, String service, String login, char[] password) {
        this.id = id;
        this.service = service;
        this.login = login;
        this.password = password;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PasswordEntry that = (PasswordEntry) o;

        if (!id.equals(that.id)) return false;
        if (!service.equals(that.service)) return false;
        if (login != null ? !login.equals(that.login) : that.login != null) return false;
        return Arrays.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + service.hashCode();
        result = 31 * result + (login != null ? login.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return String.format("%d. SERWIS: %s | LOGIN: %s", id, service, login);
    }

    public static class Builder {
        private int id;
        private String service;
        private String login;
        private char[] password;

        private Builder() {}

        public static Builder create() {
            return new Builder();
        }

        public Builder withId(int id) {
            this.id = id;
            return this;
        }

        public Builder withService(String service) {
            this.service = service;
            return this;
        }

        public Builder withLogin(String login) {
            this.login = login;
            return this;
        }

        public Builder withPassword (char[]password) {
            this.password = password;
            return this;
        }

        public PasswordEntry build() {
            return new PasswordEntry(id, service, login, password);
        }





    }

}