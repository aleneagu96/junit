import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;
import org.junit.jupiter.params.provider.*;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class CalculatorTest {

    Calculator calculator;

    @BeforeAll
    static void setUpAll() {
        System.out.println("this is where tests start");
    }


    @BeforeEach
    public void setUp() {
        calculator = new Calculator();
//    prepare test data before each test method
        System.out.println("Starting Test(x) at: " + LocalDateTime.now());
    }


    @RepeatedTest(3)
    void shouldReturnAddRepeat() {
        //given
        //when
        double result = calculator.add(15, 8);
        //then
        assertEquals(23, result);
    }

    @Test
    void shouldReturnSub() {
        //given
        //when
        double result = calculator.sub(15, 8);
        //then
        assertEquals(7, result);
    }

    @Test
    void shouldReturnMultiplicationOperation() {
//        then
        Assertions.assertAll(
                () -> assertEquals(4, calculator.multiply(2, 2), "2 x 2 sould not be 5"),
                () -> assertEquals(81, calculator.multiply(9, 9)),
                () -> assertEquals(30, calculator.multiply(5, 6))
        );
    }


    @Test
    void shouldAcceptDivideByZero() {
        IllegalArgumentException exception =
                Assertions.assertThrows(IllegalArgumentException.class, () -> calculator.divide(10, 0));
        assertEquals("Divide by 0", exception.getMessage());

    }

    @Test
    void testDivide_Positive() {
        double result = calculator.divide(15, 3);
        assertEquals(5, result);
    }


    @ParameterizedTest
    @ValueSource(doubles = {10.0, -23.0, 12.0, -2.0, 15.0})
    void shouldReturnReversedSign(double a) {
        assertEquals(-1 * a, calculator.reverseSign(a));
    }


    @ParameterizedTest
    @MethodSource("getParameters")
    void shouldReturnReverseSign(double a) {
        assertEquals(-1 * a, calculator.reverseSign(a));
    }

    static Stream<Arguments> getParameters() {
        return Stream.of(Arguments.of(1.0),
                Arguments.of(-231.0),
                Arguments.of(26.0),
                Arguments.of(-98.0));
    }

    @ParameterizedTest
    @ArgumentsSource(Parameters.class)
    void shouldReturnReverseSing3(double a) {
        assertEquals(-1 * a, calculator.reverseSign(a));
    }

    static class Parameters implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(final ExtensionContext context) {
            return Stream.of(Arguments.of(23.0),
                    Arguments.of(-231.0),
                    Arguments.of(26.0),
                    Arguments.of(-98.0));
        }
    }

    @ParameterizedTest
    @CsvSource({"10.0, 10.0, 20.0", "13.4, 2.9, 16.3", "123.2, 5.3, 128.5"})
    void shouldReturnAdditionOperation(double a, double b, double sum) {
        assertEquals(sum, calculator.add(a, b));
    }


    @ParameterizedTest
    @CsvFileSource(resources = "/dataSource.csv")
    void shouldReturnSubtractionOperation(double a, double b, double sub) {
        assertEquals(sub, calculator.sub(a, b));
    }


    @ParameterizedTest
    @ValueSource(strings = {"APRIL", "JUNE", "SEPTEMBER", "NOVEMBER"})
    void someMonths_Are30DaysLong(Month month) {
        final boolean isALeapYear = false;
        assertEquals(30, month.length(isALeapYear));
    }

    @ParameterizedTest
    @EnumSource(value = Month.class, names = {"APRIL", "JUNE", "SEPTEMBER", "NOVEMBER"},
            mode = EnumSource.Mode.INCLUDE)
        // if we put EXCLUDE will take the other 8 months
    void someMonthEnums_Are30DaysLong(Month month) {
        final boolean isALeapYear = false;
        assertEquals(30, month.length(isALeapYear));
    }

    @ParameterizedTest
    @ValueSource(strings = {"A", "D", "16"})
    void shouldReturnIntToHex(@ConvertWith(HexToInt.class) int a) {
        assertEquals(-1 * a, calculator.reverseSign(a));
        System.out.println(a);
    }

    static class HexToInt extends SimpleArgumentConverter {
        @Override
        protected Object convert(Object o, Class<?> targetType) {
            assertEquals(int.class, targetType, "Can only convert to int");
            return Integer.decode("0x" + o.toString());
        }
    }


    @AfterEach
    public void tearDown() {
        System.out.println("Stoping Test(x) at: " + LocalDateTime.now());
//        clean up test data  after each test method
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("this is where tests end");
    }
}
