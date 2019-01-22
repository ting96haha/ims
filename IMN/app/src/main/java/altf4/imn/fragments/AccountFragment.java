package altf4.imn.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import altf4.imn.R;


public class AccountFragment extends Fragment {

    private static final String logtag = "IMN_DEBUG_CHANNEL";
    EditText SIDField;
    EditText PWField;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments

        View out = inflater.inflate(R.layout.fragment_login,container,false);
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences( getContext() );
        String sid = prefs.getString("mmls_id","MMLS Student ID");//default 15 minutes

        SIDField = out.findViewById(R.id.txtId);
        if(sid != null){
            SIDField.setText(sid);
        }

        PWField = out.findViewById(R.id.txtPassword);
        Button SubmitBtn = out.findViewById(R.id.btnLogin);
        SubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor pEdit = prefs.edit();

                pEdit.putString("mmls_id",SIDField.getText().toString());
                pEdit.putString("mmls_pw",PWField.getText().toString());
                pEdit.commit();

                //switch back to NewPost
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, new NewPostFragment());
                ft.commit();
            }
        });
        return out;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Login credentials");
    }
}
