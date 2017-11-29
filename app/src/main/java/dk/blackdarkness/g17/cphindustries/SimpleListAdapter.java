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

public class SimpleListAdapter extends ArrayAdapter<Item> {
    private TextView tvHeading;
    private ImageView imageWarning;
    private ImageView imageGo;

    public SimpleListAdapter(Context context, Item[] items) {
        super(context, R.layout.simple_list_item, items);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final LayoutInflater inflater = LayoutInflater.from(getContext());
        final View simpleListItemView = inflater.inflate(R.layout.simple_list_item, parent, false);

        final Item item = getItem(position);
        this.tvHeading = simpleListItemView.findViewById(R.id.simpleListItem_tvHeading);
        this.imageWarning = simpleListItemView.findViewById(R.id.simpleListItem_imageWarning);
        this.imageGo = simpleListItemView.findViewById(R.id.simpleListItem_imageGo);

        this.tvHeading.setText(item.getName());

        if (item instanceof Scene) asScene((Scene) item);
        if (item instanceof Shoot) asShoot((Shoot) item);
        if (item instanceof Weapon) asWeapon((Weapon) item);

        return simpleListItemView;
    }

    private void asScene(Scene scene) {
        this.imageWarning.setVisibility(View.GONE);

        this.imageGo.setVisibility(View.VISIBLE);
        this.imageGo.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
    }

    private void asShoot(Shoot shoot) {
        this.imageWarning.setVisibility(View.GONE);

        this.imageGo.setVisibility(View.VISIBLE);
        this.imageGo.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
    }

    private void asWeapon(Weapon weapon) {
        // Warnings
        if (weapon.getWarnings().size() > 0) {
            this.imageWarning.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorWarning));
            this.imageWarning.setVisibility(View.VISIBLE);
        } else {
            this.imageWarning.setVisibility(View.INVISIBLE);
        }

        // Set go button image to the connection status
        this.imageGo.setImageDrawable(weapon.getConnectionStatus().getDrawable(getContext()));

        if (weapon.getConnectionStatus() == ConnectionStatus.NO_CONNECTION) {
            this.imageGo.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorDanger));
        } else {
            this.imageGo.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorPositive));
        }

    }
//    public SimpleListAdapter(Context context, NavListItem[] navListItems) {
//        super(context, R.layout.simple_list_item, navListItems);
//    }
//
//    @NonNull
//    @Override
//    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        final LayoutInflater inflator = LayoutInflater.from(getContext());
//
//        final View simpleListItemView = inflator.inflate(R.layout.simple_list_item, parent, false);
//
//        final NavListItem navListItem = getItem(position);
//        final TextView tvHeading =     simpleListItemView.findViewById(R.id.simpleListItem_tvHeading);
//        final ImageView imageWarning = simpleListItemView.findViewById(R.id.simpleListItem_imageWarning);
//        final ImageView imageGo = simpleListItemView.findViewById(R.id.simpleListItem_imageGo);
//
//        tvHeading.setText(navListItem.getText());
//
//        // Visibility and color for warning
//        if (navListItem.warningIsVisible()) {
//            imageWarning.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorWarning));
//            imageWarning.setVisibility(View.VISIBLE);
//        } else {
//            imageWarning.setVisibility(View.INVISIBLE);
//        }
//
//        // Visibility and color for goBtn / wifi status
//        if (navListItem.goBtnIsVisible()) {
//            imageGo.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
//        } else {
//            if (navListItem.getConnectionStatus() != null) {
//                imageGo.setImageDrawable(navListItem.getConnectionStatus().getDrawable(getContext()));
//
//                // If no connection, color = red, otherwise, green
//                if (navListItem.getConnectionStatus() == ConnectionStatus.NO_CONNECTION)
//                    imageGo.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorDanger));
//                else
//                    imageGo.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorPositive));
//            }
//        }
//
//        return simpleListItemView;
//    }


}
