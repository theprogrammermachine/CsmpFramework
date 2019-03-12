package rxbus.notifications;

import models.Entities;

public class UserRequestedBotView {

    private long complexId;
    private long roomId;
    private Entities.User user;

    public UserRequestedBotView(long complexId, long roomId, Entities.User user) {
        this.complexId = complexId;
        this.roomId = roomId;
        this.user = user;
    }

    public long getComplexId() {
        return complexId;
    }

    public long getRoomId() {
        return roomId;
    }

    public Entities.User getUser() {
        return user;
    }
}
