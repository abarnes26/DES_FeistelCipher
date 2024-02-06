import java.util.List;

public class Encrypter {

    public static void main(String[] args) {
        String plainText = "If the length of the original data is an integer multiple of the block size B, then an extra block of bytes with value B is added. This is necessary so the deciphering algorithm can determine with certainty whether the last byte of the last block is a pad byte indicating the number of padding bytes added or part of the plaintext message";

        String initialKey = "1010101101110011101001011000010011010011010010101110110011111101";    
        String initializationVector = ConversionUtilities.generateBinaryStringOfXLength(64);

        String permutedKey = PermutationTables.handlePermutation(initialKey, PermutationTables.keyPermutationIndexesPC1);    
        List<String> roundKeys = KeyExpander.expandInitialKey(permutedKey);
        String inputHex = ConversionUtilities.convertUTF8ToHex(plainText); 

        // pad any necessary blocks
        String paddedInput = ConversionUtilities.padInput(inputHex);
        String paddedInputBinary = ConversionUtilities.convertHexToBinary(paddedInput);

        // divide plain text into 64 bit chunks
        List<String> input64BitChunks = ConversionUtilities.chunkInput64Bit(paddedInputBinary);

        // Employ Cipher Block Chaining (CBC)
        List<String> cbcBlocks = ConversionUtilities.handleCipherBlockChaining(input64BitChunks, initializationVector);
        StringBuilder encryptedOutputBinary = new StringBuilder();

        for (String cbcBlock : cbcBlocks) {
            String l = cbcBlock.substring(0,32);
            String r = cbcBlock.substring(32, 64);

            for (String roundKey : roundKeys) {
                String inputL = l;
                String inputr = r;

                String functionOutput = Function.handleFunction(inputr, roundKey);

                l = r;
                r = ConversionUtilities.xorTwoBinaryStrings(inputL, functionOutput);
            }

            encryptedOutputBinary.append(l + r);
        }

        String encryptedOutputHex = ConversionUtilities.convertBinaryToHex(encryptedOutputBinary.toString());
        String initializationVectorHex = ConversionUtilities.convertBinaryToHex(initializationVector);
        String encryptedOutputHexFinal = (initializationVectorHex + encryptedOutputHex);

        System.out.println(encryptedOutputHexFinal);
    }
}