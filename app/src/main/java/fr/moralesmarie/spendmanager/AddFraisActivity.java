package fr.moralesmarie.spendmanager;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import fr.moralesmarie.spendmanager.Class.Client;
import fr.moralesmarie.spendmanager.Class.Frais;
import fr.moralesmarie.spendmanager.Class.Justificatif;
import fr.moralesmarie.spendmanager.Class.Notefrais;
import fr.moralesmarie.spendmanager.Class.Trajet;
import fr.moralesmarie.spendmanager.Fragment.FraisFragment;
import fr.moralesmarie.spendmanager.Fragment.TrajetFragment;
import fr.moralesmarie.spendmanager.HttpRequest.HttpGetRequest;
import fr.moralesmarie.spendmanager.HttpRequest.HttpPostRequest;

public class AddFraisActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private Button btnAddClient;
    private RadioGroup radioGroup;
    private RadioButton radioSelect;

    Spinner spinner;

    private Calendar mCurrentDate;
    private String mDate;
    private String months;
    private String days;
    private String years;

    private FraisFragment theFraisFragment;
    private TrajetFragment theTrajetFragment;

    private EditText dureeTrajet;
    private EditText villeDepard;
    private EditText villeArrivee;
    private EditText km;
    private EditText commentaire;
    private EditText montant;

    private ImageButton takePictureBtn;
    private ImageButton btnRetour;
    private ImageButton addDepense;
    private Button sendNotefrais;

    private ArrayList<Frais> listFrais;
    private ArrayList<Trajet> listTrajet;
    private ArrayList<Justificatif> listJustificatif;

    //PHOTO
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 0;

    private static String[] PERMISSIONS_STORAGE_CAMERA = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.CAPTURE_VIDEO_OUTPUT,
            Manifest.permission.CAPTURE_AUDIO_OUTPUT
    };

    private ImageView mImageThumbnail;
    private Uri imageUri;

	final String LOGIN_USER = "user_profile";
	private int idUtilisateur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_add_frais);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

		//menu nav header - info user
		String mailuser = null;
        String coorduser = null;
        TextView mail_user;
        TextView user_detail;
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        final View headerLayout = navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(this);
        SharedPreferences myPref = getSharedPreferences(LOGIN_USER, Context.MODE_PRIVATE);
        Intent i = getIntent();
        if (i != null) {
            mailuser =  myPref.getString("loginSend", mailuser);
            System.out.println("mailuser : "+mailuser);
            mail_user = (TextView)headerLayout.findViewById(R.id.mail_user);
            mail_user.setText(mailuser);

            coorduser = myPref.getString("prenom_extra", coorduser)+ " " + myPref.getString("nom_extra", coorduser);
            user_detail = (TextView)headerLayout.findViewById(R.id.user_detail);
            user_detail.setText(coorduser);

            idUtilisateur = myPref.getInt("id_user", idUtilisateur);
        }

        btnAddClient = (Button) findViewById(R.id.btnAddClient);
        btnAddClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AddFraisActivity.this, AddClientActivity.class);
                startActivity(i);
            }
        });

        //SPINNER CLIENT
        final ArrayList<Client> listClient = listClient();
        spinner = (Spinner) findViewById(R.id.spinnerClient);
        List exempleList = new ArrayList();
        exempleList.add("Choisissez un client");
        for (Client leClient : listClient) {
            exempleList.add(leClient.getNom_Client()+" "+leClient.getPrenom_Client());
        }
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, exempleList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        mCurrentDate = Calendar.getInstance();
        final int year = mCurrentDate.get(Calendar.YEAR);
        final int month = mCurrentDate.get(Calendar.MONTH);
        final int day = mCurrentDate.get(Calendar.DAY_OF_MONTH);
        if(month >= 1 && month <= 9 ){
            months = "0" + (month+1);
        } else {
            months = "" + (month+1);
        }
        if(day >= 1 && day <= 9){
            days = "0" + day;
        } else {
            days = "" + day;
        }
        mDate = year + "-" + months + "-" + days;

        radioGroup = (RadioGroup) findViewById(R.id.radioGroupMotif);

        commentaire = (EditText) findViewById(R.id.commentaireFrais);
        dureeTrajet = (EditText) findViewById(R.id.dureeTrajet);
        villeDepard = (EditText) findViewById(R.id.villeDepart);
        villeArrivee = (EditText) findViewById(R.id.villeArrivee);
        km = (EditText) findViewById(R.id.editKilometre);
        montant = (EditText) findViewById(R.id.champPrixDepense);
        takePictureBtn = (ImageButton) findViewById(R.id.imageButtonPhoto);
        mImageThumbnail = (ImageView) findViewById(R.id.imageThumbnail);
        addDepense = (ImageButton) findViewById(R.id.imageAddDepense);
        sendNotefrais = (Button) findViewById(R.id.btnValider);

        listFrais = new ArrayList<Frais>();
        listTrajet = new ArrayList<Trajet>();
        listJustificatif = new ArrayList<Justificatif>();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                radioSelect = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
                if (radioSelect.getText().toString().equals("Essence") || radioSelect.getText().toString().equals("Hotel") ||
                        radioSelect.getText().toString().equals("Parking") || radioSelect.getText().toString().equals("Restaurant")) {
                    theFraisFragment = new FraisFragment();
                    transaction.replace(R.id.fragmentLayout, theFraisFragment);
                } else if (radioSelect.getText().toString().equals("Train") || radioSelect.getText().toString().equals("Taxi") ||
                        radioSelect.getText().toString().equals("Bus") || radioSelect.getText().toString().equals("Autoroute") ||
                        radioSelect.getText().toString().equals("Avion")) {
                    theTrajetFragment = new TrajetFragment();
                    transaction.replace(R.id.fragmentLayout, theTrajetFragment);
                }
                transaction.commit();
            }
        });


        takePictureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int permission = ActivityCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (permission != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(
                            AddFraisActivity.this, PERMISSIONS_STORAGE_CAMERA,
                            CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE
                    );
                } else {
                    try {
                        takePictureIntent();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        btnRetour = (ImageButton) findViewById(R.id.imageButtonBack);
        btnRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(AddFraisActivity.this, MenuActivity.class);
                startActivity(back);
            }
        });

        addDepense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ajout en mémoire de la dépense en cours
                if (radioGroup.getCheckedRadioButtonId() == -1){
                    Context c = getApplicationContext();
                    Toast msg = Toast.makeText(c, "Veuillez renseigner un motif de note de frais !", Toast.LENGTH_SHORT);
                    msg.show();
                } else {
                    radioSelect = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
                }
                if (montant.getText().toString().equals("") || commentaire.getText().toString().equals("")){
                    Context c = getApplicationContext();
                    Toast msg = Toast.makeText(c, "Veuillez renseigner tous les champs demandés !", Toast.LENGTH_SHORT);
                    msg.show();
                } else {
                    int idDepense = 0;
                    if (radioSelect.getText().toString().equals("Essence") || radioSelect.getText().toString().equals("Hotel") ||
                            radioSelect.getText().toString().equals("Parking") || radioSelect.getText().toString().equals("Restaurant")) {
                        idDepense = getRandom();
                        Frais leFrais = new Frais(
                                theFraisFragment.getSQLDateExpense(),
                                idDepense,
                                null,
                                radioSelect.getText().toString(),
                                commentaire.getText().toString(),
                                Float.parseFloat(montant.getText().toString()),
                                0
                        );
                        listFrais.add(leFrais);
                    } else if (radioSelect.getText().toString().equals("Train") || radioSelect.getText().toString().equals("Taxi") ||
                            radioSelect.getText().toString().equals("Bus") || radioSelect.getText().toString().equals("Autoroute") ||
                            radioSelect.getText().toString().equals("Avion")) {
                        if (dureeTrajet.getText().toString().equals("") || villeDepard.getText().toString().equals("") ||
                                villeArrivee.getText().toString().equals("") || km.getText().toString().equals("")){
                            Context c = getApplicationContext();
                            Toast msg = Toast.makeText(c, "Veuillez renseigner tous les champs demandés !", Toast.LENGTH_SHORT);
                            msg.show();
                        } else {
                            idDepense = getRandom();
                            Trajet leTrajet = new Trajet(
                                    theTrajetFragment.getDureeTrajet(),
                                    theTrajetFragment.getVilleDepard(),
                                    theTrajetFragment.getVilleArrivee(),
                                    theTrajetFragment.getSQLDateAller(),
                                    theTrajetFragment.getSQLDateRetour(),
                                    theTrajetFragment.getKm(),
                                    idDepense,
                                    null,
                                    radioSelect.getText().toString(),
                                    commentaire.getText().toString(),
                                    Float.parseFloat(montant.getText().toString()),
                                    0
                            );
                            listTrajet.add(leTrajet);
                        }
                    }
                    String encodedImageData = getEncoded64ImageStringFromBitmap();
                    Justificatif leJustificatif = new Justificatif(
                            0,
                            radioSelect.getText().toString()+mDate,
                            encodedImageData,
                            idDepense,
                            0
                    );
                    listJustificatif.add(leJustificatif);
                }

                //remise à zéro
                spinner.setSelection(0);
                commentaire.setText("");
                if (radioGroup.getCheckedRadioButtonId() != -1) {
                    radioSelect.setChecked(false);
                }
                montant.setText("");
            }
        });
        sendNotefrais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton radioSelect = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
                String leClient = spinner.getSelectedItem().toString();
                if (leClient.equals("Choisissez un client")) {
                    Context c = getApplicationContext();
                    Toast msg = Toast.makeText(c, "Veuillez choisir un client dans la liste déroulante !", Toast.LENGTH_SHORT);
                    msg.show();
                } else {
                    if (montant.getText().toString().equals("") && commentaire.getText().toString().equals("")) {
                        Context c = getApplicationContext();
                        Toast msg = Toast.makeText(c, "Tous les champs de la dépense à l'écran n'ont pas été remplis. Elle ne sera pas prise en compte !", Toast.LENGTH_LONG);
                        msg.show();
                    } else {
                        //ajout en mémoire de la dépense en cours
                        int idDepense = 0;
                        if (radioSelect.getText().toString().equals("Essence") || radioSelect.getText().toString().equals("Hotel") ||
                                radioSelect.getText().toString().equals("Parking") || radioSelect.getText().toString().equals("Restaurant")) {
                            idDepense = getRandom();
                            Frais leFrais = new Frais(
                                    theFraisFragment.getSQLDateExpense(),
                                    idDepense,
                                    null,
                                    radioSelect.getText().toString(),
                                    commentaire.getText().toString(),
                                    Float.parseFloat(montant.getText().toString()),
                                    0
                            );
                            listFrais.add(leFrais);
                        } else if (radioSelect.getText().toString().equals("Train") || radioSelect.getText().toString().equals("Taxi") ||
                                radioSelect.getText().toString().equals("Bus") || radioSelect.getText().toString().equals("Autoroute") ||
                                radioSelect.getText().toString().equals("Avion")) {
                            idDepense = getRandom();
                            Trajet leTrajet = new Trajet(
                                    theTrajetFragment.getDureeTrajet(),
                                    theTrajetFragment.getVilleDepard(),
                                    theTrajetFragment.getVilleArrivee(),
                                    theTrajetFragment.getSQLDateAller(),
                                    theTrajetFragment.getSQLDateRetour(),
                                    theTrajetFragment.getKm(),
                                    idDepense,
                                    null,
                                    radioSelect.getText().toString(),
                                    commentaire.getText().toString(),
                                    Float.parseFloat(montant.getText().toString()),
                                    0
                            );
                            listTrajet.add(leTrajet);
                        }
                        String encodedImageData = getEncoded64ImageStringFromBitmap();
                        Justificatif leJustificatif = new Justificatif(
                                0,
                                radioSelect.getText().toString()+mDate,
                                encodedImageData,
                                idDepense,
                                0
                        );
                        listJustificatif.add(leJustificatif);
                    }
                    //création note de frais
                    if (listTrajet.isEmpty() && listFrais.isEmpty()) {
                        Context c = getApplicationContext();
                        Toast msg = Toast.makeText(c, "Il n'y a aucune dépense en attente impossible de créer la note de frais !", Toast.LENGTH_LONG);
                        msg.show();
                    } else {
                        int idClient = 0;
                        for (Client c : listClient) {
                            if (leClient.equals(c.getNom_Client() + " " + c.getPrenom_Client())) {
                                idClient = c.getId_Client();
                                break;
                            }
                        }
                        Notefrais laNotefrais = new Notefrais(0, mDate, mDate, 1, idClient);
                        int idNotefrais = addNotefrais(laNotefrais, idUtilisateur);
                        String result = addDepense(listTrajet, listFrais, listJustificatif, idNotefrais);
                        Context c = getApplicationContext();
                        Toast msg = Toast.makeText(c, "La note de frais à bien été ajoutée !", Toast.LENGTH_SHORT);
                        msg.show();

                        //remise à zéro
                        spinner.setSelection(0);
                        commentaire.setText("");
                        radioSelect.setChecked(false);
                        montant.setText("");
                        //remise à zero des collections de dépense
                        listFrais.clear();
                        listTrajet.clear();
                    }
                }
            }
        });
    }

    public String getEncoded64ImageStringFromBitmap() {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Bitmap bitmap = ((BitmapDrawable) mImageThumbnail.getDrawable()).getBitmap();
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
        byte[] byteFormat = stream.toByteArray();
        String imgString = Base64.encodeToString(byteFormat, Base64.DEFAULT);

        System.out.println("toto "+imgString);

        return imgString;
}

    public int getRandom(){
        Random random = new Random();
        int leRandom = 100000 + random.nextInt(1000000 - 100000);
        return leRandom;
    }

    public ArrayList<Client> listClient(){
        ArrayList<Client> listClient = new ArrayList<Client>();
        String result = "";

//        String myUrl = "http://127.0.0.1:8080/REST-API-SY4/public/client";
        String myUrl = "http://moralesmarie.alwaysdata.net/public/client";

        HttpGetRequest getRequest = new HttpGetRequest();
        try{
            result = getRequest.execute(myUrl).get(); // exécution de la connexion
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        try {
            JSONArray tblJSON = new JSONArray(result);
            for (int i = 0 ; i < tblJSON.length() ; i++) {
                JSONObject jsonObject = tblJSON.getJSONObject(i);
                Client leClient = new Client(
                        jsonObject.getInt("Id_Client"),
                        jsonObject.getString("Titre_Client"),
                        jsonObject.getString("Nom_Client"),
                        jsonObject.getString("Prenom_Client"),
                        jsonObject.getString("Adresse_Client"),
                        jsonObject.getString("Cp_Client"),
                        jsonObject.getString("Ville_Client"),
                        jsonObject.getString("Telephone_Client"),
                        jsonObject.getString("Mail_Client"),
                        jsonObject.getString("Rs_Client")
                );
                listClient.add(leClient);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return listClient;
    }

    public int addNotefrais(Notefrais laNotefrais, int idUtilisateur){
        String result = "";
        int idNotefrais;

        String myUrl = "http://moralesmarie.alwaysdata.net/public/utilisateur/"+idUtilisateur+"/notefrais";
        String params =
                "Date_Notefrais="+laNotefrais.getDate_Notefrais()+
                "&DateSoumission_Notefrais="+laNotefrais.getDateSoumission_Notefrais()+
                "&Id_Utilisateur="+laNotefrais.getId_Utilisateur()+
                "&Id_Client="+laNotefrais.getId_Client();

        HttpPostRequest postRequest = new HttpPostRequest();
        try{
            result = postRequest.execute(new String []{myUrl, params}).get();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        String[] stringId = result.split("[^ 0-9]");
        return idNotefrais = Integer.parseInt(stringId[1]);
    }

    public String addDepense(ArrayList<Trajet> listTrajet, ArrayList<Frais> listFrais, ArrayList<Justificatif> listJustificatif, int idNotefrais){
        String result = "";

        for (Frais frais : listFrais) {
            String myUrl = "http://moralesmarie.alwaysdata.net/public/notefrais/"+idNotefrais+"/depense";
            String params =
                    "DatePaiement_Depense=0000-01-01" +
                    "&Libelle_Depense=" + frais.getLibelle_Depense() +
                    "&Commentaire_Depense=" + frais.getCommentaire_Depense() +
                    "&MontantRemboursement_Depense=" + frais.getMontantRemboursement_Depense() +
                    "&Id_Notefrais=" + idNotefrais +
                    "&Date_Frais=" + frais.getDate_Frais() +
                    "&type=frais";

            HttpPostRequest postRequest = new HttpPostRequest();
            try{
                result = postRequest.execute(new String []{myUrl, params}).get();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            String[] stringId = result.split("[^ 0-9]");
            int idDepense = Integer.parseInt(stringId[1]);
            Justificatif justificatif = null;
            for (Justificatif j : listJustificatif){
                if (frais.getId_Depense()==j.getId_Depense()){
                    justificatif = j;
                }
            }
            String resultJustificatif = addJustificatif(justificatif, idNotefrais, idDepense);
        }
        for (Trajet trajet : listTrajet) {
            String myUrl = "http://moralesmarie.alwaysdata.net/public/notefrais/"+idNotefrais+"/depense";
            String params =
                    "DatePaiement_Depense=0000-01-01" +
                    "&Libelle_Depense=" + trajet.getLibelle_Depense() +
                    "&Commentaire_Depense=" + trajet.getCommentaire_Depense() +
                    "&MontantRemboursement_Depense=" + trajet.getMontantRemboursement_Depense() +
                    "&Id_Notefrais=" + idNotefrais +
                    "&Duree_Trajet=" + trajet.getDuree_Trajet() +
                    "&VilleDepart_Trajet=" + trajet.getVilleDepart_Trajet() +
                    "&VilleArrivee_Trajet=" + trajet.getVilleArrivee_Trajet() +
                    "&DateAller_Trajet=" + trajet.getDateAller_Trajet() +
                    "&DateRetour_Trajet=" + trajet.getDateRetour_Trajet() +
                    "&Kilometre_Trajet=" + trajet.getKilometre_Trajet() +
                    "&type=trajet";

            HttpPostRequest postRequest = new HttpPostRequest();
            try{
                result = postRequest.execute(new String []{myUrl, params}).get();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            String[] stringId = result.split("[^ 0-9]");
            int idDepense = Integer.parseInt(stringId[1]);
            Justificatif justificatif = null;
            for (Justificatif j : listJustificatif){
                if (trajet.getId_Depense()==j.getId_Depense()){
                    justificatif = j;
                }
            }
            String resultJustificatif = addJustificatif(justificatif, idNotefrais, idDepense);
        }
        return result;
    }

    public String addJustificatif(Justificatif leJustificatif, int idNotefrais, int idDepense){
        String result = "";
        String myUrl = "http://moralesmarie.alwaysdata.net/public/notefrais/"+idNotefrais+"/depense/"+idDepense+"/justificatif";
        String params =
                "Titre_Justificatif="+ leJustificatif.getTitre_Justificatif() +
                        "&Url_Justificatif=" + leJustificatif.getUrl_Justificatif() +
                        "&Id_Depense=" + idDepense +
                        "&Id_Notefrais=" + idNotefrais;
        HttpPostRequest postRequest = new HttpPostRequest();
        try{
            result = postRequest.execute(new String []{myUrl, params}).get();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void takePictureIntent() throws IOException {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Log.v("Path image", this.getExternalFilesDir(Environment.DIRECTORY_DCIM).getAbsolutePath());
        imageUri = Uri.fromFile(new File(this.getExternalFilesDir(Environment.DIRECTORY_DCIM), "justificatif_" + String.valueOf(System.currentTimeMillis()) + ".jpg"));
        takePictureIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageUri);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        Log.v("Path image", this.getExternalFilesDir(Environment.DIRECTORY_DCIM).getAbsolutePath());
//// Nécessaire pour les versions android API > 23 (à partir de la 7.0
//        String fileName = "justificatif_" + String.valueOf(System.currentTimeMillis());
//        File storageDic = this.getExternalFilesDir(Environment.DIRECTORY_DCIM);
//        File storedImage = File.createTempFile(fileName, ".jpg", storageDic);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            imageUri = FileProvider.getUriForFile(this, "net.mascaron.takepictureapp.fileProvider",
//                    storedImage);
//        } else {
//            imageUri = Uri.fromFile(storedImage);
//        }
//        takePictureIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageUri);
//        takePictureIntent.putExtra("maxPicturePixels", 3840 * 2160);
//        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//            startActivityForResult(takePictureIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                try {
                    retrieveCapturedPicture();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void retrieveCapturedPicture() throws FileNotFoundException {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeFile(imageUri.getPath(), options);
        mImageThumbnail.setImageBitmap(bitmap);
//        // display picture on ImageView or write path of image file into database SQLite
//        InputStream ims = getContentResolver().openInputStream(imageUri);
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
////Bitmap bitmap = BitmapFactory.decodeFile(imageUri.getPath(), options);
//        Bitmap bitmap = BitmapFactory.decodeStream(ims);
//        mImageThumbnail.setImageBitmap(bitmap);
//        mImageThumbnail.setRotation(90);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the MenuActivity; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.add_note) {
            Intent addNoteIntent = new Intent(AddFraisActivity.this, AddFraisActivity.class);
            startActivity(addNoteIntent);

        } else if (id == R.id.list_note) {
            Intent listNoteIntent = new Intent(AddFraisActivity.this, MenuActivity.class);
            startActivity(listNoteIntent);

        } else if (id == R.id.add_client) {

            Intent addClientIntent = new Intent(AddFraisActivity.this, AddClientActivity.class);
            startActivity(addClientIntent);

        } else if (id == R.id.view_stats) {
            Intent graphIntent = new Intent(AddFraisActivity.this, GraphActivity.class);
            startActivity(graphIntent);

        } else if (id == R.id.user_account) {
            Intent accountIntent = new Intent(AddFraisActivity.this, AccountActivity.class);
            startActivity(accountIntent);

        } else if (id == R.id.user_deco) {
            Intent DecoIntent = new Intent(AddFraisActivity.this, LoginActivity.class);
            startActivity(DecoIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
