package utility;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * The {@link SortUtility} class provides utility methods for sorting lists.
 * Primarily the sorting of a list in alphabetical order is available.
 */
public class SortUtility {

	/**
     * Sorts a list of strings alphabetically.
     *
     * @param inputList The list of strings to be sorted.
     * @return An alphabetically sorted list of strings.
     */
    public static List<String> alphabeticalSort(List<String> inputList) {
        List<String> sortedList = new ArrayList<>(inputList);
        Collections.sort(sortedList);
        return sortedList;
    }
}
