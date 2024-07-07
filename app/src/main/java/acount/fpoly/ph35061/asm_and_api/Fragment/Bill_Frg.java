package acount.fpoly.ph35061.asm_and_api.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

import acount.fpoly.ph35061.asm_and_api.Adapter.BillAdapter;
import acount.fpoly.ph35061.asm_and_api.Model.Bill;
import acount.fpoly.ph35061.asm_and_api.Model.Product;
import acount.fpoly.ph35061.asm_and_api.R;

public class Bill_Frg extends Fragment {
    RecyclerView recyclerBill;
    SearchView searchBill;
    ArrayList<Bill> billList;

    BillAdapter billAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bill,container,false);
        recyclerBill = view.findViewById(R.id.rcv_bill);
        searchBill = view.findViewById(R.id.search_bill);

        searchBill.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchBill(newText);
                return true;
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerBill.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerBill.getContext(),layoutManager.getOrientation());
        recyclerBill.addItemDecoration(dividerItemDecoration);
        billList = getListBill();
        billAdapter = new BillAdapter(billList);
        recyclerBill.setAdapter(billAdapter);
        return view;
    }
    private ArrayList<Bill> getListBill(){
        ArrayList<Bill> arrayBill = new ArrayList<>();

        arrayBill.add(new Bill("1","2024/04/09","huanvdph35061@fpt.edu.vn"));

        return arrayBill;
    }
    private void searchBill(String keyword) {
        if (keyword.trim().equals("")) {
            // Reset list to original state if search keyword is empty
            billList.clear();
            billList.addAll(getListBill());
        } else {
            List<Bill> searchBillList = new ArrayList<>();
            for (Bill bill : billList) {
                if (bill.getEmail().toLowerCase().contains(keyword.toLowerCase())) {
                    searchBillList.add(bill);
                }
            }
            // Update billList with search results
            billList.clear();
            billList.addAll(searchBillList);
        }
        // Notify adapter of data change
        billAdapter.notifyDataSetChanged();
    }
}