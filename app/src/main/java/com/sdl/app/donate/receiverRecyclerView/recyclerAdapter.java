package com.sdl.app.donate.receiverRecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sdl.app.donate.R;
import com.sdl.app.donate.ngolist;

import java.util.List;

public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.MyViewHolder> {

    private List<ngolist> ngoInfos;
    private ListItemClickListener  mclicklistener;

    interface ListItemClickListener {
        void onListItemClick(int itemClickedIndex);
    }

    public recyclerAdapter(List<ngolist> ngoInfos, ListItemClickListener listener) {
        this.ngoInfos = ngoInfos;
        this.mclicklistener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item_ngo_choose, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.name.setText(ngoInfos.get(position).getName());
        holder.qty.setText(ngoInfos.get(position).getAmount()+"");
        //String ad = ngoInfos.get(position).getAddress_location() + ", " + ngoInfos.get(position).getAddress_city();
        holder.address.setText(ngoInfos.get(position).getAddress());
        //holder.image.se  !!get image.!

    }

    @Override
    public int getItemCount() {
        return ngoInfos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //ImageView image;
        TextView name, qty, address;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.org_receive_name);
            qty = (TextView) itemView.findViewById(R.id.org_receive_qty);
            address = (TextView) itemView.findViewById(R.id.org_receive_address);
            //image = (ImageView) itemView.findViewById(R.id.org_receive_image);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            mclicklistener.onListItemClick(getAdapterPosition());
        }
    }
}
