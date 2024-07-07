package acount.fpoly.ph35061.asm_and_api.Interface;

import java.util.List;

import acount.fpoly.ph35061.asm_and_api.Model.Product;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ProductInterface {
    @GET("product")
    Call<List<Product>> lay_danh_sach();
}
