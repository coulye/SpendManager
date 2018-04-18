package fr.moralesmarie.spendmanager.HttpRequest;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by utilisateur on 26/02/2018.
 */

public class HttpGetRequest extends AsyncTask<String, Void, String> {

    public static final String REQUEST_METHOD = "GET";
    public static final int READ_TIMEOUT = 15000;
    public static final int CONNECTION_TIMEOUT = 15000;
    private HttpURLConnection connection;

    @Override
    protected String doInBackground(String... params) { // methode hériter de AsyncTask
        String stringUrl = params[0]; // récupere l'url qui sera en parametre
        try{
            URL myUrl = new URL(stringUrl); // enregistre l'url cible dans la variable myUrl
            connection = (HttpURLConnection) myUrl.openConnection(); // crée la connection
        }
        catch (IOException e){
            e.printStackTrace();
            return null;
        }
        return getDataFromServer(); // JSON renvoyé
    }

    protected String getDataFromServer(){
        String result;
        String inputLine;
        try{
            connection.setRequestMethod(REQUEST_METHOD); // configure la méthode de récupération HTTP, ici GET
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setConnectTimeout(CONNECTION_TIMEOUT);
            connection.connect(); // ouvre un canal de communication en se connectant à l'url
            InputStreamReader streamReader = new InputStreamReader(connection.getInputStream()); // crée une entrée de lecture du flux de l'url cible
            BufferedReader reader = new BufferedReader(streamReader); // crée un buffer de lecture
            StringBuilder stringBuilder = new StringBuilder();
            while ((inputLine = reader.readLine()) != null){ // vérifie si le contenu d'une ligne (\n) est null
                stringBuilder.append(inputLine); // lecture par ligne à chaque '\n' on parcours la boucle et on récupère la ligne suivante
            }
            reader.close(); // fermeture des flux
            streamReader.close();
            result = stringBuilder.toString(); // on stocke le résultat récuperé par le stringBuilder grace au toString()
        }
        catch (IOException e){
            e.printStackTrace();
            result = null;
        }
        return result; // on renvoie le JSON reçu du serveur
    }

    protected void onPostExecute(String result){
        super.onPostExecute(result);
    }
}
