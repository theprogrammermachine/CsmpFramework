package rxbus.notifications;

import models.Entities;

public class ComplexProfileUpdated {

    private Entities.Complex complex;

    public ComplexProfileUpdated(Entities.Complex complex) {
        this.complex = complex;
    }

    public Entities.Complex getComplex() {
        return complex;
    }
}
