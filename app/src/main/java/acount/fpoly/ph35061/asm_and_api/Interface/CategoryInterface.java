package acount.fpoly.ph35061.asm_and_api.Interface;


import java.util.List;

import acount.fpoly.ph35061.asm_and_api.Model.Category;
import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoryInterface {
    @GET("category")
    Call<List<Category>> lay_category();
}
