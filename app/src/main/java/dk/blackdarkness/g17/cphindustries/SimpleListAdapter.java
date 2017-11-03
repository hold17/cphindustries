package dk.blackdarkness.g17.cphindustries;

import android.app.Activity;
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
        final ImageView imageWarning = simpleListItemView.findViewById(R.id.simpleListItem_imageWarning);
        final ImageView imageGo = simpleListItemView.findViewById(R.id.simpleListItem_imageGo);

        tvHeading.setText(navListItem.getText());

        // Visibility and color for warning
        if (navListItem.warningIsVisible()) {
            imageWarning.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorWarning));
        } else {
            imageWarning.setVisibility(View.INVISIBLE);
        }

        // Visibility and color for goBtn / wifi status
        if (navListItem.goBtnIsVisible()) {
            imageGo.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
        } else {
            if (navListItem.getConnectionStatus() != null) {
                imageGo.setImageDrawable(navListItem.getConnectionStatus().getDrawable(getContext()));

                // If no connection, color = red, otherwise, green
                if (navListItem.getConnectionStatus() == ConnectionStatus.NO_CONNECTION)
                    imageGo.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorDanger));
                else
                    imageGo.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorPositive));
            }
        }



        if (position != 1)
            imageWarning.setVisibility(View.INVISIBLE);

        return simpleListItemView;
    }
}
