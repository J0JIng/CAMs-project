package stores;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AuthStore {
    public static void main(String[] args) {
    	 String csvFile = "data/staff_list.csv"; // Replace with your CSV file path

         Map<String, Map<String, String>> data2DMap = new HashMap<>();
         String[] columnNames = null;

         try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
             String line;
             while ((line = br.readLine()) != null) {
                 String[] data = line.split(",");
                 if (columnNames == null) {
                     columnNames = data;
                 } else if (data.length >= 2) {
                     String key = data[0];
                     Map<String, String> rowMap = new HashMap<>();
                     for (int i = 1; i < Math.min(data.length, columnNames.length); i++) {
                         rowMap.put(columnNames[i], data[i]);
                     }
                     data2DMap.put(key, rowMap);
                 }
             }
         } catch (IOException e) {
             e.printStackTrace();
         }

      // Print the contents of the 2D map
         for (Map.Entry<String, Map<String, String>> entry : data2DMap.entrySet()) {
             System.out.println("Name: " + entry.getKey());
             Map<String, String> rowMap = entry.getValue();
             System.out.println("Faculty: " + rowMap.get("Faculty"));
             System.out.println("Email: " + rowMap.get("Email"));
             System.out.println();
         }
    }
}
