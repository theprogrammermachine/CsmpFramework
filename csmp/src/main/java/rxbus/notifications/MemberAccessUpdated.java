package rxbus.notifications;

import models.Entities;

public class MemberAccessUpdated {

    private Entities.MemberAccess memberAccess;

    public MemberAccessUpdated(Entities.MemberAccess memberAccess) {
        this.memberAccess = memberAccess;
    }

    public Entities.MemberAccess getMemberAccess() {
        return memberAccess;
    }
}
