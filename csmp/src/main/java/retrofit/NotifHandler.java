package retrofit;

import models.Packet;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface NotifHandler {
    @POST("notif/notify_bot_notif_received")
    Call<Packet> notifyBotNotifReceived(@Body Packet packet);
}