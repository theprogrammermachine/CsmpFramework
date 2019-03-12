package rxbus.notifications;

import models.Entities;

import java.util.List;

public class ComplexesCreated {

    private List<Entities.Complex> complexes;

    public ComplexesCreated(List<Entities.Complex> complexes) {
        this.complexes = complexes;
    }

    public List<Entities.Complex> getComplexes() {
        return complexes;
    }
}
