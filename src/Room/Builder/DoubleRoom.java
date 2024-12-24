package Room.Builder;

public class DoubleRoom extends RoomBuilder{

    @Override
    public void buildRoomType() {
        
       room.roomType = "Double";
        
    }

    @Override
    public void buildeBoardingOptions() {
           
        
    }

    @Override
    public void buildRoomPrice() {
        
        room.price += 900;
        
    }

    
}
