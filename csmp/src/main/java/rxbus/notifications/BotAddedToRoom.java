package rxbus.notifications;

import models.Entities;

public class BotAddedToRoom {

    private Entities.Workership workership;

    public BotAddedToRoom(Entities.Workership workership) {
        this.workership = workership;
    }

    public Entities.Workership getWorkership() {
        return workership;
    }
}
