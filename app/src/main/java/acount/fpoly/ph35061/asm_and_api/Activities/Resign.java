package acount.fpoly.ph35061.asm_and_api.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import acount.fpoly.ph35061.asm_and_api.R;

public class Resign extends AppCompatActivity {
    TextInputEditText edtEmail,edtPass;
    TextInputLayout SU_til_Email,SU_til_PassWord;
    ImageView btnBack;
    Button btnDangKy;
    FirebaseAuth firebaseAuth;
    static String BASE_URL = "http://192.168.101.61:3000/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_resign);
        edtEmail = findViewById(R.id.edtEmail_RS);
        edtPass = findViewById(R.id.edtPass_RS);
        SU_til_Email = findViewById(R.id.SU_til_Email);
        SU_til_PassWord = findViewById(R.id.SU_til_PassWord);
        btnBack = findViewById(R.id.btnBack);
        btnDangKy = findViewById(R.id.btnDangKy);

        firebaseAuth = FirebaseAuth.getInstance();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Resign.this, Login.class));
            }
        });

        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email,password;
                email = edtEmail.getText().toString();
                password = edtPass.getText().toString();

                if (TextUtils.isEmpty(email)){
                    SU_til_Email.setError("Bạn chưa nhập email");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    SU_til_PassWord.setError("Bạn chưa nhập password");
                    return;
                }
                if (password.length() < 7){
                    SU_til_PassWord.setError("Mật khẩu phải nhiều hơn 7 kí tự");
                }
                firebaseAuth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(Resign.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Resign.this, Login.class));
                                }else {
                                    Toast.makeText(Resign.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}