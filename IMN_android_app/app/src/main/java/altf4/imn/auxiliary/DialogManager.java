package altf4.imn.auxiliary;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

import java.util.HashMap;
import java.util.Map;

public class DialogManager {
    /*
    this java class is used to store dialogs which can be imported and
    called easily
     */

    public static void showAlertDialog(Activity a_activity,String msg){
        AlertDialog tempDialog = initDialog_Alert(a_activity,"Alert",msg);
        tempDialog.show();
    }

    public static void showInfoDialog(Activity a_activity,String msg){
        AlertDialog tempDialog = initDialog_Alert(a_activity,"Help",msg);
        tempDialog.show();
    }


    private static AlertDialog initDialog_Alert(Activity MainActiv, String title, String msg){
        AlertDialog out = new AlertDialog.Builder(MainActiv).create();
        out.setTitle(title);
        out.setMessage(msg);
        out.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog,int which){
                        dialog.dismiss();
                    }
                }
        );
        return out;
    }
}

