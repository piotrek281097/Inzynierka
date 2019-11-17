package com.example.paymentreminderapp.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.paymentreminderapp.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserRepository {

    private final String COLLECTION_NAME = "Users";
    private final String KEY_DISPLAY_NAME = "displayName";
    private final String KEY_EMAIL = "email";
    private final String KEY_ID = "id";
    private final String KEY_ID_TOKEN = "idToken";
    private final String KEY_USERNAME = "username";
    private final String KEY_PASSWORD = "password";

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    String password;

    public void save(User user) {
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

    public void updatePassword(String username, String password) {
        db.collection(COLLECTION_NAME).document(username).update("password", password);
    }

    public DocumentSnapshot getUserByUsername(String username){
        return db.collection(COLLECTION_NAME).document(username).get().getResult();
    }

    public String getPasswordUser(String username) {
        getByUserName(username);
        return username;
    }

    public void getByUserName(String username) {
        db.collection(COLLECTION_NAME).document(username)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            System.out.println("Dokument pobrany");

                            setPassword(documentSnapshot.getString(KEY_PASSWORD));

                            User user = new User(
                                    documentSnapshot.getString(KEY_ID),
                                    documentSnapshot.getString(KEY_ID_TOKEN),
                                    documentSnapshot.getString(KEY_DISPLAY_NAME),
                                    documentSnapshot.getString(KEY_EMAIL),
                                    documentSnapshot.getString(KEY_USERNAME),
                                    documentSnapshot.getString(KEY_PASSWORD)
                            );
                        } else {
                            System.out.println("Dokument nie istnieje");
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

    private void setPassword(String passwordPassed) {
        password = passwordPassed;
    }
}
