package fr.moralesmarie.spendmanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import fr.moralesmarie.spendmanager.HttpRequest.HttpPostRequest;

public class LoginActivity extends AppCompatActivity {

    final String LOGIN_USER = "user";
    final String PASSWD_USER = "pass";
    EditText Identifiant, MDP;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Identifiant=(EditText)findViewById(R.id.EditIdentifiant);
        MDP=(EditText)findViewById(R.id.EditMDP);
        Button btnValider = (Button) findViewById(R.id.buttonAccueil);
        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connexion();
            }
        });

    }

    public void connexion(){
        String result = "";

        String loginSend = Identifiant.getText().toString();
        String passSend = MDP.getText().toString();


//        String myUrl = "http://172.20.10.5/REST-API-SY4/public/login.php";
//        String myUrl = "http://127.0.0.1:8080/REST-API-SY4/public/login.php";
        String myUrl = "http://moralesmarie.alwaysdata.net/public/login";



        String params = "mail="+loginSend+"&mdp="+passSend;

        HttpPostRequest postRequest = new HttpPostRequest();
        try{
            Log.i("test", "result "+result);

            result = postRequest.execute(new String []{myUrl, params}).get(); // exécution de la connexion
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        try{
            JSONObject objLogin = new JSONObject(result);
            if (loginSend.equals(objLogin.getString("Mail_Utilisateur")) && passSend.equals(objLogin.get("Mdp_Utilisateur"))){
                Intent i = new Intent(LoginActivity.this, MenuActivity.class);

                SharedPreferences myPref = getSharedPreferences("user_profile", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = myPref.edit();
                editor.putString("loginSend", loginSend);
                editor.putString("prenom_extra", objLogin.getString("Prenom_Utilisateur"));
                editor.putString("nom_extra", objLogin.getString("Nom_Utilisateur"));
                editor.putString("adresse_extra", (objLogin.getString("Adresse_Utilisateur")+", "+objLogin.getString("Cp_Utilisateur")+" "+objLogin.getString("Ville_Utilisateur")));
                editor.putString("tel_extra", objLogin.getString("Telephone_Utilisateur"));
                editor.putString("mail_extra", objLogin.getString("Mail_Utilisateur"));
                editor.putString("mdp_extra", objLogin.getString("Mdp_Utilisateur"));
                editor.putString("statut_extra", objLogin.getString("Statut_Utilisateur"));
                editor.putInt("id_user", objLogin.getInt("Id_Utilisateur"));

                editor.apply();
                startActivity(i);

            } else {
                Context c = getApplicationContext();
                Toast msg = Toast.makeText(c, "Mauvais identifiant ou mot de passe !", Toast.LENGTH_SHORT);
                msg.show();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }



}