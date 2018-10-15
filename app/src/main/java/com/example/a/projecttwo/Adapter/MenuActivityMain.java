package com.example.a.projecttwo.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a.projecttwo.ItemClick;
import com.example.a.projecttwo.MenuActvityContructor;
import com.example.a.projecttwo.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.example.a.projecttwo.R.layout.rcy_activitymenumain;

public class MenuActivityMain extends RecyclerView.Adapter<RecyclerViewHolder> {
    ArrayList<MenuActvityContructor> arrayList;
    Context context;
    LayoutInflater inflater;

    public MenuActivityMain(ArrayList<MenuActvityContructor> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rcy_activitymenumain,parent,false);
        RecyclerViewHolder vh = new RecyclerViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder viewHolder, int position) {
        final MenuActvityContructor playList = arrayList.get(position);
        viewHolder.title.setText(playList.getTitle());
        viewHolder.price.setText(playList.getPrice());
        Picasso.with(context)
                .load(playList.getImages())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(viewHolder.images);
        viewHolder.setItemClickListener(new ItemClick() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if(isLongClick)
                    Toast.makeText(context, "Không có chức năng", Toast.LENGTH_SHORT).show();
                else{

                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
class RecyclerViewHolder extends RecyclerView.ViewHolder implements android.view.View.OnClickListener, android.view.View.OnLongClickListener // Implement 2 sự kiện onClick và onLongClick
{
    ImageView images;
    TextView title,price;

    private ItemClick itemClickListener; // Khai báo interface
    public RecyclerViewHolder(View itemView) {
        super(itemView);
        images = (ImageView) itemView.findViewById(R.id.img_Logo_Menu);
        title= (TextView) itemView.findViewById(R.id.txt_nameMonAn_Menu);
        price = (TextView) itemView.findViewById(R.id.txt_giatien_Menu);
        itemView.setOnClickListener(this); // Mấu chốt ở đây , set sự kiên onClick cho View
        itemView.setOnLongClickListener(this); // Mấu chốt ở đây , set sự kiên onLongClick cho View
    }

    //Tạo setter cho biến itemClickListenenr
    public void setItemClickListener(ItemClick itemClickListener)
    {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false); // Gọi interface , false là vì đây là onClick
    }

    @Override
    public boolean onLongClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),true); // Gọi interface , true là vì đây là onLongClick
        return true;
    }
}

