package com.example.paymentreminderapp.invoices;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.paymentreminderapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.ButterKnife;

public class InvoicesActivity extends AppCompatActivity {

    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoices);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        username = bundle.getString("username");

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, AllInvoicesFragment.newInstance(username))
                    .commit();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            item -> {
                Fragment selectedFragment = null;

                switch (item.getItemId()) {
                    case R.id.nav_all_invoices:
                        selectedFragment = AllInvoicesFragment.newInstance(username);
                        break;
                    case R.id.nav_paid_invoices:
                        selectedFragment = PaidInvoicesFragment.newInstance(username);
                        break;
                    case R.id.nav_unpaid_invoices:
                        selectedFragment = UnpaidInvoicesFragment.newInstance(username);
                        break;
                }

                assert selectedFragment != null;
                getSupportFragmentManager().popBackStack();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .commit();

                return true;
            };
}
