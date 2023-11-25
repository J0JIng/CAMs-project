package services;

import java.util.List;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.ArrayList;
import java.util.Map;

import enums.UserRole;
import interfaces.IReportStudentService;
import interfaces.ICampCommitteeService;
import models.Camp;
import models.Student;

/**
 * Service class for generating student-related reports by camp committee members.
 */
public class ReportStudentService implements IReportStudentService {

	/**
	 * An instance of {@link CsvFileDataService} used for handling CSV file operations in the report service.
	 * It is kept private to ensure its use is exclusively in services that require it.
	 */
    private final static CsvFileDataService csvFileDataService = new CsvFileDataService();

    /**
     * An instance of {@link ICampCommitteeService} providing access to camp staff-related operations.
     * It is kept private to ensure its use is exclusively in services that require it.
     */
    private final static ICampCommitteeService campCommitteeService = new CampCommitteeService();

    /**
     * Default constructor for the ReportStudentService class.
     */
    public ReportStudentService(){
    }

    /**
     * Generates a student report for a given camp and writes it to a CSV file.
     *
     * @param filter       The filter to determine the report's content.
     * @param camp         The camp for which the report is generated.
     * @param filePathsMap The map containing file paths for different types of reports.
     * @return True if the report generation and writing were successful, false otherwise.
     */
    @Override
    public boolean generateReport(List<String> filter, Camp camp, Map<String, String> filePathsMap) {

        ArrayList<Student> combinedStudentList = Stream.concat(
            campCommitteeService.getCampAttendeeList(camp).stream(),
            campCommitteeService.getCampCommitteeList(camp).stream())
            .collect(Collectors.toCollection(ArrayList::new));

        // Get headers for CSV file
        List<String> headers = generateReportHeaderLine(filter);
    
        // Get CSV line for CSV file
        List<String> csvLines = new ArrayList<>();
        csvLines.add(generateReportCsvLine(filter, camp, combinedStudentList));
        
        // Write to CSV file
        boolean success = csvFileDataService.writeCsvFile(filePathsMap.get("reportCommittee"),headers,csvLines);
        return success;
    }

    /**
     * Generates the header line for a student report based on the specified filter.
     *
     * @param filter The filter to determine the report's content.
     * @return The list of header fields for the student report.
     */
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
    
    /**
     * Generates a CSV line for a student report based on the specified filter, camp, and list of students.
     *
     * @param filter   The filter to determine the report's content.
     * @param camp     The camp for which the student report is generated.
     * @param students The list of students to include in the student report.
     * @return The CSV line for the student report.
     */
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
            //students.sort(Comparator.comparing(Student::getName));
    
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
                    if (student.getUserRole() == UserRole.STUDENT) {
                        if (filter.size() == 1 || (filter.size() > 1 && student.getName().equals(filter.get(1)))) {
                            csvLines.append(String.join(",",
                                    camp.getCampInformation().getCampName(),
                                    student.getStudentID(),
                                    student.getName(),
                                    student.getUserRole().toString(),
                                    student.getFaculty().toString()));
                        }
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