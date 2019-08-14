package com.tje.calculatorapp_2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText input1;
    EditText input2;
    Button [] operators = new Button[4];
    Button btnClear;
    TextView resultTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input1 = findViewById(R.id.input1);
        input2 = findViewById(R.id.input2);
        operators[0] = findViewById(R.id.btnPlus);
        operators[1] = findViewById(R.id.btnMinus);
        operators[2] = findViewById(R.id.btnMulti);
        operators[3] = findViewById(R.id.btnDiv);
        btnClear = findViewById(R.id.btnClear);
        resultTextView = findViewById(R.id.resultTextView);

        setEvents();
    }

    private void setEvents(){
        for(int i = 0 ; i < operators.length ; i++){
            operators[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Double inputDouble1 = Double.parseDouble(input1.getText().toString());
                    Double inputDouble2 = Double.parseDouble(input2.getText().toString());
                    if(((Button)view).getText().toString().equals("+")){
                        String result = (inputDouble1 + inputDouble2) + "";
                        resultTextView.setText(result);
                    }
                    else if(((Button)view).getText().toString().equals("-")){
                        String result = (inputDouble1 - inputDouble2) + "";
                        resultTextView.setText(result);
                    }
                    else if(((Button)view).getText().toString().equals("*")){
                        String result = (inputDouble1 * inputDouble2) + "";
                        resultTextView.setText(result);
                    }
                    else if(((Button)view).getText().toString().equals("/")){
                        String result = (inputDouble1 / inputDouble2) + "";
                        resultTextView.setText(result);
                    }


                }
            });
        }


        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input1.setText("");
                input2.setText("");
                resultTextView.setText("");

            }
        });
    }
}
