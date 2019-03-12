package retrofit;

import models.Packet;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface BotHandler {
    @POST("pulse/bot_send_bot_view")
    Call<Packet> botSendBotView(@Body Packet packet);
    @POST("pulse/bot_update_bot_view")
    Call<Packet> botUpdateBotView(@Body Packet packet);
    @POST("pulse/bot_animate_bot_view")
    Call<Packet> botAnimateBotView(@Body Packet packet);
    @POST("pulse/bot_run_commands_on_bot_view")
    Call<Packet> botRunCommandsOnBotView(@Body Packet packet);
}
