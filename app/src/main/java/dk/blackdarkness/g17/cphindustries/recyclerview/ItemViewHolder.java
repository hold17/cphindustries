package dk.blackdarkness.g17.cphindustries.recyclerview;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import dk.blackdarkness.g17.cphindustries.R;
import dk.blackdarkness.g17.cphindustries.recyclerview.helpers.ItemTouchHelperViewHolder;
import dk.blackdarkness.g17.cphindustries.recyclerview.helpers.RecyclerViewClickListener;

class ItemViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder, View.OnClickListener {
    final TextView tvHeading;
    final ImageView imageFront;
    final ImageView imageBack;
    final RecyclerViewClickListener listener;

    ItemViewHolder(View itemView, RecyclerViewClickListener listener) {
        super(itemView);
        tvHeading = itemView.findViewById(R.id.simpleListItem_tvHeading);
        imageFront = itemView.findViewById(R.id.simpleListItem_imageFront);
        imageBack = itemView.findViewById(R.id.simpleListItem_imageBack);

        this.listener = listener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onItemSelected() {
        itemView.setBackgroundColor(Color.LTGRAY);
    }

    @Override
    public void onItemClear() {
        itemView.setBackgroundColor(0);
    }

    @Override
    public void onClick(View view) {
        int position = getAdapterPosition();
        System.out.println("item clicked on View: " + view.getTag() + " Position: " + position);
        this.listener.onClick(view, position);
    }
}