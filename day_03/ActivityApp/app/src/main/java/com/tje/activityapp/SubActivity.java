package com.tje.activityapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SubActivity extends AppCompatActivity {
    Button btn_sub;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        btn_sub = findViewById(R.id.btn_sub);
        btn_sub.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.putExtra("result","화면전환 완료");
                // setResult 메소드
                // - startActivity 메소드에 의해서 전환된 액티비티는
                // 현재 액티비티에서 실행된 결과를 intent 객체에 저장하고
                // 상태 코드와 함께 setResult 메소드를 통해 Main 액티비티로 전달할 수 있음
                // Activity.RESULT_OK : 이상없이 종료됨
                // Activity.RESULT_CANCELED : 처리과정에서 문제 발생됨
                setResult(Activity.RESULT_OK, i);

                // 액티비티를 종료하는 finish 메소드
                // - 전환된 액티비티를 종료하고 Main 액티비티로 이동동
               finish();
            }
        });

        // startActivity 메소드에 의해서 실행되는 액티비티는
        // getIntent 메소드를 사용하여 화면전환에 사용된
        // intent 객체를 추출할 수 있음
        Intent intent = getIntent();

        String msg = intent.getStringExtra("msg");
        ((TextView)findViewById(R.id.textView_sub)).setText(msg);
    }
}
