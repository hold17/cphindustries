package dk.blackdarkness.g17.cphindustries.recyclerview;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

//Created by Christoffer on 09-01-2018.



public class PopupRecListAdapter extends RecyclerView.Adapter<PopupRecListAdapter.popupViewHolder> {

    @Override
    public popupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate
        return null;
    }

    @Override
    public void onBindViewHolder(popupViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class popupViewHolder extends RecyclerView.ViewHolder{
        // add item elements
        public popupViewHolder(View itemView) {
            super(itemView);
        }
    }
}

