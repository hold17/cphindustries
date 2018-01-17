package dk.blackdarkness.g17.cphindustries.recyclerview;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import dk.blackdarkness.g17.cphindustries.dto.Scene;
import dk.blackdarkness.g17.cphindustries.dto.Shoot;
import dk.blackdarkness.g17.cphindustries.dto.Weapon;
import dk.blackdarkness.g17.cphindustries.helper.softInputHelper;
import dk.blackdarkness.g17.cphindustries.recyclerview.helpers.ItemTouchHelperAdapter;
import dk.blackdarkness.g17.cphindustries.recyclerview.helpers.ItemTouchHelperViewHolder;
import dk.blackdarkness.g17.cphindustries.recyclerview.helpers.OnStartDragListener;
import dk.blackdarkness.g17.cphindustries.recyclerview.helpers.RecyclerViewClickListener;

public class EditRecListAdapter extends RecyclerView.Adapter<EditRecListAdapter.ItemViewHolder> implements ItemTouchHelperAdapter {
    private static final String TAG = "EditRecListAdapter";
    private List<Item> mItems = new ArrayList<>();
    private final OnStartDragListener mDragStartListener;
    private final RecyclerViewClickListener listener;
    private final Context context;
    private EditText editText;
    private boolean isEditingText = false;

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

        this.editText = holder.etHeading;

        holder.tvHeading.setText(curItem.getName());
        holder.etHeading.setText(curItem.getName());
        holder.etHeading.setVisibility(View.GONE);
        holder.imageFront.setImageResource(R.drawable.ic_reorder_black_24px);
        holder.imageBack.setImageResource(R.drawable.ic_edit_black_24dp);

        // Start a drag whenever the handle view is touched
        holder.imageFront.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN && !isEditingText) {
                    mDragStartListener.onStartDrag(holder);
                }
                return false;
            }
        });
    }

    public void updateItems(List<Item> items) {
        Log.d(TAG, "updateItems: Items before: " + getItemCount());
        this.mItems = items;
        notifyDataSetChanged();
        Log.d(TAG, "updateItems: Items after: " + getItemCount());
    }

    public String getEditTextString(int position) {
        if (this.isEditingText) {
            this.isEditingText = false;
            Log.d(TAG, "getEditTextString: input string" + this.editText.getText().toString());
            notifyItemChanged(position);
            return this.editText.getText().toString();
        }
        return "";
    }

    // this could be useful at some point
    public Item getItemByPosition(int position) {
        Item item = this.mItems.get(position);
        Log.d(TAG, "getIdFromPosition: position: " + position + " | type: " + item.getClass().toString() + " | ID: " + item.getId());
        return item;
    }

    @Override
    public void onItemDismiss(int position) {
        final Item deletedItem = mItems.get(position);
        Log.d(TAG, "onItemDismiss: Deleting item: " + deletedItem.getClass().toString() + " id: "  + deletedItem.getId() + "(" + deletedItem.getName() + ")");

        if (deletedItem instanceof Scene)
            ApplicationConfig.getDaoFactory().getSceneDao().delete(deletedItem.getId());
        else if (deletedItem instanceof Shoot)
            ApplicationConfig.getDaoFactory().getShootDao().delete(deletedItem.getId());
        else if (deletedItem instanceof Weapon)
            ApplicationConfig.getDaoFactory().getWeaponDao().delete(deletedItem.getId());

        mItems.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Item movedItem = mItems.remove(fromPosition);
        mItems.add(toPosition, movedItem);
        notifyItemMoved(fromPosition, toPosition);
        Log.d(TAG, "onItemMove: moved item to: " + toPosition + " from: " + fromPosition);
        return true;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder, OnClickListener {
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
        public void onClick(View v) {
            //TODO: maybe do something about this insanity at some point

            if (etHeading.getVisibility() == View.GONE) {
                isEditingText = true;
                tvHeading.setVisibility(View.GONE);
                etHeading.setVisibility(View.VISIBLE);
                etHeading.setFocusableInTouchMode(true);
                etHeading.requestFocus();
                softInputHelper.showSoftInput(context, etHeading);
                imageBack.setImageResource(R.drawable.ic_close_red_24dp);
            } else {
                isEditingText = false;
                listener.onClick(v, getAdapterPosition());
                softInputHelper.hideSoftInput(context, v);
                tvHeading.setVisibility(View.VISIBLE);
                etHeading.setVisibility(View.GONE);
                imageBack.setImageResource(R.drawable.ic_edit_black_24dp);
            }
        }
    }
}
