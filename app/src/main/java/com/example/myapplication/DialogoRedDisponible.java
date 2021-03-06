package com.example.myapplication;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.fragment.app.DialogFragment;

import com.example.myapplication.controller.NetworkController;
import com.example.myapplication.controller.RecipesController;

import java.io.FileOutputStream;

public class DialogoRedDisponible extends DialogFragment {



    public interface NoticeDialogListener {
        public void enDialogoPositivoClick(DialogFragment dialog);
        public void enDialogoNegativoClick(DialogFragment dialog);
    }

    // Use this instance of the interface to deliver action events
    NoticeDialogListener listener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (NoticeDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(//MainActivity.toString()
                    //+ " must implement NoticeDialogListener"
                     );
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.body_dialogo_red);
        builder.setTitle(R.string.titulo_dialogo_red)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // COMPLETAR LA OPERACION GUARDANDO EN LA BASE DE DATOS
                        listener.enDialogoPositivoClick(DialogoRedDisponible.this);
                    }
                })
                .setNegativeButton("Aguardar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.enDialogoNegativoClick(DialogoRedDisponible.this);
                        // NO SUBIR A LA BASE DE DATOS Y VOLVER A INTENTARLO PARA SUBIRLO CUANDO HAYA WIFI. GUARDARLO EN ALMACENAMIENTO INTERNO
                    }
                });
        // Create the AlertDialog object and return it
        // Create the AlertDialog object and return it
        AlertDialog alert = builder.create();
        alert.show();
        Button nbutton = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
        nbutton.setBackgroundColor(getResources().getColor(R.color.white));
        nbutton.setTextColor(Color.GRAY);
        nbutton.setAllCaps(false);

        Button pbutton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
        pbutton.setBackgroundColor(getResources().getColor(R.color.color_boton));
        pbutton.setTextColor(getResources().getColor(R.color.white));
        pbutton.setAllCaps(false);
        LinearLayout parent = (LinearLayout) pbutton.getParent();
        parent.setGravity(Gravity.CENTER_HORIZONTAL);
        View leftSpacer = parent.getChildAt(1);
        leftSpacer.setVisibility(View.GONE);
        return alert;
    }




}
