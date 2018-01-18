package dk.blackdarkness.g17.cphindustries.recyclerview.helpers;

import android.view.View;

public interface RecyclerViewClickListener {
    void onClick(View view, int itemId);
    boolean onLongClick(View view, int itemId);
}
