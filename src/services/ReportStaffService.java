package services;

import java.util.List;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.ArrayList;

import enums.UserRole;


import models.Camp;
import models.Student;

public class ReportStaffService {

    private CsvFileDataService csvFileDataService = new CsvFileDataService();
    private CampStaffService campStaffService = new CampStaffService();

    // generate Report for camps
    public boolean generateReport(String filter,List<Camp> camps) {

        // Get headers for CSV file
        List<String> headers = generateReportHeaderLine(filter);
    
        // Get CSV line for CSV file
        List<String> csvLines = new ArrayList<>();

        for (Camp camp : camps) {
            // Get combined list of registered attendee and committee
            ArrayList<Student> combinedStudentList = Stream.concat(
            campStaffService.getCampAttendeeList(camp).stream(),
            campStaffService.getCampCommitteeList(camp).stream())
            .collect(Collectors.toCollection(ArrayList::new));

            csvLines.add(generateReportCsvLine(filter, camp, combinedStudentList));
        }
    
        // Write to CSV file
        boolean success = csvFileDataService.writeCsvFile("report/staff_report.csv", headers,csvLines);
        return success;
    }

    public boolean generatePerformanceReport(String filter,List<Camp> camps) {

        // Get headers for CSV file
        List<String> headers = generatePerformanceReportHeaderLine(filter);
    
        // Get CSV line for CSV file
        List<String> csvLines = new ArrayList<>();

        for (Camp camp : camps) {
            // Get combined list of registered attendee and committee
            ArrayList<Student> combinedStudentList = Stream.concat(
            campStaffService.getCampAttendeeList(camp).stream(),
            campStaffService.getCampCommitteeList(camp).stream())
            .collect(Collectors.toCollection(ArrayList::new));

            csvLines.add(generatePerformanceReportCsvLine(filter, camp, combinedStudentList));
        }
    
        // Write to CSV file
        boolean success = csvFileDataService.writeCsvFile("report/staff_report.csv", headers,csvLines);
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

    public List<String> generatePerformanceReportHeaderLine(String filter) {
        List<String> headers = new ArrayList<>();
    
        switch (filter) {
            case "No Filter":
                headers.addAll(List.of(
                    "Camp Name",
                    "Committee ID",
                    "Committee Name",
                    "Committee Points"));
                break;
    
            case "Committee ID":
                headers.addAll(List.of(
                    "Camp Name",
                    "Committee ID"));
                break;
    
            case "Committee Name":
                headers.addAll(List.of(
                    "Camp Name",
                    "Committee Name"));
                break;

            case "Committee Points":
                headers.addAll(List.of(
                    "Camp Name",
                    "Committee Points"));
                break;

            default:
                break;
        }
    
        return headers;
    }
    

    public String generatePerformanceReportCsvLine(String filter, Camp camp, List<Student> students) {
        StringBuilder csvLines = new StringBuilder();
        boolean firstStudent = true;
    
        for (Student student : students) {
            if (!firstStudent) {
                csvLines.append(System.lineSeparator()); // Add a new line for each student
            } else {
                firstStudent = false;
            }
    
            switch (filter) {

                case "No Filter":
                    csvLines.append(String.join(",",
                            camp.getCampInformation().getCampName(),
                            student.getStudentID(),
                            student.getName(),
                            (student.getStudentPoints() != 0 ? String.valueOf(student.getStudentPoints()) : "N/A")));
                    break;
    
                case "Committee ID":
                    if (student.getUserRole() == UserRole.COMMITTEE) {
                        csvLines.append(String.join(",",
                        camp.getCampInformation().getCampName(),
                        student.getStudentID() ));
                    }
                    break;

                case "Committee Name":
                    if (student.getUserRole() == UserRole.COMMITTEE) {
                        csvLines.append(String.join(",",
                        camp.getCampInformation().getCampName(),
                        student.getName()));
                    }
                    break;

                case "Committee Points":
                    if (student.getUserRole() == UserRole.COMMITTEE) {
                        csvLines.append(String.join(",",
                        camp.getCampInformation().getCampName(),
                        (student.getStudentPoints() != 0 ? String.valueOf(student.getStudentPoints()) : "N/A")));
                    }
                    break;
    
    
                default:
                    break;
            }
        }

        return csvLines.toString();
    }
}



