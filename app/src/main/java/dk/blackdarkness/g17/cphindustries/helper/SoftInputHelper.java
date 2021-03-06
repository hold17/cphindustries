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
        Log.d("SoftInputHelper", "showSoftInput: showing input");
        imm.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT);
    }

    public static void hideSoftInput(Context context, View v) {
        if (v != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            // no idea why this would be null, better make sure...
            if (imm == null) {
                throw new NullPointerException("InputMethodManager is null");
            }
            Log.d("SoftInputHelper", "hideSoftInput: hiding input");
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        } else {
            Log.d("SoftInputHelper", "hideSoftInput: getCurrentFocus returned null!");
        }
    }
}
