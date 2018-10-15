package com.example.a.projecttwo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.a.projecttwo.Adapter.CustomerManageMentAdapter;

import java.util.ArrayList;

public class CustomerManagement extends AppCompatActivity {
    ArrayList<MainContrucTor> arrayList;
    CustomerManageMentAdapter adapter;
    ListView lst_CustomerManagement;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_management);
        initControl();
        initData();
    }

    private void initData() {
        arrayList = new ArrayList<>();
        arrayList.add(new MainContrucTor("Lê Công Danh","1","Đã thanh toán"));
        arrayList.add(new MainContrucTor("Đặng Phạm Nhật Thanh","2","Đang chờ"));
        arrayList.add(new MainContrucTor("Lê Vương Thiện Phú","3","Đã thanh toán"));
        arrayList.add(new MainContrucTor("Trương Nhật Nam","4","Chưa thanh toán"));
        arrayList.add(new MainContrucTor("Hồ Hà Quỳnh Anh","5","Chưa thanh toán"));
        arrayList.add(new MainContrucTor("Lê Công Danh","1","Đã thanh toán"));
        arrayList.add(new MainContrucTor("Đặng Phạm Nhật Thanh","2","Đang chờ"));
        arrayList.add(new MainContrucTor("Lê Vương Thiện Phú","3","Đã thanh toán"));
        arrayList.add(new MainContrucTor("Trương Nhật Nam","4","Chưa thanh toán"));
        arrayList.add(new MainContrucTor("Hồ Hà Quỳnh Anh","5","Chưa thanh toán"));
        arrayList.add(new MainContrucTor("Lê Công Danh","1","Đã thanh toán"));
        arrayList.add(new MainContrucTor("Đặng Phạm Nhật Thanh","2","Đang chờ"));
        arrayList.add(new MainContrucTor("Lê Vương Thiện Phú","3","Đã thanh toán"));
        arrayList.add(new MainContrucTor("Trương Nhật Nam","4","Chưa thanh toán"));
        arrayList.add(new MainContrucTor("Hồ Hà Quỳnh Anh","5","Chưa thanh toán"));
        adapter = new CustomerManageMentAdapter(arrayList,this);
        lst_CustomerManagement.setAdapter(adapter);


        lst_CustomerManagement.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(CustomerManagement.this,MenuActivityMain.class);
                startActivity(intent);
            }
        });


    }

    private void initControl() {
        lst_CustomerManagement = (ListView) findViewById(R.id.lst_customer_customermanagementMain);
    }
}
