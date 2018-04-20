package fr.moralesmarie.spendmanager;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

import fr.moralesmarie.spendmanager.Class.Client;
import fr.moralesmarie.spendmanager.HttpRequest.HttpGetRequest;

public class AddFraisActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private Button btnAddClient;
    private ImageButton imgbtnRest;
    private ImageButton imgBtnPlane;
    private ImageButton imgBtnCar;
    private ImageButton imgBtnTrain;
    private ImageButton imgBtnHotel;
    private ImageButton imgBtnGas;
    private ImageButton imgBtnParking;
    private ImageButton imgBtnTaxi;
    private ImageButton imgBtnBus;
    private RadioButton radBtnRest;
    private RadioButton radBtnPlane;
    private RadioButton radBtnCar;
    private RadioButton radBtnTrain;
    private RadioButton radBtnHotel;
    private RadioButton radBtnGas;
    private RadioButton radBtnParking;
    private RadioButton radBtnTaxi;
    private RadioButton radBtnBus;
    private ArrayList<ImageButton> tblImgBtn;

    Spinner spinner;

    private Calendar mCurrentDate;
    private EditText mDateExpense;

    private TextView textDureeTrajet;
    private EditText dureeTrajet;
    private TextView textVilleDepard;
    private EditText villeDepard;
    private TextView textVilleArrivee;
    private EditText villeArrivee;
    private TextView textDateAller;
    private EditText mDateAller;
    private TextView textDateRetour;
    private EditText mDateRetour;


    private String mont;
    private String days;

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

        String mailuser = null;
        String coorduser = null;
        TextView mail_user;
        TextView user_detail;
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        final View headerLayout = navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(this);
        //recuperation du bundle de l intent dans LoginActivity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            //extraction de la valeur par la clé
            mailuser = extras.getString("mail_extra");
            //recuperation du texteview mail user
            mail_user = (TextView)headerLayout.findViewById(R.id.mail_user);
            mail_user.setText(mailuser);

            //recuperation du textView du nom et prenom
            coorduser = extras.getString("prenom_extra")+ " " +extras.getString("nom_extra");
            //recuperation du texteview mail user
            user_detail = (TextView)headerLayout.findViewById(R.id.user_detail);
            user_detail.setText(coorduser);
        }

        btnAddClient = (Button) findViewById(R.id.btnAddClient);
        btnAddClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AddFraisActivity.this, AddClientActivity.class);
                startActivity(i);
            }
        });

        ArrayList<Client> listClient = listClient();

        //Récupération du Spinner déclaré dans le fichier main.xml de res/layout
        spinner = (Spinner) findViewById(R.id.spinnerClient);
        //Création d'une liste d'élément à mettre dans le Spinner(pour l'exemple)
        List exempleList = new ArrayList();
        exempleList.add("Choisissez un client");

        for (Client leClient : listClient) {
            exempleList.add(leClient.getNom_Client()+" "+leClient.getPrenom_Client());
        }

		/*Le Spinner a besoin d'un adapter pour sa presentation alors on lui passe le context(this) et
                un fichier de presentation par défaut( android.R.layout.simple_spinner_item)
		Avec la liste des elements (exemple) */
        ArrayAdapter adapter = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                exempleList
        );
               /* On definit une présentation du spinner quand il est déroulé         (android.R.layout.simple_spinner_dropdown_item) */
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Enfin on passe l'adapter au Spinner et c'est tout
        spinner.setAdapter(adapter);

        //POP-UP CALENDRIER
        mCurrentDate = Calendar.getInstance();
        final int year = mCurrentDate.get(Calendar.YEAR);
        final int month = mCurrentDate.get(Calendar.MONTH);
        final int day = mCurrentDate.get(Calendar.DAY_OF_MONTH);
        mDateExpense = (EditText) findViewById(R.id.dateExpense);
        mDateAller = (EditText) findViewById(R.id.dateAller);
        mDateRetour = (EditText) findViewById(R.id.dateRetour);
        if(month >= 1 && month <= 9 ){
            mont = "0" + (month+1);
        } else {
            mont = "" + (month+1);
        }
        if(day >= 1 && day <= 9){
            days = "0" + day;
        } else {
            days = "" + day;
        }
        mDateExpense.setText(days + " - " + mont + " - " + year);
        mDateAller.setText(days + " - " + mont + " - " + year);
        mDateRetour.setText(days + " - " + mont + " - " + year);
        mDateExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog mDatePicker = new DatePickerDialog(AddFraisActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        if(month >= 1 && month <= 9 ){
                            mont = "0" + (month+1);
                        } else {
                            mont = "" + (month+1);
                        }
                        if(day >= 1 && day <= 9){
                            days = "0" + day;
                        } else {
                            days = "" + day;
                        }
                        mDateExpense.setText(days + " - " + mont + " - " + year);
                    }
                }, year, month, day);
                mDatePicker.show();
            }
        });
        mDateAller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog mDatePicker = new DatePickerDialog(AddFraisActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        if(month >= 1 && month <= 9 ){
                            mont = "0" + (month+1);
                        } else {
                            mont = "" + (month+1);
                        }
                        if(day >= 1 && day <= 9){
                            days = "0" + day;
                        } else {
                            days = "" + day;
                        }
                        mDateExpense.setText(days + " - " + mont + " - " + year);
                    }
                }, year, month, day);
                mDatePicker.show();
            }
        });
        mDateRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog mDatePicker = new DatePickerDialog(AddFraisActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        if(month >= 1 && month <= 9 ){
                            mont = "0" + (month+1);
                        } else {
                            mont = "" + (month+1);
                        }
                        if(day >= 1 && day <= 9){
                            days = "0" + day;
                        } else {
                            days = "" + day;
                        }
                        mDateExpense.setText(days + " - " + mont + " - " + year);
                    }
                }, year, month, day);
                mDatePicker.show();
            }
        });

        imgBtnBus = (ImageButton) findViewById(R.id.imageButtonBus);
        imgBtnCar = (ImageButton) findViewById(R.id.imageButtonCar);
        imgBtnGas = (ImageButton) findViewById(R.id.imageButtonGas);
        imgBtnHotel = (ImageButton) findViewById(R.id.imageButtonHotel);
        imgBtnParking = (ImageButton) findViewById(R.id.imageButtonParking);
        imgBtnPlane = (ImageButton) findViewById(R.id.imageButtonPlane);
        imgbtnRest = (ImageButton) findViewById(R.id.imageButtonRestaurant);
        imgBtnTaxi = (ImageButton) findViewById(R.id.imageButtonTaxi);
        imgBtnTrain = (ImageButton) findViewById(R.id.imageButtonTrain);

        radBtnBus = (RadioButton) findViewById(R.id.radioBus);
        radBtnCar = (RadioButton) findViewById(R.id.radioCar);
        radBtnGas = (RadioButton) findViewById(R.id.radioGas);
        radBtnHotel = (RadioButton) findViewById(R.id.radioHotel);
        radBtnParking = (RadioButton) findViewById(R.id.radioParking);
        radBtnPlane = (RadioButton) findViewById(R.id.radioPlane);
        radBtnRest = (RadioButton) findViewById(R.id.radioRestauranrt);
        radBtnTaxi = (RadioButton) findViewById(R.id.radioTaxi);
        radBtnTrain = (RadioButton) findViewById(R.id.radioTrain);



        tblImgBtn = new ArrayList<ImageButton>();
        tblImgBtn.add(imgBtnBus);
        tblImgBtn.add(imgBtnCar);
        tblImgBtn.add(imgBtnGas);
        tblImgBtn.add(imgBtnHotel);
        tblImgBtn.add(imgBtnParking);
        tblImgBtn.add(imgBtnPlane);
        tblImgBtn.add(imgbtnRest);
        tblImgBtn.add(imgBtnTaxi);
        tblImgBtn.add(imgBtnTrain);

    }

    public ArrayList<Client> listClient(){
        ArrayList<Client> listClient = new ArrayList<Client>();
        String result = "";

        //String myUrl = "http://127.0.0.1:8080/REST-API-SY4/public/client";
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
        System.out.println("Retour HTTPPostRequest : " + result );
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
//            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//                startActivityForResult(takePictureIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
//            }
            Intent addNoteIntent = new Intent(AddFraisActivity.this, AddFraisActivity.class);
            startActivity(addNoteIntent);

        } else if (id == R.id.list_note) {
            Intent listNoteIntent = new Intent(AddFraisActivity.this, MenuActivity.class);

        } else if (id == R.id.add_client) {

            Intent addClientIntent = new Intent(AddFraisActivity.this, AddClientActivity.class);
            startActivity(addClientIntent);

        } else if (id == R.id.view_stats) {

        } else if (id == R.id.user_account) {

        } else if (id == R.id.user_deco) {
            Intent DecoIntent = new Intent(AddFraisActivity.this, LoginActivity.class);
            startActivity(DecoIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
