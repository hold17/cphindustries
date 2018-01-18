package dk.blackdarkness.g17.cphindustries.helper;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class SoftInputHelper {

    public static void showSoftInput(Context context, View v) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        // no idea why this would be null, better make sure...
        if (imm == null) {
            throw new NullPointerException("InputMethodManager is null");
        }
        imm.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT);
    }

    public static void hideSoftInput(Context context, View v) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        // no idea why this would be null, better make sure...
        if (imm == null) {
            throw new NullPointerException("InputMethodManager is null");
        }
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public static void tryNewThing(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        // no idea why this would be null, better make sure...
        if (imm == null) {
            throw new NullPointerException("InputMethodManager is null");
        }
        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }
}
