package acount.fpoly.ph35061.asm_and_api.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import acount.fpoly.ph35061.asm_and_api.Adapter.FavouriteAdapter;
import acount.fpoly.ph35061.asm_and_api.Interface.FavouriteInterface;
import acount.fpoly.ph35061.asm_and_api.Model.Favourite;
import acount.fpoly.ph35061.asm_and_api.Model.Product;
import acount.fpoly.ph35061.asm_and_api.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Favourite_Frg extends Fragment {
    static String BASE_URL = "http://172.20.10.5:3000/";
    List<Favourite> favouriteList;
    FavouriteAdapter favouriteAdapter;
    RecyclerView recyclerFavourite;
    SearchView searchFavourite;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favourite,container,false);
        searchFavourite = view.findViewById(R.id.search_favourite);

        searchFavourite.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchFavourite(newText);
                return true;
            }
        });

        favouriteList = new ArrayList<>();
        recyclerFavourite = view.findViewById(R.id.rcv_favourite);
        recyclerFavourite.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        favouriteAdapter = new FavouriteAdapter(favouriteList,getContext());
        recyclerFavourite.setAdapter(favouriteAdapter);
        laydanhsachFavourite();
        return view;
    }
    private void searchFavourite(String keyword) {
        if (keyword.trim().equals("")) {
            laydanhsachFavourite();
        } else {
            List<Favourite> searchFavouriteList = new ArrayList<>();
            for (Favourite favourite : favouriteList) {
                if (favourite.getProductName().toLowerCase().contains(keyword.toLowerCase())) {
                    searchFavouriteList.add(favourite);
                }
            }
            favouriteList.clear();
            favouriteList.addAll(searchFavouriteList);
            favouriteAdapter.notifyDataSetChanged();
        }
    }
    private void laydanhsachFavourite() {
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
                    favouriteAdapter.notifyDataSetChanged();
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

    @Override
    public void onResume() {
        super.onResume();
        laydanhsachFavourite();
    }
}