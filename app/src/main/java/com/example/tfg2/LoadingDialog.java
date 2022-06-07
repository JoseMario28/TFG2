package com.example.tfg2;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

public class LoadingDialog {

    Activity activity;
    AlertDialog alertDialog;
    AlertDialog.Builder builder;

    public LoadingDialog(Activity activity){
        this.activity = activity;
    }

    public void startLoadingDialog(){
        builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading_dialog,null));
        builder.setCancelable(false);

        alertDialog = builder.create();
        alertDialog.show();
    }

    public void dismissDialog(){
        alertDialog.dismiss();

    }
}
