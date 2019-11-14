package com.example.paymentreminderapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ForNewUsersActivity extends AppCompatActivity {

    TextView instruction;
    TextView goNext;
    TextView linkToSettings;
    String instructionText = "W celu korzystania z naszej aplikacji prawdopodbnie będzie konieczna zmiana pewnych ustawień bezpieczeństwa " +
            "na Twoim koncie gmail. Jeśli twoje konto zabrania dostępu mniej bezpiecznym aplkacjom to postępuj zgodnie z instrukcją." +
            " Kliknij w poniższy link, a następnie poszukaj pozycji 'Dostęp mniej bezpiecznych aplikacji' " +
            "i udziel takim aplikacjom uprawnień. Później wróć do aplikacji i kliknij w prawym dolnym rogu przycisk DALEJ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_new_users);

        instruction = findViewById(R.id.instructionFirstStep);
        instruction.setText(instructionText);

        linkToSettings = findViewById(R.id.linkToSettingGmail);
        linkToSettings.setText("https://myaccount.google.com/lesssecureapps");
        Linkify.addLinks(linkToSettings, Linkify.ALL);

        goNext = findViewById(R.id.textLinkNext);
        goNext.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getBaseContext(), ForNewUsersNextActivity.class);
                startActivity(intent);
            }
        });
    }
}
