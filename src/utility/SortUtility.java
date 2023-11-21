package utility;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class SortUtility {

    public static List<String> alphabeticalSort(List<String> inputList) {
        List<String> sortedList = new ArrayList<>(inputList);
        Collections.sort(sortedList);
        return sortedList;
    }
}
