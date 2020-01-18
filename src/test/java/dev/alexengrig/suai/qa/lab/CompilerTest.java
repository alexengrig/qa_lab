package dev.alexengrig.suai.qa.lab;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;


public class CompilerTest {
    @Test
    public void checkSimpleExpression() {
        Double actual = new Compiler().compile("1+1");
        assertEquals(actual, Double.valueOf(2));
    }

    @Test
    public void checkMediumExpression() {
        Double actual = new Compiler().compile("1+1*(-5)-4/2");
        assertEquals(actual, Double.valueOf(-6));
    }

    @Test
    public void checkOperandExpression() {
        Double compile = new Compiler().compile("1");
        assertEquals(compile, Double.valueOf(1));
    }

    @Test(expected = Exception.class)
    public void checkEmptyExpression() {
        new Compiler().compile("");
    }

    @Test(expected = Exception.class)
    public void checkTextExpression() {
        new Compiler().compile("test");
    }

    @Test(expected = Exception.class)
    public void checkNullExpression() {
        new Compiler().compile(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkValidatorException() {
        new Compiler().compile("text");
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkOpenBracketException() {
        new Compiler().compile("(1)+2)");
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkCloseBracketException() {
        new Compiler().compile("((1)+2");
    }

    @Test
    public void checkCallValidator() {
        AtomicInteger count = new AtomicInteger();
        Function<String, Boolean> validator = (e) -> {
            count.getAndIncrement();
            return e.matches("[-+*0-9()/]+");
        };
        new Compiler(validator).compile("1+2");
        assertEquals(count.get(), 1);
    }

}