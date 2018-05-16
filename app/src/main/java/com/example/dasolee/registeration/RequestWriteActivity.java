package com.example.dasolee.registeration;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class RequestWriteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_write);

        final EditText re_TitleEditText = (EditText) findViewById(R.id.re_TitleEditText);
        final EditText re_ContentEditText = (EditText) findViewById(R.id.re_ContentEditText);

        final Button re_WriteSendButton = (Button) findViewById(R.id.re_WriteSendButton);
        re_WriteSendButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String requestTitle = re_TitleEditText.getText().toString();
                String requestContent = re_ContentEditText.getText().toString();


                if (requestTitle.equals("") || requestContent.equals("")) {

                    Toast.makeText(getApplicationContext(), "빈칸을 모두 채워주세요", Toast.LENGTH_LONG).show();
                    return;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {

                                Toast.makeText(getApplicationContext(), "게시글 등록에 성공했습니다", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), RequestActivity.class);
                                startActivity(intent);
                                finish();

                            } else {

                                Toast.makeText(getApplicationContext(), "게시글 등록에 실패했습니다", Toast.LENGTH_LONG).show();

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                RequestRequest requestRequest = new RequestRequest(requestTitle, requestContent, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RequestWriteActivity.this);
                queue.add(requestRequest);
            }
        });
    }
}

