package interfaces;

import java.util.List;
import java.util.Map;

import models.Camp;

/**
 * The {@link IReportStudentService} interface defines method(s) for students who are camp committee members 
 * for generating report of a camp.
 */
public interface IReportStudentService {

	/**
     * Generates a report for a specific camp based on the specified filters and file paths.
     *
     * @param filter        a {@link List} of filter criteria
     * @param camp          the {@link Camp} object for which the report is generated
     * @param filePathsMap  a {@link Map} of file paths for report generation
     * @return true if the report is generated successfully, false otherwise
     */
    public boolean generateReport(List<String> filter, Camp camp, Map<String, String> filePathsMap);
    
}
