package com.example.paymentreminderapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.paymentreminderapp.model.User;
import com.example.paymentreminderapp.repository.UserRepository;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainMenuActivity extends AppCompatActivity {

    @BindView(R.id.nameText) TextView name;
    @BindView(R.id.btnInvoices) TextView btnInvoices;
    @BindView(R.id.btnStatistics) TextView btnStatistics;
    @BindView(R.id.btnPayments) TextView btnPayments;
    @BindView(R.id.btnSettings) TextView btnSettings;
    @BindView(R.id.btnSignOut) TextView btnSignOut;

    GoogleSignInClient mGoogleSignInClient;
    UserRepository userRepository = new UserRepository();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        ButterKnife.bind(this);

        saveUser();

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });
    }

    private String getPasswordFromSharedPref() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);

        return pref.getString("gmailPass", null);
    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(MainMenuActivity.this,"Successfully signed out",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainMenuActivity.this, StartActivity.class));
                        finish();
                    }
                });
    }

    public void saveUser() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(MainMenuActivity.this);
        if (acct != null) {
            String displayName = acct.getDisplayName();
            String email1 = acct.getEmail();
            String id = acct.getId();
            String idToken = acct.getIdToken();
            String username = email1.replace("@gmail.com", "");
            String password = getPasswordFromSharedPref();

            String passwordUser = userRepository.getPasswordUser(username);

            if(!passwordUser.equals(password)) {
                userRepository.updatePassword(username, password);
            }

            User user = new User(id, idToken, displayName, email1, username, password);

            userRepository.save(user);
        }
    }
}
