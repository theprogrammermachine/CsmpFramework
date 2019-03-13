package home;

import callbacks.terminal.OnBotViewInitialized;
import helpers.DatabaseHelper;
import models.*;
import rxbus.Subscribe;
import rxbus.notifications.*;

import java.lang.reflect.Member;
import java.util.*;

public class Program {

    public static void main(String[] args) {
        new Program();
    }

    private CSMP csmp;
    private Timer timer;
    private Hashtable<Long, Boolean> spinning;

    private Controls.Control home;

    private Program() {
        timer = new Timer();
        csmp = new CSMP(4, "+FgCr2NOCNZtvUFhpT2R7QXfMpGIKM5L56fiqVYzyLovEIEe2c8qdabdRbZbwedZR");
        csmp.bus().register(this);
        spinning = new Hashtable<>();
        csmp.start();
    }

    private Controls.Control initHomeView(String userTitle) {
        Controls.PanelCtrl panelCtrl = new Controls.PanelCtrl();
        panelCtrl.setLayoutType(Controls.PanelCtrl.LayoutType.LINEAR_VERTICAL);
        panelCtrl.setWidth(Controls.Control.MATCH_PARENT);
        panelCtrl.setHeight(Controls.Control.MATCH_PARENT);
        panelCtrl.setControls(new ArrayList<>());

        Controls.TextCtrl textCtrl = new Controls.TextCtrl();
        textCtrl.setId("box");
        textCtrl.setGravityType(Controls.TextCtrl.GravityType.CENTER);
        textCtrl.setBackColor("#151E27");
        textCtrl.setText("Hello " + userTitle.split(" ")[0]);
        textCtrl.setTextColor("#0000ff");
        textCtrl.setTextSize(20);
        textCtrl.setBorderColor("#0000ff");
        textCtrl.setBorderWidth(2);
        textCtrl.setCornerRadius(16);
        textCtrl.setWidth(200);
        textCtrl.setHeight(64);
        textCtrl.setX(Controls.Control.CENTER);
        textCtrl.setY(16);
        panelCtrl.getControls().add(textCtrl);

        Controls.RecyclerCtrl recyclerCtrl = new Controls.RecyclerCtrl();
        recyclerCtrl.setWidth(300);
        recyclerCtrl.setHeight(Controls.Control.WRAP_CONTENT);
        recyclerCtrl.setX(Controls.Control.CENTER);
        recyclerCtrl.setY(112);
        recyclerCtrl.setBorderColor("#0000ff");
        recyclerCtrl.setBorderWidth(2);
        recyclerCtrl.setCornerRadius(16);
        recyclerCtrl.setGridSpanCount(2);
        recyclerCtrl.setRecyclerType(Controls.RecyclerCtrl.RecyclerLayoutType.GRID);
        recyclerCtrl.setOrientation(Controls.RecyclerCtrl.RecyclerOrientation.VERTICAL);
        recyclerCtrl.setItems(new ArrayList<>());
        HashSet<Integer> selectedIds = new HashSet<Integer>(Arrays.asList(0, 3, 4, 7, 8));
        for (int counter = 0; counter < 10; counter++) {
            Controls.TextCtrl itemCtrl = new Controls.TextCtrl();
            itemCtrl.setId("item" + counter);
            if (counter % 2 == 0)
                itemCtrl.setText("T-shirt " + counter);
            else
                itemCtrl.setText("Shoes " + counter);
            itemCtrl.setGravityType(Controls.TextCtrl.GravityType.CENTER);
            itemCtrl.setTextColor("#ffffff");
            itemCtrl.setTextSize(20);
            itemCtrl.setWidth(Controls.Control.MATCH_PARENT);
            itemCtrl.setHeight(64);
            itemCtrl.setBorderColor("#0000ff");
            itemCtrl.setBorderWidth(1);
            if (selectedIds.contains(counter))
                itemCtrl.setBackColor("#212E3E");
            else
                itemCtrl.setBackColor("#151E27");
            recyclerCtrl.getItems().add(itemCtrl);
        }
        panelCtrl.getControls().add(recyclerCtrl);

        return panelCtrl;
    }

    @Subscribe
    public void onCsmpStarted(CsmpStarted csmpStarted) {
        for (Entities.Workership ws : csmp.getWorkerships())
            spinning.put(ws.getRoom().getComplex().getComplexId(), true);
        csmp.initBotViewToAll(home);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
//                for (Tuple2<Long, Long, Long, Short> connected : connecteds.values()) {
//                    Updates.Update update, update2;
//                    if (connected.getItem4() == 0) {
//                        update = new Updates.ControlUpdateBackColor();
//                        update.setControlId("box");
//                        ((Updates.ControlUpdateBackColor) update).setValue("#000000");
//                        update2 = new Updates.TextCtrlUpdateTextColor();
//                        update2.setControlId("box");
//                        ((Updates.TextCtrlUpdateTextColor) update2).setValue("#ffffff");
//                        connected.setItem4((short) 1);
//                    } else {
//                        update = new Updates.ControlUpdateBackColor();
//                        update.setControlId("box");
//                        ((Updates.ControlUpdateBackColor) update).setValue("#ffffff");
//                        update2 = new Updates.TextCtrlUpdateTextColor();
//                        update2.setControlId("box");
//                        ((Updates.TextCtrlUpdateTextColor) update2).setValue("#000000");
//                        connected.setItem4((short) 0);
//                    }
//                    csmp.updateBotView(connected.getItem1(), connected.getItem2(), connected.getItem3(), update);
//                    csmp.updateBotView(connected.getItem1(), connected.getItem2(), connected.getItem3(), update2);
//                }
            }
        }, 1, 1500);
    }

    @Subscribe
    public void onMessageReceived(MessageReceived messageReceived) {
        Entities.Message message = messageReceived.getMessage();
        if (message instanceof Entities.TextMessage) {
            String text = ((Entities.TextMessage) message).getText();
            if (text.equals("stop spinning")) {
                spinning.replace(message.getRoom().getRoomId(), false);
                Codes.StopTask stopTask = new Codes.StopTask();
                stopTask.setTaskName("colorsTask");
                csmp.runCommandOnBotView(message.getRoom().getComplexId(), message.getRoomId(), CSMP.ALL_USERS_IN_ROOM, stopTask);
            } else if (text.equals("start spinning")) {
                spinning.replace(message.getRoom().getRoomId(), true);
                Codes.StartTask startTask = new Codes.StartTask();
                startTask.setTaskName("colorsTask");
                csmp.runCommandOnBotView(message.getRoom().getComplexId(), message.getRoomId(), CSMP.ALL_USERS_IN_ROOM, startTask);
            }
        }
    }

    @Subscribe
    public void onBotAddedToRoom(BotAddedToRoom botAddedToRoom) {
        spinning.put(botAddedToRoom.getWorkership().getRoom().getComplex().getComplexId(), true);
        for (Entities.Membership member : botAddedToRoom.getWorkership().getRoom().getComplex().getMembers()) {
            pushInitialHomeViewToClient(
                    botAddedToRoom.getWorkership().getRoom().getComplex().getComplexId(),
                    botAddedToRoom.getWorkership().getRoom().getRoomId(),
                    member.getUser());
        }
    }

    @Subscribe
    public void onUserRequestedBotView(UserRequestedBotView userRequestedBotView) {
        pushInitialHomeViewToClient(userRequestedBotView.getComplexId(), userRequestedBotView.getRoomId(), userRequestedBotView.getUser());
    }

    private void pushInitialHomeViewToClient(long complexId, long roomId, Entities.User user) {
        csmp.initBotView(complexId, roomId
                , user.getBaseUserId(), initHomeView(user.getTitle())
                , () -> {
                    Codes.Value colorWhite = new Codes.Value();
                    colorWhite.setValueType(Codes.DataType.STRING);
                    colorWhite.setValue("#0000ff");

                    Codes.Value colorBlack = new Codes.Value();
                    colorBlack.setValueType(Codes.DataType.STRING);
                    colorBlack.setValue("#00ff00");

                    Codes.Value boolTrue = new Codes.Value();
                    boolTrue.setValueType(Codes.DataType.BOOL);
                    boolTrue.setValue(true);

                    Codes.Value boolFalse = new Codes.Value();
                    boolFalse.setValueType(Codes.DataType.BOOL);
                    boolFalse.setValue(false);

                    List<Codes.Code> codes = new ArrayList<>();

                    Codes.Variable colorFlag = new Codes.Variable();
                    colorFlag.setName("colorFlag");
                    colorFlag.setValue(boolTrue);

                    Codes.Definition colorFlagDefinition = new Codes.Definition();
                    colorFlagDefinition.setVariable(colorFlag);
                    codes.add(colorFlagDefinition);

                    Codes.Variable bcVar = new Codes.Variable();
                    bcVar.setName("bc");
                    bcVar.setValue(colorWhite);

                    Codes.Definition bcVarDefinition = new Codes.Definition();
                    bcVarDefinition.setVariable(bcVar);
                    codes.add(bcVarDefinition);

                    Codes.Variable tcVar = new Codes.Variable();
                    tcVar.setName("tc");
                    tcVar.setValue(colorBlack);

                    Codes.Definition tcVarDefinition = new Codes.Definition();
                    tcVarDefinition.setVariable(tcVar);
                    codes.add(tcVarDefinition);

                    Codes.ModifyMirror modifyBcMirror = new Codes.ModifyMirror();
                    Bindings.Mirror bcVarMirror = new Bindings.MirrorToBorderColor();
                    bcVarMirror.setAction(Bindings.Mirror.ActionType.BIND);
                    bcVarMirror.setCtrlName("box");
                    bcVarMirror.setVarName("bc");
                    modifyBcMirror.setMirror(bcVarMirror);
                    codes.add(modifyBcMirror);

                    Codes.ModifyMirror modifyTcMirror = new Codes.ModifyMirror();
                    Bindings.Mirror tcVarMirror = new Bindings.MirrorToTextColor();
                    tcVarMirror.setAction(Bindings.Mirror.ActionType.BIND);
                    tcVarMirror.setCtrlName("box");
                    tcVarMirror.setVarName("tc");
                    modifyTcMirror.setMirror(tcVarMirror);
                    codes.add(modifyTcMirror);

                    Codes.Value rotation0Value = new Codes.Value();
                    rotation0Value.setValueType(Codes.DataType.INT);
                    rotation0Value.setValue(0);

                    Codes.Variable rotationVar = new Codes.Variable();
                    rotationVar.setName("bannerRotation");
                    rotationVar.setValue(rotation0Value);

                    Codes.Definition rotationDef = new Codes.Definition();
                    rotationDef.setVariable(rotationVar);
                    codes.add(rotationDef);

                    Codes.ModifyMirror rotationMM = new Codes.ModifyMirror();
                    Bindings.Mirror rotationMirror = new Bindings.MirrorToRotationY();
                    rotationMirror.setAction(Bindings.Mirror.ActionType.BIND);
                    rotationMirror.setVarName("bannerRotation");
                    rotationMirror.setCtrlName("box");
                    rotationMM.setMirror(rotationMirror);
                    codes.add(rotationMM);

                    Codes.Task task = new Codes.Task();
                    task.setName("colorsTask");
                    task.setPeriod(1000);
                    task.setCodes(new ArrayList<>());

                    Codes.Assignment colorFlagAssignmentNot = new Codes.Assignment();
                    colorFlagAssignmentNot.setVariable(colorFlag);
                    Codes.MathExpNot colorFlagNot = new Codes.MathExpNot();
                    colorFlagNot.setCode(colorFlag);
                    colorFlagAssignmentNot.setValue(colorFlagNot);
                    task.getCodes().add(colorFlagAssignmentNot);

                    Codes.If ifCode = new Codes.If();
                    Codes.EQCompare eqCompare = new Codes.EQCompare();
                    eqCompare.setItem1(colorFlag);
                    eqCompare.setItem2(boolTrue);
                    ifCode.setCondition(eqCompare);
                    ifCode.setIfCodes(new ArrayList<>());
                    ifCode.setElseCodes(new ArrayList<>());

                    Codes.Assignment bcVarBlackAssignment = new Codes.Assignment();
                    bcVarBlackAssignment.setVariable(bcVar);
                    bcVarBlackAssignment.setValue(colorBlack);
                    ifCode.getIfCodes().add(bcVarBlackAssignment);

                    Codes.Assignment tcVarWhiteAssignment = new Codes.Assignment();
                    tcVarWhiteAssignment.setVariable(tcVar);
                    tcVarWhiteAssignment.setValue(colorBlack);
                    ifCode.getIfCodes().add(tcVarWhiteAssignment);

                    Codes.Assignment bcVarWhiteAssignment = new Codes.Assignment();
                    bcVarWhiteAssignment.setVariable(bcVar);
                    bcVarWhiteAssignment.setValue(colorWhite);
                    ifCode.getElseCodes().add(bcVarWhiteAssignment);

                    Codes.Assignment tcVarBlackAssignment = new Codes.Assignment();
                    tcVarBlackAssignment.setVariable(tcVar);
                    tcVarBlackAssignment.setValue(colorWhite);
                    ifCode.getElseCodes().add(tcVarBlackAssignment);

                    task.getCodes().add(ifCode);

                    Codes.Assignment rotationAssignment = new Codes.Assignment();
                    rotationAssignment.setVariable(rotationVar);
                    rotationAssignment.setValue(rotation0Value);
                    task.getCodes().add(rotationAssignment);

                    Codes.PerformAnim performRotationAnim = new Codes.PerformAnim();
                    Anims.ControlAnimRotationY rotationAnim = new Anims.ControlAnimRotationY();
                    rotationAnim.setControlId("box");
                    rotationAnim.setDuration(750);
                    rotationAnim.setFinalValue(360);
                    performRotationAnim.setAnim(rotationAnim);
                    task.getCodes().add(performRotationAnim);

                    Codes.DefineTask defineTask = new Codes.DefineTask();
                    defineTask.setTask(task);
                    codes.add(defineTask);

                    if (spinning.get(complexId)) {
                        Codes.StartTask startTask = new Codes.StartTask();
                        startTask.setTaskName("colorsTask");
                        codes.add(startTask);
                    }

                    csmp.runCommandsOnBotView(
                            complexId,
                            roomId,
                            user.getBaseUserId(),
                            codes);
                });
    }

    @Subscribe
    public void onUserJoint(UserJointComplex userJointComplex) {
        Entities.Membership membership = userJointComplex.getMembership();
        Entities.User user = membership.getUser();
        Entities.Complex complex = membership.getComplex();
        Entities.Room hall = complex.getRooms().get(0);
        csmp.createTextMessage(complex.getComplexId(), hall.getRoomId(), "User "
                        + user.getTitle() + "\n" + "welcome to " + complex.getTitle() + " complex"
                , textMessage -> {

                });
    }
}
