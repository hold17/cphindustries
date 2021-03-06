package dk.blackdarkness.g17.cphindustries.recyclerview.helpers;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import dk.blackdarkness.g17.cphindustries.R;
import dk.blackdarkness.g17.cphindustries.recyclerview.EditRecListAdapter;

public class SimpleItemTouchHelperCallback extends ItemTouchHelper.Callback {
    private final Drawable deleteIcon;
    private final int intrinsicWidth;
    private final int intrinsicHeight;
    private final ColorDrawable background;
    private final int backgroundColor;
    private static final float ALPHA_FULL = 1.0f;
    private final ItemTouchHelperAdapter mAdapter;
    private boolean dragEnabled = true;
    private boolean swipeEnabled = true;

    public SimpleItemTouchHelperCallback(Context context, ItemTouchHelperAdapter adapter) {
        mAdapter = adapter;
        dragEnabled = swipeEnabled = true;

        this.deleteIcon = ContextCompat.getDrawable(context, R.drawable.ic_delete_white_24dp);
        this.intrinsicWidth = this.deleteIcon.getIntrinsicWidth();
        this.intrinsicHeight = this.deleteIcon.getIntrinsicHeight();
        this.background = new ColorDrawable();
        this.backgroundColor = Color.parseColor("#f44336");
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return dragEnabled;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return swipeEnabled;
    }

    public void setLongPressDragEnabled(boolean dragEnabled) {
        this.dragEnabled = dragEnabled;
    }

    public void setSwipeEnabled(boolean swipeEnabled) {
        this.swipeEnabled = swipeEnabled;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        // set movement flags
        final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags;
        // disable swipe when editing text in editRecyclerAdapter
        if (recyclerView.getAdapter() instanceof EditRecListAdapter) {
            if (((EditRecListAdapter) recyclerView.getAdapter()).isEditingText) {
                swipeFlags = 0;
                return makeMovementFlags(dragFlags, swipeFlags);
            }
        }
        swipeFlags = ItemTouchHelper.LEFT/* | ItemTouchHelper.END*/;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder source, RecyclerView.ViewHolder target) {
        if (source.getItemViewType() != target.getItemViewType()) {
            return false;
        }
        // notify the adapter of the move
        return mAdapter.onItemMove(source.getAdapterPosition(), target.getAdapterPosition());
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int i) {
        // notify the adapter of the dismissal
        mAdapter.onItemDismiss(viewHolder.getAdapterPosition());
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        //Log.d("SimpleItemTouchHelper", "onChildDraw: isCurrentlyActive: " + isCurrentlyActive + " dX: " + dX + " dY: " + dY);
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            View itemView = viewHolder.itemView;

            int itemHeight = viewHolder.itemView.getBottom() - viewHolder.itemView.getTop();
            this.background.setColor(this.backgroundColor);
            this.background.setBounds(itemView.getRight() + (int)dX, itemView.getTop(), itemView.getRight(), itemView.getBottom());
            this.background.draw(c);

            int deleteIconTop = itemView.getTop() + (itemHeight - this.intrinsicHeight) / 2;
            int deleteIconMargin = (itemHeight - this.intrinsicHeight) / 2;
            int deleteIconLeft = itemView.getRight() - deleteIconMargin - this.intrinsicWidth;
            int deleteIconRight = itemView.getRight() - deleteIconMargin;
            int deleteIconBottom = deleteIconTop + this.intrinsicHeight;

            // only draw icon when view is not super close to edge
            // fixes weird bug where deleteicon could be seen through parent object
            if (dX < -0.1f) {
                this.deleteIcon.setBounds(deleteIconLeft, deleteIconTop, deleteIconRight, deleteIconBottom);
                this.deleteIcon.draw(c);
            }

            // fade out the view as it is swiped out of the parent's bounds
            final float alpha = ALPHA_FULL - Math.abs(dX) / (float) viewHolder.itemView.getWidth();
            viewHolder.itemView.setAlpha(alpha);
            viewHolder.itemView.setTranslationX(dX);
        }
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        // we only want the active item to change
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            if (viewHolder instanceof ItemTouchHelperViewHolder) {
                // let the view holder know that this item is being moved or dragged
                ItemTouchHelperViewHolder itemViewHolder = (ItemTouchHelperViewHolder) viewHolder;
                itemViewHolder.onItemSelected();
            }
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);

        viewHolder.itemView.setAlpha(ALPHA_FULL);

        if (viewHolder instanceof ItemTouchHelperViewHolder) {
            // tell the viewHolder it should restore the idle state
            ItemTouchHelperViewHolder itemViewHolder = (ItemTouchHelperViewHolder) viewHolder;
            itemViewHolder.onItemClear();
        }
    }
}
