package dk.blackdarkness.g17.cphindustries.recyclerview;

import android.annotation.SuppressLint;
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
    public boolean isEditingText = false;
    private int itemUnderEdit = -1;
    private RecyclerView recyclerView;

    public EditRecListAdapter(Context context, OnStartDragListener dragStartListener, List<Item> items, RecyclerViewClickListener listener) {
        mDragStartListener = dragStartListener;
        mItems.addAll(items);
        this.context = context;
        this.listener = listener;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.edit_recyclerview_list_item, parent, false);
        return new ItemViewHolder(view, this.listener);
    }

    @SuppressLint("ClickableViewAccessibility")
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

    public String setNewItemTextAndReturnText() {
        if (this.isEditingText) {
            Log.d(TAG, "getEditTextString: input string" + this.editText.getText().toString());
            String newName = this.editText.getText().toString();
            mItems.get(itemUnderEdit).setName(newName);
            notifyItemChanged(itemUnderEdit);
            ((ItemViewHolder) recyclerView.getChildViewHolder(recyclerView.getChildAt(itemUnderEdit))).disableEditMode(recyclerView.getRootView());
            this.itemUnderEdit = -1;
            this.isEditingText = false;
            return newName;
        }
        // TODO: remove before entering production!
        throw new NullPointerException("isEditingText was NOT true somehow.\nThis obviously shouldn't happen.\nPlease contact Anders Wiberg Olsen at anders@wiberg.tech");
    }

    private Item getItemByPosition(int position) {
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
        public void onClick(View view) {
            int position = getAdapterPosition();
            // call this on very first click on editPen in recyclerAdapter
            if (!isEditingText && itemUnderEdit != position) {
                itemUnderEdit = getAdapterPosition();
                isEditingText = true;
                enableEditMode();
            }
            // call this on first click on editPen on different item than last clicked
            else if (isEditingText && itemUnderEdit != position) {
                // call disableEditMode() on last clicked to clear it's state;
                ((ItemViewHolder) recyclerView.getChildViewHolder(recyclerView.getChildAt(itemUnderEdit))).disableEditMode(view);
                itemUnderEdit = position;
                enableEditMode();
            }
            // click was on same item as last clicked, reset edit state
            else {
                itemUnderEdit = -1;
                isEditingText = false;
                disableEditMode(view);
            }
            // send click event with itemId to fragment
            Log.d(TAG, "onClick: item clicked at position: " + position);
            listener.onClick(view, getItemByPosition(getAdapterPosition()).getId());
        }

        void disableEditMode(View view) {
            etHeading.setText(getItemByPosition(getAdapterPosition()).getName());
            etHeading.setVisibility(View.GONE);
            tvHeading.setVisibility(View.VISIBLE);
            softInputHelper.hideSoftInput(context, view);
            imageBack.setImageResource(R.drawable.ic_edit_black_24dp);
        }

        void enableEditMode() {
            tvHeading.setVisibility(View.GONE);
            etHeading.setVisibility(View.VISIBLE);
            etHeading.setFocusableInTouchMode(true);
            etHeading.requestFocus();
            softInputHelper.showSoftInput(context, etHeading);
            imageBack.setImageResource(R.drawable.ic_close_black_24dp);
        }
    }
}
