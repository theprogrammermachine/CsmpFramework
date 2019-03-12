package rxbus.notifications;

import models.Entities;

public class BotProfileUpdated {

    private Entities.Bot bot;

    public BotProfileUpdated(Entities.Bot bot) {
        this.bot = bot;
    }

    public Entities.Bot getBot() {
        return bot;
    }
}
