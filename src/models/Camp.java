package models;

import java.util.ArrayList;
import java.util.List;

public class Camp {
    
    private CampInformation campInformation;
    private boolean campVisibility;
    
    public Camp(CampInformation info) {
        this.campInformation = info;
    }
    public boolean getVisibility() {
        return this.campVisibility;
    }
    public void setVisibility(boolean bool) {
        this.campVisibility = bool;
    }
    public CampInformation getCampInformation() {
        return this.campInformation;
    }
    public void setCampInformation(CampInformation info) {
        this.campInformation = info;
    }
    
}