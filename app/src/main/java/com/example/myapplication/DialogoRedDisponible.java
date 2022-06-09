package com.example.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

public class DialogoRedDisponible extends DialogFragment {

    public interface NoticeDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
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
                .setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // COMPLETAR LA OPERACION GUARDANDO EN LA BASE DE DATOS
                        listener.onDialogPositiveClick(DialogoRedDisponible.this);

                    }
                })
                .setNegativeButton("AGUARDAR", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onDialogNegativeClick(DialogoRedDisponible.this);
                        // NO SUBIR A LA BASE DE DATOS Y VOLVER A INTENTARLO PARA SUBIRLO CUANDO HAYA WIFI
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }


}
