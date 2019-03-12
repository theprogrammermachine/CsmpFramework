package rxbus.notifications;

import models.Entities;

public class MessageSeen {

    private Entities.Message message;

    public MessageSeen(Entities.Message message) {
        this.message = message;
    }

    public Entities.Message getMessage() {
        return message;
    }
}
