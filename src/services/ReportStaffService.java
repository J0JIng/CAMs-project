package services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import models.Camp;
import models.Enquiry;
import models.Student;
import services.CsvFileDataService;
import stores.DataStore;
import enums.UserRole;
import services.CampStaffService;
import services.EnquiryService;


public class ReportStaffService {
    
    private String currentStaffId; //check camp staff created 
    private CsvFileDataService csvFileDataService; //to write to csv 
    private UserRole currentUserRole; //filter base on attendeee,campcommittee
    private CampStaffService campStaffService;
    private EnquiryService enquiryService;

    public ReportStaffService(String currentStaffId, CsvFileDataService csvFileDataService, UserRole currentUserRole, CampStaffService campStaffService, EnquiryService enquiryService) {
        this.currentStaffId = currentStaffId;
        this.csvFileDataService = csvFileDataService;
        this.currentUserRole = currentUserRole;
        this.campStaffService = campStaffService; 
        this.enquiryService = enquiryService;
    }

    public void generateCampReport(String campName, String roleFilter) {
    Map<String, Camp> camps = DataStore.getCampData();
    boolean campExists = false;
    boolean staffCreatedCamp = false;

    for (Camp camp : camps.values()) {
        if (camp.getCampInformation().getCampName().equals(campName)) {
            campExists = true;
            if (camp.getCampInformation().getCampStaffInCharge().equals(currentStaffId)) {
                staffCreatedCamp = true;
                List<Student> registeredStudents = campStaffService.getCampAttendeeList(camp);
                registeredStudents.sort(Comparator.comparing(Student::getName));

                List<String> headers = List.of(
                         "Student ID", 
                                     "Student Name", 
                                     "Student Role",
                                     "Camp Name", 
                                     "Camp Start Date", 
                                     "Camp End Date", 
                                     "Registration Closing Date",
                                     "Location", 
                                     "Total Slots", 
                                     "Camp Committee Slots", 
                                     "Camp Description",
                                     "Staff In Charge",
                                     "Faculty Group"
                                    );

                List<String> csvLines = new ArrayList<>();

                for (Student student : registeredStudents) {
                    if (student.getCommitteeStatus().equals(roleFilter)) {
                        String line = String.join(",",
                        student.getStudentID(),
                        student.getName(),
                        student.getCommitteeStatus(),
                        camp.getCampInformation().getCampName(),
                        String.valueOf(camp.getCampInformation().getCampStartDate()), 
                        String.valueOf(camp.getCampInformation().getCampEndDate()), 
                        String.valueOf(camp.getCampInformation().getCampRegistrationClosingDate()),
                        camp.getCampInformation().getCampLocation(),
                        String.valueOf(camp.getCampInformation().getCampTotalSlots()),
                        String.valueOf(camp.getCampInformation().getCampCommitteeSlots()),
                        camp.getCampInformation().getCampDescription(),
                        camp.getCampInformation().getCampStaffInCharge(),
                        String.valueOf(camp.getCampInformation().getFacultyGroup())
                        );
                        csvLines.add(line);
                    }
                }

                if (!csvLines.isEmpty()) {
                    String filePath = camp.getCampInformation().getCampName().replaceAll("\\s+", "_") + "_report.csv";
                    boolean success = csvFileDataService.writeCsvFile(filePath, headers, csvLines);
                    if (success) {
                        System.out.println("Camp Report for " + camp.getCampInformation().getCampName() + " generated successfully.");
                    } else {
                        System.out.println("Failed to generate the camp report for " + camp.getCampInformation().getCampName());
                    }
                } else {
                    System.out.println("No registered students found for " + camp.getCampInformation().getCampName() + " with the specified role.");
                }
                break; 
            }
        }
    }

    if (!campExists) {
        System.out.println("No such camp found: " + campName);
    } else if (!staffCreatedCamp) {
        System.out.println("Staff did not create this camp: " + campName);
    }
}

// commiteemember id,name, points 
public void generateCommitteePerformanceReport(String campName) {
    Map<String, Camp> camps = DataStore.getCampData();

    boolean campExists = false;
    boolean staffCreatedCamp = false;
    for (Camp camp : camps.values()) {
        if (camp.getCampInformation().getCampName().equals(campName)) {
            campExists = true;
             if (camp.getCampInformation().getCampStaffInCharge().equals(currentStaffId)) {
                staffCreatedCamp = true;
                 List<Student> committeeMembers= campStaffService.getCampCommitteeList(camp);
                 committeeMembers.sort(Comparator.comparing(Student::getName));
                    List<String> headers = List.of(
                                     "Committee Member ID", 
                                     "Committee Member Name",
                                     "Points"
                                    );

    List<String> csvLines = new ArrayList<>();

     for (Student student : committeeMembers) {
        String line = String.join(",",
             student.getStudentID(),
             student.getName(),
            String.valueOf(student.getStudentPoints())
        );
        csvLines.add(line);
    }
            if (!csvLines.isEmpty()) {
                    String filePath = campName.replaceAll("\\s+", "_") + "_committee_performance.csv";
                    boolean success = csvFileDataService.writeCsvFile(filePath, headers, csvLines);
                    if (success) {
                        System.out.println("Committee Performance Report for " + campName + " generated successfully.");
                    } else {
                        System.out.println("Failed to generate the committee performance report for " + campName);
                    }
                } else {
                    System.out.println("No committee members found for " + campName);
                }
                break; 
            }
        }
    }

        if (!campExists) 
                  System.out.println("No such camp found: " + campName);     
        else if (!staffCreatedCamp) 
                 System.out.println("Staff did not create this camp: " + campName);
     }

    // student id, enquiryid, message,status,response,responderid
    public void generateEnquiryReport(String campName) {
        Map<String, Camp> camps = DataStore.getCampData();
        Camp camp = camps.get(campName);
    
        if (camp == null) {
            System.out.println("No such camp found: " + campName);
            return; 
        }
        List<Enquiry> enquiries = enquiryService.getAllEnquiriesForCamp(camp);
    
        List<String> headers = List.of(
            "Student ID",
            "Enquiry ID",
            "Message",
            "Status",
            "Response",
            "Responder ID"
        );
    
        List<String> csvLines = new ArrayList<>();
    
        for (Enquiry enquiry : enquiries) {
    
            String line = String.join(",",
                enquiry.getSenderID(),
                String.valueOf(enquiry.getEnquiryID()), 
                enquiry.getEnquiryMessage(),            
                enquiry.getEnquiryStatus().toString(),  
                enquiry.getEnquiryResponse(),          
                enquiry.getResponderID()                
            );
            csvLines.add(line);
        }
    
        if (!csvLines.isEmpty()) {
            String filePath = campName.replaceAll("\\s+", "_") + "_enquiries.csv";
            boolean success = csvFileDataService.writeCsvFile(filePath, headers, csvLines);
            if (success) {
                System.out.println("Enquiry Report for " + campName + " generated successfully.");
            } else {
                System.out.println("Failed to generate the enquiry report for " + campName);
            }
        } else {
            System.out.println("No enquiries found for " + campName);
        }
    }


}
