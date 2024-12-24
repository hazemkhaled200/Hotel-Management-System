package Room.Decorator;

import Room.Room;

public class BedBreakfast extends RoomDecorator{

    public BedBreakfast(Room room){
        super(room);
        //this.room = room;
    }

    @Override
    public void setAvailable() {
        super.setAvailable();
    }

    @Override
    public void setBoardingOption() {
        super.setBoardingOption();
        room.boardingOption = "Bed And Breakfast";
    }

    @Override
    public void setPrice() {
        super.setPrice();
        room.price += 100;
    }

    @Override
    public void setRoomType() {
        super.setRoomType();
    }
    
}
