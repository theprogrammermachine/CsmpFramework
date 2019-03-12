package rxbus.notifications;

import models.Entities;

public class WorkerUpdated {

    private Entities.Workership workership;

    public WorkerUpdated(Entities.Workership workership) {
        this.workership = workership;
    }

    public Entities.Workership getWorkership() {
        return workership;
    }
}
