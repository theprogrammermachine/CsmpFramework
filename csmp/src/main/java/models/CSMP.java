package models;

import callbacks.network.ServerCallback;
import callbacks.network.ServerCallback2;
import callbacks.terminal.OnBotViewInitialized;
import callbacks.terminal.OnFileMessageCreated;
import callbacks.terminal.OnTextMessageCreated;
import com.fasterxml.jackson.core.type.TypeReference;
import helpers.*;
import retrofit.BotHandler;
import retrofit.ComplexHandler;
import retrofit.MessageHandler;
import retrofit.NotifHandler;
import retrofit2.Call;
import rxbus.Bus;
import rxbus.notifications.*;
import signalr.HubConnection;
import signalr.HubConnectionBuilder;
import signalr.HubConnectionState;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class CSMP {

    public static final long ALL_USERS_IN_ROOM = 0;

    private String connectionState;
    public String getConnectionState() {
        return connectionState;
    }

    private HubConnection connection = HubConnectionBuilder.create(NetworkHelper.SERVER_IP + "NotificationsHub/").build();
    private Timer timer = new Timer();
    private Timer timer2 = new Timer();

    public CSMP(long sessionId, String token) {
        LogHelper.start();
        DatabaseHelper.setup(sessionId, token);
        JsonHelper.setup();
        NetworkHelper.setup();
        connectionState = "Preparing";
        connection.clearHandlers();
        connection.on("NotifyInviteCreated", CSMP.this::onInviteCreated, Notifications.InviteCreationNotification.class);
        connection.on("NotifyInviteCancelled", CSMP.this::onInviteCancelled, Notifications.InviteCancellationNotification.class);
        connection.on("NotifyUserJointComplex", CSMP.this::onUserJointComplex, Notifications.UserJointComplexNotification.class);
        connection.on("NotifyInviteAccepted", CSMP.this::onInviteAccepted, Notifications.InviteAcceptanceNotification.class);
        connection.on("NotifyInviteIgnored", CSMP.this::onInviteIgnored, Notifications.InviteIgnoranceNotification.class);
        connection.on("NotifyTextMessageReceived", CSMP.this::onTextMessage, Notifications.TextMessageNotification.class);
        connection.on("NotifyPhotoMessageReceived", CSMP.this::onPhotoMessage, Notifications.PhotoMessageNotification.class);
        connection.on("NotifyAudioMessageReceived", CSMP.this::onAudioMessage, Notifications.AudioMessageNotification.class);
        connection.on("NotifyVideoMessageReceived", CSMP.this::onVideoMessage, Notifications.VideoMessageNotification.class);
        connection.on("NotifyServiceMessageReceived", CSMP.this::onServiceMessage, Notifications.ServiceMessageNotification.class);
        connection.on("NotifyContactCreated", CSMP.this::onContactCreated, Notifications.ContactCreationNotification.class);
        connection.on("NotifyComplexDeleted", CSMP.this::onComplexDeletion, Notifications.ComplexDeletionNotification.class);
        connection.on("NotifyRoomDeleted", CSMP.this::onRoomDeletion, Notifications.RoomDeletionNotification.class);
        connection.on("NotifyBotAddedToRoom", CSMP.this::onBotAddedToRoom, Notifications.BotAddedToRoomNotification.class);
        connection.on("NotifyBotRemovedFromRoom", CSMP.this::onBotRemovedFromRoom, Notifications.BotRemovedFromRoomNotification.class);
        connection.on("NotifyUserRequestedBotView", CSMP.this::onUserRequestedBotView, Notifications.UserRequestedBotViewNotification.class);
    }

    public Bus bus() {
        return BusHelper.bus();
    }

    public List<Entities.Workership> getWorkerships() {
        return DatabaseHelper.getWorkerships();
    }

    public void createTextMessage(long complexId, long roomId, String text, OnTextMessageCreated callback) {
        Packet packet = new Packet();
        Entities.Complex complex = new Entities.Complex();
        complex.setComplexId(complexId);
        packet.setComplex(complex);
        Entities.Room room = new Entities.Room();
        room.setRoomId(roomId);
        packet.setRoom(room);
        Entities.TextMessage textMessage = new Entities.TextMessage();
        textMessage.setText(text);
        packet.setTextMessage(textMessage);
        Call<Packet> call = NetworkHelper.getRetrofit().create(MessageHandler.class).botCreateTextMessage(packet);
        NetworkHelper.requestServer(call, new ServerCallback2() {
            @Override
            public void onRequestSuccess(Packet packet) {
                LogHelper.log("Aseman", "text message created successfully");
                callback.textMessageCreated(packet.getTextMessage());
            }
            @Override
            public void onLogicalError(String errorCode) {
                LogHelper.log("Aseman", "server logical error with error code : " + errorCode);
            }
            @Override
            public void onServerFailure() {
                LogHelper.log("Aseman", "server failure");
            }
            @Override
            public void onConnectionFailure() {
                LogHelper.log("Aseman", "connection failure");
            }
        });
    }

    public void createPhotoMessage(long complexId, long roomId, long fileId, OnFileMessageCreated callback) {
        Packet packet = new Packet();
        Entities.Complex complex = new Entities.Complex();
        complex.setComplexId(complexId);
        packet.setComplex(complex);
        Entities.Room room = new Entities.Room();
        room.setRoomId(roomId);
        packet.setRoom(room);
        Entities.File file = new Entities.File();
        file.setFileId(fileId);
        packet.setFile(file);
        Call<Packet> call = NetworkHelper.getRetrofit().create(MessageHandler.class).botCreateTextMessage(packet);
        NetworkHelper.requestServer(call, new ServerCallback2() {
            @Override
            public void onRequestSuccess(Packet packet) {
                LogHelper.log("Aseman", "text message created successfully");
                callback.fileMessageCreated(packet.getPhotoMessage());
            }
            @Override
            public void onLogicalError(String errorCode) {
                LogHelper.log("Aseman", "server logical error with error code : " + errorCode);
            }
            @Override
            public void onServerFailure() {
                LogHelper.log("Aseman", "server failure");
            }
            @Override
            public void onConnectionFailure() {
                LogHelper.log("Aseman", "connection failure");
            }
        });
    }

    public void createAudioMessage(long complexId, long roomId, long fileId, OnFileMessageCreated callback) {
        Packet packet = new Packet();
        Entities.Complex complex = new Entities.Complex();
        complex.setComplexId(complexId);
        packet.setComplex(complex);
        Entities.Room room = new Entities.Room();
        room.setRoomId(roomId);
        packet.setRoom(room);
        Entities.File file = new Entities.File();
        file.setFileId(fileId);
        packet.setFile(file);
        Call<Packet> call = NetworkHelper.getRetrofit().create(MessageHandler.class).botCreateTextMessage(packet);
        NetworkHelper.requestServer(call, new ServerCallback2() {
            @Override
            public void onRequestSuccess(Packet packet) {
                LogHelper.log("Aseman", "text message created successfully");
                callback.fileMessageCreated(packet.getAudioMessage());
            }
            @Override
            public void onLogicalError(String errorCode) {
                LogHelper.log("Aseman", "server logical error with error code : " + errorCode);
            }
            @Override
            public void onServerFailure() {
                LogHelper.log("Aseman", "server failure");
            }
            @Override
            public void onConnectionFailure() {
                LogHelper.log("Aseman", "connection failure");
            }
        });
    }

    public void createVideoMessage(long complexId, long roomId, long fileId, OnFileMessageCreated callback) {
        Packet packet = new Packet();
        Entities.Complex complex = new Entities.Complex();
        complex.setComplexId(complexId);
        packet.setComplex(complex);
        Entities.Room room = new Entities.Room();
        room.setRoomId(roomId);
        packet.setRoom(room);
        Entities.File file = new Entities.File();
        file.setFileId(fileId);
        packet.setFile(file);
        Call<Packet> call = NetworkHelper.getRetrofit().create(MessageHandler.class).botCreateTextMessage(packet);
        NetworkHelper.requestServer(call, new ServerCallback2() {
            @Override
            public void onRequestSuccess(Packet packet) {
                LogHelper.log("Aseman", "text message created successfully");
                callback.fileMessageCreated(packet.getVideoMessage());
            }
            @Override
            public void onLogicalError(String errorCode) {
                LogHelper.log("Aseman", "server logical error with error code : " + errorCode);
            }
            @Override
            public void onServerFailure() {
                LogHelper.log("Aseman", "server failure");
            }
            @Override
            public void onConnectionFailure() {
                LogHelper.log("Aseman", "connection failure");
            }
        });
    }

    public void initBotView(long complexId, long roomId, long userId, Controls.Control view, OnBotViewInitialized callback) {
        Packet packet = new Packet();
        Entities.Complex complex = new Entities.Complex();
        complex.setComplexId(complexId);
        packet.setComplex(complex);
        Entities.Room room = new Entities.Room();
        room.setRoomId(roomId);
        packet.setRoom(room);
        Entities.User user = new Entities.User();
        user.setBaseUserId(userId);
        packet.setUser(user);
        try {
            String json = JsonHelper.serializeObject(view);
            packet.setRawJson(json);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        NetworkHelper.requestServer(NetworkHelper.getRetrofit().create(BotHandler.class).botSendBotView(packet),
                new ServerCallback2() {
                    @Override
                    public void onRequestSuccess(Packet packet) {
                        LogHelper.log("Aseman", "bot view initialization : successful.");
                        callback.botViewInitialized();
                    }
                    @Override
                    public void onLogicalError(String errorCode) {
                        LogHelper.log("Aseman", "bot view initialization : server logic error , error_code : " + errorCode);
                    }
                    @Override
                    public void onServerFailure() {
                        LogHelper.log("Aseman", "bot view initialization : server failure.");
                    }
                    @Override
                    public void onConnectionFailure() {
                        LogHelper.log("Aseman", "bot view initialization : connection failure.");
                    }
                });
    }

    public void initBotViewToAll(Controls.Control view) {
        for (Entities.Workership workership : DatabaseHelper.getWorkerships()) {
            Entities.Room room = workership.getRoom();
            Entities.Complex complex = room.getComplex();
            List<Entities.Membership> members = complex.getMembers();
            for (Entities.Membership membership : members) {
                Entities.User user = membership.getUser();
                initBotView(complex.getComplexId(), room.getRoomId(), user.getBaseUserId(), view, () -> {});
            }
        }
    }

    public void updateBotView(long complexId, long roomId, long userId, Updates.Update update) {
        Packet packet = new Packet();
        Entities.Complex complex = new Entities.Complex();
        complex.setComplexId(complexId);
        packet.setComplex(complex);
        Entities.Room room = new Entities.Room();
        room.setRoomId(roomId);
        packet.setRoom(room);
        Entities.User user = new Entities.User();
        user.setBaseUserId(userId);
        packet.setUser(user);
        try {
            String json = JsonHelper.serializeObject(update);
            packet.setRawJson(json);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        NetworkHelper.requestServer(NetworkHelper.getRetrofit().create(BotHandler.class).botUpdateBotView(packet),
                new ServerCallback2() {
                    @Override
                    public void onRequestSuccess(Packet packet) {
                        LogHelper.log("Aseman", "bot view update : successful.");
                    }
                    @Override
                    public void onLogicalError(String errorCode) {
                        LogHelper.log("Aseman", "bot view update : server logic error , error_code : " + errorCode);
                    }
                    @Override
                    public void onServerFailure() {
                        LogHelper.log("Aseman", "bot view update : server failure.");
                    }
                    @Override
                    public void onConnectionFailure() {
                        LogHelper.log("Aseman", "bot view update : connection failure.");
                    }
                });
    }

    public void updateBotViewToAll(Updates.Update update) {
        for (Entities.Workership workership : DatabaseHelper.getWorkerships()) {
            Entities.Room room = workership.getRoom();
            Entities.Complex complex = room.getComplex();
            List<Entities.Membership> members = complex.getMembers();
            for (Entities.Membership membership : members) {
                Entities.User user = membership.getUser();
                updateBotView(complex.getComplexId(), room.getRoomId(), user.getBaseUserId(), update);
            }
        }
    }

    public void updateBotView(long complexId, long roomId, long userId, List<Updates.Update> updates) {
        Packet packet = new Packet();
        Entities.Complex complex = new Entities.Complex();
        complex.setComplexId(complexId);
        packet.setComplex(complex);
        Entities.Room room = new Entities.Room();
        room.setRoomId(roomId);
        packet.setRoom(room);
        Entities.User user = new Entities.User();
        user.setBaseUserId(userId);
        packet.setUser(user);
        try {
            String json = JsonHelper.serializeObject(updates);
            packet.setRawJson(json);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        packet.setBatchData(true);
        NetworkHelper.requestServer(NetworkHelper.getRetrofit().create(BotHandler.class).botUpdateBotView(packet),
                new ServerCallback2() {
                    @Override
                    public void onRequestSuccess(Packet packet) {
                        LogHelper.log("Aseman", "bot view update : successful.");
                    }
                    @Override
                    public void onLogicalError(String errorCode) {
                        LogHelper.log("Aseman", "bot view update : server logic error , error_code : " + errorCode);
                    }
                    @Override
                    public void onServerFailure() {
                        LogHelper.log("Aseman", "bot view update : server failure.");
                    }
                    @Override
                    public void onConnectionFailure() {
                        LogHelper.log("Aseman", "bot view update : connection failure.");
                    }
                });
    }

    public void updateBotViewToAll(List<Updates.Update> updates) {
        for (Entities.Workership workership : DatabaseHelper.getWorkerships()) {
            Entities.Room room = workership.getRoom();
            Entities.Complex complex = room.getComplex();
            List<Entities.Membership> members = complex.getMembers();
            for (Entities.Membership membership : members) {
                Entities.User user = membership.getUser();
                updateBotView(complex.getComplexId(), room.getRoomId(), user.getBaseUserId(), updates);
            }
        }
    }

    public void animateBotView(long complexId, long roomId, long userId, Anims.Anim animation) {
        Packet packet = new Packet();
        Entities.Complex complex = new Entities.Complex();
        complex.setComplexId(complexId);
        packet.setComplex(complex);
        Entities.Room room = new Entities.Room();
        room.setRoomId(roomId);
        packet.setRoom(room);
        Entities.User user = new Entities.User();
        user.setBaseUserId(userId);
        packet.setUser(user);
        try {
            String json = JsonHelper.serializeObject(animation);
            packet.setRawJson(json);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        NetworkHelper.requestServer(NetworkHelper.getRetrofit().create(BotHandler.class).botAnimateBotView(packet),
                new ServerCallback2() {
                    @Override
                    public void onRequestSuccess(Packet packet) {
                        LogHelper.log("Aseman", "bot view animation : successful.");
                    }
                    @Override
                    public void onLogicalError(String errorCode) {
                        LogHelper.log("Aseman", "bot view animation : server logic error , error_code : " + errorCode);
                    }
                    @Override
                    public void onServerFailure() {
                        LogHelper.log("Aseman", "bot view animation : server failure.");
                    }
                    @Override
                    public void onConnectionFailure() {
                        LogHelper.log("Aseman", "bot view animation : connection failure.");
                    }
                });
    }

    public void animateBotViewToAll(Anims.Anim anim) {
        for (Entities.Workership workership : DatabaseHelper.getWorkerships()) {
            Entities.Room room = workership.getRoom();
            Entities.Complex complex = room.getComplex();
            List<Entities.Membership> members = complex.getMembers();
            for (Entities.Membership membership : members) {
                Entities.User user = membership.getUser();
                animateBotView(complex.getComplexId(), room.getRoomId(), user.getBaseUserId(), anim);
            }
        }
    }

    public void animateBotView(long complexId, long roomId, long userId, List<Anims.Anim> anims) {
        Packet packet = new Packet();
        Entities.Complex complex = new Entities.Complex();
        complex.setComplexId(complexId);
        packet.setComplex(complex);
        Entities.Room room = new Entities.Room();
        room.setRoomId(roomId);
        packet.setRoom(room);
        Entities.User user = new Entities.User();
        user.setBaseUserId(userId);
        packet.setUser(user);
        try {
            String json = JsonHelper.serializeObject(anims);
            packet.setRawJson(json);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        packet.setBatchData(true);
        NetworkHelper.requestServer(NetworkHelper.getRetrofit().create(BotHandler.class).botAnimateBotView(packet),
                new ServerCallback2() {
                    @Override
                    public void onRequestSuccess(Packet packet) {
                        LogHelper.log("Aseman", "bot view animation : successful.");
                    }
                    @Override
                    public void onLogicalError(String errorCode) {
                        LogHelper.log("Aseman", "bot view animation : server logic error , error_code : " + errorCode);
                    }
                    @Override
                    public void onServerFailure() {
                        LogHelper.log("Aseman", "bot view animation : server failure.");
                    }
                    @Override
                    public void onConnectionFailure() {
                        LogHelper.log("Aseman", "bot view animation : connection failure.");
                    }
                });
    }

    public void animateBotViewToAll(List<Anims.Anim> anims) {
        for (Entities.Workership workership : DatabaseHelper.getWorkerships()) {
            Entities.Room room = workership.getRoom();
            Entities.Complex complex = room.getComplex();
            List<Entities.Membership> members = complex.getMembers();
            for (Entities.Membership membership : members) {
                Entities.User user = membership.getUser();
                animateBotView(complex.getComplexId(), room.getRoomId(), user.getBaseUserId(), anims);
            }
        }
    }

    public void runCommandOnBotView(long complexId, long roomId, long userId, Codes.Code code) {
        Packet packet = new Packet();
        Entities.Complex complex = new Entities.Complex();
        complex.setComplexId(complexId);
        packet.setComplex(complex);
        Entities.Room room = new Entities.Room();
        room.setRoomId(roomId);
        packet.setRoom(room);
        Entities.User user = new Entities.User();
        user.setBaseUserId(userId);
        packet.setUser(user);
        try {
            String json = JsonHelper.serializeObject(code);
            packet.setRawJson(json);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        NetworkHelper.requestServer(NetworkHelper.getRetrofit().create(BotHandler.class).botRunCommandsOnBotView(packet),
                new ServerCallback2() {
                    @Override
                    public void onRequestSuccess(Packet packet) {
                        LogHelper.log("Aseman", "bot view running commands : successful.");
                    }
                    @Override
                    public void onLogicalError(String errorCode) {
                        LogHelper.log("Aseman", "bot view running commands : server logic error , error_code : " + errorCode);
                    }
                    @Override
                    public void onServerFailure() {
                        LogHelper.log("Aseman", "bot view running commands : server failure.");
                    }
                    @Override
                    public void onConnectionFailure() {
                        LogHelper.log("Aseman", "bot view running commands : connection failure.");
                    }
                });
    }

    public void runCommandOnBotViewToAll(Codes.Code command) {
        for (Entities.Workership workership : DatabaseHelper.getWorkerships()) {
            Entities.Room room = workership.getRoom();
            Entities.Complex complex = room.getComplex();
            List<Entities.Membership> members = complex.getMembers();
            for (Entities.Membership membership : members) {
                Entities.User user = membership.getUser();
                runCommandOnBotView(complex.getComplexId(), room.getRoomId(), user.getBaseUserId(), command);
            }
        }
    }

    public void runCommandsOnBotView(long complexId, long roomId, long userId, List<Codes.Code> codes) {
        Packet packet = new Packet();
        Entities.Complex complex = new Entities.Complex();
        complex.setComplexId(complexId);
        packet.setComplex(complex);
        Entities.Room room = new Entities.Room();
        room.setRoomId(roomId);
        packet.setRoom(room);
        Entities.User user = new Entities.User();
        user.setBaseUserId(userId);
        packet.setUser(user);
        try {
            String json = JsonHelper.getMapper().writerFor(new TypeReference<List<Codes.Code>>(){}).writeValueAsString(codes);
            LogHelper.log("Aseman", json);
            packet.setRawJson(json);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        packet.setBatchData(true);
        NetworkHelper.requestServer(NetworkHelper.getRetrofit().create(BotHandler.class).botRunCommandsOnBotView(packet),
                new ServerCallback2() {
                    @Override
                    public void onRequestSuccess(Packet packet) {
                        LogHelper.log("Aseman", "bot view running commands : successful.");
                    }
                    @Override
                    public void onLogicalError(String errorCode) {
                        LogHelper.log("Aseman", "bot view running commands : server logic error , error_code : " + errorCode);
                    }
                    @Override
                    public void onServerFailure() {
                        LogHelper.log("Aseman", "bot view running commands : server failure.");
                    }
                    @Override
                    public void onConnectionFailure() {
                        LogHelper.log("Aseman", "bot view running commands : connection failure.");
                    }
                });
    }

    public void runCommandsOnBotViewToAll(List<Codes.Code> commands) {
        for (Entities.Workership workership : DatabaseHelper.getWorkerships()) {
            Entities.Room room = workership.getRoom();
            Entities.Complex complex = room.getComplex();
            List<Entities.Membership> members = complex.getMembers();
            for (Entities.Membership membership : members) {
                Entities.User user = membership.getUser();
                runCommandsOnBotView(complex.getComplexId(), room.getRoomId(), user.getBaseUserId(), commands);
            }
        }
    }

    public void start() {
        NetworkHelper.requestServer(NetworkHelper.getRetrofit().create(ComplexHandler.class).botGetWorkerships(), new ServerCallback2() {
            @Override
            public void onRequestSuccess(Packet packet) {
                LogHelper.log("Aseman", "fetching workerships : successful");
                DatabaseHelper.setWorkerships(packet.getWorkerships());
                new Thread(() -> {
                    try {
                        connection.start().blockingAwait();
                        loginToHub();
                        connectionState = "Online";
                        BusHelper.bus().post(new ConnectionStateChanged(ConnectionStateChanged.State.Connected));
                        BusHelper.bus().post(new CsmpStarted());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            if (connection.getConnectionState() == HubConnectionState.DISCONNECTED) {
                                connectionState = "Connecting";
                                BusHelper.bus().post(new ConnectionStateChanged(ConnectionStateChanged.State.Connecting));
                                try {
                                    connection.start().blockingAwait();
                                    loginToHub();
                                    connectionState = "Online";
                                    BusHelper.bus().post(new ConnectionStateChanged(ConnectionStateChanged.State.Connected));
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                        }
                    }, 1000, 10000);
                    timer2.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            if (connection.getConnectionState() == HubConnectionState.CONNECTED) {
                                try {
                                    LogHelper.log("Aseman", "sending keep-alive");
                                    String authResult1 = connection.invoke(String.class
                                            , "KeepAlive").blockingGet();
                                    LogHelper.log("Aseman", "received keep-alive answer : " + authResult1);
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                        }
                    }, 1000, 10000);
                }).start();
            }

            @Override
            public void onLogicalError(String errorCode) {
                LogHelper.log("Aseman", "fetching workerships : server logic error");
            }

            @Override
            public void onServerFailure() {
                LogHelper.log("Aseman", "fetching workerships : server failure");
            }

            @Override
            public void onConnectionFailure() {
                LogHelper.log("Aseman", "fetching workerships : connection failure");
            }
        });
    }

    private void loginToHub() {
        Entities.Session session = DatabaseHelper.getSingleSession();
        if (session != null) {
            LogHelper.log(CSMP.class.getName(), "Logging in to hub " + session.getSessionId() + " " + session.getToken());
            String result = connection.invoke(String.class, "LoginBot", session.getSessionId(), session.getToken())
                    .doOnError(Throwable::printStackTrace).blockingGet();
            LogHelper.log(CSMP.class.getName(), result.equals("success") ? "Logged in to hub successfully." : "Hub login attempt failure.");
        }
    }

    private void onUserRequestedBotView(Notifications.UserRequestedBotViewNotification notif) {
        LogHelper.log("Aseman", "User Requested Bot View notification");

        BusHelper.bus().post(new UserRequestedBotView(notif.getComplexId(), notif.getRoomId(), notif.getUser()));

        notifyServerNotifReceived(notif.getNotificationId());
    }

    private void onBotAddedToRoom(Notifications.BotAddedToRoomNotification notif) {
        LogHelper.log("Aseman", "Received Bot Added notification");

        notifyServerNotifReceived(notif.getNotificationId());
    }

    private void onBotRemovedFromRoom(Notifications.BotRemovedFromRoomNotification notif) {
        LogHelper.log("Aseman", "Received Bot Removed notification");

        notifyServerNotifReceived(notif.getNotificationId());
    }

    private void onInviteCreated(final Notifications.InviteCreationNotification icn) {
        LogHelper.log("Aseman", "Received Invite Creation notification");

        Entities.Invite invite = icn.getInvite();

        BusHelper.bus().post(new InviteCreated(invite));

        notifyServerNotifReceived(icn.getNotificationId());
    }

    private void onInviteCancelled(final Notifications.InviteCancellationNotification icn) {
        LogHelper.log("Aseman", "Received Invite Cancellation notification");

        BusHelper.bus().post(new InviteCancelled(icn.getInvite()));

        notifyServerNotifReceived(icn.getNotificationId());
    }

    private void onInviteAccepted(final Notifications.InviteAcceptanceNotification ian) {
        LogHelper.log("Aseman", "Received Invite Acceptance notification");

        BusHelper.bus().post(new InviteResolved(ian.getInvite()));

        notifyServerNotifReceived(ian.getNotificationId());
    }

    private void onInviteIgnored(final Notifications.InviteIgnoranceNotification iin) {
        LogHelper.log("Aseman", "Received Invite Ignorance notification");

        BusHelper.bus().post(new InviteResolved(iin.getInvite()));

        notifyServerNotifReceived(iin.getNotificationId());
    }

    private void onUserJointComplex(final Notifications.UserJointComplexNotification ujcn) {
        LogHelper.log("Aseman", "Received User Joint Complex notification");

        Entities.Membership mem = ujcn.getMembership();

        BusHelper.bus().post(new MembershipCreated(mem));

        notifyServerNotifReceived(ujcn.getNotificationId());
    }

    private void onComplexDeletion(final Notifications.ComplexDeletionNotification cdn) {
        LogHelper.log("Aseman", "Received Complex Deletion notification");

        notifyServerNotifReceived(cdn.getNotificationId());
    }

    private void onRoomDeletion(final Notifications.RoomDeletionNotification rdn) {
        LogHelper.log("Aseman", "Received Room Deletion notification");

        notifyServerNotifReceived(rdn.getNotificationId());
    }

    private void onContactCreated(final Notifications.ContactCreationNotification ccn) {
        LogHelper.log("Aseman", "Received contact notification");

        Entities.Complex complex = ccn.getContact().getComplex();
        Entities.Room room = complex.getRooms().get(0);
        room.setComplex(complex);

        BusHelper.bus().post(new ComplexCreated(complex));
        BusHelper.bus().post(new RoomCreated(complex.getComplexId(), room));
        BusHelper.bus().post(new ContactCreated(ccn.getContact()));

        notifyServerNotifReceived(ccn.getNotificationId());
    }

    private void onTextMessage(final Notifications.TextMessageNotification mcn) {
        LogHelper.log("Aseman", "Received text message notification");
        final Entities.TextMessage message = mcn.getMessage();
        message.setSeenByMe(false);

        Entities.MessageLocal messageLocal = new Entities.MessageLocal();
        messageLocal.setMessageId(message.getRoomId());
        messageLocal.setSent(true);
        BusHelper.bus().post(new MessageReceived(message, messageLocal));

        notifyServerNotifReceived(mcn.getNotificationId());
    }

    private void onPhotoMessage(final Notifications.PhotoMessageNotification mcn) {
        LogHelper.log("Aseman", "Received photo message notification");
        final Entities.PhotoMessage message = mcn.getMessage();
        message.setSeenByMe(false);

        Entities.FileLocal fileLocal = new Entities.FileLocal();
        fileLocal.setFileId(message.getPhoto().getFileId());
        fileLocal.setPath("");
        fileLocal.setProgress(0);
        fileLocal.setTransferring(false);
        BusHelper.bus().post(new FileReceived(DocTypes.Photo, message.getPhoto(), fileLocal));
        Entities.MessageLocal messageLocal = new Entities.MessageLocal();
        messageLocal.setMessageId(message.getRoomId());
        messageLocal.setSent(true);
        BusHelper.bus().post(new MessageReceived(message, messageLocal));

        notifyServerNotifReceived(mcn.getNotificationId());
    }

    private void onAudioMessage(final Notifications.AudioMessageNotification mcn) {
        LogHelper.log("Aseman", "Received audio message notification");
        final Entities.AudioMessage message = mcn.getMessage();
        message.setSeenByMe(false);

        Entities.FileLocal fileLocal = new Entities.FileLocal();
        fileLocal.setFileId(message.getAudio().getFileId());
        fileLocal.setPath("");
        fileLocal.setProgress(0);
        fileLocal.setTransferring(false);
        BusHelper.bus().post(new FileReceived(DocTypes.Audio, message.getAudio(), fileLocal));
        Entities.MessageLocal messageLocal = new Entities.MessageLocal();
        messageLocal.setMessageId(message.getRoomId());
        messageLocal.setSent(true);
        BusHelper.bus().post(new MessageReceived(message, messageLocal));

        notifyServerNotifReceived(mcn.getNotificationId());
    }

    private void onVideoMessage(final Notifications.VideoMessageNotification mcn) {
        LogHelper.log("Aseman", "Received video message notification");
        final Entities.VideoMessage message = mcn.getMessage();
        message.setSeenByMe(false);

        Entities.FileLocal fileLocal = new Entities.FileLocal();
        fileLocal.setFileId(message.getVideo().getFileId());
        fileLocal.setPath("");
        fileLocal.setProgress(0);
        fileLocal.setTransferring(false);

        Entities.MessageLocal messageLocal = new Entities.MessageLocal();
        messageLocal.setMessageId(message.getRoomId());
        messageLocal.setSent(true);
        BusHelper.bus().post(new FileReceived(DocTypes.Video, message.getVideo(), fileLocal));
        BusHelper.bus().post(new MessageReceived(message, messageLocal));

        notifyServerNotifReceived(mcn.getNotificationId());
    }

    private void onServiceMessage(final Notifications.ServiceMessageNotification mcn) {
        LogHelper.log("Aseman", "Received service message notification");
        final Entities.ServiceMessage message = mcn.getMessage();
        message.setSeenByMe(false);

        Entities.MessageLocal messageLocal = new Entities.MessageLocal();
        messageLocal.setMessageId(message.getRoomId());
        messageLocal.setSent(true);
        BusHelper.bus().post(new MessageReceived(message, messageLocal));

        notifyServerNotifReceived(mcn.getNotificationId());
    }

    private void notifyServerNotifReceived(String notifId) {
        Packet packet = new Packet();
        Notifications.Notification notification = new Notifications.Notification();
        notification.setNotificationId(notifId);
        packet.setNotif(notification);
        Call<Packet> call = NetworkHelper.getRetrofit().create(NotifHandler.class).notifyBotNotifReceived(packet);
        NetworkHelper.requestServer(call, new ServerCallback() {
            public void onRequestSuccess(Packet packet) { }
            public void onServerFailure() { }
            public void onConnectionFailure() { }
        });
    }
}
