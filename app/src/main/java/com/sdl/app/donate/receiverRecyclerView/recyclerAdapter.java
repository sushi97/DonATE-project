package com.sdl.app.donate.receiverRecyclerView;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sdl.app.donate.Home_Screeen;
import com.sdl.app.donate.R;
import com.sdl.app.donate.ngolist;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.MyViewHolder> {

    ApiInterface apiInterface = ApiClient.getapiClient().create(ApiInterface.class);
//    Button button = new Button();
//    button =
    private List<ngolist> ngoInfos;
//    private ListItemClickListener  mclicklistener;
    Context context;

//    interface ListItemClickListener {
//        void onListItemClick(int itemClickedIndex);
//    }

    public recyclerAdapter(List<ngolist> ngoInfos, Context context) {
        this.ngoInfos = ngoInfos;
//        this.mclicklistener = listener;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item_ngo_choose, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.name.setText(ngoInfos.get(position).getName());
        holder.qty.setText(ngoInfos.get(position).getAmount()+"");
        //String ad = ngoInfos.get(position).getAddress_location() + ", " + ngoInfos.get(position).getAddress_city();
//        holder.address.setText(ngoInfos.get(position).getAddress());
        //holder.image.se  !!get image.!


        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent getin = new Intent();
                int noof = getin.getIntExtra("LOCCOUNT", 0);
                Toast.makeText(context, "index : " + position, Toast.LENGTH_LONG).show();
                ngolist sendinfo = new ngolist();
                sendinfo.userFrom = "Sushrut";
                sendinfo.userTo = ngoInfos.get(position).getName();
                sendinfo.address = "Pune";
                sendinfo.quantity = noof+"";

                Call<List<ngolist>> call = apiInterface.getrecv(sendinfo);

                call.enqueue(new Callback<List<ngolist>>() {
                    @Override
                    public void onResponse(Call<List<ngolist>> call, Response<List<ngolist>> response) {
                        List<ngolist> res = response.body();
                        if(res.get(0).isSuccess()) {
                            //Toast.makeText(getApplicationContext(), "Successfully sent to "+ finallist.get(itemClickedIndex).getName(), Toast.LENGTH_LONG).show();
                            Log.e("TAG", ngoInfos.get(position).getName() + ":::" + position);
                            Intent intent = new Intent(context, Home_Screeen.class);
                            context.startActivity(intent);
                            new ngo_receiver().finish();
                        }else {
                            Toast.makeText(context, "Successfully not sent", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ngolist>> call, Throwable t) {

                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return ngoInfos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        //ImageView image;
        TextView name, qty, address;
        Button button;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.org_receive_name);
            qty = (TextView) itemView.findViewById(R.id.org_receive_qty);
//            address = (TextView) itemView.findViewById(R.id.org_receive_address);
            //image = (ImageView) itemView.findViewById(R.id.org_receive_image);
            button = (Button) itemView.findViewById(R.id.toDonate);
            //itemView.setOnClickListener(this);
        }


//        @Override
//        public void onClick(View view) {
//            mclicklistener.onListItemClick(getAdapterPosition());
//        }
    }
}
