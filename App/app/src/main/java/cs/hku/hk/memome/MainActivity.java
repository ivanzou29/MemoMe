package cs.hku.hk.memome;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class MainActivity extends AppCompatActivity{

    TextView email;
    TextView password;
    Button logIn;
    Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
//                        .setDefaultFontPath("font/cute.ttf")
//                        .setFontAttrId(R.attr.fontPath)
//                        .build()
//        );
        setContentView(R.layout.log_in);
        logIn =findViewById(R.id.log_in_button);
        logIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                email = findViewById(R.id.get_email);
                password = findViewById(R.id.get_password);
                String eStr = email.getText().toString();
                String pStr = password.getText().toString();
                Boolean exist = Boolean.TRUE;
                Boolean correct = Boolean.TRUE;
                //TODO:check whether email exists and whether the password is correct
                if (!exist) {
                    Toast.makeText(MainActivity.this,"unidentified email, please log in first.", Toast.LENGTH_LONG).show();
                    //Todo: send an intent to sign up
                } else {
                    if (!correct) {
                        Toast.makeText(MainActivity.this,"wrong password", Toast.LENGTH_LONG).show();
                    } else {
                        Intent myIntent = new Intent(v.getContext(), MainPage.class);
                        myIntent.putExtra("email",eStr);
                        startActivity(myIntent);
                    }
                }

            }
        });

        signUp = findViewById(R.id.sign_up_button);
        signUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), SignUpActivity.class);
                startActivity(myIntent);
            }
        });
    }
}
