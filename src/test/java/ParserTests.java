import java.security.InvalidParameterException;

import org.junit.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assertions.*;

import controller.Parser;

public class ParserTests {

    @Test
    public void testWithValidInputContainingHex() {
        System.out.println("Testing Parser Main Functionality");
        double[] testValidOutput = {32.943008, -106.914925, 1883089, 1.26, 0.000000, 10, 8.54, 31.05, 0.000000, -102};
        String test = "S32.943008,-106.914925,1883089,1.26,0.000000,A,8.54,31.05,0.000000,-102,E";
        Parser testP = new Parser(10, true, 5);
        double[] testOut = testP.parse(test);

        for (int i = 0; i < testValidOutput.length; i++) {
            Assert.assertEquals(testValidOutput[i], testOut[i], 0);
            System.out.println("Success at index:" + i);
        }
        System.out.println("test complete - valid input containing hex");
    }

    @Test
    public void testStartAndEndChars() {
        String test_1 = "32.943008,-106.914925,1883089,1.26,0.000000,A,8.54,31.05,0.000000,-102";
        String test_2 = "S32.943008,-106.914925,1883089,1.26,0.000000,A,8.54,31.05,0.000000,-102";
        String test_3 = "32.943008,-106.914925,1883089,1.26,0.000000,A,8.54,31.05,0.000000,-102,E";
        String test_4 = "32.943008,-106.914925,1883089,1.26,0.000000,A,8.54,31.05,0.000000,-102-E";
        Parser testP = new Parser(10, true, 5);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            testP.parse(test_1);
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            testP.parse(test_2);
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            testP.parse(test_3);
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            testP.parse(test_4);
        });
        System.out.println("Test with no start or end characters passed");
    }
    
    @Test
    public void testInvalidHexChar() {
    	String test = "S32.943008,-106.914925,1883089,1.26,0.000000,K,8.54,31.05,0.000000,-102,E"; // max hex is 0xF
    	Parser testP = new Parser(10, true, 5);
    	Assertions.assertThrows(InvalidParameterException.class, () -> {
    		testP.parse(test);
    	});
    	System.out.println("Test with invalid hex character passed");
    }
    
    @Test
    public void testIncorrectNumberOfValues() {
    	String test = "S32.943008,-106.914925,1883089,1.26,0.000000,A,8.54,31.05,0.000000,-102,E"; // 10 values
    	Parser testP = new Parser(9, true, 5);
    	Assertions.assertThrows(IllegalArgumentException.class, () -> {
    		testP.parse(test);
    	});
    	System.out.println("Test with wrong number of values passed");
    }
    
    @Test
    public void testNotDoublesInData() {
    	String test = "S32.943008,-106.914925,1883089,1.26,0.000000,A,Bravo,Charlie,Delta,-102,E";
    	Parser testP = new Parser(10, true, 5);
    	Assertions.assertThrows(InvalidParameterException.class, () -> {
    		testP.parse(test);
    	});
    	System.out.println("Test with strings instead of doubles in data passed");
    }
}
