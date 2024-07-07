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

import acount.fpoly.ph35061.asm_and_api.Adapter.CartAdapter;
import acount.fpoly.ph35061.asm_and_api.Interface.CartInterface;
import acount.fpoly.ph35061.asm_and_api.Model.Cart;
import acount.fpoly.ph35061.asm_and_api.Model.Product;
import acount.fpoly.ph35061.asm_and_api.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Cart_Frg extends Fragment {
    static String BASE_URL = "http://172.20.10.5:3000/";
    List<Cart> cartList;
    CartAdapter cartAdapter;
    private RecyclerView rcv_cart;
    SearchView search_bill;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bag,container,false);

        search_bill = view.findViewById(R.id.search_bill);

        search_bill.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchCart(newText);
                return false;
            }
        });

        cartList = new ArrayList<>();
        rcv_cart = view.findViewById(R.id.rcvCart);
        rcv_cart.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        cartAdapter = new CartAdapter(cartList,getContext());
        rcv_cart.setAdapter(cartAdapter);
        laydanhsachCart();
        return view;

    }
    private void searchCart(String keyword) {
        if (keyword.trim().equals("")) {
            laydanhsachCart();
        } else {
            List<Cart> searchCartList = new ArrayList<>();
            for (Cart cart : cartList) {
                if (cart.getProductName().toLowerCase().contains(keyword.toLowerCase())) {
                    searchCartList.add(cart);
                }
            }
            cartList.clear();
            cartList.addAll(searchCartList);
            cartAdapter.notifyDataSetChanged();
        }
    }
    private void laydanhsachCart() {
        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        CartInterface cartInterface = retrofit.create(CartInterface.class);
        Call<List<Cart>> objCall = cartInterface.lay_danh_sach();

        objCall.enqueue(new Callback<List<Cart>>() {
            @Override
            public void onResponse(Call<List<Cart>> call, Response<List<Cart>> response) {
                if (response.isSuccessful()){
                    cartList.clear();
                    cartList.addAll(response.body());
                    cartAdapter.notifyDataSetChanged();
                }else {
                    Log.d("zzzzzz", "onResponse: Kkong lay duoc ds");
                }
            }

            @Override
            public void onFailure(Call<List<Cart>> call, Throwable throwable) {
                Log.e("zzzzzzz", "onFailure: loi " + throwable.getMessage() );
                throwable.printStackTrace();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        laydanhsachCart();
    }
}