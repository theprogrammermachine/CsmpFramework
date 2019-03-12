package callbacks.terminal;

import models.Entities;

public interface OnTextMessageCreated {
    void textMessageCreated(Entities.Message textMessage);
}
