package com.example.dasolee.registeration;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView registerButton = (TextView) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

        final EditText idText = (EditText) findViewById(R.id.idText);
        final EditText passwordText = (EditText) findViewById(R.id.passwordText);
        final Button loginButton = (Button) findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String userID = idText.getText().toString();
                String userPassword = passwordText.getText().toString();

                Response.Listener<String> responseLister = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success) {

                                Toast.makeText(getApplicationContext(),"로그인 되었습니다",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                return;

                            }
                            else{

                                Toast.makeText(getApplicationContext(),"로그인에 실패했습니다",Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest(userID, userPassword, responseLister);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(dialog != null)
        {
            dialog.dismiss();
            dialog = null;
        }
    }
}
