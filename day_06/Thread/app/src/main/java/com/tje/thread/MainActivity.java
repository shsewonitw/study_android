package com.tje.thread;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    Button btnThreadStart;

    int a = 1;
    class NormalThread extends Thread{
        @Override
        public void run() {
            // 쓰레드의 run 메소드가 해야할 작업
            // 메인쓰레드를 제외하고는 UI를 만지면 안되기 때문에 runOnUiThread 사용
            // 메인쓰레드에게 처리할 UI 작업을 전달하는 코드
            for(int i = 1 ; i <= 10 ; i++){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                            textView.setText(String.format("Thread MSG %d",a++));

                        }
                });
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        btnThreadStart = findViewById(R.id.btnThreadStart);

        btnThreadStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NormalThread normalThread = new NormalThread();
                normalThread.start();
                /*
                new Thread(){
                    @Override
                    public void run() {
                        for(int i = 1 ; i <= 10 ; i++){
                            textView.setText(String.format("Thread MSG %d",i));
                            try {
                                Thread.sleep(1500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }.start();
                */
            }
        });

    }
}
