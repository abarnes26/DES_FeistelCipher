import java.util.List;
import java.util.Map;
import java.util.HashMap;

import java.util.AbstractMap;

public class Decrypter {
    public static void main(String[] args) {
        String encryptedText = "76A4E917DC45A84816F2B17388774D7FCD019836938D6092C2DEB11C607AB4E21CF69EB6477E31F4D6D8AA0FF08AA5177298F89F434A1860FDB6CD651EC3F6FB1A5D1A31503CE0398BA4559540EA536EB11B8AFA5731F024FE8773493EC0C594FD67BC788AD86678958DD4E9E0798E845EA27C755E7E58424FA4EDC03E22B5FD9A9824771A9E0B8C783AFDB4735B212281BDCC23C63358DDD0FC9E83235E903AFACE3C8F99BDF2EB1DC8CECCE874A7B7775F86A9FCFAAF2D145D021FE3587E0AEF8FD09155ABD09DA6FF5DA006EF8B300ACE8282B0ADF6D71F986972E187FCB3848436BD1388F61FB9DF7AA343897A1A36422587033ED2A8701609137B511DA66EDACB87D5D4F125C6E68BC78DC9AB003C33A63DDF72EFD34EFC50229DB8D5EB4EC4BA1284C9DDE2";

        String initialKey = "1010101101110011101001011000010011010011010010101110110011111101";    
        // String initializationVector = ConversionUtilities.generateBinaryStringOfXLength(64);
        String initializationVector = "0011001110011110011010011110101101001101010111110100100010010101";
        

        String permutedKey = PermutationTables.handlePermutation(initialKey, PermutationTables.keyPermutationIndexesPC1);        
        List<String> roundKeys = KeyExpander.expandInitialKey(permutedKey);

        String inputBinary = ConversionUtilities.convertHexToBinary(encryptedText);

        // divide plain text into 64 bit chunks
        List<String> input64BitChunks = ConversionUtilities.chunkInput64Bit(inputBinary);

        // Employ Cipher Block Chaining (CBC)
        // List<String> cbcBlocks = ConversionUtilities.handleCipherBlockChaining(input64BitChunks, initializationVector);
        StringBuilder encryptedOutputBinary = new StringBuilder();

        for (String cbcBlock : input64BitChunks) {
            String l = cbcBlock.substring(0,32);
            String r = cbcBlock.substring(32, 64);

            for (Integer i = 15; i > -1; i--) {
                String roundKey = roundKeys.get(i);
                String inputL = l;
                String inputr = r;

                String functionOutput = Function.handleFunction(inputL, roundKey);

                r = l;
                l = ConversionUtilities.xorTwoBinaryStrings(inputr, functionOutput);
            }

            encryptedOutputBinary.append(l + r);
        }

        String encryptedOutputUTF = ConversionUtilities.convertBinaryToUTF(encryptedOutputBinary.toString());

        System.out.println(encryptedOutputUTF);
    }
}
