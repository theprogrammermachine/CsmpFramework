package retrofit;

import models.Packet;
import retrofit2.Call;
import retrofit2.http.POST;

public interface ComplexHandler {
    @POST("complex/bot_get_workerships")
    Call<Packet> botGetWorkerships();
}
