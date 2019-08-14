package com.tje.calculatorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    Button [] numbers = new Button[11];
    Button [] operators = new Button[4];
    Button btnResult;
    TextView resultTextView;
    double result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numbers[0] = findViewById(R.id.btn0);
        numbers[1] = findViewById(R.id.btn1);
        numbers[2] = findViewById(R.id.btn2);
        numbers[3] = findViewById(R.id.btn3);
        numbers[4] = findViewById(R.id.btn4);
        numbers[5] = findViewById(R.id.btn5);
        numbers[6] = findViewById(R.id.btn6);
        numbers[7] = findViewById(R.id.btn7);
        numbers[8] = findViewById(R.id.btn8);
        numbers[9] = findViewById(R.id.btn9);
        numbers[10] = findViewById(R.id.btnDot);
        operators[0] = findViewById(R.id.btnPlus);
        operators[1] = findViewById(R.id.btnMinus);
        operators[2] = findViewById(R.id.btnMulti);
        operators[3] = findViewById(R.id.btnDiv);
        btnResult = findViewById(R.id.btnResult);
        resultTextView = findViewById(R.id.resultTextView);


        setEvents();

    }

    private void setEvents(){
        for( int i = 0 ; i < numbers.length ; i++ ){
            numbers[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String prevResultText = (String) resultTextView.getText();

                    if(prevResultText.equals("0")){
                            prevResultText = "";
                    }

                    String btnText = (String) ((Button)view).getText();
                    resultTextView.setText(prevResultText + btnText);
                }
            });
        }

        for( int i = 0 ; i < operators.length ; i++ ){
            operators[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String prevResultText = (String) resultTextView.getText();
                    String btnText = (String) ((Button)view).getText();
                    resultTextView.setText(prevResultText + " " + btnText + " ");
                }
            });
        }

        btnResult.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String originalText = (String) resultTextView.getText();
                StringTokenizer st = new StringTokenizer(originalText);

                if( st.countTokens() < 3 ){
                    Toast toast = Toast.makeText(getApplicationContext(), "입력을 확인하세요~", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                double lValue = Double.parseDouble(st.nextToken());
                String operator = st.nextToken().trim();
                double rValue = Double.parseDouble(st.nextToken());

                if(operator.equals("+"))
                    result = lValue + rValue;
                else if(operator.equals("-"))
                    result = lValue - rValue;
                else if(operator.equals("*"))
                    result = lValue * rValue;
                else if(operator.equals("/"))
                    result = lValue / rValue;

                resultTextView.setText(originalText + "=" + result);
            }
        });
    }
}
