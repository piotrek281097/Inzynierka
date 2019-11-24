package com.example.paymentreminderapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.example.paymentreminderapp.model.Invoice;
import com.example.paymentreminderapp.model.User;
import com.example.paymentreminderapp.repository.InvoiceRepository;
import com.example.paymentreminderapp.repository.UserRepository;
import com.example.paymentreminderapp.service.EmailCheckWorker;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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
    InvoiceRepository invoiceRepository = new InvoiceRepository();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final String COLLECTION_NAME = "Users";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        ButterKnife.bind(this);

        saveUserAndLaunchCheckEmailWorker();
        //checkEmailAccount();

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
//                        Toast.makeText(MainMenuActivity.this,"Successfully signed out",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainMenuActivity.this, StartActivity.class));
                        finish();
                    }
                });
    }

    public void saveUserAndLaunchCheckEmailWorker() {
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

            //userRepository.save(user);
            saveToFirestoreAndLaunchCheckEmailWorker(user);
            String uuid = UUID.randomUUID().toString();

            Invoice invoice1 = new Invoice(
                    UUID.randomUUID().toString(),
                    username,
                    100.00,
                    "ZŁ",
                    new Date().toString(),
                    "1234567890",
                    false,
                    false,
                    false,
                    "Orange1",
                    "link",
                    "2214356720192411"
                    );

            Invoice invoice2 = new Invoice(
                    UUID.randomUUID().toString(),
                    username,
                    200.00,
                    "ZŁ",
                    new Date().toString(),
                    "2234567890",
                    false,
                    false,
                    false,
                    "Orange2",
                    "link",
                    "2214356720192411"
            );

            Invoice invoice3 = new Invoice(
                    UUID.randomUUID().toString(),
                    username,
                    300.00,
                    "ZŁ",
                    new Date().toString(),
                    "3234567890",
                    false,
                    false,
                    false,
                    "Orange3",
                    "link",
                    "2214356720192411"
            );

//
//            invoiceRepository.save(invoice1);
//            invoiceRepository.save(invoice2);
//            invoiceRepository.save(invoice3);
        }
    }

    public void checkEmailAccount() {
        System.out.println("CHECK_EMAIL_LAUNCHED");
        Constraints myConstraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        PeriodicWorkRequest request = new PeriodicWorkRequest.Builder(EmailCheckWorker.class, 20, TimeUnit.MINUTES)
                .setConstraints(myConstraints)
                .build();

        //to anuluje
//            WorkManager.getInstance(getBaseContext()).cancelUniqueWork("jobTag");
//            WorkManager.getInstance(getBaseContext()).cancelUniqueWork("jobTag2");

                    //cancelAllWorkByTag("jobTag2");
//        WorkManager.getInstance(getBaseContext()).cancelAllWorkByTag("jobTag");


//        WorkManager.getInstance(getBaseContext())
//                .enqueueUniquePeriodicWork("taskTag", ExistingPeriodicWorkPolicy.KEEP, request);
    }

    public void saveToFirestoreAndLaunchCheckEmailWorker(User user) {
        db.collection(COLLECTION_NAME).document(user.getUsername()).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (!documentSnapshot.exists()) {
                            db.collection(COLLECTION_NAME).document(user.getUsername()).set(user)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            System.out.println("User saved");
                                            checkEmailAccount();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d("ERROR_LOG_", e.toString());
                                        }
                                    });
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("FAILURE");
                    }
                });
    }

}
