package Room.Builder;
import Room.Room;

public abstract class RoomBuilder{

    Room room = new Room();

    abstract public void buildRoomType();
    abstract public void buildeBoardingOptions();
    abstract public void buildRoomPrice();

    public Room getRoom(){
        return room;
    }
    
} 
