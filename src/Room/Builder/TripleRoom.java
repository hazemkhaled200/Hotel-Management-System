package Room.Builder;

public class TripleRoom extends RoomBuilder{

    @Override
    public void buildRoomType() {
        
       room.roomType = "Triple";
        
    }

    @Override
    public void buildeBoardingOptions() {
           
        
    }

    @Override
    public void buildRoomPrice() {
        
        room.price += 1300;
        
    }
}
