package dk.blackdarkness.g17.cphindustries.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import dk.blackdarkness.g17.cphindustries.R;
import dk.blackdarkness.g17.cphindustries.dto.Item;
import dk.blackdarkness.g17.cphindustries.dto.Scene;
import dk.blackdarkness.g17.cphindustries.dto.Shoot;
import dk.blackdarkness.g17.cphindustries.dto.Weapon;

import dk.blackdarkness.g17.cphindustries.recyclerview.helpers.ItemTouchHelperAdapter;
import dk.blackdarkness.g17.cphindustries.recyclerview.helpers.RecyclerViewClickListener;

public class StdRecListAdapter extends RecyclerView.Adapter<ItemViewHolderCommon> implements ItemTouchHelperAdapter {
    private final List<Item> mItems = new ArrayList<>();
    private final RecyclerViewClickListener listener;
    private final Context context;

    public StdRecListAdapter(Context context, List<Item> items, RecyclerViewClickListener listener) {
        mItems.addAll(items);
        this.context = context;
        this.listener = listener;
    }

    @Override
    public ItemViewHolderCommon onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.simple_list_item, parent, false);
        return new ItemViewHolderCommon(view, this.listener);
    }

    @Override
    public void onBindViewHolder(final ItemViewHolderCommon holder, int position) {
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

    @Override
    public void onItemDismiss(int position) {
        mItems.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Item movedItem = mItems.remove(fromPosition);
        mItems.add(toPosition, movedItem);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}
