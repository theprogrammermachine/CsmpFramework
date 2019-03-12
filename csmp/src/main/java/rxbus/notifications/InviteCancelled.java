package rxbus.notifications;

import models.Entities;

public class InviteCancelled {

    private Entities.Invite invite;

    public InviteCancelled(Entities.Invite invite) {
        this.invite = invite;
    }

    public Entities.Invite getInvite() {
        return invite;
    }
}
