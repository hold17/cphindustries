package dk.blackdarkness.g17.cphindustries.dto;

import android.content.Context;
import android.graphics.drawable.Drawable;

import dk.blackdarkness.g17.cphindustries.R;

/**
 * Created by awo on 03/11/2017.
 */

public enum FireMode {
    FULL_AUTO, BURST, SINGLE, SAFE;

    public int getDrawableId() {
        switch (this) {
            case SINGLE: return R.drawable.ic_single;
            default: return R.drawable.ic_burst;
        }
    }
}
