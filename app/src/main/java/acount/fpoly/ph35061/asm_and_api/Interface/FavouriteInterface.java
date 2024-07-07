package acount.fpoly.ph35061.asm_and_api.Interface;


import java.util.List;

import acount.fpoly.ph35061.asm_and_api.Model.Favourite;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface FavouriteInterface {
    @GET("favourite")
    Call<List<Favourite>> lay_danh_sach();

    //them
    @POST("favourite")
    Call<Favourite> them_yeu_thich (@Body Favourite objFavoutire );

    @DELETE("favourite/{id}")
    Call<Void> xoa_yeu_thich (@Path("id") String id);


}
