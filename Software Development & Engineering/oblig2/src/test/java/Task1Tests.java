import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Task1Tests {
    Task1 divisible;

    @BeforeEach
    void init() {
        divisible = new Task1();
    }

    @Test
    @DisplayName("Divisible by three")
    void divisibleByThree() throws TooNegativeException {
        assertEquals("Hi", divisible.isDivisible(3), "3 % 3 should be equal to Hi");
        assertEquals("Hi", divisible.isDivisible(33), "33 % 3 should be equal to Hi");
        assertEquals("Hi", divisible.isDivisible(66), "66 % 3 should be equal to Hi");
        assertEquals("Hi", divisible.isDivisible(69), "69 % 3 should be equal to Hi");
    }

    @Test
    @DisplayName("Divisible by five")
    void divisibleByFive() throws TooNegativeException {
        assertEquals("Of", divisible.isDivisible(5), "5 % 5 should be equal to Of");
        assertEquals("Of", divisible.isDivisible(20), "20 % 5 should be equal to Of");
        assertEquals("Of", divisible.isDivisible(55), "55 % 5 should be equal to Of");
        assertEquals("Of", divisible.isDivisible(100), "100 % 5 should be equal to Of");
    }

    @Test
    @DisplayName("Divisible by three and five")
    void divisibleByThreeAndFive() throws TooNegativeException {
        assertEquals("HiOf", divisible.isDivisible(15), "15 % 3 and 15 % 5 should be equal to HiOf");
        assertEquals("HiOf", divisible.isDivisible(30), "30 % 3 and 15 % 5 should be equal to HiOf");
        assertEquals("HiOf", divisible.isDivisible(60), "60 % 3 and 15 % 5 should be equal to HiOf");
    }

    @ParameterizedTest
    @DisplayName("Divisible by three parameterized")
    @ValueSource(ints = { 3, 33, 66, 69 })
    void divisibleByThreeParameterized(int num) throws TooNegativeException {
        assertEquals("Hi", divisible.isDivisible(num), num + " % 3 should be equal to Hi");
    }

    @ParameterizedTest
    @DisplayName("Divisible by five parameterized")
    @ValueSource(ints = { 5, 20, 55, 100 })
    void divisibleByFiveParameterized(int num) throws TooNegativeException {
        assertEquals("Of", divisible.isDivisible(num), num + " % 5 should be equal to Of");
    }


    @ParameterizedTest
    @DisplayName("Divisible by three and five parameterized")
    @ValueSource(ints = { 15, 30, 60 })
    void divisibleByThreeAndFiveParameterized(int num) throws TooNegativeException {
        assertEquals("HiOf", divisible.isDivisible(num), num + " % 3 and " + num + " & 5 should be equal to HiOf");
    }

    @Test
    @DisplayName("Throws TooNegativeException")
    void tooNegativeException() {
        assertThrows(TooNegativeException.class, () -> { divisible.isDivisible(0); });
        assertThrows(TooNegativeException.class, () -> { divisible.isDivisible(-1); });
        assertThrows(TooNegativeException.class, () -> { divisible.isDivisible(-69); });
    }
}
