package acount.fpoly.ph35061.asm_and_api.Interface;

import java.util.List;

import acount.fpoly.ph35061.asm_and_api.Model.Cart;
import acount.fpoly.ph35061.asm_and_api.Model.Favourite;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CartInterface {
    @GET("cart")
    Call<List<Cart>> lay_danh_sach();

    //them
    @POST("cart")
    Call<Cart> them_gio_hang (@Body Cart objCart);

    @DELETE("cart/{id}")
    Call<Void> xoa_gio_hang (@Path("id") String id);
}
