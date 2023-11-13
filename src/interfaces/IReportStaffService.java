package interfaces;

import models.Camp;

public interface IReportStaffService extends IReportService {
    /**
     * Generates a report of the list of students attending a specific camp.
     *
     * @param camp    the {@link Camp} object for which to generate the report
     * @param filters filters for the report (e.g., attendee, camp committee)
     * @return true if the report is created successfully, false otherwise
     */
    public boolean generateCampAttendeeReport(Camp camp, String filters);

    /**
     * Generates a performance report of camp committee members for a specific camp.
     *
     * @param camp the {@link Camp} object for which to generate the performance report
     * @return true if the report is created successfully, false otherwise
     */
    public boolean generateCampCommitteePerformanceReport(Camp camp);
}
