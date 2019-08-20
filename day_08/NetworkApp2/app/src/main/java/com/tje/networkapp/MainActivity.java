package com.tje.networkapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    private static final String HOST_NETWORK_PROTOCOL = "http://";
    private static final String HOST_ADDRESS = "192.168.0.30:8080";
    private static final String HOST_APP_NAME = "/android";

    Button btn_send_get;
    Button btn_send_post;
    TextView tv_receive_msg;
    EditText et_msg;
    Button btn_login;
    Button btn_logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_send_get = findViewById(R.id.btn_send_get);
        btn_send_post = findViewById(R.id.btn_send_post);
        tv_receive_msg = findViewById(R.id.tv_receive_msg);
        et_msg = findViewById(R.id.et_msg);
        btn_login = findViewById(R.id.btn_login);
        btn_logout = findViewById(R.id.btn_logout);
        btn_send_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 비동기 방식으로 통신을 처리할 수 있는 예제
                // AsyncTask 클래스
                // 비동기 방식으로 백그라운드 작업을 처리할 때 사용할 수 있는 클래스
                // 수초 정도의 작업에 적합한 클래스
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {

                        String msg = et_msg.getText().toString().trim();
                        String param = null;
                        if(msg.length() > 0)
                            param = String.format("?msg=%s",msg);

                        String targetURL = "/request_get" + (param != null ? param : "");

                        try {
                            URL endPoint =
                                    new URL(HOST_NETWORK_PROTOCOL +
                                            HOST_ADDRESS +
                                            HOST_APP_NAME +
                                            targetURL);
                            HttpURLConnection connection = (HttpURLConnection)endPoint.openConnection();
                            // HTTP 접속 방식(메소드) 설정
                            // 기본값 GET
                            connection.setRequestMethod("GET");


                            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                                // 연결 및 데이터 수신 성공
                                BufferedReader in =
                                        new BufferedReader(
                                                new InputStreamReader(connection.getInputStream(),"UTF-8"));

                                // JSON 포맷을 처리하기 위한 객체 생성
                                Gson gson = new Gson();
                                // 서버에서 전달한 JSON 문서를 사용하여
                                // Member 클래스의 객체로 변환
                                final Member member = gson.fromJson(in,Member.class);

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        StringBuilder msg = new StringBuilder();
                                        msg.append("NAME : " + member.getName()+"\n");
                                        msg.append("AGE : " + member.getAge()+"\n");
                                        msg.append("TEL : " + member.getTel()+"\n");
                                        msg.append("IMAGE : " + member.getImageUri()+"\n");

                                        tv_receive_msg.setText(msg.toString());
                                    }
                                });
                            } else {
                                // 통신 에러
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                         tv_receive_msg.setText("통신에러");
                                    }
                                });
                            }

                            connection.disconnect();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });


        btn_send_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        String targetURL = "/request_post";

                        String msg = et_msg.getText().toString().trim();
                        String param = null;
                        if(msg.length() > 0)
                            param = String.format("msg=%s",msg);

                        try {
                            URL endPoint =
                                    new URL(HOST_NETWORK_PROTOCOL +
                                            HOST_ADDRESS +
                                            HOST_APP_NAME +
                                            targetURL);
                            HttpURLConnection connection = (HttpURLConnection)endPoint.openConnection();
                            connection.setRequestMethod("POST");

                            if(param != null) {
                                // 파라미터 값의 전송
                                connection.setDoOutput(true);
                                connection.getOutputStream().write(param.getBytes());
                            }

                            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                                BufferedReader in =
                                        new BufferedReader(
                                                new InputStreamReader(connection.getInputStream(),"UTF-8"));

                                Gson gson = new Gson();
                                final Member member = gson.fromJson(in,Member.class);

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        StringBuilder msg = new StringBuilder();
                                        msg.append("NAME : " + member.getName()+"\n");
                                        msg.append("AGE : " + member.getAge()+"\n");
                                        msg.append("TEL : " + member.getTel()+"\n");
                                        msg.append("IMAGE : " + member.getImageUri()+"\n");

                                        tv_receive_msg.setText(msg.toString());
                                    }
                                });
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        tv_receive_msg.setText("통신에러");
                                    }
                                });
                            }

                            connection.disconnect();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        String targetURL = "/app_login";
                        String temp = et_msg.getText().toString().trim();
                        StringTokenizer st = new StringTokenizer(temp," ");

                        if( st.countTokens() != 2 ) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tv_receive_msg.setText("ID PW 형식으로 입력하세요");
                                }
                            });
                            return;
                        }

                        String id = null;
                        String pw = null;
                        if(st.hasMoreTokens()){
                            id = st.nextToken();
                            pw = st.nextToken();
                        }

                        String param = null;
                        if( (id != null) && (pw != null) )
                            param = String.format("id=%s&pw=%s",id,pw);

                        try {
                            URL endPoint =
                                    new URL(HOST_NETWORK_PROTOCOL +
                                            HOST_ADDRESS +
                                            HOST_APP_NAME +
                                            targetURL);
                            HttpURLConnection connection = (HttpURLConnection)endPoint.openConnection();

                            // 세션 사용을 위한 쿠키 값을 추출
                            // (기존에 저장된 쿠키의 값을 추출)
                            String cookieString = CookieManager.getInstance().getCookie(HOST_NETWORK_PROTOCOL +
                                    HOST_ADDRESS +
                                    HOST_APP_NAME);

                            // 서버로 요청을 전달할 때,
                            // 사전에 저장된 쿠키의 값을 전달하는 방법
                            if( cookieString != null ){
                                connection.setRequestProperty("Cookie",cookieString);
                            }
                            connection.setRequestMethod("POST");

                            if(param != null) {
                                // 파라미터 값의 전송
                                connection.setDoOutput(true);
                                connection.getOutputStream().write(param.getBytes());
                            }

                            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                                // 서버로부터 수신된 쿠키의 값을 CookieManager 에 저장
                                Map<String, List<String>> headerFields = connection.getHeaderFields();
                                String COOKIES_HEADER = "Set-Cookie";
                                List<String> cookiesHeader = headerFields.get(COOKIES_HEADER);

                                if(cookiesHeader != null){
                                    for(String cookie : cookiesHeader){
                                        String cookieName = HttpCookie.parse(cookie).get(0).getName();
                                        String cookieValue = HttpCookie.parse(cookie).get(0).getValue();

                                        cookieString = cookieName + "=" + cookieValue;
                                        CookieManager.getInstance().setCookie(
                                                HOST_NETWORK_PROTOCOL +
                                                        HOST_ADDRESS +
                                                        HOST_APP_NAME, cookieString
                                        );
                                    }
                                }


                                BufferedReader in =
                                        new BufferedReader(
                                                new InputStreamReader(connection.getInputStream(),"UTF-8"));

                                Gson gson = new Gson();

                                JsonParser parser = new JsonParser();
//                                JsonElement element = parser.parse(in);
//                                final String login_result = element.getAsJsonObject().get("login_result").getAsString();
//                                final String login_msg = element.getAsJsonObject().get("login_msg").getAsString();

                                final HashMap<String,String> result = gson.fromJson(in, new TypeToken<HashMap<String,String>>(){}.getType());



                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        if(result.get("login_result").equals("true"))
                                            tv_receive_msg.setText("로그인성공");
                                        else
                                            tv_receive_msg.setText("로그인실패");

                                            tv_receive_msg.append(result.get("login_msg"));
                                    }
                                });
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        tv_receive_msg.setText("통신에러");
                                    }
                                });
                            }

                            connection.disconnect();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        });


        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        String targetURL = "/app_logout";

                        try {
                            URL endPoint =
                                    new URL(HOST_NETWORK_PROTOCOL +
                                            HOST_ADDRESS +
                                            HOST_APP_NAME +
                                            targetURL);
                            HttpURLConnection connection = (HttpURLConnection)endPoint.openConnection();

                            // 세션 사용을 위한 쿠키 값을 추출
                            // (기존에 저장된 쿠키의 값을 추출)
                            String cookieString = CookieManager.getInstance().getCookie(HOST_NETWORK_PROTOCOL +
                                    HOST_ADDRESS +
                                    HOST_APP_NAME);

                            // 서버로 요청을 전달할 때,
                            // 사전에 저장된 쿠키의 값을 전달하는 방법
                            if( cookieString != null ){
                                connection.setRequestProperty("Cookie",cookieString);
                            } else{
                                // 쿠키가 존재하지않는경우 = 로그인 되어있지 않는 경우
                                return;
                            }
                            connection.setRequestMethod("GET");



                            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                                // 서버로부터 수신된 쿠키의 값을 CookieManager 에 저장
                                Map<String, List<String>> headerFields = connection.getHeaderFields();
                                String COOKIES_HEADER = "Set-Cookie";
                                List<String> cookiesHeader = headerFields.get(COOKIES_HEADER);

                                if(cookiesHeader != null){
                                    for(String cookie : cookiesHeader){
                                        String cookieName = HttpCookie.parse(cookie).get(0).getName();
                                        String cookieValue = HttpCookie.parse(cookie).get(0).getValue();

                                        cookieString = cookieName + "=" + cookieValue;
                                        CookieManager.getInstance().setCookie(
                                                HOST_NETWORK_PROTOCOL +
                                                        HOST_ADDRESS +
                                                        HOST_APP_NAME, cookieString
                                        );
                                    }
                                }


                                BufferedReader in =
                                        new BufferedReader(
                                                new InputStreamReader(connection.getInputStream(),"UTF-8"));

                                Gson gson = new Gson();

                                JsonParser parser = new JsonParser();
//                                JsonElement element = parser.parse(in);
//                                final String login_result = element.getAsJsonObject().get("login_result").getAsString();
//                                final String login_msg = element.getAsJsonObject().get("login_msg").getAsString();

                                final HashMap<String,String> result = gson.fromJson(in, new TypeToken<HashMap<String,String>>(){}.getType());



                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        if(result.get("logout_result").equals("true"))
                                            tv_receive_msg.setText("로그아웃 성공");
                                        else
                                            tv_receive_msg.setText("로그아웃 실패");
                                        tv_receive_msg.setText(result.get("logout_msg"));
                                    }
                                });
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        tv_receive_msg.setText("통신에러");
                                    }
                                });
                            }

                            connection.disconnect();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        });
    }
}
