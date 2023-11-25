package services;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;

import interfaces.IEnquiryResponderService;
import interfaces.ICampStaffService;
import interfaces.IReportStaffService;
import enums.UserRole;
import models.Enquiry;
import models.Camp;
import models.Student;

public class ReportStaffService implements IReportStaffService {
    
    private final static CsvFileDataService csvFileDataService = new CsvFileDataService();
    private final static ICampStaffService campStaffService = new CampStaffService();
    private final static IEnquiryResponderService enquiryResponderService = new EnquiryResponderService();

    public ReportStaffService(){
    }

    // generate Report for camps
    @Override
    public boolean generateReport(List<String> filter,List<Camp> camps,Map<String, String> filePathsMap) {
        // Sort camps alphabetically based on their names
        camps.sort(Comparator.comparing(camp -> camp.getCampInformation().getCampName()));

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
        boolean success = csvFileDataService.writeCsvFile(filePathsMap.get("reportStaff"), headers,csvLines);
        return success;
    }

    @Override
    public boolean generatePerformanceReport(List<String> filter,List<Camp> camps,Map<String, String> filePathsMap) {
        // Sort camps alphabetically based on their names
        camps.sort(Comparator.comparing(camp -> camp.getCampInformation().getCampName()));

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
        boolean success = csvFileDataService.writeCsvFile(filePathsMap.get("reportStaff"), headers,csvLines);
        return success;
    }

    @Override
    public boolean generateEnquiryReport(List<String> filter,List<Camp> camps,Map<String, String> filePathsMap) {
        // Sort camps alphabetically based on their names
        camps.sort(Comparator.comparing(camp -> camp.getCampInformation().getCampName()));

        // Get headers for CSV file
        List<String> headers = generateEnquiryReportHeaderLine(filter);
    
        // Get CSV line for CSV file
        List<String> csvLines = new ArrayList<>();

        for (Camp camp : camps) {
            // Get map of Enquiries
            Map<Integer,Enquiry> enquiryData = enquiryResponderService.getAllEnquiriesForCamp(camp);
            csvLines.add(generateEnquiryReportCsvLine(filter, camp, enquiryData));
        }
    
        // Write to CSV file
        boolean success = csvFileDataService.writeCsvFile(filePathsMap.get("reportStaff"), headers,csvLines);
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

    public List<String> generatePerformanceReportHeaderLine(List<String> filter) {
        List<String> headers = new ArrayList<>();

        if (!filter.isEmpty()) {
            String firstFilter = filter.get(0);

            switch (firstFilter) {
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
        }
        return headers;
    }
    

    public String generatePerformanceReportCsvLine(List<String> filter, Camp camp, List<Student> students) {
        StringBuilder csvLines = new StringBuilder();
        boolean firstStudent = true;
        
        if (!filter.isEmpty()) {
            String firstFilter = filter.get(0);

            for (Student student : students) {
                if (!firstStudent) {
                    csvLines.append(System.lineSeparator()); // Add a new line for each student
                } else {
                    firstStudent = false;
                }
        
                switch (firstFilter) {

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
                        if (student.getUserRole() == UserRole.COMMITTEE  && filter.size()==1 ) {
                            csvLines.append(String.join(",",
                            camp.getCampInformation().getCampName(),
                            student.getName()));
                        }else if (student.getUserRole() == UserRole.COMMITTEE && filter.size()>1 && student.getName().equals(filter.get(1))) {
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
        }

        return csvLines.toString();
    }

    public List<String> generateEnquiryReportHeaderLine(List<String> filter){
        List<String> headers = new ArrayList<>();
        
        if (!filter.isEmpty()) {
            String firstFilter = filter.get(0);

            switch (firstFilter) {
            case "No Filter":
                headers.addAll(List.of(
                    "Camp Name",
                    "Enquiry ID",
                    "Sender ID",
                    "Responder ID",
                    "Enquiry Status",
                    "Enquiry Message",
                    "Enquiry Response"));
                break;
            
            case "Enquiry ID":
                headers.addAll(List.of(
                    "Camp Name",
                    "Enquiry ID"));
                break;
    
            case "Sender ID":
                headers.addAll(List.of(
                    "Camp Name",
                    "Enquiry ID",
                    "Sender ID"));
                break;
    
            case "Responder ID":
                headers.addAll(List.of(
                    "Camp Name",
                    "Enquiry ID",
                    "Responder ID"));
                break;

            case "Enquiry Status":
                headers.addAll(List.of(
                    "Camp Name",
                    "Enquiry ID",
                    "Enquiry Status"));
                break;
            
            case "Enquiry Message":
                headers.addAll(List.of(
                    "Camp Name",
                    "Enquiry ID",
                    "Enquiry Status"));
                break;

            case "Enquiry Response":
                headers.addAll(List.of(
                    "Camp Name",
                    "Enquiry ID",
                    "Enquiry Status"));
                break;

            default:
                break;
            }
        }
        return headers;
    }

    public String generateEnquiryReportCsvLine(List<String> filter, Camp camp, Map<Integer, Enquiry> enquiries) {
        StringBuilder csvLines = new StringBuilder();
        boolean firstEnquiry = true;

        if (!filter.isEmpty()) {
            String firstFilter = filter.get(0);
    
            for (Enquiry enquiry : enquiries.values()) {
                if (!firstEnquiry) {
                    csvLines.append(System.lineSeparator()); // Add a new line for each student
                } else {
                    firstEnquiry = false;
                }
        
                switch (firstFilter) {
                    case "No Filter":
                        csvLines.append(String.join(",",
                                camp.getCampInformation().getCampName(),
                                (enquiry.getEnquiryID() != 0 ? String.valueOf(enquiry.getEnquiryID()) : "N/A"),
                                enquiry.getSenderID(),
                                enquiry.getResponderID(),
                                enquiry.getEnquiryStatus().toString(),
                                enquiry.getEnquiryMessage(),
                                (enquiry.getEnquiryResponse()!= null && 
                                !enquiry.getEnquiryMessage().isEmpty() ? enquiry.getEnquiryResponse() : "NA")));
                        break;
        
                    case "Enquiry ID":
                        csvLines.append(String.join(",",
                                camp.getCampInformation().getCampName(),
                                (enquiry.getEnquiryID() != 0 ? String.valueOf(enquiry.getEnquiryID()) : "N/A")));
                        break;

                    case "Sender ID":
                        csvLines.append(String.join(",",
                                camp.getCampInformation().getCampName(),
                                (enquiry.getEnquiryID() != 0 ? String.valueOf(enquiry.getEnquiryID()) : "N/A"),
                                enquiry.getSenderID()));
                        break;

                    case "Responder ID":
                        csvLines.append(String.join(",",
                                camp.getCampInformation().getCampName(),
                                (enquiry.getEnquiryID() != 0 ? String.valueOf(enquiry.getEnquiryID()) : "N/A"),
                                enquiry.getResponderID()));
                        break;
                    
                    case "Enquiry Status":
                        csvLines.append(String.join(",",
                                camp.getCampInformation().getCampName(),
                                (enquiry.getEnquiryID() != 0 ? String.valueOf(enquiry.getEnquiryID()) : "N/A"),
                                enquiry.getEnquiryStatus().toString()));
                        break;
                    
                    case "Enquiry Message":
                        csvLines.append(String.join(",",
                                camp.getCampInformation().getCampName(),
                                (enquiry.getEnquiryID() != 0 ? String.valueOf(enquiry.getEnquiryID()) : "N/A"),
                                enquiry.getEnquiryMessage()));
                        break;

                    case "Enquiry Response":
                        csvLines.append(String.join(",",
                                camp.getCampInformation().getCampName(),
                                (enquiry.getEnquiryID() != 0 ? String.valueOf(enquiry.getEnquiryID()) : "N/A"),
                                (enquiry.getEnquiryResponse()!= null && 
                                !enquiry.getEnquiryMessage().isEmpty() ? enquiry.getEnquiryResponse() : "NA")));
                        break;

                    default:
                        break;
                }
            }
        }
        return csvLines.toString();
    }
}



