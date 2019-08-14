package com.tje.practice_membermanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_INSERT = 1;

    MemberDbHelper memberDbHelper;

    Button btn_add;
    Button btn_update;
    Button btn_delete;
    TextView tv_no_member;
    RecyclerView memberRecyclerView;
    RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        memberDbHelper = new MemberDbHelper(this);

        btn_add = findViewById(R.id.btn_add);
        btn_update = findViewById(R.id.btn_update);
        btn_delete = findViewById(R.id.btn_delete);
        tv_no_member = findViewById(R.id.tv_no_member);
        memberRecyclerView = findViewById(R.id.memberRecyclerView);

        layoutManager = new LinearLayoutManager(this);
        memberRecyclerView.setLayoutManager(layoutManager);


        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MemberAddActivity.class);
                startActivityForResult(intent,REQUEST_CODE_INSERT);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
