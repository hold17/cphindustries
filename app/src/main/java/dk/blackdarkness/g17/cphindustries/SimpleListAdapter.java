package dk.blackdarkness.g17.cphindustries;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import dk.blackdarkness.g17.cphindustries.dto.*;

/**
 * Created by awo on 03/11/2017.
 */

public class SimpleListAdapter extends ArrayAdapter<NavListItem> {
    private NavListItem navListItem;
    private TextView tvHeading;
    private ImageView imageFront;
    private ImageView imageBack;
    private Item item;

    public SimpleListAdapter(Context context, NavListItem[] items) {
        super(context, R.layout.simple_list_item, items);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final LayoutInflater inflater = LayoutInflater.from(getContext());
        final View simpleListItemView = inflater.inflate(R.layout.simple_list_item, parent, false);

        this.navListItem = getItem(position);
        this.tvHeading = simpleListItemView.findViewById(R.id.simpleListItem_tvHeading);
        this.imageFront = simpleListItemView.findViewById(R.id.simpleListItem_imageFront);
        this.imageBack = simpleListItemView.findViewById(R.id.simpleListItem_imageBack);
        this.item = navListItem.getItem();

        this.tvHeading.setText(item.getName());

        if (navListItem.isEditable()) {
            asEditable(navListItem);
            return simpleListItemView;
        }

        if (item instanceof Scene) asScene((Scene) item);
        if (item instanceof Shoot) asShoot((Shoot) item);
        if (item instanceof Weapon) asWeapon((Weapon) item);

        return simpleListItemView;
    }

    private void asScene(Scene scene) {
        this.imageFront.setVisibility(View.GONE);
        this.imageBack.setImageResource(R.drawable.ic_chevron_right_black_24dp);
        this.imageBack.setVisibility(View.VISIBLE);
    }

    private void asShoot(Shoot shoot) {
        this.imageFront.setVisibility(View.GONE);
        this.imageBack.setImageResource(R.drawable.ic_chevron_right_black_24dp);
        this.imageBack.setVisibility(View.VISIBLE);
    }

    private void asWeapon(Weapon weapon) {
        // Warnings
        if (weapon.getWarnings().size() > 0) {
            this.imageFront.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorWarning));
            this.imageFront.setVisibility(View.VISIBLE);
        } else {
            this.imageFront.setVisibility(View.INVISIBLE);
        }

        // Set go button image to the connection status
        this.imageBack.setImageDrawable(weapon.getConnectionStatus().getDrawable(getContext()));

        if (weapon.getConnectionStatus() == ConnectionStatus.NO_CONNECTION) {
            this.imageBack.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorDanger));
        } else {
            this.imageBack.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorPositive));
        }
    }

    private void asEditable(NavListItem navListItem) {
        this.imageFront.setImageResource(R.drawable.ic_reorder_black_24px);
        this.imageBack.setImageResource(R.drawable.ic_edit_black_24dp);
    }
}
