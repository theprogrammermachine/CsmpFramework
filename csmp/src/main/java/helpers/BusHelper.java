package helpers;

import rxbus.Bus;
import rxbus.BusProvider;

public class BusHelper {

    public static Bus bus() {
        return BusProvider.getInstance();
    }
}
