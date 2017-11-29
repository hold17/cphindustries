package dk.blackdarkness.g17.cphindustries.dto;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import dk.blackdarkness.g17.cphindustries.R;

/**
 * Created by awo on 03/11/2017.
 */

public enum ConnectionStatus {
    FULL, BAR_4, BAR_3, BAR_2, BAR_1, BAR_0, NO_CONNECTION;

    public Drawable getDrawable(Context context) {
        switch (this) {
            case NO_CONNECTION: return context.getDrawable(R.drawable.ic_signal_wifi_off_black_24dp);
            case BAR_3: return context.getDrawable(R.drawable.ic_signal_wifi_3_bar_black_24dp);
            case BAR_2: return context.getDrawable(R.drawable.ic_signal_wifi_2_bar_black_24dp);
            case BAR_1: return context.getDrawable(R.drawable.ic_signal_wifi_1_bar_black_24dp);
            case BAR_0: return context.getDrawable(R.drawable.ic_signal_wifi_0_bar_black_24dp);
            case BAR_4: case FULL: return context.getDrawable(R.drawable.ic_signal_wifi_4_bar_black_24dp);
            default: return null;
        }
    }
}
