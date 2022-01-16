import java.security.InvalidParameterException;

import org.junit.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assertions.*;

import controller.Parser;

public class ParserTests {
	
	
	/*
	 * Telemetry parser tests
	 */
	
    @Test
    public void testValidTelemetryMessage() {
    	String test = "s-004.38;-003.69;0000.00;-00.72;000.58;009.41;022.31;101237.30;00107.73;454469476;-736939739;20:04:29e";
    	Parser testP = new Parser(12);
    	double[] data =  testP.parse(test);
    	Assertions.assertEquals(-4.38, data[0]);
    	Assertions.assertEquals(-3.69, data[1]);
    	Assertions.assertEquals(0.0, data[2]);
    	Assertions.assertEquals(-0.72, data[3]);
    	Assertions.assertEquals(0.58, data[4]);
    	Assertions.assertEquals(9.41, data[5]);
    	Assertions.assertEquals(22.31, data[6]);
    	Assertions.assertEquals(101237.3, data[7]);
    	Assertions.assertEquals(107.73, data[8]);
    	Assertions.assertEquals(454469476, data[9]);
    	Assertions.assertEquals(-736939739, data[10]);
    	Assertions.assertEquals(72269, data[11]);
    	
    }

    @Test
    public void testStartAndEndChars() {
        String test_1 = "-004.38;-003.69;0000.00;-00.72;000.58;009.41;022.31;101237.30;00107.73;454469476;-736939739;20:04:29e";
        String test_2 = "s-004.38;-003.69;0000.00;-00.72;000.58;009.41;022.31;101237.30;00107.73;454469476;-736939739;20:04:29";
        String test_3 = "-004.38;-003.69;0000.00;-00.72;000.58;009.41;022.31;101237.30;00107.73;454469476;-736939739;20:04:29";
        Parser testP = new Parser(12);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            testP.parse(test_1);
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            testP.parse(test_2);
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            testP.parse(test_3);
        });
        System.out.println("Test with no start or end characters passed");
    }
    

    
    @Test
    public void testIncorrectNumberOfValues() {
    	String test = "s-003.69;0000.00;-00.72;000.58;009.41;022.31;101237.30;00107.73;454469476;-736939739;20:04:29e"; // 10 values
    	Parser testP = new Parser(9);
    	Assertions.assertThrows(IllegalArgumentException.class, () -> {
    		testP.parse(test);
    	});
    	System.out.println("Test with wrong number of values passed");
    }
    
    @Test
    public void testNotDoublesInData() {
    	String test = "sA;-003.69;0000.00;-00.72;000.58;009.41;022.31;101237.30;00107.73;454469476;-736939739;20:04:29e";
    	Parser testP = new Parser(12);
    	Assertions.assertThrows(InvalidParameterException.class, () -> {
    		testP.parse(test);
    	});
    	System.out.println("Test with strings instead of doubles in data passed");
    }
    
    @Test
    public void testNewlineInData() {
    	String test = "s-004.38;-003.69\n;0000.00;-00.72;000.58;009.41;022.31;101237.30;00107.73;454469476;-736939739;20:04:29e";
    	Parser testP = new Parser(12);
    	double[] data = testP.parse(test);
    	Assertions.assertEquals(-3.69, data[1]);
    	System.out.println("Test with new line in data passed");
    }
    
    @Test
    public void testEmptyParameterInData() {
    	String test = "s-004.38;;0000.00;-00.72;000.58;009.41;022.31;101237.30;00107.73;454469476;-736939739;20:04:29e";
    	Parser testP = new Parser(12);
    	Assertions.assertThrows(InvalidParameterException.class, () -> {
    		testP.parse(test);
    	});
    	System.out.println("Test with an empty parameter in data passed");
    }
    
    @Test
    public void testEmptyData() {
    	String test = "";
    	Parser testP = new Parser(12);
    	Assertions.assertThrows(IllegalArgumentException.class, () -> {
    		testP.parse(test);
    	});
    	System.out.println("Test with empty data passed");
    }
    
    /*
     * Flight computer parser tests
     */
    

//    @Test
//    public void testValidTelemetryMessageFC() {
//    	String test = "S,0.85,-128.71,1004.91,140.00,-490.00,70.00,1005.24,45.4583817,-73.4328384,00,04,50,E";
//    	Parser testP = new Parser(12);
//    	double[] data =  testP.parseFC(test);
//    	Assertions.assertEquals(0.85, data[0]);
//    	Assertions.assertEquals(-128.71, data[1]);
//    	Assertions.assertEquals(1004.91, data[2]);
//    	Assertions.assertEquals(140.0, data[3]);
//    	Assertions.assertEquals(-490.0, data[4]);
//    	Assertions.assertEquals(70.0, data[5]);
//    	Assertions.assertEquals(1005.24, data[6]);
//    	Assertions.assertEquals(45.4583817, data[7]);
//    	Assertions.assertEquals(-73.4328384, data[8]);
//    	Assertions.assertEquals(0, data[9]);
//    	Assertions.assertEquals(4, data[10]);
//    	Assertions.assertEquals(50, data[11]);
//    	
//    }
    
    //test with state, change other tests to 13 and add state?
    @Test
    public void testValidTelemetryMessageFCWithState() {
    	String test = "S,0.85,-128.71,1004.91,140.00,-490.00,70.00,1005.24,45.4583817,-73.4328384,00,04,50,00110010,E";
    	Parser testP = new Parser(13);
    	double[] data =  testP.parseFC(test);
    	Assertions.assertEquals(0.85, data[0]);
    	Assertions.assertEquals(-128.71, data[1]);
    	Assertions.assertEquals(1004.91, data[2]);
    	Assertions.assertEquals(140.0, data[3]);
    	Assertions.assertEquals(-490.0, data[4]);
    	Assertions.assertEquals(70.0, data[5]);
    	Assertions.assertEquals(1005.24, data[6]);
    	Assertions.assertEquals(45.4583817, data[7]);
    	Assertions.assertEquals(-73.4328384, data[8]);
    	Assertions.assertEquals(0, data[9]);
    	Assertions.assertEquals(4, data[10]);
    	Assertions.assertEquals(50, data[11]);
    	Assertions.assertEquals(3, data[12]);
    	Assertions.assertEquals(2, data[13]);
    }
    
    @Test
    public void testIncorrectStateLength() {
    	String test = "";
    	Parser testP = new Parser(13);
    	Assertions.assertThrows(IllegalArgumentException.class, () -> {
    		testP.parseFC(test);
    	});
    	System.out.println("Test with wrong state length passed");
    }
    
    @Test
    public void testStartAndEndCharsFC() {
        String test_1 = "S,0.85,-128.71,1004.91,140.00,-490.00,70.00,1005.24,45.4583817,-73.4328384,00,04,50,00110010,";
        String test_2 = ",0.85,-128.71,1004.91,140.00,-490.00,70.00,1005.24,45.4583817,-73.4328384,00,04,50,00110010,E";
        String test_3 = ",0.85,-128.71,1004.91,140.00,-490.00,70.00,1005.24,45.4583817,-73.4328384,00,04,50,00110010,";
        String test_4 = "S0.85,-128.71,1004.91,140.00,-490.00,70.00,1005.24,45.4583817,-73.4328384,00,04,50,00110010,";
        Parser testP = new Parser(13);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            testP.parseFC(test_1);
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            testP.parseFC(test_2);
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            testP.parseFC(test_3);
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            testP.parseFC(test_4);
        });
        System.out.println("Test with no start or end characters passed");
    }
    

    
    @Test
    public void testIncorrectNumberOfValuesFC() {
    	String test = "s-003.69;0000.00;-00.72;000.58;009.41;022.31;101237.30;00107.73;454469476;-736939739;20:04:29e"; // 10 values
    	Parser testP = new Parser(9);
    	Assertions.assertThrows(IllegalArgumentException.class, () -> {
    		testP.parseFC(test);
    	});
    	System.out.println("Test with wrong number of values passed");
    }
    
    @Test
    public void testNotDoublesInDataFC() {
    	String test = "S,A,-128.71,1004.91,140.00,-490.00,70.00,1005.24,45.4583817,-73.4328384,00,04,50,00110010,E";
    	Parser testP = new Parser(13);
    	Assertions.assertThrows(InvalidParameterException.class, () -> {
    		testP.parseFC(test);
    	});
    	System.out.println("Test with strings instead of doubles in data passed");
    }
    
    @Test
    public void testNewlineInDataFC() {
    	String test = "S,0.85,-128.71\n,1004.91,140.00,-490.00,70.00,1005.24,45.4583817,-73.4328384,00,04,50,00110010,E";
    	Parser testP = new Parser(13);
    	double[] data = testP.parseFC(test);
    	Assertions.assertEquals(-128.71, data[1]);
    	System.out.println("Test with new line in data passed");
    }
    
    @Test
    public void testEmptyParameterInDataFC() {
    	String test = "S,0.85,,1004.91,140.00,-490.00,70.00,1005.24,45.4583817,-73.4328384,00,04,50,00110010,E";
    	Parser testP = new Parser(13);
    	Assertions.assertThrows(InvalidParameterException.class, () -> {
    		testP.parseFC(test);
    	});
    	System.out.println("Test with an empty parameter in data passed");
    }
    
    @Test
    public void testEmptyDataFC() {
    	String test = "";
    	Parser testP = new Parser(13);
    	Assertions.assertThrows(IllegalArgumentException.class, () -> {
    		testP.parseFC(test);
    	});
    	System.out.println("Test with empty data passed");
    }
    
	/*
	 * Currently don't have hex values in our telemetry messages.
	 */
//    @Test
//    public void testWithValidInputContainingHex() {
//        System.out.println("Testing Parser Main Functionality");
//        double[] testValidOutput = {32.943008, -106.914925, 1883089, 1.26, 0.000000, 10, 8.54, 31.05, 0.000000, -102};
//        String test = "S32.943008,-106.914925,1883089,1.26,0.000000,A,8.54,31.05,0.000000,-102,E";
//        Parser testP = new Parser(10, true, 5);
//        double[] testOut = testP.parse(test);
//
//        for (int i = 0; i < testValidOutput.length; i++) {
//            Assert.assertEquals(testValidOutput[i], testOut[i], 0);
//            System.out.println("Success at index:" + i);
//        }
//        System.out.println("test complete - valid input containing hex");
//    }
//	
//	@Test
//    public void testInvalidHexChar() {
//    	String test = "S32.943008,-106.914925,1883089,1.26,0.000000,K,8.54,31.05,0.000000,-102,E"; // max hex is 0xF
//    	Parser testP = new Parser(10, true, 5);
//    	Assertions.assertThrows(InvalidParameterException.class, () -> {
//    		testP.parse(test);
//    	});
//    	System.out.println("Test with invalid hex character passed");
//    }
//	
//    @Test
//    public void testNoHexExpected() {
//    	String test_1 = "S32.943008,-106.914925,1883089,1.26,0.000000,F,8.54,31.05,0.000000,-102,E";
//    	String test_2 = "S32.943008,-106.914925,1883089,1.26,0.000000,8,8.54,31.05,0.000000,-102,E";
//    	double[] expected_2 = {32.943008,-106.914925,1883089.0,1.26,0.0,8.0,8.54,31.05,0.0,-102.0};
//    	Parser testP = new Parser(10, false, 5);
//    	
//    	Assertions.assertThrows(InvalidParameterException.class, () -> {
//    		testP.parse(test_1);
//    	});
//    	
//    	double[] actual_2 = testP.parse(test_2);
//    	for (int i = 0; i < expected_2.length; i++) {
//    		Assertions.assertEquals(expected_2[i], actual_2[i]);
//    		System.out.println("Success at index: " + i);
//    	}
//    	
//    	System.out.println("Test with unexpected hex value passed");
//    	
//    }
    

}
