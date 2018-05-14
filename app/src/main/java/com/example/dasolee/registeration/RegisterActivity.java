package com.example.dasolee.registeration;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    private ArrayAdapter adapter;
    private Spinner spinner;

    private Spinner userID;
    private Spinner userPassword;
    private String userGender;
    private Spinner userJob;
    private Spinner userEmail;
    private AlertDialog dialog;
    private boolean validate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        spinner = (Spinner) findViewById(R.id.jobSpinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.job, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        final EditText idText = (EditText) findViewById(R.id.idText);
        final EditText passwordText = (EditText) findViewById(R.id.passwordText);
        final EditText emailText = (EditText) findViewById(R.id.emailText);

        RadioGroup genderGroup = (RadioGroup) findViewById(R.id.genderGroup);
        int genderGroupID = genderGroup.getCheckedRadioButtonId();
        userGender = ((RadioButton)findViewById(genderGroupID)).getText().toString();

        genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                RadioButton genderButton = (RadioButton) findViewById(checkedId);
                userGender = genderButton.getText().toString();
            }
        });

        final Button validateButton = (Button) findViewById(R.id.validateButton);
        validateButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String userID = idText.getText().toString();
                if(validate)
                {
                    return;
                }
                if(userID.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"아이디를 입력해주세요",Toast.LENGTH_SHORT).show();
                    return;
                }
                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success) {
                                Toast.makeText(getApplicationContext(),"사용가능한 아이디입니다",Toast.LENGTH_SHORT).show();
                                idText.setEnabled(false);
                                validate = true;
                                idText.setBackgroundColor(getResources().getColor(R.color.colorGray));
                                validateButton.setBackgroundColor(getResources().getColor(R.color.colorGray));
                                return;
                            }
                            else {
                                Toast.makeText(getApplicationContext(),"사용할 수 없는 아이디입니다",Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                };
                ValidateRequest validateRequest = new ValidateRequest(userID, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(validateRequest);
            }
        });

        Button registerButton = (Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String userID = idText.getText().toString();
                String userPassword = passwordText.getText().toString();
                String userJob = spinner.getSelectedItem().toString();
                String userEmail = emailText.getText().toString();

                if(!validate)
                {
                    Toast.makeText(getApplicationContext(),"아이디 중복확인을 해주세요",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(userID.equals("")||userPassword.equals("")||userEmail.equals("")||userJob.equals("")||userGender.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"빈칸을 입력해 주세요",Toast.LENGTH_SHORT).show();
                    return;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = builder.setMessage("회원가입에 성공했습니다")
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                finish();
                                                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                                                startActivity(intent);
                                            }
                                        }).create();
                                dialog.show();
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = builder.setMessage("회원가입에 실패했습니다")
                                        .setNegativeButton("확인",null)
                                        .create();
                                dialog.show();
                            }
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                };
                RegisterRequest registerRequest = new RegisterRequest(userID, userPassword, userGender, userJob,userEmail, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
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
