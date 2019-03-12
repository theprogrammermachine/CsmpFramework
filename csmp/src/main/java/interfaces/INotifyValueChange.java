package interfaces;

import models.Codes;

public interface INotifyValueChange {
    void notifyValueChanged(String ctrlName, Codes.Value value);
}
