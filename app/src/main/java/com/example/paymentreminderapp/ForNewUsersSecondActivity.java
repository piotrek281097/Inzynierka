package com.example.paymentreminderapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForNewUsersSecondActivity extends AppCompatActivity {

    @BindView(R.id.instructionFirstStep) TextView instruction;
    @BindView(R.id.textLinkNext) TextView  goNext;
    @BindView(R.id.linkToSettingGmail) TextView linkToSettings;

    String instructionText = "W celu korzystania z naszej aplikacji prawdopodbnie będzie konieczna zmiana pewnych ustawień bezpieczeństwa " +
            "na Twoim koncie gmail. Jeśli twoje konto zabrania dostępu mniej bezpiecznym aplkacjom to postępuj zgodnie z instrukcją." +
            " Kliknij w poniższy link, a następnie poszukaj pozycji 'Dostęp mniej bezpiecznych aplikacji' " +
            "i udziel takim aplikacjom uprawnień. Później wróć do aplikacji i kliknij w prawym dolnym rogu przycisk DALEJ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_new_users_second);
        ButterKnife.bind(this);

        instruction.setText(instructionText);

        linkToSettings.setText("https://myaccount.google.com/lesssecureapps");
        Linkify.addLinks(linkToSettings, Linkify.ALL);

        goNext.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getBaseContext(), ForNewUsersThirdActivity.class);
                startActivity(intent);
            }
        });
    }
}
