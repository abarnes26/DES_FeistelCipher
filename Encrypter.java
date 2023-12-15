import java.util.List;

public class Encrypter {

    public static void main(String[] args) {
        String plainText = "Here is a somewhat long string to test with.  I was going to use Lorem Ipsum but I want it to be ovious when the decrypting works.  Much more exciting to read my own message coming out of the decrypter instead of Lorem Ipsum. Without this additional sentence it came out to exactly 64 bits!";
        // input 64 bit key

        String initialkey = "1010101101110011101001011000010011010011010010101110110011111101";    
        String initializationVector = ConversionUtilities.generateBinaryStringOfXLength(64);

        List<String> roundKeys = KeyExpander.expandInitialKey(initialkey);
        String inputHex = ConversionUtilities.convertUTF8ToHex(plainText);   

        // pad any necessary blocks
        String paddedInput = ConversionUtilities.padInput(inputHex);
        String paddedInputBinary = ConversionUtilities.convertHexToBinary(paddedInput);

        // divide plain text into 64 bit chunks
        List<String> input64BitChunks = ConversionUtilities.chunkInput64Bit(paddedInputBinary);

        // Employ Cipher Block Chaining (CBC)
        List<String> cbcBlocks = ConversionUtilities.handleCipherBlockChaining(input64BitChunks, initializationVector);

    }
}
