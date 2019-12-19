package controller.dataprocessing;
import java.nio.charset.StandardCharsets;

public class TextEncoding {
//private Hashtable<Character, String> decoding;
private Character[] EncodingTable = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', ',', '.', '-', '\n', 'S', 'E'};
private TextFormatting textFormatting;
private String[] decodingTable;

    public TextEncoding(TextFormatting textFormattingIn) {
        //this.decoding = new Hashtable<>();
        this.textFormatting = textFormattingIn;
        this.decodingTable = new String[256];
        generateTable();
    }

    int[] extractNibbles(Character charIn) {
        int leftNibble = -1, rightNibble = -1;
        String stringOfChar = charIn.toString();
        byte charAsByte = this.textFormatting == TextFormatting.ASCII ? stringOfChar.getBytes(StandardCharsets.US_ASCII)[0] : stringOfChar.getBytes(StandardCharsets.UTF_8)[0];
        leftNibble = charAsByte >> 4;
        rightNibble = charAsByte % 16;
        return new int[] {leftNibble, rightNibble};
    }

    int combineNibbles(int[] nibblesIn) {
        return combineNibbles(nibblesIn[0], nibblesIn[1]);
    }

    int combineNibbles(int leftNibble, int rightNibble) {
        leftNibble = leftNibble << 4;
        int charOut = leftNibble + rightNibble;
        return charOut;
    }

    private void generateTable() {
        for (int charVal = 0; charVal < 256; charVal++) {
            int[] vals = extractNibbles((char)charVal);

//            this.decodingTable[charVal];
        }
    }
}

