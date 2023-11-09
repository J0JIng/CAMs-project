package services;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import models.Camp;
import models.Student;
import stores.DataStore;

public class ReportService {
    
    private String currentStaffId; 

    public ReportService(String currentStaffId) {
        this.currentStaffId = currentStaffId;
    }

    public void generateCampReport() {
        Map<String, Camp> camps = DataStore.getCampData();
        Map<String, Student> students = DataStore.getStudentData();
        for (Camp camp : camps.values()) {
            if (camp.getCampInformation().getCampStaffInCharge().equals(currentStaffId)) { 
                List<Student> registeredStudents = camp.getRegisteredStudents();
                registeredStudents.sort(Comparator.comparing(Student::getName));

                String campReportFilename = sanitizeFilename(camp.getCampInformation().getCampName()) + "_report.csv";
                try (FileWriter csvWriter = new FileWriter(campReportFilename)) {
                    csvWriter.append("Student ID,Student Name,Student Role,Camp Name,Camp Description \n");

                    for (Student student : registeredStudents) {
                    	csvWriter.append(escapeCsv(student.getName()))
                        .append(",")
                        .append(escapeCsv(student.getCommitteeStatus()))
                        .append(",")
                        .append(escapeCsv(camp.getCampInformation().getCampName()))
                        .append(",")
                        .append(escapeCsv(camp.getCampInformation().getCampDescription()))
                        .append("\n");
                    }
                    System.out.println("Camp Report for " + camp.getCampInformation().getCampName() + " generated successfully.");
                } catch (IOException e) {
                    System.err.println("An error occurred while generating the camp report for " + camp.getCampInformation().getCampName());
                    e.printStackTrace();
                }
            }
        }
    }


    private String escapeCsv(String field) {
        return "\"" + field.replace("\"", "\"\"") + "\"";
    }


    private String sanitizeFilename(String filename) {
        return filename.replaceAll("\\s+", "_");
    }
    
    
}
