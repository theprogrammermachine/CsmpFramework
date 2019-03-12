package rxbus.notifications;

import models.Entities;

public class InviteResolved {

    private Entities.Invite invite;

    public InviteResolved(Entities.Invite invite) {
        this.invite = invite;
    }

    public Entities.Invite getInvite() {
        return invite;
    }
}
