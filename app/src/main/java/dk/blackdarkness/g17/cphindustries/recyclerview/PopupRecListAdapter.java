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
import dk.blackdarkness.g17.cphindustries.dataaccess.ApplicationConfig;
import dk.blackdarkness.g17.cphindustries.dto.Item;

//Created by Christoffer on 09-01-2018.



public class PopupRecListAdapter extends RecyclerView.Adapter<PopupRecListAdapter.popupViewHolder> {


    private final List<Item> mItems = new ArrayList<>();
    private final Context context;
    private CallbackPopup callbackPopup;
    private int weaponId;


    public PopupRecListAdapter(Context context, List<Item> items, CallbackPopup callbackPopup, int weaponId){
        mItems.addAll(items);
        this.context = context;
        this.callbackPopup = callbackPopup;
        this.weaponId = weaponId;


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

        //marks already existing weapons in shoot
        if (ApplicationConfig.getDaoFactory().getShootWeaponDao().get().contains(curItem.getId())){
            holder.listCb.setChecked(true);
        } else {
            holder.listCb.setChecked(false);
        }


        //Adding or remove item by check.
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                CheckBox cb = (CheckBox) v;

                //Check if checked
                if (cb.isChecked()){
                    callbackPopup.onCheckClickSend(curItem.getId());

                }
                else if (!cb.isChecked()){
                    ApplicationConfig.getDaoFactory().getShootWeaponDao().delete(mItems.get(pos).getId());
                    //Snak den her med de andre

                }
            }
        });

    }

    @Override
    public int getItemCount() {

        return mItems.size();
    }

    class popupViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        // add item elements
        final TextView listTv;
//        View listV;
        final CheckBox listCb;
        ItemClickListener itemClickListener;

        public popupViewHolder(View itemView) {
            super(itemView);

            listTv = (TextView) itemView.findViewById(R.id.recListText);
            listCb = (CheckBox) itemView.findViewById(R.id.recListCheckBox);
            listCb.setOnClickListener(this);

        }
     public void setItemClickListener(ItemClickListener ic){
            this.itemClickListener=ic;
     }

        @Override
        public void onClick(View view) {
            this.itemClickListener.onItemClick(view, getLayoutPosition());
        }
    }
}

