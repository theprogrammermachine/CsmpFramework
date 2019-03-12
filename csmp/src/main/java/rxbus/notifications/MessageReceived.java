package rxbus.notifications;

import models.Entities;

public class MessageReceived {

    private Entities.Message message;
    private Entities.MessageLocal messageLocal;

    public MessageReceived(Entities.Message message, Entities.MessageLocal messageLocal) {
        this.message = message;
        this.messageLocal = messageLocal;
    }

    public Entities.Message getMessage() {
        return message;
    }

    public Entities.MessageLocal getMessageLocal() {
        return messageLocal;
    }
}
