package acount.fpoly.ph35061.asm_and_api.Interface;

import java.util.List;

import acount.fpoly.ph35061.asm_and_api.Model.Account;
import acount.fpoly.ph35061.asm_and_api.Model.Cart;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AccountInterface {

    @GET("account")
    Call<List<Account>> lay_thong_tin();

    @POST("account")
    Call<Account> them_tai_khoan (@Body Account objAccount);
}
