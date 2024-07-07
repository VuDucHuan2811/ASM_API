package acount.fpoly.ph35061.asm_and_api.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import acount.fpoly.ph35061.asm_and_api.Fragment.Bill_Frg;
import acount.fpoly.ph35061.asm_and_api.Fragment.Cart_Frg;
import acount.fpoly.ph35061.asm_and_api.Fragment.Favourite_Frg;
import acount.fpoly.ph35061.asm_and_api.Fragment.Home;
import acount.fpoly.ph35061.asm_and_api.R;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottomNav);
        frameLayout = findViewById(R.id.frgLayout);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment;
                if (menuItem.getItemId() == R.id.navHome){
                    fragment = new Home();
                }else if (menuItem.getItemId() == R.id.navBag){
                    fragment = new Cart_Frg();
                }else if (menuItem.getItemId() == R.id.navMenu)   {
                    fragment = new Bill_Frg();
                }else
                    fragment = new Favourite_Frg();

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frgLayout,fragment)
                        .commit();
                return true;
            }
        });
    }
}