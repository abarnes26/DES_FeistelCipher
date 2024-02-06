import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConversionUtilities {

    public static String convertUTF8ToBinary(String utf8Input) {
        StringBuilder result = new StringBuilder();
        char[] chars = utf8Input.toCharArray();

        for (char aChar : chars) {
            result.append(String.format("%8s", Integer.toBinaryString(aChar)).replaceAll(" ", "0"));
        }

        return result.toString();

        // return Base64.getEncoder().encodeToString(utf8Input.getBytes());
    }

    public static String convertUTF8ToHex(String input) {
        String inputBinary = convertUTF8ToBinary(input);
        String inputHex = convertBinaryToHex(inputBinary);

        return inputHex;
    }

    public static String convertBinaryToHex(String binaryInput) {
        int len = binaryInput.length();
        byte[] byteArray = new byte[len / 8];

        for (int i = 0; i < len; i += 8) {
            String binaryByte = binaryInput.substring(i, i + 8);
            byte b = (byte) Integer.parseInt(binaryByte, 2);
            byteArray[i / 8] = b;
        }
        
        StringBuilder hexOutput = new StringBuilder();
        for (byte b : byteArray) {
            hexOutput.append(String.format("%02X", b));
        }
        
        return hexOutput.toString();
    }

    public static String convertBinaryToUTF(String binaryInput) {
        int len = binaryInput.length();
        byte[] byteArray = new byte[len / 8];

        for (int i = 0; i < len; i += 8) {
            String binaryByte = binaryInput.substring(i, i + 8);
            byte b = (byte) Integer.parseInt(binaryByte, 2);
            byteArray[i / 8] = b;
        }

        String utf8Output = new String(byteArray);

        return utf8Output;
    }

    public static String convertHexToBinary(String hexInput) {
        String unpaddedBinary = new BigInteger(hexInput, 16).toString(2);
        int numLeadingZeros = hexInput.length() * 4 - unpaddedBinary.length();

        StringBuilder utf8Output = new StringBuilder();
        for (int i = 0; i < numLeadingZeros; i++) {
            utf8Output.append("0");
        }

        utf8Output.append(unpaddedBinary);

        return utf8Output.toString();
    }

    public static String xorTwoBinaryStrings(String input1, String input2) {
        char[] input1Chars = input1.toCharArray();
        char[] input2Chars = input2.toCharArray();
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < input1Chars.length; i++) {
            if (input1Chars[i] == input2Chars[i]) {
                result.append("0");
            } else {
                result.append("1");
            }

            if (i == (input2Chars.length - 1)) {
                break;
            }
        }

        return result.toString();
    }

    public static String padInput(String input) {
        // We're using the PKCS#7 padding method here
        Integer bytesToPad = ((((input.length() % 16) - 16) / 2) * -1);

        if (bytesToPad != 8) {
            for (Integer i = 0; i < bytesToPad; i++) {
                input += "0" + String.valueOf(bytesToPad);
            }
        }

        return input;
    }

    public static String generateBinaryStringOfXLength(Integer desiredStringLength) {
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder binaryStringBuilder = new StringBuilder();

        for (int i = 0; i < desiredStringLength; i++) {
            int randomBit = secureRandom.nextInt(2);
            binaryStringBuilder.append(randomBit);
        }

        return binaryStringBuilder.toString();
    }

    public static List<String> chunkInput64Bit(String inputBinary) {
        List<String> input64BitChunks = new ArrayList<>();
        Integer startingIndex = 0;
        Integer endingIndex = 64;

        for (Integer i = 0; i < inputBinary.length() / 64; i++) {
            String chunk = inputBinary.substring(startingIndex, endingIndex);
            input64BitChunks.add(chunk);

            startingIndex += 64;            
            endingIndex += 64;
        }

        return input64BitChunks;
    }

    public static List<String> handleCipherBlockChaining(List<String> input64BitChunks, String initializationVector) {
        List<String> cipherBlocks = new ArrayList<>();

        for (Integer i = 0; i < input64BitChunks.size(); i++) {

            if (i != 0) {
                String cipherBlock = xorTwoBinaryStrings(input64BitChunks.get(i), cipherBlocks.get(i - 1));
                cipherBlocks.add(cipherBlock);
            } else {
                String cipherBlock = xorTwoBinaryStrings(input64BitChunks.get(i),  initializationVector);
                cipherBlocks.add(cipherBlock);
            }
        }

        return cipherBlocks;
    }

    public static String handleCipherBlockUnChaining(List<String> decrypted64BitBlocks, String initializationVector) {
        StringBuilder decryptedUnchainedBlocks = new StringBuilder();
        Integer finalBlockIndex = (decrypted64BitBlocks.size() - 1);

        for (Integer i = finalBlockIndex; i > -1; i--) {
            if (i != 0) {
                String cipherBlock = xorTwoBinaryStrings(decrypted64BitBlocks.get(i), decrypted64BitBlocks.get(i - 1));
                decryptedUnchainedBlocks.insert(0, cipherBlock);
            } else {
                String cipherBlock = xorTwoBinaryStrings(decrypted64BitBlocks.get(i),  initializationVector);
                decryptedUnchainedBlocks.insert(0, cipherBlock);
            }
        }

        return decryptedUnchainedBlocks.toString();
    }

    public static Map<String, String> binaryStringToUTFStringTable = new HashMap<String, String>() {{
        put("00", "0");
        put("01", "1");
        put("10", "2");
        put("11", "3");
        put("0000", "0");
        put("0001", "1");
        put("0010", "2");
        put("0011", "3");
        put("0100", "4");
        put("0101", "5");
        put("0110", "6");
        put("0111", "7");
        put("1000", "8");
        put("1001", "9");
        put("1010", "10");
        put("1011", "11");
        put("1100", "12");
        put("1101", "13");
        put("1110", "14");
        put("1111", "15");
    }};

    public static Map<String, String> UTFStringToBinaryTable = new HashMap<String, String>() {{
        put("0", "00");
        put("1", "01");
        put("2", "10");
        put("3", "11");
        put("0", "0000");
        put("1", "0001");
        put("2", "0010");
        put("3", "0011");
        put("4", "0100");
        put("5", "0101");
        put("6", "0110");
        put("7", "0111");
        put("8", "1000");
        put("9", "1001");
        put("10", "1010");
        put("11", "1011");
        put("12", "1100");
        put("13", "1101");
        put("14", "1110");
        put("15", "1111");
    }};
}
