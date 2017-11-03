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

public class SimpleListAdapter extends ArrayAdapter<String> {
    public SimpleListAdapter(Context context, String[] foods) {
        super(context, R.layout.simple_list_item, foods);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final LayoutInflater inflator = LayoutInflater.from(getContext());

        final View simpleListItemView = inflator.inflate(R.layout.simple_list_item, parent, false);

        final String foodItem = getItem(position);
        final TextView tvHeading =     simpleListItemView.findViewById(R.id.simpleListItem_tvHeading);
        final ImageView imageWarning = simpleListItemView.findViewById(R.id.simpleListItem_imageWarning);
        final ImageView imageGo = simpleListItemView.findViewById(R.id.simpleListItem_imageGo);


        tvHeading.setText(foodItem);
        imageWarning.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorWarning));
        imageGo.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));

        if (position != 1)
            imageWarning.setVisibility(View.INVISIBLE);

        return simpleListItemView;
    }
}
