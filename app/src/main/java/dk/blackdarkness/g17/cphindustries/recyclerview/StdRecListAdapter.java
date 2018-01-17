package dk.blackdarkness.g17.cphindustries.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import dk.blackdarkness.g17.cphindustries.R;
import dk.blackdarkness.g17.cphindustries.dto.Item;
import dk.blackdarkness.g17.cphindustries.dto.Scene;
import dk.blackdarkness.g17.cphindustries.dto.Shoot;
import dk.blackdarkness.g17.cphindustries.dto.Weapon;

import dk.blackdarkness.g17.cphindustries.recyclerview.helpers.RecyclerViewClickListener;

public class StdRecListAdapter extends RecyclerView.Adapter<StdRecListAdapter.ItemViewHolder> {
    private static final String TAG = "StdRecListAdapter";
    private List<Item> mItems = new ArrayList<>();
    private final RecyclerViewClickListener listener;
    private final Context context;

    public StdRecListAdapter(Context context, List<Item> items, RecyclerViewClickListener listener) {
        mItems.addAll(items);
        this.context = context;
        this.listener = listener;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.std_recycleview_list_item, parent, false);
        return new ItemViewHolder(view, this.listener);
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position) {
        final Item curItem = mItems.get(position);

        holder.tvHeading.setText(curItem.getName());

        if (curItem instanceof Scene) {
            holder.imageFront.setVisibility(View.GONE);
            holder.imageBack.setImageResource(R.drawable.ic_chevron_right_black_24dp);
            holder.imageBack.setVisibility(View.VISIBLE);
        } else if (curItem instanceof Shoot) {
            holder.imageFront.setVisibility(View.GONE);
            holder.imageBack.setImageResource(R.drawable.ic_chevron_right_black_24dp);
            holder.imageBack.setVisibility(View.VISIBLE);
        } else if (curItem instanceof Weapon) {
            // Set first image to warning no matter what, then invisible if there are no warnings - to align text
            holder.imageFront.setImageResource(R.drawable.ic_warning_orange_24dp);
            if (((Weapon) curItem).getWarnings().size() == 0) {
                holder.imageFront.setVisibility(View.INVISIBLE);
            }
            // Set go button image to the connection status
            holder.imageBack.setImageResource(((Weapon) curItem).getConnectionStatus().getDrawableId());
        }
    }

    public void updateItems(List<Item> items) {
        Log.d(TAG, "updateItems: Items before: " + getItemCount());
        this.mItems = items;
        notifyDataSetChanged();
        Log.d(TAG, "updateItems: Items after: " + getItemCount());
    }

    private Item getItemByPosition(int position) {
        Item item = this.mItems.get(position);
        Log.d(TAG, "getIdFromPosition: position: " + position + " | type: " + item.getClass().toString() + " | ID: " + item.getId());
        return item;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView tvHeading;
        final ImageView imageFront;
        final ImageView imageBack;
        final RecyclerViewClickListener listener;

        ItemViewHolder(View itemView, RecyclerViewClickListener listener) {
            super(itemView);
            tvHeading = itemView.findViewById(R.id.stdRecyclerViewListItem_tvHeading);
            imageFront = itemView.findViewById(R.id.stdRecyclerViewListItem_imageFront);
            imageBack = itemView.findViewById(R.id.stdRecyclerViewListItem_imageBack);

            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Log.d(TAG, "onClick: item clicked at position: " + position);
            listener.onClick(view, getItemByPosition(getAdapterPosition()).getId());
        }
    }
}
