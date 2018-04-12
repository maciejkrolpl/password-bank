package com.github.maciejkrolpl.model;

import org.junit.Assert;
import org.junit.Test;

public class PasswordSafeTest {

    @Test
    public void givenEntryPasswordSafeWhenAddingEntryEntryShouldBeAdded() {
        PasswordSafe passwordSafe = new PasswordSafe();
        passwordSafe.addEntry("Odnoklassniki", "Siergiej", "qwe".toCharArray());
        Assert.assertTrue(passwordSafe.entryExists("Odnoklassniki", "Siergiej"));
    }
}
