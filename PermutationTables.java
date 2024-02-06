import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PermutationTables {
    // ----IMPORTANT-----
    // indexes in permutation tables are indexed at "1" insted of "0"
    public static List<Integer> keyPermutationIndexesPC1 = Arrays.asList(
        57, 49, 41, 33, 25, 17, 9,
        1,  58, 50, 42, 34, 26, 18,
        10, 2,  59, 51, 43, 35, 27,
        19, 11, 3,  60, 52, 44, 36,
        63, 55, 47, 39, 31, 23, 15,
        7,  62, 54, 46, 38, 30, 22,
        14, 6,  61, 53, 45, 37, 29,
        21, 13, 5,  28, 20, 12, 4
    );

    public static List<Integer> keyPermutationIndexesPC2 = Arrays.asList(
        14,  17,  11,  24,  1,   5,
        3,   28,  15,  6,   21,  10,
        23,  19,  12,  4,   26,  8,
        16,  7,   27,  20,  13,  2,
        41,  52,  31,  37,  47,  55,
        30,  40,  51,  45,  33,  48,
        44,  49,  39,  56,  34,  53,
        46,  42,  50,  36,  29,  32
    );

    public static List<Integer> functionExpansionTable = Arrays.asList(
        32,  1,  2,  3,  4,  5,
        4,  5,  6,  7,  8,  9,
        8,  9, 10, 11, 12, 13,
       12, 13, 14, 15, 16, 17,
       16, 17, 18, 19, 20, 21,
       20, 21, 22, 23, 24, 25,
       24, 25, 26, 27, 28, 29,
       28, 29, 30, 31, 32,  1
    );

    public static List<Integer> functionStraightPBox = Arrays.asList(
        16, 7, 20, 21,
        29, 12, 28, 17,
        1,  15, 23, 26,
        5,  18, 31, 10,
        2,  8,  24, 14,
        32, 27, 3,  9,
        19, 13, 30, 6,
        22, 11, 4,  25
    );

    public static String handlePermutation(String input, List<Integer> permutationTable) {
        StringBuilder permutedInput = new StringBuilder();
        char[] inputChars = input.toCharArray();

        for (Integer permutationIndex : permutationTable) {
            permutedInput.append(inputChars[permutationIndex - 1]);
        }

        return permutedInput.toString();
    }
}
