package services;

import java.util.List;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;

import enums.UserRole;
import interfaces.IReportStudentService;
import models.Camp;
import models.Student;
import stores.DataStore;

public class ReportStudentService implements IReportStudentService {

    private CsvFileDataService csvFileDataService = new CsvFileDataService();

    public ReportStudentService(){
    }

    public List<Student> getCombinedRegisteredList(Camp camp) {
        Map<String, List<String>> attendeeData = DataStore.getCampToRegisteredStudentData();
        Map<String, List<String>> committeeData = DataStore.getCampToRegisteredCampCommitteeData();
        Map<String, Student> studentData = DataStore.getStudentData();
    
        return Stream.concat(
                attendeeData.getOrDefault(camp.getCampInformation().getCampName(), Collections.emptyList()).stream(),
                committeeData.getOrDefault(camp.getCampInformation().getCampName(), Collections.emptyList()).stream()
            )
            .map(studentId -> studentData.get(studentId))
            .filter(Objects::nonNull) // Filter out null entries using Objects::nonNull
            .collect(Collectors.toList());
    }

    // generate Report for camps
    @Override
    public boolean generateReport(List<String> filter, Camp camp, Map<String, String> filePathsMap) {

        List<Student> combinedRegisteredList = getCombinedRegisteredList(camp);

        // Get headers for CSV file
        List<String> headers = generateReportHeaderLine(filter);
    
        // Get CSV line for CSV file
        List<String> csvLines = new ArrayList<>();
        csvLines.add(generateReportCsvLine(filter, camp, combinedRegisteredList));
        
        // Write to CSV file
        boolean success = csvFileDataService.writeCsvFile(filePathsMap.get("reportCommittee"),headers,csvLines);
        return success;
    }

    public List<String> generateReportHeaderLine(List<String> filter) {
        List<String> headers = new ArrayList<>();
    
        if (!filter.isEmpty()) {
            String firstFilter = filter.get(0);

            switch (firstFilter) {
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
        }
        return headers;
    }
    
    public String generateReportCsvLine(List<String> filter, Camp camp, List<Student> students) {
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
    
        if (!filter.isEmpty()) {
            String firstFilter = filter.get(0);

            // Sort students based on their names
            students.sort(Comparator.comparing(Student::getName));
    
            for (Student student : students) {
                if (!firstStudent) {
                    csvLines.append(System.lineSeparator()); // Add a new line for each student
                } else {
                    firstStudent = false;
                }
    
                switch (firstFilter) {
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
                        if (student.getUserRole() == UserRole.STUDENT && filter.size()==1) {
                            csvLines.append(String.join(",",
                                    camp.getCampInformation().getCampName(),
                                    student.getStudentID(),
                                    student.getName(),
                                    student.getUserRole().toString(),
                                    student.getFaculty().toString()));
                        }else if (student.getUserRole() == UserRole.STUDENT && filter.size()>1 && student.getName().equals(filter.get(1))) {
                            csvLines.append(String.join(",",
                                    camp.getCampInformation().getCampName(),
                                    student.getStudentID(),
                                    student.getName(),
                                    student.getUserRole().toString(),
                                    student.getFaculty().toString()));
                        }
                        break;
    
                    case "Camp Committee":
                        if (student.getUserRole() == UserRole.COMMITTEE && filter.size()==1) {
                            csvLines.append(String.join(",",
                                    camp.getCampInformation().getCampName(),
                                    student.getStudentID(),
                                    student.getName(),
                                    student.getUserRole().toString(),
                                    student.getFaculty().toString()));
                        }else if (student.getUserRole() == UserRole.COMMITTEE && filter.size()>1 && student.getName().equals(filter.get(1))) {
                            csvLines.append(String.join(",",
                                    camp.getCampInformation().getCampName(),
                                    student.getStudentID(),
                                    student.getName(),
                                    student.getUserRole().toString(),
                                    student.getFaculty().toString()));
                        }
                        break;
    
                    case "Location":
                        csvLines.append(String.join(",", camp.getCampInformation().getCampName(), camp.getCampInformation().getCampLocation()));
                        break;
    
                    case "Date":
                        csvLines.append(String.join(",", camp.getCampInformation().getCampName(), String.valueOf(camp.getCampInformation().getCampStartDate())));
                        break;
    
                    default:
                        break;
                }
            }
        }
        return csvLines.toString();
    }
    
}