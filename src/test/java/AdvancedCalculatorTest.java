import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test Math Operations in Calculator Class")
public class AdvancedCalculatorTest {
    Calculator calculator ;

    @BeforeEach
    void beforeEachTestMethod() {
        calculator = new Calculator();

    }


     /******@ParameterizedTest. Multiple Parameters with @MethodSource:  *******************/

    @DisplayName("Test Integer Division [num1 ,num2 ,expected ] with @MethodSource")
    @ParameterizedTest
    @MethodSource("IntegerDivisonInputParameters")
    void testDivide_WhenDivideTwoNumbers(int num1 ,int num2 , int expected){
        System.out.println("Running..."+num1+"/" + num2 + "=" + expected );
        int actual = calculator.Divide(num1,num2);
        assertEquals(expected,actual,"the result of divison must be right");
    }
    private static Stream <Arguments> IntegerDivisonInputParameters (){
     return  Stream.of(
             Arguments.of(12,6,2),
             Arguments.of(16,2,8),
             Arguments.of(20,5,4)
             );
    }
    /*********************************************************************************************/

    @DisplayName("Validate Divide by zero Exception")
    @Test
    void testDivide_WhenDivideByZero_ShouldThrowAnArithmeticException(){
        int num1=12 ;
        int num2=0 ;
        System.out.println("validate ArithmeticException using Lambda expression => " +num1 + "/" + num2);

        String expectedMessageException = "/ by zero" ;
       ArithmeticException actualException= assertThrows(ArithmeticException.class, () -> calculator.Divide(num1,num2),
                "divide by zero should throw an ArithmeticException");
       assertEquals(expectedMessageException,actualException.getMessage(),"Unexpected Exception message");
    }
    /**********************************************************************************************/

    @DisplayName("Validate Divide by zero Exception using Repetition")
    @RepeatedTest(value=5,name="{displayName}. Repetition {currentRepetition} of "+"{totalRepetitions}")
    void testDivide_WhenDivideByZero_ShouldThrowAnArithmeticException(RepetitionInfo repetitionInfo ,TestInfo testInfo  ){
        System.out.println("Running "+testInfo.getTestMethod().get().getName());
        System.out.println("Repetition number "+repetitionInfo.getCurrentRepetition()+ " of " +repetitionInfo.getTotalRepetitions());
        int num1=12 ;
        int num2=0 ;
        // validate throw exception using Lambda expression
        String expectedMessageException = "/ by zero" ;
        ArithmeticException actualException= assertThrows(ArithmeticException.class, () -> calculator.Divide(num1,num2),
                "divide by zero should throw an ArithmeticException");
        assertEquals(expectedMessageException,actualException.getMessage(),"Unexpected Exception message");
    }
    /******@ParameterizedTest. Multiple Parameters with @CsvSource:  *******************/
    @DisplayName("Test Integer Multiplication [num1 ,num2 ,expected ] with @CsvSource")
    @ParameterizedTest
    @CsvSource(
            {"10,5,50",
              "20,5,100",
              "100,5,500"
    })
    void testMultiplyWhenMultiplyTwoNumbersUsingCsvSource(int num1 ,int num2 , int expected){
        System.out.println("Running..."+num1+"*" + num2 + "=" + expected );
        int actual = calculator.Multiply(num1,num2);

        assertEquals(expected,actual,()-> num1 + " * " + num2 + " do not produce "+expected);

    }
    /**********************************************************************************************/

    @DisplayName("Test Integer Multiplication [num1 ,num2 ,expected ] with @CsvFileSource")
    @ParameterizedTest
    @CsvFileSource(resources="/IntegerMultiplication.csv")
    void testMultiplyWhenMultiplyTwoNumbersUsingCsvFileSource(int num1 ,int num2 , int expected){
        System.out.println("Running..."+num1+"*" + num2 + "=" + expected );
        int actual = calculator.Multiply(num1,num2);

        assertEquals(expected,actual,()-> num1 + " * " + num2 + " do not produce "+expected);

    }
    /**********************************************************************************************/

    /******@ParameterizedTest + @ValueSource annotation :  *******************/
    @ParameterizedTest
    @ValueSource(ints = {10,5})
    void testDivideWhenUsingvalueSource(int value){
        int actual=calculator.Divide(10,value);
        System.out.println(actual);
        assertNotNull(value);
    }

}

