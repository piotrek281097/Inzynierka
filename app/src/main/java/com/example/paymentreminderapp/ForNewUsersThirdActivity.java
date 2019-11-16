package com.example.paymentreminderapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForNewUsersThirdActivity extends AppCompatActivity {

    @BindView(R.id.instructionSecondStep) TextView instruction;
    @BindView(R.id.textLinkNext) TextView finish;
    @BindView(R.id.password) EditText password;

    String instructionText = "W celu dostępu do Twojego konta gmail prosimy o jednorazowe podanie hasła do konta." +
            " Hasło to zostanie wykorzystane jedynie na użytek tej aplikacji. To już ostatni etap konfiguracji." +
            " Po tym etapie można już się logować.";

    String passwordGmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_new_users_third);
        ButterKnife.bind(this);

        instruction.setText(instructionText);

        finish.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                passwordGmail = password.getText().toString();

                savePassword(passwordGmail);

                Intent intent = new Intent(getBaseContext(), StartActivity.class);
                startActivity(intent);
            }
        });
    }

    private void savePassword(String passwordGmail) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = pref.edit();

        editor.putString("gmailPass", passwordGmail);
        editor.commit();

        String pass = pref.getString("gmailPass", null);

        System.out.println("PASSWORD : " + pass);

        /*

         */
    }
}
