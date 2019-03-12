package callbacks.network;

import models.Packet;

public interface ServerCallback2 {
    void onRequestSuccess(Packet packet);
    void onLogicalError(String errorCode);
    void onServerFailure();
    void onConnectionFailure();
}
