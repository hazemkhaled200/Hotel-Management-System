package Room.Decorator;

import Room.Room;

public class FullBoard extends RoomDecorator{

    public FullBoard(Room room){
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
        room.boardingOption = "Full Board";
    }

    @Override
    public void setPrice() {
        super.setPrice();
        room.price += 300;
    }

    @Override
    public void setRoomType() {
        super.setRoomType();
    }

    
    
}
