package by.zarembo.project.util;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

public class CommentValidatorTest {

    @Test(dataProvider = "dataForCommentValidator")
    public void testValidate(String string,boolean expected,List<String> errorMessages) {
        assertEquals(CommentValidator.validate(string,errorMessages),expected);
    }

@DataProvider(name = "dataForCommentValidator")
    public Object[][] dataForValidator() {
        List<String> errorMessages = new ArrayList<>();
        return new Object[][]{
                {"", false,errorMessages},
                {null, false,errorMessages},
                {"Инкапсуляция (encapsulation) — принцип, объединяющий данные и код, манипулирующий этими данными, а также защищающий данные от прямого внешнего доступа и неправильного использования. Другими словами, доступ к данным класса возможен только посредством методов этого же класса. " +
                        "Наследование (inheritance) — процесс, посредством которого один класс может наследовать свойства другого класса и добавлять к ним свойства и методы, характерные только для него. Наследование бывает двух видов: одиночное наследование — подкласс (производный класс) " +
                        "имеет один и только один суперкласс (предок); множественное наследование — класс может иметь любое количество предков (в Java запрещено). Полиморфизм (polymorphism) — механизм, использующий одно и то же имя метода для решения похожих, " +
                        "но несколько отличающихся задач в различных объектах при наследовании из одного суперкласса. Целью полиморфизма является " +
                        "использование одного имени при выполнении общих для суперкласса " +
                        "и подклассов действий. ",false,errorMessages},
                {"First comment",true,errorMessages},
                {"Make!! a comment!!.",true,errorMessages},
                {".",true,errorMessages},
                {"..../../././",true,errorMessages},
                {"123 times",true,errorMessages},
                {"First sentence\r\n" +
                        "Second sentence",true,errorMessages},
        };
    }
}