package acount.fpoly.ph35061.asm_and_api.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.w3c.dom.Text;

import acount.fpoly.ph35061.asm_and_api.Interface.AccountInterface;
import acount.fpoly.ph35061.asm_and_api.Model.Account;
import acount.fpoly.ph35061.asm_and_api.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class    AccountActivity extends AppCompatActivity {
    ImageView btn_back;
    Button btnLogout,btnUpdate;
    TextInputLayout til_Fullname,til_Username,til_Phone,til_Date;
    TextInputEditText edtFullname,edtUsername,edtPhone,edtDate;
    static String BASE_URL = "http://172.20.10.5:3000/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_account);
        btn_back = findViewById(R.id.btn_back);
        btnLogout = findViewById(R.id.btn_dangxuat);
        btnUpdate = findViewById(R.id.btnUpdate);
        til_Fullname = findViewById(R.id.til_Fullname);
        til_Username = findViewById(R.id.til_Username);
        til_Phone = findViewById(R.id.til_Phone);
        til_Date = findViewById(R.id.til_Date);
        edtFullname = findViewById(R.id.edtFullname);
        edtUsername = findViewById(R.id.edtUsername);
        edtPhone = findViewById(R.id.edtPhone);
        edtDate = findViewById(R.id.edtDate);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullname = edtFullname.getText().toString();
                String username = edtUsername.getText().toString();
                String phone = edtPhone.getText().toString();
                String date = edtDate.getText().toString();

                Account account = new Account();
                account.setFullname(fullname);
                account.setUsername(username);
                account.setPhone(phone);
                account.setDate(date);

                addAccount(account);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountActivity.this, Login.class));
                finishAffinity();
            }
        });
    }
    private void addAccount(Account account){
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        AccountInterface accountInterface = retrofit.create(AccountInterface.class);
        Call<Account> objCall = accountInterface.them_tai_khoan(account);

        objCall.enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if (response.isSuccessful()){
                    if (response.isSuccessful()){
                        Toast.makeText(AccountActivity.this, "Cap nhat thong tin thanh cong", Toast.LENGTH_SHORT).show();
                    }else {
                        Log.d("zzzzzz","onResponse : Khong lay duoc danh sach");
                    }
                }
            }

            @Override
            public void onFailure(Call<Account> call, Throwable throwable) {
                Log.d("zzzzzz","onFailure : Loi");
                throwable.printStackTrace();
            }
        });
    }

}