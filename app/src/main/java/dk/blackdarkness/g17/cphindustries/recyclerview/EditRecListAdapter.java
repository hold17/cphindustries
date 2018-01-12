package dk.blackdarkness.g17.cphindustries.recyclerview;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import dk.blackdarkness.g17.cphindustries.R;
import dk.blackdarkness.g17.cphindustries.dataaccess.ApplicationConfig;
import dk.blackdarkness.g17.cphindustries.dto.Item;
import dk.blackdarkness.g17.cphindustries.recyclerview.helpers.ItemTouchHelperAdapter;
import dk.blackdarkness.g17.cphindustries.recyclerview.helpers.ItemTouchHelperViewHolder;
import dk.blackdarkness.g17.cphindustries.recyclerview.helpers.OnStartDragListener;
import dk.blackdarkness.g17.cphindustries.recyclerview.helpers.RecyclerViewClickListener;

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
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.edit_recyclerview_list_item, parent, false);
        return new ItemViewHolder(view, this.listener);
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position) {
        final Item curItem = mItems.get(position);

        holder.tvHeading.setText(curItem.getName());
        holder.etHeading.setText(curItem.getName());
        holder.etHeading.setVisibility(View.GONE);
        holder.imageFront.setImageResource(R.drawable.ic_reorder_black_24px);
        holder.imageBack.setImageResource(R.drawable.ic_edit_black_24dp);
        // Start a drag whenever the handle view it touched
        holder.imageFront.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                mDragStartListener.onStartDrag(holder);
            }
            return false;
        });
        holder.imageBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.etHeading.getVisibility() == View.GONE) {
                    holder.tvHeading.setVisibility(View.GONE);
                    holder.etHeading.setVisibility(View.VISIBLE);
                } else {
                    holder.tvHeading.setVisibility(View.VISIBLE);
                    holder.etHeading.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onItemDismiss(int position) {
        final Item deletedItem = mItems.get(position);
        System.out.println("Iraq: Deleting scene " + deletedItem.getId() + "(" + deletedItem.getName() + ")");

        // TODO: Only works for scenes, crashes for shoots and weapons - See issue #xx on GH
//        ApplicationConfig.getDaoFactory().getSceneDao().delete(deletedItem.getId());

        mItems.remove(position);
        notifyItemRemoved(position);

        System.out.println("IRAQ: I was dismissed: " + position);
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

    static class ItemViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder, OnClickListener {
        final TextView tvHeading;
        final EditText etHeading;
        final ImageView imageFront;
        final ImageView imageBack;
        final RecyclerViewClickListener listener;

        ItemViewHolder(View itemView, RecyclerViewClickListener listener) {
            super(itemView);
            tvHeading = itemView.findViewById(R.id.editRecyclerViewListItem_tvHeading);
            etHeading = itemView.findViewById(R.id.editRecyclerViewListItem_etHeading);
            imageFront = itemView.findViewById(R.id.editRecyclerViewListItem_imageFront);
            imageBack = itemView.findViewById(R.id.editRecyclerViewListItem_imageBack);

            this.listener = listener;

            imageBack.setOnClickListener(this);
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
            System.out.println("item clicked on View: " + view + " Position: " + position);
            this.listener.onClick(view, position);
        }
    }
}
