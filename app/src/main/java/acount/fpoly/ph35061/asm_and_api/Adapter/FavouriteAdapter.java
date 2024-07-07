package acount.fpoly.ph35061.asm_and_api.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import acount.fpoly.ph35061.asm_and_api.Fragment.Favourite_Frg;
import acount.fpoly.ph35061.asm_and_api.Interface.FavouriteInterface;
import acount.fpoly.ph35061.asm_and_api.Model.Favourite;
import acount.fpoly.ph35061.asm_and_api.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.ViewHolder> {
    List<Favourite> favouriteList;
    Context context;
    static String BASE_URL = "http://172.20.10.5:3000/";

    public FavouriteAdapter(List<Favourite> favouriteList, Context context) {
        this.favouriteList = favouriteList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favourite, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Favourite favourite = favouriteList.get(position);


        Glide.with(holder.itemView.getContext())
                .load(favourite.getImage())
                .placeholder(R.drawable.orange)
                .error(R.drawable.orange)
                .into(holder.img_favourite);
        holder.txt_tensp_favourite.setText(favourite.getProductName());
        holder.txt_giasp_favourite.setText(favourite.getPrice() + "$");

        holder.img_unfavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteFavourite(favourite);
            }
        });
    }



    @Override
    public int getItemCount() {
        return favouriteList.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{
        ImageView img_favourite;
        ImageButton img_unfavorite;
        TextView txt_tensp_favourite, txt_giasp_favourite;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img_favourite = itemView.findViewById(R.id.img_favourite);
            img_unfavorite = itemView.findViewById(R.id.img_unfavorite);
            txt_giasp_favourite = itemView.findViewById(R.id.txt_giasp_favourite);
            txt_tensp_favourite = itemView.findViewById(R.id.txt_tensp_favourite);

        }
    }

    private void DeleteFavourite(Favourite favourite) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        FavouriteInterface favouriteInterface = retrofit.create(FavouriteInterface.class);
        Call<Void> call = favouriteInterface.xoa_yeu_thich(favourite.get_id());

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {

                    Toast.makeText(context, "Un favourite Succes", Toast.LENGTH_SHORT).show();
                    GetDanhSachFavourite();


                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
                Log.e("Error", "errr: " + throwable.getMessage());
            }
        });
    }


    void GetDanhSachFavourite() {

        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        FavouriteInterface favouriteInterface = retrofit.create(FavouriteInterface.class);

        Call<List<Favourite>> objCall = favouriteInterface.lay_danh_sach();

        objCall.enqueue(new Callback<List<Favourite>>() {
            @Override
            public void onResponse(Call<List<Favourite>> call, Response<List<Favourite>> response) {
                if (response.isSuccessful()){
                    favouriteList.clear();
                    favouriteList.addAll(response.body());
                    notifyDataSetChanged();
                }else {
                    Log.d("zzzzzz", "onResponse: Kkong lay duoc ds");
                }
            }

            @Override
            public void onFailure(Call<List<Favourite>> call, Throwable throwable) {
                Log.e("zzzzzzz", "onFailure: loi " + throwable.getMessage() );
                throwable.printStackTrace();
            }
        });

    }
}
