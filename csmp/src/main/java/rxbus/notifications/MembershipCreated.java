package rxbus.notifications;

import models.Entities;

public class MembershipCreated {

    private Entities.Membership membership;

    public MembershipCreated(Entities.Membership membership) {
        this.membership = membership;
    }

    public Entities.Membership getMembership() {
        return membership;
    }
}
