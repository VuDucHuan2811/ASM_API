package acount.fpoly.ph35061.asm_and_api.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import acount.fpoly.ph35061.asm_and_api.Activities.AccountActivity;
import acount.fpoly.ph35061.asm_and_api.Adapter.CategoryAdapter;
import acount.fpoly.ph35061.asm_and_api.Interface.CategoryInterface;
import acount.fpoly.ph35061.asm_and_api.Model.Category;
import acount.fpoly.ph35061.asm_and_api.Model.Favourite;
import acount.fpoly.ph35061.asm_and_api.Model.Product;
import acount.fpoly.ph35061.asm_and_api.Interface.ProductInterface;
import acount.fpoly.ph35061.asm_and_api.Adapter.ProductAdapter;
import acount.fpoly.ph35061.asm_and_api.R;
import acount.fpoly.ph35061.asm_and_api.Activities.SeeAll;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Home extends Fragment {
    RecyclerView recyclerProduct;
    RecyclerView recyclerCategory;
    ProductAdapter productAdapter;
    CategoryAdapter categoryAdapter;
    List<Product> productList;
    List<Category> categoryList;
    List<Favourite> favouriteList;
    ImageView searchProduct;
    ImageView btnAccout;
    TextView seeall;
    static String BASE_URL = "http://172.20.10.5:3000/";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        recyclerProduct = view.findViewById(R.id.rcv_product);
        recyclerCategory = view.findViewById(R.id.rcv_category);
        btnAccout = view.findViewById(R.id.btnAccount);
        searchProduct = view.findViewById(R.id.searchProduct);
        seeall = view.findViewById(R.id.seeall);

        seeall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SeeAll.class));
            }
        });

        btnAccout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AccountActivity.class));
            }
        });

        searchProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SeeAll.class));
            }
        });

        //product
        RecyclerView.LayoutManager layoutManagerProduct = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        recyclerProduct.setLayoutManager(layoutManagerProduct);
        productList = new ArrayList<>();
        laydanhSachProduct();
        productAdapter = new ProductAdapter(productList,getContext());
        recyclerProduct.setAdapter(productAdapter);

        //category
        RecyclerView.LayoutManager layoutManagerCategory = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        recyclerCategory.setLayoutManager(layoutManagerCategory);
        categoryList = new ArrayList<>();
        laydanhSachCategory();
        categoryAdapter = new CategoryAdapter(categoryList,getContext());
        recyclerCategory.setAdapter(categoryAdapter);


        return view;
    }

    private void laydanhSachProduct(){
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ProductInterface productInterface = retrofit.create(ProductInterface.class);

        Call<List<Product>> objCall = productInterface.lay_danh_sach();

        objCall.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()){
                    productList.clear();
                    productList.addAll(response.body());
                    productAdapter.notifyDataSetChanged();
                }else {
                    Log.d("zzzzzz","onResponse : Khong lay duoc danh sach");
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable throwable) {
                Log.d("zzzzzz","onFailure : Loi");
                throwable.printStackTrace();
            }
        });
    }

    private void laydanhSachCategory(){
        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        CategoryInterface categoryInterface = retrofit.create(CategoryInterface.class);

        Call<List<Category>> objCall = categoryInterface.lay_category();

        objCall.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful()){
                    categoryList.clear();
                    categoryList.addAll(response.body());
                    categoryAdapter.notifyDataSetChanged();
                }else {
                    Log.d("zzzzzz", "onResponse: Kkong lay duoc ds");
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable throwable) {
                Log.e("zzzzzzz", "onFailure: loi " + throwable.getMessage() );
                throwable.printStackTrace();
            }
        });
    }

}