package services;

import java.util.List;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

import enums.UserRole;

import models.Camp;
import models.Student;
import stores.DataStore;

public class ReportStudentService {

    private CsvFileDataService csvFileDataService = new CsvFileDataService();
    // generate Report for camps
    public boolean generateReport(String filter, Camp camp) {

        Map<String, List<String>> attendeeData = DataStore.getCampToRegisteredStudentData();
        Map<String, List<String>> committeeData = DataStore.getCampToRegisteredCampCommitteeData();
        Map<String, Student> studentData = DataStore.getStudentData();

        List<Student> combinedRegisteredList = Stream.concat(
                attendeeData.getOrDefault(camp.getCampInformation().getCampName(), Collections.emptyList()).stream(),
                committeeData.getOrDefault(camp.getCampInformation().getCampName(), Collections.emptyList()).stream()
            )
            .map(studentId -> studentData.get(studentId))
            .filter(Objects::nonNull) // Filter out null entries using Objects::nonNull
            .collect(Collectors.toList());

        // Get headers for CSV file
        List<String> headers = generateReportHeaderLine(filter);
    
        // Get CSV line for CSV file
        List<String> csvLines = new ArrayList<>();
        csvLines.add(generateReportCsvLine(filter, camp, combinedRegisteredList));
        
        // Write to CSV file
        boolean success = csvFileDataService.writeCsvFile("report/committee_report.csv", headers,csvLines);
        return success;
    }


    public List<String> generateReportHeaderLine(String filter) {
        List<String> headers = new ArrayList<>();
    
        switch (filter) {
            case "No Filter":
                headers.addAll(List.of(
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
                    "Student Role",
                    "Student Faculty"));
                break;
    
            case "Camp Information":
                headers.addAll(List.of(
                    "Camp Name",
                    "Camp Start Date",
                    "Camp End Date",
                    "Registration Closing Date",
                    "User Group",
                    "Location",
                    "Total Slots",
                    "Camp Committee Slots",
                    "Description",
                    "Staff In Charge"));
                break;
    
            case "Attendee":
            case "Camp Committee":
                headers.addAll(List.of(
                    "Camp Name",
                    "Student ID",
                    "Student Name",
                    "Student Role",
                    "Student Faculty"));
                break;
    
            case "Location":
                headers.addAll(List.of(
                    "Camp Name",
                    "Location"));
                break;
    
            case "Date":
                headers.addAll(List.of(
                    "Camp Name",
                    "Date"));
                break;
    
            default:
                break;
        }
    
        return headers;
    }
    
    public String generateReportCsvLine(String filter, Camp camp, List<Student> students) {
        StringBuilder csvLines = new StringBuilder();
        boolean firstStudent = true;
    
        String commonFields = String.join(",",
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
        );
    
        for (Student student : students) {
            if (!firstStudent) {
                csvLines.append(System.lineSeparator()); // Add a new line for each student
            } else {
                firstStudent = false;
            }
    
            switch (filter) {

                case "No Filter":
                    csvLines.append(String.join(",", commonFields,
                            student.getStudentID(),
                            student.getName(),
                            student.getUserRole().toString(),
                            student.getFaculty().toString()
                    ));
                    break;
    
                case "Camp Information":
                    csvLines.append(String.join(",", commonFields));
                    break;
    
                case "Attendee":
                    if (student.getUserRole() == UserRole.STUDENT) {
                        csvLines.append(String.join(",",
                                camp.getCampInformation().getCampName(),
                                student.getStudentID(),
                                student.getName(),
                                student.getUserRole().toString(),
                                student.getFaculty().toString()));
                    }
                    break;
    
                case "Camp Committee":
                    if (student.getUserRole() == UserRole.COMMITTEE) {
                        csvLines.append(String.join(",",
                                camp.getCampInformation().getCampName(),
                                student.getStudentID(),
                                student.getName(),
                                student.getUserRole().toString(),
                                student.getFaculty().toString()));
                    }
                    break;
    
                case "Location":
                    csvLines.append(String.join(",",camp.getCampInformation().getCampName(),camp.getCampInformation().getCampLocation()));
                    break;
    
                case "Date":
                    csvLines.append(String.join(",",camp.getCampInformation().getCampName(),String.valueOf(camp.getCampInformation().getCampStartDate())));
                    break;
    
                default:
                    break;
            }
        }
        return csvLines.toString();
    }
}