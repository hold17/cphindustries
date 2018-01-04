package dk.blackdarkness.g17.cphindustries.recyclerview;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import dk.blackdarkness.g17.cphindustries.R;
import dk.blackdarkness.g17.cphindustries.dto.ConnectionStatus;
import dk.blackdarkness.g17.cphindustries.dto.Item;
import dk.blackdarkness.g17.cphindustries.dto.Scene;
import dk.blackdarkness.g17.cphindustries.dto.Shoot;
import dk.blackdarkness.g17.cphindustries.dto.Weapon;
import dk.blackdarkness.g17.cphindustries.recyclerview.helpers.ItemTouchHelperAdapter;
import dk.blackdarkness.g17.cphindustries.recyclerview.helpers.ItemTouchHelperViewHolder;
import dk.blackdarkness.g17.cphindustries.recyclerview.helpers.OnStartDragListener;
import dk.blackdarkness.g17.cphindustries.recyclerview.helpers.RecyclerViewClickListener;

/**
 * Created by Thoma on 01/03/2018.
 */

public class EditRecListAdapter extends RecyclerView.Adapter<EditRecListAdapter.ItemViewHolder> implements ItemTouchHelperAdapter {

    private final List<Item> mItems = new ArrayList<>();
    private final OnStartDragListener mDragStartListener;
    private final RecyclerViewClickListener listener;
    private final Context context;

    public EditRecListAdapter(Context context, OnStartDragListener dragStartListener, List<Item> items, RecyclerViewClickListener listener) {
        mDragStartListener = dragStartListener;
        mItems.addAll(items);
        this.context = context;
        this.listener = listener;
    }

    @Override
    public EditRecListAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.simple_list_item, parent, false);
        return new EditRecListAdapter.ItemViewHolder(view, this.listener);
    }

    @Override
    public void onBindViewHolder(final EditRecListAdapter.ItemViewHolder holder, int position) {
        final Item curItem = mItems.get(position);

        holder.tvHeading.setText(curItem.getName());
        holder.imageFront.setImageResource(R.drawable.ic_reorder_black_24px);
        holder.imageBack.setImageResource(R.drawable.ic_edit_black_24dp);
        // Start a drag whenever the handle view it touched
        holder.imageFront.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                mDragStartListener.onStartDrag(holder);
            }
            return false;
        });
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


    public static class ItemViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder, View.OnClickListener {
        private final TextView tvHeading;
        private final ImageView imageFront;
        private final ImageView imageBack;
        private final RecyclerViewClickListener listener;

        private ItemViewHolder(View itemView, RecyclerViewClickListener listener) {
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
            System.out.println("dk.blackdarkness.g17.cphindustries.RecyclerView item clicked on View: " + view.getTag() +  " Position: " + position);
            this.listener.onClick(view, position);
        }
    }
}
