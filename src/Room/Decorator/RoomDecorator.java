package Room.Decorator;

import Room.Room;

public abstract class RoomDecorator extends Room{

    Room room;

    RoomDecorator(Room room){
        this.room = room;
    }

    /*public void setRoom(Room room){
        this.room = room;
    }*/

    @Override
    public void setBoardingOption() {}

    @Override
    public void setPrice() {}

    public Room getRoom(){
        room.isAvailable = false;
        return room;
    }
    
}
