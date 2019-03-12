package rxbus.notifications;

import models.Entities;

public class UserJointComplex {

    private Entities.Membership membership;

    public UserJointComplex(Entities.Membership membership) {
        this.membership = membership;
    }

    public Entities.Membership getMembership() {
        return membership;
    }
}
