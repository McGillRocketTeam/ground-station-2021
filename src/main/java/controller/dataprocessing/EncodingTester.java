package controller.dataprocessing;

public class EncodingTester {
    public static void main(String[] args) {
        TextEncoding te = new TextEncoding(TextFormatting.ASCII);
        TestAllChars(TextFormatting.ASCII);
        TestAllChars(TextFormatting.UTF8);
    }

    private static void TestAllChars(TextFormatting tfIn) {
        TextEncoding testEncoder = new TextEncoding(tfIn);
        boolean testFail = false;
        boolean[] tests = new boolean[128];
        for (int i = 0; i < 128; i++) {
            char cIn = (char) i;
            int[] test = testEncoder.extractNibbles(cIn);
            char cOut = (char) testEncoder.combineNibbles(test);
            if (cIn != cOut) {
                testFail = true;
            }
            tests[i] = cIn == cOut;
        }
        System.out.println("Done: Test Fail:: " + testFail);
    }

}
