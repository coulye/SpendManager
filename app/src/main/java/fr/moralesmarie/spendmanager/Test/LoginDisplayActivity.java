package fr.moralesmarie.spendmanager.Test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import fr.moralesmarie.spendmanager.LoginActivity;
import fr.moralesmarie.spendmanager.R;

/**
 * Created by pierrick.pabijan on 03/04/2018.
 */

public class LoginDisplayActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_display);
        final Button retour = (Button) findViewById(R.id.retour);
        retour.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent2 = new Intent(LoginDisplayActivity.this, LoginActivity.class);
                startActivity(intent2);
            }
        });
    }
}