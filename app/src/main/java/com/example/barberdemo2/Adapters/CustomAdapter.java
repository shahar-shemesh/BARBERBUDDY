package com.example.barberdemo2.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barberdemo2.Models.BarberShop;
import com.example.barberdemo2.R;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    Context context;
    private ArrayList<BarberShop> dataSet;
    ArrayList<String> dataPathKeyList;

    private ItemClickListener clickListener;


    public CustomAdapter(ArrayList<BarberShop> dataSet, ArrayList<String> dataPathKeyList, Context context, ItemClickListener clickListener) {
        this.dataSet = dataSet;
        this.dataPathKeyList = dataPathKeyList;
        this.context = context;
        this.clickListener = clickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{ // שליפת כל הפקדים מהcard
        TextView barbershopName, barbershopLocation, barbershopPhone;
        CardView cardView;
        ImageView barbershopImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            barbershopName = itemView.findViewById(R.id.textViewBarbershopNameCard);
            barbershopLocation = itemView.findViewById(R.id.textViewBarbershopLocationCard);
            barbershopImage = itemView.findViewById(R.id.imageViewBarbershopImageCard);
            barbershopImage.setImageResource(R.drawable.profile);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cards_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        int posTemp = position;

        BarberShop currentBarberShop = dataSet.get(position);
        holder.barbershopName.setText(currentBarberShop.getBuisnessName());
        holder.barbershopLocation.setText(currentBarberShop.getLocation());

//
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onItemClick(dataSet.get(posTemp), dataPathKeyList.get(posTemp), view);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public interface ItemClickListener{
        public void onItemClick(BarberShop currentBarberShop, String dataKey, View view);
    }


}
