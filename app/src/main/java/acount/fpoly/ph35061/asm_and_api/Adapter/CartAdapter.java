package acount.fpoly.ph35061.asm_and_api.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.DecimalFormat;
import java.util.List;

import acount.fpoly.ph35061.asm_and_api.Interface.CartInterface;
import acount.fpoly.ph35061.asm_and_api.Model.Cart;
import acount.fpoly.ph35061.asm_and_api.Model.Product;
import acount.fpoly.ph35061.asm_and_api.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    List<Cart> cartList;
    List<Product> productList;
    Context context;
    int soluong = 1;
    static String BASE_URL = "http://172.20.10.5:3000/";

    public CartAdapter(List<Cart> cartList, Context context) {
        this.cartList = cartList;
        this.context = context;
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        Cart cart = cartList.get(position);
        Glide.with(holder.itemView.getContext())
                .load(cart.getImage())
                .placeholder(R.drawable.orange)
                .error(R.drawable.orange)
                .into(holder.img);
        holder.productName.setText(cart.getProductName());
        holder.price.setText(cart.getPrice());
        holder.quantity.setText(cart.getQuantity());

        holder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soluong++;
                holder.quantity.setText(String.valueOf(soluong));
            }
        });
        holder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (soluong > 1){
                    soluong--;
                    holder.quantity.setText(String.valueOf(soluong));
                }else {
                    Toast.makeText(context, "So luong khong the nho hon 0", Toast.LENGTH_SHORT).show();
                }

            }
        });
        holder.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteCard(cart);
                laydanhsachCart();
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img,btnPlus,btnMinus;
        TextView productName;
        TextView price;
        TextView quantity;
        Button btnBuy,btnCancel;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_Cart);
            productName = itemView.findViewById(R.id.productName_cart);
            price = itemView.findViewById(R.id.price_cart);
            quantity = itemView.findViewById(R.id.txtSoLuong);
            btnPlus = itemView.findViewById(R.id.btnPlus);
            btnMinus = itemView.findViewById(R.id.btnMinus);
            btnBuy = itemView.findViewById(R.id.btnBuy);
            btnCancel = itemView.findViewById(R.id.btnCancel);
        }
    }
    private void DeleteCard(Cart ObjCart){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CartInterface cartInterface = retrofit.create(CartInterface.class);
        Call<Void> call = cartInterface.xoa_gio_hang(ObjCart.get_id());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
                Log.e("Error", "errr: " + throwable.getMessage());
            }
        });
    }
    private void laydanhsachCart(){
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
                    notifyDataSetChanged();
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
}
