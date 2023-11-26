package interfaces;

import java.util.List;
import java.util.Map;

import models.Camp;

/**
 * The {@link IReportStaffService} interface defines methods for staff 
 * for generating various reports types related to camps.
 */
public interface IReportStaffService {

	/**
     * Generates a general report based on the specified filters, camps, and file paths.
     *
     * @param filter        a {@link List} of filter criteria
     * @param camps         a {@link List} of {@link Camp} objects to include in the report
     * @param filePathsMap  a {@link Map} of file paths for report generation
     * @return true if the report is generated successfully, false otherwise
     */
    public boolean generateReport(List<String> filter,List<Camp> camps,Map<String, String> filePathsMap);

    /**
     * Generates a performance report based on the specified filters, camps, and file paths.
     *
     * @param filter        a {@link List} of filter criteria
     * @param camps         a {@link List} of {@link Camp} objects to include in the report
     * @param filePathsMap  a {@link Map} of file paths for report generation
     * @return true if the performance report is generated successfully, false otherwise
     */
    public boolean generatePerformanceReport(List<String> filter,List<Camp> camps,Map<String, String> filePathsMap);

    /**
     * Generates an enquiry report based on the specified filters, camps, and file paths.
     *
     * @param filter        a {@link List} of filter criteria
     * @param camps         a {@link List} of {@link Camp} objects to include in the report
     * @param filePathsMap  a {@link Map} of file paths for report generation
     * @return true if the enquiry report is generated successfully, false otherwise
     */
    public boolean generateEnquiryReport(List<String> filter,List<Camp> camps,Map<String, String> filePathsMap);
    
}
