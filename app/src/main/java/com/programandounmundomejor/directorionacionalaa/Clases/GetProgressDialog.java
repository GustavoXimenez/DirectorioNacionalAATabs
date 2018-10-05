package com.programandounmundomejor.directorionacionalaa.Clases;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.WindowManager;

public class GetProgressDialog {
    private static ProgressDialog getProgressDialog;

    public void startProgressDialog(final Activity mContext)
    {
        getProgressDialog = new ProgressDialog(mContext);
        getProgressDialog.setMessage("Por favor espere...");
        getProgressDialog.show();
        getProgressDialog.setCanceledOnTouchOutside(false);
        mContext.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        getProgressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event)
            {
                if (keyCode == KeyEvent.KEYCODE_BACK)
                {
                    //the user pressed back button - do whatever here
                    //normally you dismiss the dialog like dialog.dismiss();
                    mContext.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                }
                return false;
            }
        });
    }

    public void stopProgressDialog(Activity mContext)
    {
        getProgressDialog.dismiss();
        mContext.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }
}
