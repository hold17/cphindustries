package dk.blackdarkness.g17.cphindustries.recyclerview;

import dk.blackdarkness.g17.cphindustries.dto.Item;

/**
 * Created by Thoma on 11/29/2017.
 */

public class NavListItem {
    private Item item;
    private boolean isEditable;

    public NavListItem(Item item, boolean isEditable) {
        this.item = item;
        this.isEditable = isEditable;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public boolean isEditable() {
        return isEditable;
    }

    public void setEditable(boolean editable) {
        isEditable = editable;
    }
}
