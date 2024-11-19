package com.example.sw.pypago;


import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sw.pypago.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.regex.Pattern;


public class RegisterActivity extends AppCompatActivity {

    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^[a-zA-Z0-9!@.#$%^&*?_~]{4,16}$");

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextPassword_Check;
    private EditText editTextnickname;

    private String email = "";
    private String password = "";
    private String nickname = "";
    private BackKeyClickHandler backKeyClickHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();
        backKeyClickHandler = new BackKeyClickHandler(this);
        editTextEmail = findViewById(R.id.register_email);
        editTextPassword = findViewById(R.id.register_password);
        editTextnickname = findViewById(R.id.register_nickname);



    }

    public void signUp(View view) {
        email = editTextEmail.getText().toString();
        password = editTextPassword.getText().toString();
        nickname = editTextnickname.getText().toString();
        if (isValidEmail() && isValidPasswd() && isValidnick()) {
            createUser(email, password, nickname);
        }

    }



    private boolean isValidEmail() {
        if (email.isEmpty()) {

            Toast.makeText(this, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "이메일 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show();

            return false;
        } else {
            return true;
        }
    }

    private boolean isValidPasswd() {
        if (password.isEmpty()) {

            Toast.makeText(this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!PASSWORD_PATTERN.matcher(password).matches()) {
            Toast.makeText(this, "비밀번호 범위는 4~16자 입니다.", Toast.LENGTH_SHORT).show();

            return false;
        } else {
            return true;
        }
    }


    private boolean isValidnick() {
        if (nickname.isEmpty()) {
            Toast.makeText(this, "닉네임을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            return true;
        }
    }
    private void createUser(String email, String password, final String nickname) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            User user = new User();
                            user.nickName = nickname;
                            user.uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            db.collection("회원").document(user.uid).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(RegisterActivity.this, R.string.success_signup, Toast.LENGTH_SHORT).show();
                                    RegisterActivity.this.finish();
                                    Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                                    startActivity(intent);
                                }
                            });




                        } else {

                            Toast.makeText(RegisterActivity.this, R.string.failed_signup, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
public void onBackPressed() {

    backKeyClickHandler.onBackPressed();
    super.onBackPressed();

}
}