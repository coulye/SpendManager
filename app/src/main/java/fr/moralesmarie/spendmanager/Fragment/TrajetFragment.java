package fr.moralesmarie.spendmanager.Fragment;

import android.app.DatePickerDialog;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

import fr.moralesmarie.spendmanager.AddFraisActivity;
import fr.moralesmarie.spendmanager.R;

public class TrajetFragment extends Fragment {

    private Calendar mCurrentDate;
    private String mDate;
    private String months;
    private String days;
    private String years;

    private EditText dureeTrajet;
    private EditText villeDepard;
    private EditText villeArrivee;
    private EditText mDateAller;
    private String SQLDateAller;
    private EditText mDateRetour;
    private String SQLDateRetour;
    private EditText km;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.trajet_fragment, container, false);
        //Instancier vos composants graphique ici (faîtes vos findViewById)
        return view; }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //POP-UP CALENDRIER
        mCurrentDate = Calendar.getInstance();
        final int year = mCurrentDate.get(Calendar.YEAR);
        final int month = mCurrentDate.get(Calendar.MONTH);
        final int day = mCurrentDate.get(Calendar.DAY_OF_MONTH);
        mDateAller = (EditText) view.findViewById(R.id.dateAller);
        mDateRetour = (EditText) view.findViewById(R.id.dateRetour);
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
        years = String.valueOf(year);
        mDateAller.setText(days + " - " + months + " - " + year);
        mDateRetour.setText(days + " - " + months + " - " + year);
        mDate = year + "-" + months + "-" + days;
        SQLDateAller = year+"-"+months+"-"+days;
        SQLDateRetour = year+"-"+months+"-"+days;
        mDateAller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog mDatePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
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
                        mDateAller.setText(days + " - " + months + " - " + year);
                        SQLDateAller = year+"-"+months+"-"+days;
                    }
                }, year, month, day);
                mDatePicker.show();
            }
        });
        mDateRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog mDatePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
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
                        mDateRetour.setText(days + " - " + months + " - " + year);
                        SQLDateRetour = year+"-"+months+"-"+days;
                    }
                }, year, month, day);
                mDatePicker.show();
            }
        });

        dureeTrajet = (EditText) view.findViewById(R.id.dureeTrajet);
        villeDepard = (EditText) view.findViewById(R.id.villeDepart);
        villeArrivee = (EditText) view.findViewById(R.id.villeArrivee);
        km = (EditText) view.findViewById(R.id.editKilometre);
    }

    public String getDureeTrajet() {
        return dureeTrajet.getText().toString();
    }
    public String getVilleDepard() {
        return villeDepard.getText().toString();
    }
    public String getVilleArrivee() {
        return villeArrivee.getText().toString();
    }
    public String getSQLDateAller() {
        return SQLDateAller;
    }
    public String getSQLDateRetour() {
        return SQLDateRetour;
    }
    public Float getKm() {
        return Float.parseFloat(km.getText().toString());
    }
}