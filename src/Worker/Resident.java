package Worker;

public class Resident {
    
    String name, contactInfo, roomType, serviceReceived, boardingOptions;
    int id, durationOfStay;
    public void setDurationOfStay(int durationOfStay) {
        this.durationOfStay = durationOfStay;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }
    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }
    public void setServiceReceived(String serviceReceived) {
        this.serviceReceived = serviceReceived;
    }
    public void setBoardingOptions(String boardingOptions) {
        this.boardingOptions = boardingOptions;
    }
    public void setId(int id) {
        this.id = id;
    }
    
}
