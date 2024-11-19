package com.example.sw.pypago;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sw.pypago.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class InsertActivity extends AppCompatActivity {
    MemoDB helper;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        getSupportActionBar().setTitle("개발일지 입력");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        helper=new MemoDB(this);
        db=helper.getWritableDatabase();

        FloatingActionButton btnsave=findViewById(R.id.btnsave);
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edtcontent=findViewById(R.id.edtcontent);
                String strcontent=edtcontent.getText().toString();

                Date now=new Date();
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd hh:mm ss");

                sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
                String strnow=sdf.format(now);


                String sql="insert into memo(content,wdate) values(";
                sql += "'" + strcontent + "',";
                sql += "'" + strnow + "')";
                db.execSQL(sql);
                Toast.makeText(InsertActivity.this, "저장되었습니다.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}