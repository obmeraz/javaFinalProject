package by.zarembo.project.util;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class LifeHackValidatorTest {

    @Test
    public void testValidate() {
    }

    @Test(dataProvider = "dataForValidateName")
    public void testValidateName(String name, boolean expected) {
        assertEquals(LifeHackValidator.validateName(name), expected);
    }

    @DataProvider(name = "dataForValidateName")
    public Object[][] dataForValidateName() {
        return new Object[][]{
                {null, false},
                {"", false},
                {"Few", false},
                {"end sentence.\r\n", true},
                {"12321", true},
                {"Classes model real-world entities from a template perspective; for example, car and savings account. Objects represent specific entities; for example," +
                        " John’s red Toyota Camry (a car instance) and Cuifen’s savings account with a balance of twenty thousand dollars (a savings account instance). " +
                        "Entities have attributes, such as color red, make Toyota, model Camry, and balance twenty thousand dollars. An entity’s collection of attributes is referred to as its state. Entities also have behaviors, such as open car door, drive car, " +
                        "display fuel consumption, deposit, withdraw, and show account balance.", false},
                {"Just lifehack name", true},
                {"Top 12 things", true},
                {"What you need to know:...", true},
                {"Русский и English", true},
        };
    }

    @Test(dataProvider = "dataForValidateExcerpt")
    public void testValidateExcerpt(String excerpt, boolean expected) {
        assertEquals(LifeHackValidator.validateExcerpt(excerpt), expected);
    }

    @DataProvider(name = "dataForValidateExcerpt")
    public Object[][] dataForValidateExcerpt() {
        return new Object[][]{
                {null, false},
                {"", false},
                {"Few", false},
                {"end sentence.\r\n", true},
                {"12321", true},
                {"Classes model real-world entities from a template perspective; for example, car and savings account. " +
                        "Objects represent specific entities; for example," +
                        " John’s red Toyota Camry ", false},
                {"Just lifehack excerpt", true},
                {"Top 12 things", true},
                {"What you need to know:...", true},
                {"Русский и English", true},
        };
    }

    @Test(dataProvider = "dataForValidateCategory")
    public void testValidateCategory(String category, boolean expected) {
        assertEquals(LifeHackValidator.validateCategory(category), expected);
    }

    @DataProvider(name = "dataForValidateCategory")
    public Object[][] dataForValidateCategory() {
        return new Object[][]{
                {null, false},
                {"", false},
                {"sport", true},
                {"cinema", true},
                {"12321", false},
                {"Classes model real-world entities from a template perspective; for example, car and savings account." +
                        " Objects represent specific entities; for example," +
                        " John’s red Toyota Camry ", false},
                {"life", true},
                {"technologies", true},
                {"motivation", true},
                {"news", true},
        };
    }

    @Test(dataProvider = "dataForValidateContent")
    public void testValidateContent(String content, boolean expected) {
        assertEquals(LifeHackValidator.validateContent(content), expected);
    }

    @DataProvider(name = "dataForValidateContent")
    public Object[][] dataForValidateContent() {
        return new Object[][]{
                {null, false},
                {"", false},
                {"Some text one two three", true},
                {"12312312 any numbers", true},
                {"12321", true},
                {"Classes model real-world entities from a template perspective; for example, car and savings account." +
                        " Objects represent specific entities; for example," +
                        " John’s red Toyota Camry ", true},
        };
    }

    @Test(dataProvider = "dataForValidateId")
    public void testValidateId(String id, boolean expected) {
        assertEquals(LifeHackValidator.validateId(id), expected);
    }

    @DataProvider(name = "dataForValidateId")
    public Object[][] dataForValidateId() {
        return new Object[][]{
                {null, false},
                {"", false},
                {"12", true},
                {"12312312 any numbers", false},
                {"12321", true},
                {"Classes model real-world entities from a template perspective; for example, car and savings account." +
                        " Objects represent specific entities; for example," +
                        " John’s red Toyota Camry ", false},
        };
    }
}