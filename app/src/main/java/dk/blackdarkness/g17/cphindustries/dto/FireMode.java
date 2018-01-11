package dk.blackdarkness.g17.cphindustries.dto;

import dk.blackdarkness.g17.cphindustries.R;

public enum FireMode {
    FULL_AUTO, BURST, SINGLE, SAFE;

    public int getDrawableId() {
        switch (this) {
            case SINGLE: return R.drawable.ic_single;
            default: return R.drawable.ic_burst;
        }
    }
}
