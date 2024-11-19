package com.example.sw.pypago;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import com.example.sw.pypago.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ProjectActivity extends AppCompatActivity {
    Button btn1;
    Button btn2;
    private EditText editTitle;
    private EditText purpose;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    private EditText editHash;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    public CollectionReference projectRef = db.collection("프로젝트");
    FirebaseAuth mAuth;
    private FirebaseStorage storage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);
        storage = FirebaseStorage.getInstance();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},0);
        }

        final StorageReference storageRef = storage.getReferenceFromUrl("gs://fir-test-ead0c.appspot.com");

        editTitle = findViewById(R.id.Project_titleText);


        purpose = findViewById(R.id.Project_purpose);
        btn1 = (Button) findViewById(R.id.Project_Button);
        btn1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                addProject(v,storageRef);

            }
        });

    }

    public void addProject(View v,StorageReference storageRef){
     final String title = editTitle.getText().toString();
     final String pur = purpose.getText().toString();
     final long time = System.currentTimeMillis();
     final Date day = new Date(time);

        if (title.isEmpty()  || pur.isEmpty() ) {
            Toast.makeText(this, "강의 제목과 내용을 입력해주세요.", Toast.LENGTH_SHORT).show();

        }else {


            mAuth = FirebaseAuth.getInstance();
            FirebaseUser muser = mAuth.getCurrentUser();
            final String user = muser.getEmail().toString();
            String uid = muser.getUid();


            ProjectInfo projectInfo = new ProjectInfo(user, title, pur, time, mFormat.format(day));

            projectRef.add(projectInfo);



            Toast.makeText(ProjectActivity.this, "강의가 등록되었습니다.", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(ProjectActivity.this, MainActivity.class);
            startActivity(intent);

        }
    }

    public String getPath(Uri uri){
        String [] proj = {MediaStore.Images.Media.DATA};
        CursorLoader cursorLoader = new CursorLoader(this,uri,proj,null,null,null);

        Cursor cursor = cursorLoader.loadInBackground();
        int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        return cursor.getString(index);

    }


}
