package com.hfl.account;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by hfl on 2018/6/2.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<Account> list;
    private Context context;
    private AccountDatabase ad;
    public MyAdapter(MainActivity context, AccountDatabase ad, List<Account> list){
        this.list=list;
        this.ad=ad;
        this.context=context;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final MyViewHolder holder=new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item,parent,false));
        holder.upIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=holder.getAdapterPosition();
                Account a=list.get(position);
                a.setBalance(a.getBalance()+1);
                notifyDataSetChanged();
                ad.update(a);
            }
        });
        holder.downIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=holder.getAdapterPosition();
                Account a=list.get(position);
                a.setBalance(a.getBalance() - 1);
                notifyDataSetChanged();
                ad.update(a);
            }
        });
        holder.deleteIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setTitle("是否确认删除？");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int position=holder.getAdapterPosition();
                        Account a=list.get(position);
                        list.remove(a);
                        ad.delete(a.getId());
                        notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("CANCEL", null);
                builder.show();
        }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(MyAdapter.MyViewHolder holder, int position) {
        Account ac=list.get(position);
        holder.idTV.setText(ac.getId() + "");
        holder.nameTV.setText(ac.getName());
        holder.balanceTV.setText(ac.getBalance() + "");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView upIV ;
        ImageView downIV;
        ImageView deleteIV;
        TextView idTV ;
        TextView nameTV ;
        TextView balanceTV;
        public MyViewHolder(View itemView) {
            super(itemView);
            upIV = (ImageView) itemView.findViewById(R.id.upIV);
            downIV = (ImageView) itemView.findViewById(R.id.downIV);
            deleteIV = (ImageView) itemView.findViewById(R.id.deleteIV);
            idTV = (TextView) itemView.findViewById(R.id.idTV);
            nameTV = (TextView) itemView.findViewById(R.id.nameTV);
            balanceTV = (TextView)itemView.findViewById(R.id.balanceTV);
        }
    }
}
