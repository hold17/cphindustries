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

/**
 * Created by awo on 03/11/2017.
 */

public class SimpleListAdapter extends ArrayAdapter<NavListItem> {
    public SimpleListAdapter(Context context, NavListItem[] navListItems) {
        super(context, R.layout.simple_list_item, navListItems);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final LayoutInflater inflator = LayoutInflater.from(getContext());

        final View simpleListItemView = inflator.inflate(R.layout.simple_list_item, parent, false);

        final NavListItem navListItem = getItem(position);
        final TextView tvHeading =     simpleListItemView.findViewById(R.id.simpleListItem_tvHeading);
        ImageView imageFront = simpleListItemView.findViewById(R.id.simpleListItem_imageFront);
        ImageView imageBack = simpleListItemView.findViewById(R.id.simpleListItem_imageBack);

        tvHeading.setText(navListItem.getText());

        // Visibility and color for warning
        if (navListItem.warningIsVisible()) {
            imageFront.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorWarning));
            imageFront.setVisibility(View.VISIBLE);
        } else {
            imageFront.setVisibility(View.INVISIBLE);
        }

        // Visibility and color for goBtn / wifi status
        if (navListItem.goBtnIsVisible()) {
            imageBack.setImageResource(R.drawable.ic_chevron_right_black_24dp);
            imageBack.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
        } else {
            if (navListItem.getConnectionStatus() != null) {
                imageBack.setImageDrawable(navListItem.getConnectionStatus().getDrawable(getContext()));

                // If no connection, color = red, otherwise, green
                if (navListItem.getConnectionStatus() == ConnectionStatus.NO_CONNECTION)
                    imageBack.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorDanger));
                else
                    imageBack.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorPositive));
            }
        }

        if(navListItem.isEditable()) {
            imageFront.setVisibility(View.VISIBLE);
            imageFront.setImageResource(R.drawable.ic_reorder_black_24px);
            imageBack.setImageResource(R.drawable.ic_edit_black_24dp);
            imageBack.setVisibility(View.VISIBLE);
        }

        return simpleListItemView;
    }
}
