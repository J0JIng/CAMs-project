package models;

import enums.MessageStatus;

public class Enquiry {
    private int enquiryID;
    private String campName; 
    private String senderID;
    private String responderID;
    private MessageStatus enquiryStatus;
    private String enquiryMessage;
    private String enquiryResponse;

    public Enquiry(int enquiryID, String senderID, String campName, String message){

        this.enquiryID = enquiryID;
        this.senderID = senderID;
        this.enquiryMessage = message;
        this.enquiryStatus = MessageStatus.DRAFT;
        this.enquiryResponse = null;  
        this.responderID = null;  

        public int getEnquiryID() {
            return enquiryID;
        }
    
        public void setEnquiryID(int enquiryID) {
            this.enquiryID = enquiryID;
        }
    
        public String getSenderID() {
            return senderID;
        }
    
        public void setSenderID(String senderID) {
            this.senderID = senderID;
        }
        public String getcampName() {
            return campName;
        }
    
        public void setcampName(String campName) {
            this.campName= campName;
        }
    
        public String getResponderID() {
            return responderID;
        }
    
        public void setResponderID(String responderID) {
            this.responderID = responderID;
        }
    
        public MessageStatus getEnquiryStatus() {
            return enquiryStatus;
        }
    
        public void setEnquiryStatus(MessageStatus enquiryStatus) {
            this.enquiryStatus = enquiryStatus;
        }
    
        public String getEnquiryMessage() {
            return enquiryMessage;
        }
    
        public void setEnquiryMessage(String enquiryMessage) {
            this.enquiryMessage = enquiryMessage;
        }
    
        public String getEnquiryResponse() {
            return enquiryResponse;
        }
    
        public void setEnquiryResponse(String enquiryResponse) {
            this.enquiryResponse = enquiryResponse;
        }


    }
    
}
