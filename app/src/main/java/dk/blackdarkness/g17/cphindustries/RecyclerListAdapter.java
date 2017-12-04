/*
 * Copyright (C) 2015 Paul Burke
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dk.blackdarkness.g17.cphindustries;

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

import dk.blackdarkness.g17.cphindustries.dto.ConnectionStatus;
import dk.blackdarkness.g17.cphindustries.dto.Item;
import dk.blackdarkness.g17.cphindustries.dto.Scene;
import dk.blackdarkness.g17.cphindustries.dto.Shoot;
import dk.blackdarkness.g17.cphindustries.dto.Weapon;
import dk.blackdarkness.g17.cphindustries.helper.ItemTouchHelperAdapter;
import dk.blackdarkness.g17.cphindustries.helper.ItemTouchHelperViewHolder;
import dk.blackdarkness.g17.cphindustries.helper.OnStartDragListener;
import dk.blackdarkness.g17.cphindustries.helper.RecyclerViewClickListener;

/**
 * Simple RecyclerView.Adapter that implements {@link ItemTouchHelperAdapter} to respond to move and
 * dismiss events from a {@link android.support.v7.widget.helper.ItemTouchHelper}.
 *
 * @author Paul Burke (ipaulpro)
 */
public class RecyclerListAdapter extends RecyclerView.Adapter<RecyclerListAdapter.ItemViewHolder>
        implements ItemTouchHelperAdapter {

    private final List<NavListItem> mItems = new ArrayList<>();
    private final OnStartDragListener mDragStartListener;
    private final RecyclerViewClickListener listener;
    private final Context context;

    public RecyclerListAdapter(Context context, OnStartDragListener dragStartListener, List<NavListItem> items, RecyclerViewClickListener listener) {
        mDragStartListener = dragStartListener;
        mItems.addAll(items);
        this.context=context;
        this.listener = listener;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.simple_list_item, parent, false);
        return new ItemViewHolder(view, this.listener);
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position) {
        final NavListItem curNavListItem = mItems.get(position);
        final Item item = curNavListItem.getItem();

        holder.tvHeading.setText(curNavListItem.getItem().getName());

        if (curNavListItem.isEditable()) {
            holder.imageFront.setImageResource(R.drawable.ic_reorder_black_24px);
            holder.imageBack.setImageResource(R.drawable.ic_edit_black_24dp);
            // Start a drag whenever the handle view it touched
            holder.imageFront.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        mDragStartListener.onStartDrag(holder);
                    }
                    if (v.performClick())
                        System.out.println("onTouch detected a click!");
                    return false;
                }
            });
        } else if (item instanceof Scene) {
            holder.imageFront.setVisibility(View.GONE);
            holder.imageBack.setImageResource(R.drawable.ic_chevron_right_black_24dp);
            holder.imageBack.setVisibility(View.VISIBLE);
        } else if (item instanceof Shoot) {
            holder.imageFront.setVisibility(View.GONE);
            holder.imageBack.setImageResource(R.drawable.ic_chevron_right_black_24dp);
            holder.imageBack.setVisibility(View.VISIBLE);
        } else if (item instanceof Weapon) {
            // Warnings
            if (((Weapon) item).getWarnings().size() > 0) {
                holder.imageFront.setColorFilter(ContextCompat.getColor(context, R.color.colorWarning));
                holder.imageFront.setVisibility(View.VISIBLE);
            } else {
                holder.imageFront.setVisibility(View.INVISIBLE);
            }

            // Set go button image to the connection status
            holder.imageBack.setImageDrawable(((Weapon) item).getConnectionStatus().getDrawable(context));

            if (((Weapon) item).getConnectionStatus() == ConnectionStatus.NO_CONNECTION) {
                holder.imageBack.setColorFilter(ContextCompat.getColor(context, R.color.colorDanger));
            } else {
                holder.imageBack.setColorFilter(ContextCompat.getColor(context, R.color.colorPositive));
            }
        }
    }

    @Override
    public void onItemDismiss(int position) {
        mItems.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        NavListItem movedItem = mItems.remove(fromPosition);
        mItems.add(toPosition, movedItem);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    /**
     * Simple example of a view holder that implements {@link ItemTouchHelperViewHolder} and has a
     * "handle" view that initiates a drag event when touched.
     */
    public static class ItemViewHolder extends RecyclerView.ViewHolder implements
//            ItemTouchHelperViewHolder,
            View.OnClickListener
    {

        private final TextView tvHeading;
        private final ImageView imageFront;
        private final ImageView imageBack;

        private final RecyclerViewClickListener listener;

        private ItemViewHolder(View itemView, RecyclerViewClickListener listener) {
            super(itemView);
            tvHeading = (TextView) itemView.findViewById(R.id.simpleListItem_tvHeading);
            imageFront = (ImageView) itemView.findViewById(R.id.simpleListItem_imageFront);
            imageBack = (ImageView) itemView.findViewById(R.id.simpleListItem_imageBack);

            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            System.out.println("I WAS CLICKED!!!");
            this.listener.onClick(view, getAdapterPosition());

        }

        //        @Override
//        public void onItemSelected() {
//            itemView.setBackgroundColor(Color.LTGRAY);
//        }
//
//        @Override
//        public void onItemClear() {
//            itemView.setBackgroundColor(0);
//        }
    }
}
