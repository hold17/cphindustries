package dk.blackdarkness.g17.cphindustries.dto;
import dk.blackdarkness.g17.cphindustries.R;

/**
 * Created by awo on 03/11/2017.
 */

public enum ConnectionStatus {
    FULL, BAR_4, BAR_3, BAR_2, BAR_1, BAR_0, NO_CONNECTION;

    public int getDrawableId() {
        switch (this) {
            case NO_CONNECTION: return R.drawable.ic_signal_wifi_off_black_24dp;
            case BAR_3: return R.drawable.ic_signal_wifi_3_bar_black_24dp;
            case BAR_2: return R.drawable.ic_signal_wifi_2_bar_black_24dp;
            case BAR_1: return R.drawable.ic_signal_wifi_1_bar_black_24dp;
            case BAR_0: return R.drawable.ic_signal_wifi_0_bar_black_24dp;
            case BAR_4: case FULL: return R.drawable.ic_signal_wifi_4_bar_black_24dp;
            default: return R.drawable.ic_signal_wifi_off_black_24dp;
        }
    }
}
