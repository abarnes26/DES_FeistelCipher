import java.util.ArrayList;
import java.util.List;

public class Function {

    public static String handleFunction(String input, String key) {
        // expand input from 32 bits to 48 bits (permutation)
        String expandedInput = PermutationTables.handlePermutation(input, PermutationTables.functionExpansionTable);

        // XOR two values
        String xorResult = ConversionUtilities.xorTwoBinaryStrings(key, expandedInput);

        // break the resulting 48 bits into 8 groups of 6 bits
        List<String> sBoxBlocks = new ArrayList<>();
        Integer startingIndex = 0;
        Integer endingIndex = 6;

        for (Integer i = 0; i < 8; i++) {
            sBoxBlocks.add(xorResult.substring(startingIndex, endingIndex));
            startingIndex += 6;
            endingIndex += 6;
        }

        StringBuilder functionOutputPrePermutation = new StringBuilder();

        // run an s-box lookup to map each of the 6 bits to 4 bits
        for (Integer i = 0; i < sBoxBlocks.size(); i++) {
            String rowSelection = sBoxBlocks.get(i).substring(0,1) + sBoxBlocks.get(i).substring(5,6);
            String columnSelection = sBoxBlocks.get(i).substring(1,5);

            String rowSelectionUFT = ConversionUtilities.binaryStringToUTFStringTable.get(rowSelection);
            String columnSelectionUFT = ConversionUtilities.binaryStringToUTFStringTable.get(columnSelection);
            String sBoxOutput = new String();

            switch (i) {
                case 0:
                    sBoxOutput = SBoxTables.sBox1.get(rowSelectionUFT).get(columnSelectionUFT);
                    break;
                case 1:
                    sBoxOutput = SBoxTables.sBox2.get(rowSelectionUFT).get(columnSelectionUFT);
                    break;
                case 2:
                    sBoxOutput = SBoxTables.sBox3.get(rowSelectionUFT).get(columnSelectionUFT);
                    break;
                case 3:
                    sBoxOutput = SBoxTables.sBox4.get(rowSelectionUFT).get(columnSelectionUFT);
                    break;
                case 4:
                    sBoxOutput = SBoxTables.sBox5.get(rowSelectionUFT).get(columnSelectionUFT);
                    break;
                case 5:
                    sBoxOutput = SBoxTables.sBox6.get(rowSelectionUFT).get(columnSelectionUFT);
                    break;
                case 6:
                    sBoxOutput = SBoxTables.sBox7.get(rowSelectionUFT).get(columnSelectionUFT);
                    break;
                case 7:
                    sBoxOutput = SBoxTables.sBox8.get(rowSelectionUFT).get(columnSelectionUFT);
                    break;
            }

            String sboxOutputBinary = ConversionUtilities.UTFStringToBinaryTable.get(sBoxOutput);
            functionOutputPrePermutation.append(sboxOutputBinary);
        }

        String functionOutput = PermutationTables.handlePermutation(functionOutputPrePermutation.toString(), PermutationTables.functionStraightPBox);

        return functionOutput;
    }
    
}
