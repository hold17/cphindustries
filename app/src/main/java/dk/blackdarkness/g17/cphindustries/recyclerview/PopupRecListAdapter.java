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
import dk.blackdarkness.g17.cphindustries.dataaccess.ShootWeaponDao;
import dk.blackdarkness.g17.cphindustries.dto.Item;
import dk.blackdarkness.g17.cphindustries.recyclerview.helpers.PopupCallback;

public class PopupRecListAdapter extends RecyclerView.Adapter<PopupRecListAdapter.ItemViewHolder> {
    private final List<Item> mItems = new ArrayList<>();
    private final Context context;
    private PopupCallback popupCallback;
    private int weaponId;
    private ShootWeaponDao shootWeaponDao;

    public PopupRecListAdapter(Context context, List<Item> items, PopupCallback popupCallback, int weaponId){
        mItems.addAll(items);
        this.context = context;
        this.popupCallback = popupCallback;
        this.weaponId = weaponId;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.weapon_detail_popup_item, parent, false);

        // TODO: Find way to not have a dao in this class at all
        this.shootWeaponDao = ApplicationConfig.getDaoFactory().getShootWeaponDao();

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        final Item curItem = mItems.get(position);

        holder.listTv.setText(curItem.getName());

        // marks already existing shoots related to weapon
        if (this.shootWeaponDao.getShootWeapon(mItems.get(position).getId(), this.weaponId) != null) {
            holder.listCb.setChecked(true);
        } else {
            holder.listCb.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        final TextView listTv;
        final CheckBox listCb;

        ItemViewHolder(View itemView) {
            super(itemView);

            listTv = itemView.findViewById(R.id.recListText);
            listCb = itemView.findViewById(R.id.recListCheckBox);
            listCb.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int shootId = mItems.get(getAdapterPosition()).getId();
            // send back checkbox checked event
            CheckBox cb = (CheckBox) view;
            if (cb.isChecked()){
                popupCallback.onCheckClickSend(shootId, true);
            }
            else if (!cb.isChecked()){
                popupCallback.onCheckClickSend(shootId, false);
            }
        }
    }
}

