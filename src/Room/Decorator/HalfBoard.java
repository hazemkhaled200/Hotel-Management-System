package Room.Decorator;

import Room.Room;

public class HalfBoard extends RoomDecorator{

    public HalfBoard(Room room){
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
        room.boardingOption = "Half Board";
    }

    @Override
    public void setPrice() {
        super.setPrice();
        room.price += 200;
    }

    @Override
    public void setRoomType() {
        super.setRoomType();
    }
    
}
