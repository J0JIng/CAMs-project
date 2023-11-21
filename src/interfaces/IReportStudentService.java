package interfaces;

import java.util.List;
import java.util.Map;

import models.Camp;

public interface IReportStudentService {

    public boolean generateReport(List<String> filter, Camp camp, Map<String, String> filePathsMap);
    
}
