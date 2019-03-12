package rxbus.notifications;

import models.Entities;

public class RoomRemoved {

    private Entities.Room room;

    public RoomRemoved(Entities.Room room) {
        this.room = room;
    }

    public Entities.Room getRoom() {
        return room;
    }
}
