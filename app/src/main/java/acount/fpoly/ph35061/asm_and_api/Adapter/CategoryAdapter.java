package acount.fpoly.ph35061.asm_and_api.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import acount.fpoly.ph35061.asm_and_api.Model.Category;
import acount.fpoly.ph35061.asm_and_api.R;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder>{
    List<Category> ListItem;
    Context context;

    public CategoryAdapter(List<Category> listItem, Context context) {
        ListItem = listItem;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View View = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent,false);
        return new ViewHolder(View);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txt_category.setText(ListItem.get(position).getCateName());

    }

    @Override
    public int getItemCount() {
        return ListItem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txt_category;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_category = itemView.findViewById(R.id.txt_category);
        }
    }
}
