import java.util.List;
import java.util.Map;
import java.util.HashMap;

import java.util.AbstractMap;
import java.util.ArrayList;

public class Decrypter {
    public static void main(String[] args) {
        String encryptedInput = "F6E2A07A868C5D7CEF034F6B43A15B632EF0BD0A285EA99E387C643F4981472BBF887B00C94FBC950F973A23963D36B31A44F9D4A06AA2AD2FCB8B4EF0ED55B05F5416B9D3882D2514A874B7E7BC14AAE5D255A3ACCED8A7";
        String key = "1010101101110011101001011000010011010011010010101110110011111101";    

        String initializationVectorHex = encryptedInput.substring(0, 16);
        String encryptedText = encryptedInput.substring(16, encryptedInput.length());
        
        String permutedKey = PermutationTables.handlePermutation(key, PermutationTables.keyPermutationIndexesPC1);        
        List<String> roundKeys = KeyExpander.expandInitialKey(permutedKey);

        String inputBinary = ConversionUtilities.convertHexToBinary(encryptedText);
        // divide plain text into 64 bit chunks
        List<String> input64BitChunks = ConversionUtilities.chunkInput64Bit(inputBinary);
        List<String> decryptedCBCBlocks = new ArrayList<>();

        for (String inputChunk : input64BitChunks) {
            String l = inputChunk.substring(0,32);
            String r = inputChunk.substring(32, 64);

            for (Integer i = 15; i > -1; i--) {
                String roundKey = roundKeys.get(i);
                String inputL = l;
                String inputr = r;

                String functionOutput = Function.handleFunction(inputL, roundKey);

                r = l;
                l = ConversionUtilities.xorTwoBinaryStrings(inputr, functionOutput);
            }

            decryptedCBCBlocks.add(l + r);
        }

        String initializationVector = ConversionUtilities.convertHexToBinary(initializationVectorHex);
        String decryptedOutputBinary = ConversionUtilities.handleCipherBlockUnChaining(decryptedCBCBlocks, initializationVector);
        String decryptedOutputUTF = ConversionUtilities.convertBinaryToUTF(decryptedOutputBinary.toString());

        System.out.println(decryptedOutputUTF);
    }
}
