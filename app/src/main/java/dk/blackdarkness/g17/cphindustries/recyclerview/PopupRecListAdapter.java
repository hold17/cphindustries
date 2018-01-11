package dk.blackdarkness.g17.cphindustries.recyclerview;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import dk.blackdarkness.g17.cphindustries.R;
import dk.blackdarkness.g17.cphindustries.dto.Item;

//Created by Christoffer on 09-01-2018.



public class PopupRecListAdapter extends RecyclerView.Adapter<PopupRecListAdapter.popupViewHolder> {


    private final List<Item> mItems = new ArrayList<>();
    private final Context context;

    public PopupRecListAdapter(Context context, List<Item> items){
        mItems.addAll(items);
        this.context = context;


    }

    @Override
    public popupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate
        View view = LayoutInflater.from(context).inflate(R.layout.weapon_detail_popup_item, parent, false);
        return new popupViewHolder(view);

    }

    @Override
    public void onBindViewHolder(popupViewHolder holder, int position) {
        final Item curItem = mItems.get(position);

        holder.listTv.setText(curItem.getName());
//        holder.listCb.setChecked(false);

    }

    @Override
    public int getItemCount() {

        return mItems.size();
    }

    class popupViewHolder extends RecyclerView.ViewHolder{
        // add item elements
        final TextView listTv;
//        View listV;
        final CheckBox listCb;

        public popupViewHolder(View itemView) {
            super(itemView);

            listTv = (TextView) itemView.findViewById(R.id.recListText);
            listCb = (CheckBox) itemView.findViewById(R.id.recListCheckBox);
        }
    }
}

