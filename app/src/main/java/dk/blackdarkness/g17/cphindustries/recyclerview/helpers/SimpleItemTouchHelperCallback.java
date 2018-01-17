package dk.blackdarkness.g17.cphindustries.recyclerview.helpers;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import dk.blackdarkness.g17.cphindustries.recyclerview.EditRecListAdapter;

public class SimpleItemTouchHelperCallback extends ItemTouchHelper.Callback {
    private static final float ALPHA_FULL = 1.0f;
    private final ItemTouchHelperAdapter mAdapter;
    private boolean dragEnabled = true;
    private boolean swipeEnabled = true;

    public SimpleItemTouchHelperCallback(ItemTouchHelperAdapter adapter) {
        mAdapter = adapter;
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
        swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
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
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            // fade out the view as it is swiped out of the parent's bounds
            final float alpha = ALPHA_FULL - Math.abs(dX) / (float) viewHolder.itemView.getWidth();
            viewHolder.itemView.setAlpha(alpha);
            viewHolder.itemView.setTranslationX(dX);
        } else {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
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
