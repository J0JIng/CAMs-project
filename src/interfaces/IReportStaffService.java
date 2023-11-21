package interfaces;

import java.util.List;
import java.util.Map;

import models.Camp;

public interface IReportStaffService {

    public boolean generateReport(List<String> filter,List<Camp> camps,Map<String, String> filePathsMap);

    public boolean generatePerformanceReport(List<String> filter,List<Camp> camps,Map<String, String> filePathsMap);

    public boolean generateEnquiryReport(List<String> filter,List<Camp> camps,Map<String, String> filePathsMap);
    
}
