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
    public void testValidTelemetryMessage() {
    	String test = "S-11.31;12.88;355.88;-1.76;-2.42;9.37;101380.52;95.83;454469395;-736939729;00:02:19E";
    	Parser testP = new Parser(11);
    	double[] data =  testP.parse(test);
    	Assertions.assertEquals(-11.31, data[0]);
    	Assertions.assertEquals(12.88, data[1]);
    	Assertions.assertEquals(355.88, data[2]);
    	Assertions.assertEquals(-1.76, data[3]);
    	Assertions.assertEquals(-2.42, data[4]);
    	Assertions.assertEquals(9.37, data[5]);
    	Assertions.assertEquals(101380.52, data[6]);
    	Assertions.assertEquals(95.83, data[7]);
    	Assertions.assertEquals(454469395, data[8]);
    	Assertions.assertEquals(-736939729, data[9]);
    	System.out.println(data[10]);
    	Assertions.assertEquals(219, data[10]);
    	
    }
    

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
    
    @Test
    public void testNoHexExpected() {
    	String test_1 = "S32.943008,-106.914925,1883089,1.26,0.000000,F,8.54,31.05,0.000000,-102,E";
    	String test_2 = "S32.943008,-106.914925,1883089,1.26,0.000000,8,8.54,31.05,0.000000,-102,E";
    	double[] expected_2 = {32.943008,-106.914925,1883089.0,1.26,0.0,8.0,8.54,31.05,0.0,-102.0};
    	Parser testP = new Parser(10, false, 5);
    	
    	Assertions.assertThrows(InvalidParameterException.class, () -> {
    		testP.parse(test_1);
    	});
    	
    	double[] actual_2 = testP.parse(test_2);
    	for (int i = 0; i < expected_2.length; i++) {
    		Assertions.assertEquals(expected_2[i], actual_2[i]);
    		System.out.println("Success at index: " + i);
    	}
    	
    	System.out.println("Test with unexpected hex value passed");
    	
    }
}
