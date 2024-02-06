import java.util.List;
import java.util.Map;
import java.util.HashMap;

import java.util.AbstractMap;
import java.util.ArrayList;

public class Decrypter {
    public static void main(String[] args) {
        String encryptedInput = "7B1AD9775216C3DDC9138AC12B97C261F037756C60FFB7E05238703C6979CBC91DDAD67F135E39B104C6F4AF525449964CD5F8A213AC69826039B3662C5BF8DEE2B4CCB5D15FB7790435758662B9B9416C750984A7A34666C6C4635EC079DF7852E2654303CFC514F2D200484715C79EA1F3C1C9D02382D13D325030EB179DDA2670611C6E7EB5850975F4A7D596969E2811E381A42E9C98ACE15CC3BB98C8AFB30673F26DEDE668EF800AAC8F63FC62B82261A87166CFFE8602AE7FECCE116F0F08C473FDF9B610A14BDCE55DED287DE714C9ED5714F5ABDAF00AE6BF87BAEE02D97CDD30E32133213DE8DF2B4CEB7FDE8547EBBAE31E4D13CB4C019AAF92C128D31A6AA17A0390DCEB2F5F946C4E2C142251B98EB4FE0C8EB2775ACC230FB777CAF5C5CCC9F73515B80C080A37C93BC27C364DF9A34D337CEA3F865D96A833318D77A396775A86AD4B9168E336FB89F5FE0425673F54DC1981D93902C73F36";
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
