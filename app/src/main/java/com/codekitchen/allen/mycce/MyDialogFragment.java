package com.codekitchen.allen.mycce;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;



public class MyDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        String result = getArguments().getString("result");

        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
//        dialog.setIcon(R.drawable.bells);
        dialog.setTitle(result);
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // dosomething
//                getActivity().onOptionsItemSelected();
            }
        });
        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // dosomething
            }
        });

        return dialog.create();
    }

}
