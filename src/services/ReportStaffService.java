package services;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;

import enums.FacultyGroups;
import interfaces.IFileDataService;
import utility.FilePathsUtility;

import models.Camp;
import models.Student;
import stores.DataStore;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

public class ReportStaffService {

    private CsvFileDataService csvFileDataService = new CsvFileDataService();

    public boolean generateReport(List<String> filters, List<Camp> camps) {
        if (filters.isEmpty() || camps == null) {
            System.out.println("No filters or camps provided. Cannot generate report.");
            return false;
        }

        List<String> headers = new ArrayList<>();
        headers.add("Camp Name");

        // Iterate through filters and add corresponding headers
        for (String filter : filters) {
            switch (filter) {
                case "No Filter":
                    // generate Report for ALL Camps
                    break;
                case "Attendee":
                    // generate report of ALL camps with Attendee Name
                    headers.add("Attendee Names");
                    // ask for name
                    // get all camps with the person name
                    break;
                case "Camp Committee":
                    // generate report of ALL camps with Camp Committee Name
                    // ask for name
                    // get all camps with the person name
                    
                    break;
                case "Location":
                    // generate report of ALL camps with Camp Committee Name
                    // ask for Location
                    // get all camps with the person name
               
                    break;
                case "Date":
                    // generate report of ALL camps Starting from the Date
                    // ask for Location
                    // get all camps with the person name

                default :
                    break;
                // Add more cases for additional filters as needed

            }
        }

        return true;
    }

    public boolean generateReport(List<String>filters,List<Student> students,Camp camp){

        List<String> headers = List.of(
                "Camp Name",
                "Camp Start Date",
                "Camp End Date",
                "Registration Closing Date",
                "User Group",
                "Location",
                "Total Slots",
                "Camp Committee Slots",
                "Description",
                "Staff In Charge",
                "Student ID",
                "Student Name",
                "Student Role"
        );

        List<String> csvLines = new ArrayList<>();
        csvLines.add(generateCsvLine(camp, students));
       
        boolean success = csvFileDataService.writeCsvFile("report/staff_report.csv", headers, csvLines);
        return success;
    }

    public String generateCsvLine(Camp camp, List<Student> students) {
        StringBuilder csvLine = new StringBuilder();
        
        csvLine.append(String.join(",", 
            camp.getCampInformation().getCampName(),
            String.valueOf(camp.getCampInformation().getCampStartDate()),
            String.valueOf(camp.getCampInformation().getCampEndDate()),
            String.valueOf(camp.getCampInformation().getCampRegistrationClosingDate()),
            camp.getCampInformation().getFacultyGroup().toString(),
            camp.getCampInformation().getCampLocation(),
            String.valueOf(camp.getCampInformation().getCampTotalSlots()),
            String.valueOf(camp.getCampInformation().getCampCommitteeSlots()),
            camp.getCampInformation().getCampDescription(),
            camp.getCampInformation().getCampStaffInCharge()
        ));
    
        for (Student student : students) {
            csvLine.append(",");
            csvLine.append(String.join(",",
                student.getStudentID(),
                student.getName(),
                student.getFaculty().toString()
            ));
        }
    
        return csvLine.toString();
    }
    

}
