package com.tje.userevent;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    EditText editText;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textView = findViewById(R.id.textView);
        editText = findViewById(R.id.editText);
        button = findViewById(R.id.button);


        // 버튼 클릭 이벤트 처리
        // setOnClickListener 메소드에 이벤트를 처리할 수 있는 리스너 객체를
        // 전달하여 이벤트를 처리할 수 있음
//        button.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                textView.setText( editText.getText() );
//                editText.setText("");
//            }
//        });
    }

    public void btnClick(View view){
        textView.setText( editText.getText() );
        Toast toast = Toast.makeText(getApplicationContext(),editText.getText(),Toast.LENGTH_SHORT);
        //editText.setText("");
        toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
        toast.show();


        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(editText.getText());
        builder.setTitle("대화상자 타이틀 입니다.");
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                textView.setText(String.valueOf(i));
            }
        });
        builder.setNegativeButton("취소",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                textView.setText(String.valueOf(i));
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
