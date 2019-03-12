package rxbus.notifications;

import models.Entities;

public class ContactCreated {

    private Entities.Contact contact;

    public ContactCreated(Entities.Contact contact) {
        this.contact = contact;
    }

    public Entities.Contact getContact() {
        return contact;
    }
}
