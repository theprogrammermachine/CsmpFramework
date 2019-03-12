package helpers;

import models.Entities;

import java.util.List;

public class DatabaseHelper {

    private static Entities.Session singleSession;
    public static Entities.Session getSingleSession() {
        return singleSession;
    }

    private static List<Entities.Workership> workerships;
    public static List<Entities.Workership> getWorkerships() {
        return workerships;
    }
    public static void setWorkerships(List<Entities.Workership> workerships) {
        DatabaseHelper.workerships = workerships;
    }

    public static  void setup(long sessionId, String token) {
        DatabaseHelper.singleSession = new Entities.Session();
        DatabaseHelper.singleSession.setSessionId(sessionId);
        DatabaseHelper.singleSession.setToken(token);
    }
}
