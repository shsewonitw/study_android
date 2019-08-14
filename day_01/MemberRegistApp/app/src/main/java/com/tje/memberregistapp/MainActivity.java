package com.tje.memberregistapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    // 라디오 버튼의 값을 추출할 수 있는 변수 선언
    RadioGroup genderRadioGroup;

    EditText idEditText;
    EditText pwEditText;
    EditText nameEditText;
    EditText ageEditText;
    EditText phoneEditText;
    ToggleButton acceptToggleButton;
    Switch acceptSwitch;
    Button submitBtn;
    Button resetBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        genderRadioGroup = findViewById(R.id.genderRaioGroup);
         idEditText = findViewById(R.id.idEditText);
         pwEditText = findViewById(R.id.pwEditText);
         nameEditText = findViewById(R.id.nameEditText);
         ageEditText = findViewById(R.id.ageEditText);
         phoneEditText = findViewById(R.id.phoneEditText);
         acceptToggleButton = findViewById(R.id.acceptToggleButton);
         acceptSwitch = findViewById(R.id.acceptSwitch);
         submitBtn = findViewById(R.id.submitBtn);
         resetBtn = findViewById(R.id.resetBtn);

        setEvents();

    }

    private void setEvents(){

        submitBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // 선택된 라디오 버튼의 값을 추출
                // 1. 라디오 그룹에서 현재 선택된 라디오 버튼의 ID값을 추출
                // 2. 추출된 ID 값을 사용하여 라디오 버튼의 객체를 반환
                int selectedRadioId = genderRadioGroup.getCheckedRadioButtonId();
                RadioButton gender = findViewById(selectedRadioId);


                Toast toast = Toast.makeText(getApplicationContext(), gender.getText(), Toast.LENGTH_SHORT);
                toast.show();
            }
        });

    }
}
