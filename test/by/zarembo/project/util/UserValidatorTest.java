package by.zarembo.project.util;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class UserValidatorTest {


    @Test(dataProvider = "dataForValidateEmail")
    public void testValidateEmail(String email, boolean expected) {
        assertEquals(UserValidator.validateEmail(email), expected);
    }

    @DataProvider(name = "dataForValidateEmail")
    public Object[][] dataForValidateEmail() {
        return new Object[][]{
                {null, false},
                {"", false},
                {"12", false},
                {"any numbers", false},
                {"email gmail@gmail.com", false},
                {"email@", false},
                {"email@@@gmail.com", false},
                {"email@@@gmail", false},
                {"email@gmail.com", true},
                {"zarembo12valentine@gmail.com", true},
                {"zarembo12valentine@gmail,com", false},
        };
    }

    @Test(dataProvider = "dataForValidatePassword")
    public void testValidatePassword(String password, boolean expected) {
        assertEquals(UserValidator.validatePassword(password), expected);
    }

    @DataProvider(name = "dataForValidatePassword")
    public Object[][] dataForValidatePassword() {
        return new Object[][]{
                {null, false},
                {"", false},
                {"12", false},
                {"125asdmas", false},
                {"dsanjnwdj", false},
                {"......", false},
                {"1a", false},
                {"qwerty1A", true},
                {"a1Sdfght", true},
                {"12345678Aw", true},
        };
    }

    @Test(dataProvider = "dataForValidateName")
    public void testValidateName(String name,boolean expected) {
        assertEquals(UserValidator.validateName(name),expected);
    }

    @DataProvider(name = "dataForValidateName")
    public Object[][] dataForValidateName() {
        return new Object[][]{
                {null, false},
                {"", false},
                {"12", false},
                {"125asdmas", false},
                {"dsanjnwdj", false},
                {"......", false},
                {"1a", false},
                {"Valentine", true},
                {"valentune", false},
                {"Dzmitriy", true},
        };
    }
}