package controllers;

import java.util.ArrayList;
import java.util.List;
import services.CampStaffService;
import services.CampStudentService;
import controllers.CampServiceController;
import models.Camp;

/* The campController */
public class CampServiceController {
    public static List<Camp> camps = new ArrayList<>();
    public CampServiceController(List<Camp> camps) {
        CampServiceController.camps = camps;
    }
    public CampStaffService campStaffService = new CampStaffService();
    public CampStudentService campStudentService = new CampStudentService();
}