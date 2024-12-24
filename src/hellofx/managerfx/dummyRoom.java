package hellofx.managerfx;

public class dummyRoom {
    
    String roomType, isAvailable, boardingOptions;
    int id;
    double price;
    int empId;
    int resId;

    public void setEmpId(int empId) {
        this.empId = empId;
    }
    public void setResId(int resId) {
        this.resId = resId;
    }
    public int getEmpId() {
        return empId;
    }
    public int getResId() {
        return resId;
    }
    //*****************************************
    //***************Setters*******************
    //*****************************************
    public void setId(int id){
        this.id = id;
    }
    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }
    public void setBoardingOptions(String boardingOptions) {
        this.boardingOptions = boardingOptions;
    }
    public void setIsAvailable(String isAvailable) {
        this.isAvailable = isAvailable;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    
    //*****************************************
    //*****************************************

    public String getRoomType() {
        return roomType;
    }
    public String getBoardingOptions() {
        return boardingOptions;
    }
    public String getIsAvailable() {
        return isAvailable;
    }
    public int getId() {
        return id;
    }
    public double getPrice() {
        return price;
    }
    
}
