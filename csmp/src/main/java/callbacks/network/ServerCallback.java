package callbacks.network;

import models.Packet;

public interface ServerCallback {
    void onRequestSuccess(Packet packet);
    void onServerFailure();
    void onConnectionFailure();
}
