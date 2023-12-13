import java.util.List;

public class Encrypter {

    public static void main(String[] args) {
        // input 64 bit key
        //String initialkey = "1010101101110011101001011000010011010011010010101110110011111101";        
        //List<String> roundKeys = KeyExpander.expandInitialKey(initialkey);

        String initializationVector = ConversionUtilities.generateBinaryStringOfXLength(64);
        System.out.println(initializationVector);        
        System.out.println(initializationVector.length());

        // divide plain text into 64 bit chunks
        // pad and necessary blocks
        // Employ Cipher Block Chaining (CBC) --
        // - XOR each block with the previous block BEFORE encryption'
        // - XOR the first block with an IV.  This IV does not need to be kept secret and will be prepended to the cypher text.  
        // - For security purposes it only matters if the IV is unique
    }
}
