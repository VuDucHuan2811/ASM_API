package acount.fpoly.ph35061.asm_and_api.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import acount.fpoly.ph35061.asm_and_api.Model.Bill;
import acount.fpoly.ph35061.asm_and_api.R;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.ViewHolder> {
    private ArrayList<Bill> lisBill;
    Context context;

    public BillAdapter(ArrayList<Bill> lisBill) {
        this.lisBill = lisBill;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bill, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Bill bill = lisBill.get(position);

        holder.txt_billid.setText("Bill: " + bill.get_id());
        holder.txt_date.setText("Date: " + bill.getDate());
        holder.txt_email.setText("Email: " + bill.getEmail());
    }

    @Override
    public int getItemCount() {
        return lisBill.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txt_billid, txt_date, txt_email;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_billid = itemView.findViewById(R.id.txt_billid);
            txt_date = itemView.findViewById(R.id.txt_date);
            txt_email = itemView.findViewById(R.id.txt_email);
        }
    }
}
