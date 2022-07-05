package com.example.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.fragment.app.DialogFragment;

import com.example.myapplication.controller.NetworkController;

public class DialogoAlertaRed extends DialogFragment {
    private NetworkController controlador_red = NetworkController.getInstancia();

    public interface AvisarAlertaDialogListener {
        public void onClickPositivo(DialogFragment dialog);
        public void onClickNegativo(DialogFragment dialog);
    }

    AvisarAlertaDialogListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (AvisarAlertaDialogListener) context;
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
        //Context context = builder.getContext();
        builder.setMessage(R.string.body_alerta_red);
        builder.setTitle(R.string.titulo_dialogo_red)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onClickPositivo(DialogoAlertaRed.this);


                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onClickNegativo(DialogoAlertaRed.this);
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
