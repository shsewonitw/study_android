package com.tje.membermanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MemberAddActivity extends AppCompatActivity {

    MemberDbHelper memberDbHelper = new MemberDbHelper(this);

    private EditText editText_id;
    private EditText editText_password;
    private EditText editText_name;
    private EditText editText_age;
    private EditText editText_phone;
    private EditText editText_address;
    private Button button_submit;
    private Button button_reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_add);

        editText_id = findViewById(R.id.editText_id);
        editText_password = findViewById(R.id.editText_password);
        editText_name = findViewById(R.id.editText_name);
        editText_age = findViewById(R.id.editText_age);
        editText_phone = findViewById(R.id.editText_phone);
        editText_address = findViewById(R.id.editText_address);
        button_submit = findViewById(R.id.button_submit);
        button_reset = findViewById(R.id.button_reset);


        button_submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Member member = new Member();
                member.setId(editText_id.getText().toString());
                member.setPassword(editText_password.getText().toString());
                member.setName(editText_name.getText().toString());
                member.setAge(Integer.parseInt(editText_age.getText().toString()));
                member.setPhone(editText_phone.getText().toString());
                member.setAddress(editText_address.getText().toString());

                boolean result = memberDbHelper.insert(member);

                Intent intent = new Intent();
                intent.putExtra("result",result);
                intent.putExtra("add_member",member);
                setResult(result ? RESULT_OK : RESULT_CANCELED, intent);
                finish();

            }
        });

        button_reset.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                editText_id.setText("");
                editText_password.setText("");
                editText_name.setText("");
                editText_age.setText("");
                editText_phone.setText("");
                editText_address.setText("");
            }
        });
    }
}
