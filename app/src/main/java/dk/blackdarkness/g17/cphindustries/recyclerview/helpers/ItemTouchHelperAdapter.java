package dk.blackdarkness.g17.cphindustries.recyclerview.helpers;

public interface ItemTouchHelperAdapter {

    boolean onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);
}
