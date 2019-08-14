package com.tje.android_01;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    int count = 1;

    // UI에 정의된 각 위젯에 접근하기 위한 클래스의 변수 선언
    TextView titleTextView;
    Button changeTitleBtn;
    Button toastBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        changeTitleBtn = findViewById(R.id.updateTitleBtn);
        titleTextView = findViewById(R.id.titleTextView);
        toastBtn = findViewById(R.id.toastButton);

        changeTitleBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                titleTextView.setText("타이틀 - "+count);
                count++;
            }
        });

        toastBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, titleTextView.getText(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
