import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KeyExpander {

    private static List<Integer> cRounds1 = Arrays.asList(1, 9, 16);        
    private static List<Integer> cRounds2 = Arrays.asList(2, 3, 4, 5, 6, 7, 8, 10, 11, 12, 13, 14, 15);
    private static List<Integer> dRounds1 = Arrays.asList(1);        
    private static List<Integer> dRounds2 = Arrays.asList(2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16);
    
    public static List<String> expandInitialKey(String initialKey) {
        List<String> roundKeySet = new ArrayList<>();

        // 1 - initial permutation of the inputkey (PC-1)
        String permutedKey = handleKeyPermutation(initialKey, PermutationTables.keyPermutationIndexesPC1);        


        for (Integer i = 1; i < 17; i++) {
            permutedKey = handleKeyRotation(permutedKey, i);

            // 4 - After the rotation, each of the C and D halves is subjected to a compression permutation called PC-2 rearranges the bits of each half according to another fixed permutation table. The result is a 48-bit subkey for that round.
            String roundKey = handleKeyPermutation(permutedKey, PermutationTables.keyPermutationIndexesPC2);

            System.out.println(roundKey);               
            System.out.println(roundKey.length());            
         
            roundKeySet.add(roundKey);
        }

        return roundKeySet;
    }

    private static String handleKeyPermutation(String keyInput, List<Integer> permutationTable) {
        String permutedKey = "";

        List<String> permutedKeyArray = new ArrayList<String>();
        List<String> keyInputStringArray = new ArrayList<String>();

        char[] keyInputChars = keyInput.toCharArray();

        for (char keyInputChar : keyInputChars) {
            keyInputStringArray.add(String.valueOf(keyInputChar));
        }

        for (Integer permutationIndex : permutationTable) {
            permutationIndex -= 1;
            permutedKeyArray.add(String.valueOf(keyInputStringArray.get(permutationIndex)));
        }

        for (String keyCharacter : permutedKeyArray) {
            permutedKey += keyCharacter;
        }

        return permutedKey;
    }

    private static String handleKeyRotation(String permutedKey, Integer currentRound) {
        // 2 - After the initial permutation, the 56-bit key is divided into two 28-bit halves, often referred to as C and D.
        String c = permutedKey.substring(0, 28);        
        String d = permutedKey.substring(28, 56);

        // 3 - Both the C and D halves are independently rotated (shifted) to the left by a varying number of positions for each round. The number of positions shifted is determined by the specific round number.
        c = rotateKeyHalf(c, cRounds1, cRounds2, currentRound);        
        d = rotateKeyHalf(d, dRounds1, dRounds2, currentRound);

        return (c + d);
    }

    private static String rotateKeyHalf(String keyHalf, List<Integer> rotationIndexes1, List<Integer> rotationIndexes2, Integer currentRound) {
        if (rotationIndexes1.contains(currentRound)) {
            String charactersToShift = keyHalf.substring(0, 1);
            keyHalf = keyHalf.substring(1, 28) + charactersToShift;
        } else if (rotationIndexes2.contains(currentRound)) {
            String charactersToShift = keyHalf.substring(0, 2);
            keyHalf = keyHalf.substring(2, 28) + charactersToShift;
        }

        return keyHalf;
    }
}
