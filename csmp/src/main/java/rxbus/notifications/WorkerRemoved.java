package rxbus.notifications;

import models.Entities;

public class WorkerRemoved {

    private Entities.Workership workership;

    public WorkerRemoved(Entities.Workership workership) {
        this.workership = workership;
    }

    public Entities.Workership getWorkership() {
        return workership;
    }
}
