package rxbus.notifications;

import models.Entities;

import java.util.List;

public class RoomsCreated {

    private long complexId;
    private List<Entities.Room> rooms;

    public RoomsCreated(long complexId, List<Entities.Room> rooms) {
        this.complexId = complexId;
        this.rooms = rooms;
    }

    public long getComplexId() {
        return complexId;
    }

    public List<Entities.Room> getRooms() {
        return rooms;
    }
}
