package acount.fpoly.ph35061.asm_and_api.Activities;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.SearchView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import acount.fpoly.ph35061.asm_and_api.Adapter.ProductAdapter;
import acount.fpoly.ph35061.asm_and_api.Interface.ProductInterface;
import acount.fpoly.ph35061.asm_and_api.Model.Product;
import acount.fpoly.ph35061.asm_and_api.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SeeAll extends AppCompatActivity {
    RecyclerView rcv_seeAll;
    List<Product> productList;
    ProductAdapter productAdapter;
    SearchView searchAll;
    static String BASE_URL = "http://172.20.10.5:3000/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_see_all);
        rcv_seeAll = findViewById(R.id.rcv_seeall);
        searchAll = findViewById(R.id.search_All);

        searchAll.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchProduct(newText);
                return true;
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        rcv_seeAll.setLayoutManager(gridLayoutManager);
        productList = new ArrayList<>();
        laydanhSachProduct();
        productAdapter = new ProductAdapter(productList,this);
        rcv_seeAll.setAdapter(productAdapter);
    }
    private void searchProduct(String keyword) {
        if (keyword.trim().equals("")) {
            laydanhSachProduct();
        } else {
            List<Product> searchProductList = new ArrayList<>();
            for (Product product : productList) {
                if (product.getProductName().toLowerCase().contains(keyword.toLowerCase())) {
                    searchProductList.add(product);
                }
            }
            productList.clear();
            productList.addAll(searchProductList);
            productAdapter.notifyDataSetChanged();
        }
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
}