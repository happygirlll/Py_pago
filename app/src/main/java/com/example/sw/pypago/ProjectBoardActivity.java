package com.example.sw.pypago;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sw.pypago.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ProjectBoardActivity extends AppCompatActivity {
    private CommentAdapter mAdapter;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth;

    private static final String KEY_TITLE = "title";
    private static final String KEY_PURPOSE = "purpose";
    private static final String KEY_EMAIL = "userID";

    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_broad);

        final Intent intent = getIntent();
        final TextView mtitle = findViewById(R.id.borad_title);
        final TextView mpur = findViewById(R.id.borad_purpose);

        final String docuId = intent.getStringExtra("project_docuId");
        final String path = intent.getStringExtra("project_path");

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser muser = mAuth.getCurrentUser();
        final String email = muser.getEmail().toString();

        Query query = db.collection("프로젝트").document(docuId).collection("comment").orderBy("mdate");
        FirestoreRecyclerOptions<CommentData> options = new FirestoreRecyclerOptions.Builder<CommentData>()
                .setQuery(query,CommentData.class)
                .build();
        mAdapter = new CommentAdapter(options,this);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.comment_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);

        db.collection("프로젝트").document(docuId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    String title = documentSnapshot.getString(KEY_TITLE);
                    String pur = documentSnapshot.getString(KEY_PURPOSE);
                    String email = documentSnapshot.getString(KEY_EMAIL);

                    mtitle.setText(title);
                    mpur.setText(pur);



                }else{
                    Toast.makeText(ProjectBoardActivity.this, "Not Data", Toast.LENGTH_SHORT).show();
                }

            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

        Button send = findViewById(R.id.comment_send);
        final EditText editText = findViewById(R.id.comment_edit_message);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                long time = System.currentTimeMillis();
                Date day = new Date(time);
              String text = editText.getText().toString();
              CommentData commentData = new CommentData(docuId,text,mFormat.format(day),email);
                db.collection("프로젝트").document(docuId).collection("comment").document().set(commentData);
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);



            }
        });
        mAdapter.setOnItemClickListener(new CommentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final DocumentSnapshot documentSnapshot, int position) {

                CommentData commentData = documentSnapshot.toObject(CommentData.class);
                String getKeyEmail = commentData.getmEmail().toString();

                if (email.equals(getKeyEmail)) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(ProjectBoardActivity.this);
                    dialog.setTitle("댓글 삭제")
                            .setMessage("댓글을 삭제하시겠습니까?")
                            .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    db.collection("프로젝트").document(docuId).collection("comment").document(documentSnapshot.getId()).delete();
                                    finish();
                                    overridePendingTransition(0, 0);
                                    startActivity(getIntent());
                                    overridePendingTransition(0, 0);
                                    Toast.makeText(ProjectBoardActivity.this, "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                    dialog.create();
                    dialog.show();

                }
            }
        });

    }
    @Override
    public void onStart() {
        super.onStart();
        mAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        mAdapter.stopListening();
    }

}
