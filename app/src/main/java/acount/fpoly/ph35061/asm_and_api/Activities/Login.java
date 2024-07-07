package acount.fpoly.ph35061.asm_and_api.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import acount.fpoly.ph35061.asm_and_api.R;


public class Login extends AppCompatActivity {
    TextInputEditText edtEmail,edtPass;
    TextInputLayout SU_til_Email,SU_til_PassWord;
    Button btnDangNhap;
    TextView DangKy;
    FirebaseAuth firebaseAuth;
    TextView forgotPass;

    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtEmail = findViewById(R.id.edtEmail_LG);
        edtPass = findViewById(R.id.edtPass_LG);
        SU_til_Email = findViewById(R.id.SU_til_Email);
        SU_til_PassWord = findViewById(R.id.SU_til_PassWord);
        forgotPass = findViewById(R.id.forgotPass);
        btnDangNhap = findViewById(R.id.btnDangNhap);
        DangKy = findViewById(R.id.Resign);

        firebaseAuth = FirebaseAuth.getInstance();

        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogForGot();
            }
        });

        DangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,Resign.class));
            }
        });

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
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
                     firebaseAuth.signInWithEmailAndPassword(email,password)
                             .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                 @Override
                                 public void onComplete(@NonNull Task<AuthResult> task) {
                                     if (task.isSuccessful()) {
                                         Toast.makeText(Login.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                         Intent intent = new Intent(Login.this,MainActivity.class);
                                         startActivity(intent);
                                         finish();
                                     }else {
                                         Toast.makeText(Login.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                                     }
                                 }
                             });
            }
        });
    }
    private void showDialogForGot() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_forgot, null);
        builder.setView(view);
        builder.setCancelable(false);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        TextInputLayout ForgotEmail = view.findViewById(R.id.ForgotEmail);
        TextInputEditText edtEmail = view.findViewById(R.id.edit_email);
        Button btnSend = view.findViewById(R.id.btn_Send);
        Button btnCancel = view.findViewById(R.id.btn_Cancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = edtEmail.getText().toString().trim();
                if (!TextUtils.isEmpty(email))
                {
                    SendMail();
                }else {
                    ForgotEmail.setError("Ban chua nhap email");
                }
            }
            private void SendMail(){
                btnSend.setVisibility(View.INVISIBLE);

                firebaseAuth.sendPasswordResetEmail(email)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(Login.this, "Vui long kiem tra email cua ban", Toast.LENGTH_SHORT).show();
                                alertDialog.dismiss();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Login.this, "Email cua ban chua duoc dang ky", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

}