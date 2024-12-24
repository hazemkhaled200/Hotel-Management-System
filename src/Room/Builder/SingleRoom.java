package Room.Builder;

public class SingleRoom extends RoomBuilder{

    @Override
    public void buildRoomType() {
        
       room.roomType = "Single";
        
    }

    @Override
    public void buildeBoardingOptions() {}

    @Override
    public void buildRoomPrice() {
        
        room.price += 500;
        
    }

    
    
    
}
