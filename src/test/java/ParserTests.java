import org.junit.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assertions.*;

import controller.Parser;

public class ParserTests {

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
//        System.out.println("test done");
//    }

    @Test
    public void testStartAndEndChars() {
        String test = "32.943008,-106.914925,1883089,1.26,0.000000,A,8.54,31.05,0.000000,-102";
        Parser testP = new Parser(10, true, 5);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            testP.parse(test);
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
    
}
