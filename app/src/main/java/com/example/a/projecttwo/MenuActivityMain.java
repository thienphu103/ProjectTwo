package com.example.a.projecttwo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class MenuActivityMain extends AppCompatActivity {
    private RecyclerView mRecyclerView_details;
    private RecyclerView.Adapter mAdapter_details;
    private RecyclerView.LayoutManager mLayoutManager_details;
    ArrayList<MenuActvityContructor> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_main);
        initControl();
        initData();
    }

    private void initData() {
        arrayList = new ArrayList<>();
        arrayList.add(new MenuActvityContructor("http://quananhotram.com/upload/product/400x350x1/078790308298.png","Cơm Xào Bò","500.000"));
        arrayList.add(new MenuActvityContructor("http://quananhotram.com/upload/product/400x350x1/078790308298.png","Cơm Xào Bò","500.000"));
        arrayList.add(new MenuActvityContructor("http://quananhotram.com/upload/product/400x350x1/078790308298.png","Cơm Xào Bò","500.000"));
        arrayList.add(new MenuActvityContructor("http://quananhotram.com/upload/product/400x350x1/078790308298.png","Cơm Xào Bò","500.000"));
        arrayList.add(new MenuActvityContructor("http://quananhotram.com/upload/product/400x350x1/078790308298.png","Cơm Xào Bò","500.000"));
        arrayList.add(new MenuActvityContructor("http://quananhotram.com/upload/product/400x350x1/078790308298.png","Cơm Xào Bò","500.000"));
        mRecyclerView_details.setHasFixedSize(true);
        mLayoutManager_details = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView_details.setLayoutManager(mLayoutManager_details);
        mAdapter_details = new com.example.a.projecttwo.Adapter.MenuActivityMain(arrayList, this);
        mRecyclerView_details.setAdapter(mAdapter_details);
    }

    private void initControl() {
        mRecyclerView_details = (RecyclerView) findViewById(R.id.RcV_ActivityMenuMain);
    }
}
