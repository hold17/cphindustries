package dk.blackdarkness.g17.cphindustries.helper;

import android.content.Context;

import dk.blackdarkness.g17.cphindustries.R;

public enum FragmentType {
    SCENES, SHOOTS, WEAPONS;

    /**
     * @param context
     * @param pluralCount
     */
    public String getTypeAsString(Context context , int pluralCount) {
        switch (this) {
            case SCENES: return context.getResources().getQuantityString(R.plurals.scene, pluralCount);
            case SHOOTS: return context.getResources().getQuantityString(R.plurals.shoot, pluralCount);
            case WEAPONS: return context.getResources().getQuantityString(R.plurals.weapon, pluralCount);
            default: return "Easter Egg";
        }
    }
}
