package com.example.a.projecttwo.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a.projecttwo.MainContrucTor;
import com.example.a.projecttwo.R;

import java.util.ArrayList;

public class CustomerManageMentAdapter extends BaseAdapter {
    ArrayList<MainContrucTor> arrayList;
    LayoutInflater inflater;
    Context context;

    public CustomerManageMentAdapter(ArrayList<MainContrucTor> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(view == null){
            viewHolder = new ViewHolder();
            view = inflater.inflate(R.layout.lst_customermanagementmain,null );
            viewHolder.NameCustomer = (TextView) view.findViewById(R.id.txt_name_customermanagementListview);
            viewHolder.NumberTable = (TextView) view.findViewById(R.id.txt_soghe_customermanagementListview);
            viewHolder.Status = (TextView) view.findViewById(R.id.txt_trangthai_customermanagementListview);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        MainContrucTor contrucTor = arrayList.get(i);
        viewHolder.NameCustomer.setText(contrucTor.getNameCustomer().trim());
        viewHolder.NumberTable.setText(contrucTor.getNumberTable());
        viewHolder.Status.setText(contrucTor.getStatus());
        if(viewHolder.Status.getText().toString().equals("Đã thanh toán")){
            viewHolder.Status.setTextColor(Color.parseColor("#4e9525"));
        } if(viewHolder.Status.getText().toString().equals("Chưa thanh toán")){
            viewHolder.Status.setTextColor(Color.parseColor("#ff1f5a"));
        } if(viewHolder.Status.getText().toString().equals("Đang chờ")){
            viewHolder.Status.setTextColor(Color.parseColor("#929aab"));
        }



        return view;
    }

    class ViewHolder{
        TextView NameCustomer, NumberTable, Status;
    }

}
