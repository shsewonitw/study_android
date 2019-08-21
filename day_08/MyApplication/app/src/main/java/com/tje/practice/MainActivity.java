package com.tje.practice;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final String HOST_NETWORK_PROTOCOL = "http://";
    private static final String HOST_ADDRESS = "192.168.0.30:8080";
    private static final String HOST_APP_NAME = "/yeojeong";

    Button btn_join;
    Button btn_login;
    EditText et_id;
    EditText et_pw;
    EditText et_name;
    EditText et_gender;
    EditText et_birth;
    EditText et_tel;
    EditText et_email;
    View join_form;
    View login_form;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_join = findViewById(R.id.btn_join);
        btn_login = findViewById(R.id.btn_login);


        btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                join_dialog();
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                login_dialog();
            }
        });
    }

    private void join_dialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        join_form = LayoutInflater.from(this).inflate(R.layout.join_form,null);
        et_id = join_form.findViewById(R.id.et_id);
        et_pw = join_form.findViewById(R.id.et_pw);
        et_name = join_form.findViewById(R.id.et_name);
        et_gender = join_form.findViewById(R.id.et_gender);
        et_birth = join_form.findViewById(R.id.et_birth);
        et_tel = join_form.findViewById(R.id.et_tel);
        et_email = join_form.findViewById(R.id.et_email);
        builder.setView(join_form);
        builder.setPositiveButton("가입", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {

                        String id = et_id.getText().toString().trim();
                        String pw = et_pw.getText().toString().trim();
                        String name = et_name.getText().toString().trim();
                        String gender = et_gender.getText().toString().trim();
                        String birth = et_birth.getText().toString().trim();
                        String tel = et_tel.getText().toString().trim();
                        String email = et_email.getText().toString().trim();



                        String params = String.format("id=%s&pw=%s&name=%s&gender=%s&birth=%s&tel=%s&email=%s",
                                id,pw,name,gender,birth,tel,email);




                        String targetURL = "/android_join";
                        try {
                            URL endPoint = new URL(HOST_NETWORK_PROTOCOL+
                                    HOST_ADDRESS +
                                    HOST_APP_NAME +
                                    targetURL);

                            HttpURLConnection connection = (HttpURLConnection)endPoint.openConnection();
                            connection.setRequestMethod("POST");
                            connection.setDoOutput(true);

                            PrintWriter writer = new PrintWriter(
                                    new BufferedWriter(new OutputStreamWriter(connection.getOutputStream())));


                            writer.print(params);
                            writer.flush();

                            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                                BufferedReader in = new BufferedReader(
                                        new InputStreamReader(
                                                connection.getInputStream(),"UTF-8"));

                                final String result = in.readLine();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
                                    }
                                });



                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void login_dialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        login_form = LayoutInflater.from(this).inflate(R.layout.login_form,null);
        et_id = login_form.findViewById(R.id.et_id);
        et_pw = login_form.findViewById(R.id.et_pw);

        builder.setView(login_form);
        builder.setPositiveButton("로그인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {

                        String id = et_id.getText().toString().trim();
                        String pw = et_pw.getText().toString().trim();




                        String params = String.format("id=%s&pw=%s",
                                id,pw);




                        String targetURL = "/android_login";
                        try {
                            URL endPoint = new URL(HOST_NETWORK_PROTOCOL+
                                    HOST_ADDRESS +
                                    HOST_APP_NAME +
                                    targetURL);

                            HttpURLConnection connection = (HttpURLConnection)endPoint.openConnection();
                            connection.setRequestMethod("POST");
                            connection.setDoOutput(true);

                            PrintWriter writer = new PrintWriter(
                                    new BufferedWriter(new OutputStreamWriter(connection.getOutputStream())));


                            writer.print(params);
                            writer.flush();

                            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                                BufferedReader in = new BufferedReader(
                                        new InputStreamReader(
                                                connection.getInputStream(),"UTF-8"));

                                final String result = in.readLine();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
                                    }
                                });



                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
