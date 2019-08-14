package com.tje.animal;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    private AppCompatActivity activity = this;

    RadioGroup radio_group;
    ImageButton imageButton;

    Switch btn_switch;
    ToggleButton btn_toggle;

    public void initRefs() {
        radio_group = (RadioGroup)findViewById(R.id.radio_group);
        imageButton = (ImageButton)findViewById(R.id.imageButton);

        btn_switch = (Switch)findViewById(R.id.btn_switch);
        btn_toggle = (ToggleButton)findViewById(R.id.btn_toggle);
    }

    public void setEvents() {
        // 라디오 버튼에 대한 이벤트 처리
        // RadioGroup 객체에 대해서 이벤트 콜백 메소드를 정의하는 방식
        // setOnCheckedChangeListener : 그룹내에 라디오버튼의 선택이 변경되는
        // 이벤트가 발생할 때 실행할 코드를 정의하는 메소드
        radio_group.setOnCheckedChangeListener(
                new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        // 현재 선택된 라디오 버튼의 객체를 반환
                        final RadioButton rb = (RadioButton)findViewById(checkedId);
                        String type = rb.getText().toString();

                        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

                        builder.setTitle("이미지 출력 여부 확인");
                        builder.setMessage(
                                String.format("'%s'를 선택했습니다.\n이미지를 출력할까요?", type));
                        builder.setPositiveButton("예",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        int image_id = 0;

                                        if( rb.getText().toString().equals("강아지") )
                                            image_id = R.drawable.dog;
                                        else if( rb.getText().toString().equals("고양이") )
                                            image_id = R.drawable.cat;
                                        else
                                            image_id = R.drawable.fox;

                                        imageButton.setImageResource(image_id);
                                    }
                                });
                        builder.setNegativeButton("아니요", null);
                        builder.create().show();
                    }
                });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int checkedId = radio_group.getCheckedRadioButtonId();
                RadioButton rb = (RadioButton)findViewById(checkedId);
                String type = rb.getText().toString();
                String msg = "현재 보이는 이미지는 " + type + " 입니다.";

                Toast.makeText(
                        getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            }
        });

        btn_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 현재 상태의 값으 ㄹ추출하는 메소드 호출
                // isChecked : 선택된 경우 true, 선택되지 않은 경우 false
                if( btn_switch.isChecked() )
                    Toast.makeText(getApplicationContext(), "SWITCH 버튼이 선택되었습니다.", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(), "SWITCH 버튼이 선택이 해제되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        btn_toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( btn_toggle.isChecked() )
                    Toast.makeText(getApplicationContext(), "Toggle 버튼이 선택되었습니다.", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(), "Toggle 버튼이 선택이 해제되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initRefs();
        setEvents();
    }
}


