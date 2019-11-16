package com.example.paymentreminderapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForNewUsersActivity extends AppCompatActivity {

    @BindView(R.id.informationAssurance) TextView information;
    @BindView(R.id.textLinkNextToSecond) TextView goNext;
    @BindView(R.id.textLinkOut) TextView goOut;


    String informationText = "Drogi użytkowniku, nasza aplikacja wymaga dostępu do Twojego prywatnego konta gmail" +
            " w celu sprawdzania nowych faktur, żadne inne treści nas nie interesują. Zapewniamy, że żadne dane użytkownika nie" +
            " będą wykorzystywane poza tą aplikacją. Jeśli sie na to nie zgadzasz WYJDŹ z aplikacji," +
            " natomiast jeśli chcesz kontynuować to kliknij DALEJ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_new_users);
        ButterKnife.bind(this);

        information.setText(informationText);

        goOut.setOnClickListener(v -> {
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        });

        goNext.setOnClickListener(v -> {
            Intent intent = new Intent(getBaseContext(), ForNewUsersSecondActivity.class);
            startActivity(intent);
        });
    }
}
