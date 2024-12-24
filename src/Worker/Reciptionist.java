package Worker;

import Room.Builder.RoomBuilder;
import Room.Decorator.BedBreakfast;
import Room.Decorator.FullBoard;
import Room.Decorator.HalfBoard;
import Room.Decorator.RoomDecorator;
import Room.Room;

public class Reciptionist extends Worker {

    Resident res = new Resident();

    // *****************************************
    // **********resident management************
    // *****************************************
    public void addResidents(String name, String contact, String duration, String room, String services) {
        res.name = name;
        res.contactInfo = contact;
        // res.durationOfStay = duration;
        // res.roomAssignment = room;
        res.serviceReceived = services;
    }

    public void deleteResident(String id) {

    }

    public void editResident(String id) {

    }
    // *****************************************
    // *****************************************

    public double calculateResidentCost(String id) {
        return 0;
    }// calculate residents costs based on duration, room type and decorations.

    public void roomAssignment() {

    }// book an available room for a client.

    public void residentInfo(String id) {

    }// show specific resident.

    // *****************************************
    // **********Builder Functions**************
    // *****************************************

    RoomBuilder builder;
    RoomDecorator decorator;

    public void setBuilder(RoomBuilder builder) {
        this.builder = builder;
    }

    public void buildRoom() {
        builder.buildRoomType();
        builder.buildRoomPrice();
    }

    public void decorateRoom(String decoration) {

        if (decoration.equals("full board")) {
            decorator = new FullBoard(builder.getRoom());
        } else if (decoration.equals("half board")) {
            decorator = new HalfBoard(builder.getRoom());
        } else if (decoration.equals("bed and breakfast")) {
            decorator = new BedBreakfast(builder.getRoom());
        } else {
            System.out.println("Wrong Input!");
            decorator = new BedBreakfast(builder.getRoom()); // Because this is the normal
        }

        decorator.setBoardingOption();
        decorator.setPrice();

    }

    public Room roomKey() {
        return decorator.getRoom();
    }

    // *****************************************
    // *****************************************

}
