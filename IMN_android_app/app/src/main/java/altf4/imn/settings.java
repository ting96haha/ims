package altf4.imn;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class settings extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        Spinner spn_Update = findViewById(R.id.spnUpdate);
        ArrayAdapter<CharSequence> adp_Update = ArrayAdapter.createFromResource(this, R.array.frequency, android.R.layout.simple_spinner_item);
        adp_Update.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_Update.setAdapter(adp_Update);
        spn_Update.setOnItemSelectedListener(this);

        Spinner spn_Clear = findViewById(R.id.spnClear);
        ArrayAdapter<CharSequence> adp_Clear = ArrayAdapter.createFromResource(this, R.array.clear, android.R.layout.simple_spinner_item);
        adp_Clear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_Clear.setAdapter(adp_Update);
        spn_Clear.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView <?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView <?> parent) {

    }
}
