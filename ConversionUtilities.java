import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Base64;

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

    public static String generateBinaryStringOfXLength(Integer desiredStringLength) {
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder binaryStringBuilder = new StringBuilder();

        for (int i = 0; i < desiredStringLength; i++) {
            int randomBit = secureRandom.nextInt(2);
            binaryStringBuilder.append(randomBit);
        }

        return binaryStringBuilder.toString();
    }
}
