package com.cookandroid.zzickmuck;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    BottomNavigationView bottomNavigationView;
    Fragment1 fragment1;
    Fragment2 fragment2;

    EditText editText;
    Button btAdd;
    RecyclerView recyclerView;

    List<MainData> dataList = new ArrayList<>();
    RoomDB database;
    MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragment1 = new Fragment1();
        fragment2 = new Fragment2();

        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment1).commit();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.tab1:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment1).commit();

                        return true;
                    case R.id.tab2:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment2).commit();

                        return true;

        editText = findViewById(R.id.edit_text);
        btAdd = findViewById(R.id.bt_add);
        recyclerView = findViewById(R.id.recycler_view);

        database = RoomDB.getInstance(this);

        dataList = database.mainDao().getAll();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new MainAdapter(MainActivity.this, dataList);

        recyclerView.setAdapter(adapter);

        btAdd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String sText = editText.getText().toString().trim();
                if (!sText.equals(""))
                {
                    MainData data = new MainData();
                    data.setText(sText);
                    database.mainDao().insert(data);

                    editText.setText("");

                    dataList.clear();
                    dataList.addAll(database.mainDao().getAll());
                    adapter.notifyDataSetChanged();
                }
            }
        });

    }
}