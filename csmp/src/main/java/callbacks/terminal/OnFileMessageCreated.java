package callbacks.terminal;

import models.Entities;

public interface OnFileMessageCreated {
    void fileMessageCreated(Entities.Message message);
}
