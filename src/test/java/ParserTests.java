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
        System.out.println("test done");
    }

    @Test
    public void testStartAndEndChars() {
        String test = "32.943008,-106.914925,1883089,1.26,0.000000,A,8.54,31.05,0.000000,-102";
        Parser testP = new Parser(10, true, 5);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            testP.parse(test);
        });
        System.out.println("Test with no start or end characters passed");
    }
}
