package retrofit;

import models.Packet;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface MessageHandler {
    @POST("message/bot_create_text_message")
    Call<Packet> botCreateTextMessage(@Body Packet packet);
    @POST("message/bot_create_file_message")
    Call<Packet> botCreateFileMessage(@Body Packet packet);
}
