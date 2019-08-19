package com.tje.networkapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    EditText et_id;
    EditText et_pw;
    TextView tv_result;
    Button btn_login;
    TextView tv_list;
    RecyclerView rc_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_id = findViewById(R.id.ed_id);
        et_pw = findViewById(R.id.et_pw);
        tv_result = findViewById(R.id.tv_result);
        tv_list = findViewById(R.id.tv_list);


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 서버로 사용자가 입력한 ID/PW를 전달하여
                // 로그인 처리의 결과를 반환받는 이벤트 처리 코드
                final String str_id = et_id.getText().toString().trim();
                final String str_pw = et_pw.getText().toString().trim();

                if( str_id.length() == 0 || str_pw.length() == 0 ){
                    Toast.makeText(MainActivity.this, "아이디와 패스워드는 반드시 입력해야 합니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 로그인 처리를 위한 서버의 URL 변수
                final String str_url = "http://192.168.0.30:8080/android/login";

                new Thread(){
                    @Override
                    public void run() {

                        try {
                            URL url = new URL(str_url);
                            HttpURLConnection connection = (HttpURLConnection)url.openConnection();

                            // POST 방식으로 서버에 전달할 데이터 생성
                            String params = String.format("id=%s&pw=%s",str_id,str_pw);

                            // 서버에 데이터를 전송하기 위한 스트림 생성
                            // 1. HttpURLConnection 객체의 setDoOutput 메소드의 매개변수로
                            //    true를 전달 ( POST 방식으로 데이터를 전송하는 경우에만 작성)
                            connection.setDoOutput(true);

                            // 2. 출력스트림 생성
                            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());

                            // 3. 데이터 출력과 flush
                            writer.write(params);
                            writer.flush();

                            /*
                            final int code = connection.getResponseCode();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MainActivity.this, "code : " + code, Toast.LENGTH_SHORT).show();
                                }
                            });
                            */
                            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF8"));

                            StringBuilder input = new StringBuilder();
                            String line;
                            while( (line = reader.readLine()) != null){
                                input.append(line);
                            }

                            JSONObject jsonObject = new JSONObject(input.toString());
                            final String r_id = jsonObject.getString("id");
                            final String r_pw = jsonObject.getString("pw");
                            final String r_r = jsonObject.getString("r");

                            runOnUiThread(new Runnable(){
                                @Override
                                public void run() {
                                    tv_result.setText(
                                            r_id + " , " +r_pw + " , " + r_r
                                    );
                                    tv_result.setVisibility(View.VISIBLE);
                                }
                            });

                            /*
                            final String input_str = input.toString();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(MainActivity.this, "Response JSON : " + input_str, Toast.LENGTH_LONG).show();
                                    }
                                });
                             */

                            writer.close();
                            connection.disconnect();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }.start();
            }
        });

        ///////////////////////////////////////////////////////////////////////////////
                final String str_url = "http://192.168.0.30:8080/yeojeong/list";

                new Thread(){
                    @Override
                    public void run() {

                        try {
                            URL url = new URL(str_url);
                            HttpURLConnection connection = (HttpURLConnection)url.openConnection();

                            connection.setDoOutput(true);


                            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));

                            StringBuilder input = new StringBuilder();
                            String line;
                            while( (line = reader.readLine()) != null){
                                input.append(line);
                            }

                            final String temp = input.toString();


                            runOnUiThread(new Runnable(){
                                @Override
                                public void run() {
                                   tv_list.setText(
                                   temp
                                   );

                                }
                            });

                            connection.disconnect();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }.start();



        ///////////////////////////////////////////////////////////////////////////////
    }
}
