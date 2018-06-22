package fr.moralesmarie.spendmanager.Test;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import fr.moralesmarie.spendmanager.LoginActivity;
import fr.moralesmarie.spendmanager.R;


public class OublieActivity extends Activity {
    final String Email = "usr_email";
    EditText Mail;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.oublie);

        Mail=(EditText)findViewById(R.id.editText);

    }
    public void oublie2(View view) {

        String mail=Mail.getText().toString();
        String type ="oubli";

        OublieManager oublieManager= new OublieManager(OublieActivity.this);
        oublieManager.execute(type, mail);

    }
    public void retour(View view) {
        Intent intent = new Intent(OublieActivity.this, LoginActivity.class);
        startActivity(intent);
    }

}
