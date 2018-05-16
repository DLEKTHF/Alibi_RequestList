package com.example.dasolee.registeration;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class ImageActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        ListView listview ;
        ImageListAdapter adapter;

        adapter = new ImageListAdapter();

        listview = (ListView) findViewById(R.id.imageListView);
        listview.setAdapter(adapter);

        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.ic_mail_outline_black_24dp),
                "Box", "Account Box Black 36dp") ;
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.ic_mail_outline_black_24dp),
                "Circle", "Account Circle Black 36dp") ;
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.ic_mail_outline_black_24dp),
                "Ind", "Assignment Ind Black 36dp") ;

        Button requestButton = (Button) findViewById(R.id.imageButton);

        requestButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ImageWriteActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
