package acount.fpoly.ph35061.asm_and_api.Adapter;

import android.app.Activity;
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
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import acount.fpoly.ph35061.asm_and_api.Interface.CartInterface;
import acount.fpoly.ph35061.asm_and_api.Interface.FavouriteInterface;
import acount.fpoly.ph35061.asm_and_api.Model.Cart;
import acount.fpoly.ph35061.asm_and_api.Model.Favourite;
import acount.fpoly.ph35061.asm_and_api.Model.Product;
import acount.fpoly.ph35061.asm_and_api.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    List<Product> productList;
    private Context context;
    int soluong = 0;
    static String BASE_URL = "http://172.20.10.5:3000/";
    public ProductAdapter(List<Product> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{
        public ImageView Image;
        public TextView ProductName;
        public TextView Price;
        public Button btnAdd_Cart;
        public ImageView btnAdd_Fav;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            Image = itemView.findViewById(R.id.image);
            ProductName = itemView.findViewById(R.id.ProductName);
            Price = itemView.findViewById(R.id.Price);
            btnAdd_Cart = itemView.findViewById(R.id.btnAdd_to_card);
            btnAdd_Fav = itemView.findViewById(R.id.love);
        }
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item,parent,false);
        return new ProductViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
         Product product = productList.get(position);
         if (product == null){
             return;
         }
         Glide.with(holder.itemView.getContext())
                         .load(product.getImage())
                                 .placeholder(R.drawable.orange)
                                         .error(R.drawable.orange)
                                                 .into(holder.Image);
         holder.ProductName.setText(product.getProductName());
         holder.Price.setText(String.valueOf(product.getPrice())+"$");

         holder.Image.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 showDiaLogDetail(product);
             }
         });
         holder.btnAdd_Cart.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 showDiaLogDetail(product);
             }
         });
         holder.btnAdd_Fav.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Favourite favourite = new Favourite(product.get_id(),product.getProductName(),product.getImage(),String.valueOf(product.getPrice()));
                 add_Favourite(favourite);
             }
         });
    }


    @Override
    public int getItemCount() {
        return productList.size();
    }

    private void showDiaLogDetail(Product product){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_showdetail,null);
        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        ImageView btnBack = view.findViewById(R.id.btnBack);
        TextView txtTitle = view.findViewById(R.id.txtTitle);
        ImageView image_detail = view.findViewById(R.id.image_detail);
        TextView txtProductName = view.findViewById(R.id.txtProductName);
        TextView txtPrice = view.findViewById(R.id.txtPrice);
        TextView txtDes = view.findViewById(R.id.txtDes);
        ImageView btnPlus = view.findViewById(R.id.btnPlus);
        ImageView btnMinus = view.findViewById(R.id.btnMinus);
        TextView txtSoluong = view.findViewById(R.id.txtSoLuong);
        Button btnAdd_Cart = view.findViewById(R.id.btnAdd_card);
        Button btnAdd_Fav = view.findViewById(R.id.btnAdd_to_Fav);

        Glide.with(context)
                .load(product.getImage())
                .placeholder(R.drawable.orange)
                .error(R.drawable.orange)
                .into(image_detail);
        txtTitle.setText(product.getProductName());
        txtProductName.setText(product.getProductName());
        txtPrice.setText(product.getPrice() + " $");
        txtDes.setText(product.getDescription());

        btnAdd_Cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cart cart = new Cart(product.get_id(),product.getProductName(),product.getImage(),String.valueOf(product.getPrice()),String.valueOf(soluong));
                add_Cart(cart);
                alertDialog.dismiss();
            }
        });

        btnAdd_Fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Favourite favourite = new Favourite(product.get_id(),product.getProductName(),product.getImage(),String.valueOf(product.getPrice()));
                add_Favourite(favourite);
                alertDialog.dismiss();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soluong++;
                txtSoluong.setText(String.valueOf(soluong));
            }
        });

        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (soluong > 1){
                    soluong--;
                    txtSoluong.setText(String.valueOf(soluong));
                }else {
                    Toast.makeText(context, "So luong khong the nho hon 0", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void add_Favourite(Favourite objFav){
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        FavouriteInterface favouriteInterface = retrofit.create(FavouriteInterface.class);
        Call<Favourite> objCall = favouriteInterface.them_yeu_thich(objFav);

        objCall.enqueue(new Callback<Favourite>() {
            @Override
            public void onResponse(Call<Favourite> call, Response<Favourite> response) {
                Toast.makeText(context, "Da them vao danh sach yeu thich", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Favourite> call, Throwable throwable) {
                Log.e("Error", "onFailure: " + throwable.getMessage());
                Toast.makeText(context, "Có lỗi xảy ra, vui lòng thử lại sau", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void add_Cart(Cart objCart){
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        CartInterface cartInterface = retrofit.create(CartInterface.class);
        Call<Cart> objCall = cartInterface.them_gio_hang(objCart);
        objCall.enqueue(new Callback<Cart>() {
            @Override
            public void onResponse(Call<Cart> call, Response<Cart> response) {
                Toast.makeText(context, "Da them vao danh sach gio hang", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Cart> call, Throwable throwable) {
                Log.e("Error", "onFailure: " + throwable.getMessage());
                Toast.makeText(context, "Có lỗi xảy ra, vui lòng thử lại sau", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

