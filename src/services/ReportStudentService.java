import models.Student;
import stores.DataStore;

public class ReportStudentService {
    
    private String currentCommitteeMemberId; // ID of the committee member
    private CampStudentService campStudentService;
    private CampStaffService campStaffService;
    private EnquiryService enquiryService;
    private CsvFileDataService csvFileDataService; // To write to csv

    public ReportStudentService(String currentCommitteeMemberId, CampStudentService campStudentService, CampStaffService campStaffService, EnquiryService enquiryService, CsvFileDataService csvFileDataService) {
        this.currentCommitteeMemberId = currentCommitteeMemberId;
        this.campStudentService = campStudentService;
        this.campStaffService = campStaffService;
        this.enquiryService = enquiryService;
        this.csvFileDataService = csvFileDataService;
    }

    
    public void generateCampReport(String campName, String roleFilter) {
        Map<String, Camp> camps = DataStore.getCampData();
        Camp camp = camps.get(campName);
    
        if (camp == null) {
            System.out.println("No such camp found: " + campName);
            return;
        }
        //extract commitee members of certain camp        
        List<String> committeeMembers = DataStore.getCampToRegisteredCampCommitteeData().get(campName);
        if (committeeMembers == null || !committeeMembers.contains(currentCommitteeMemberId)) {
            System.out.println("Current user is not a committee member for this camp: " + campName);
            return;
        }
        //if committee of camp...
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
                    if (!csvLines.isEmpty()) {
                        String filePath = campName.replaceAll("\\s+", "_") + "_camp_report.csv";
                        boolean success = csvFileDataService.writeCsvFile(filePath, headers, csvLines);
                        if (success) {
                            System.out.println("Camp Report for " + campName + " generated successfully.");
                        } else {
                            System.out.println("Failed to generate the camp report for " + campName);
                        }
                    } else {
                        System.out.println("No students found for " + campName);
                    }
                }
                }
    
        
    
      

    public void generateEnquiryReport(String campName) {
        Map<String, Camp> camps = DataStore.getCampData();
        Camp camp = camps.get(campName);

        if (camp == null) {
            System.out.println("No such camp found: " + campName);
            return;
        }

        // Check if the current user is a committee member for this camp
        List<String> committeeMembers = DataStore.getCampToRegisteredCampCommitteeData().get(campName);
        if (committeeMembers == null || !committeeMembers.contains(currentCommitteeMemberId)) {
            System.out.println("Current user is not a committee member for this camp: " + campName);
            return;
        }

        List<Enquiry> enquiries = enquiryService.getAllEnquiriesForCamp(camp);

        List<String> headers = List.of(
            "Student ID", "Enquiry ID", "Message", "Status", "Response", "Responder ID"
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
