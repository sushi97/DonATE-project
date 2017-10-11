package com.sdl.app.donate.mydonations;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sdl.app.donate.R;
import com.sdl.app.donate.ngolist;

import java.util.List;

/**
 * Created by vikas on 8/10/17.
 */

public class RecAdapter extends RecyclerView.Adapter<RecAdapter.MyViewHolder> {
    List<ngolist> ngoinfos;

    public RecAdapter(List<ngolist> ngoinfos) {
        this.ngoinfos = ngoinfos;
    }

    @Override
    public RecAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_mydonations, parent, false);

        return new RecAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecAdapter.MyViewHolder holder, int position) {
        holder.type.setText("Food");
        holder.date.setText("Date : " + ngoinfos.get(position).getDate());
        holder.amount.setText("Count : " + ngoinfos.get(position).getAmount());
        holder.userto.setText("To : " + ngoinfos.get(position).userTo);
    }

    @Override
    public int getItemCount() {
        return ngoinfos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView userto, date, type, amount;
        public MyViewHolder(View itemView) {
            super(itemView);

            type = (TextView) itemView.findViewById(R.id.donated_item);
            amount = (TextView) itemView.findViewById(R.id.donated_amount);
            userto = (TextView) itemView.findViewById(R.id.donated_to_name);
            date = (TextView) itemView.findViewById(R.id.donate_date);
        }
    }
}
