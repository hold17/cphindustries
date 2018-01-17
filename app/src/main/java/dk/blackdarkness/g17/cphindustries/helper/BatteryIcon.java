package dk.blackdarkness.g17.cphindustries.helper;

import dk.blackdarkness.g17.cphindustries.R;

/**
 * Created by awo on 17-01-2018.
 */

public class BatteryIcon {
    public static int getDrawableId(int batteryLevel) {
        if (batteryLevel < 20) return R.drawable.ic_battery_empty_black_24dp;
        if (batteryLevel < 30) return R.drawable.ic_battery_20_black_24dp;
        if (batteryLevel < 50) return R.drawable.ic_battery_50_black_24dp;
        if (batteryLevel < 60) return R.drawable.ic_battery_80_black_24dp;
        if (batteryLevel < 90) return R.drawable.ic_battery_90_black_24dp;
        if (batteryLevel <= 100) return R.drawable.ic_battery_full_black_24dp;

        return R.drawable.ic_battery_empty_black_24dp;
    }
}
