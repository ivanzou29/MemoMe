package cs.hku.hk.memome;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
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

import cs.hku.hk.memome.jdbc.UserJdbcDao;
import cs.hku.hk.memome.model.User;

/**
 * The activity for login / register / logout functionality.
 */
public class MainActivity extends AppCompatActivity {

    TextView email;
    TextView password;
    Button logIn;
    Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);
        boolean loggingOut = false;
        try {
            Bundle extras = getIntent().getExtras();
            loggingOut = extras.getBoolean("loggingOut");
        } catch (Exception e) {

        }
        email = findViewById(R.id.get_email);
        password = findViewById(R.id.get_password);
        if (!loggingOut) {
            SharedPreferences sp = getSharedPreferences("config", 0);
            String emailSP = sp.getString("email", "");
            String passcodeSP = sp.getString("passcode", "");
            email.setText(emailSP);
            password.setText(passcodeSP);
            Boolean exist = Boolean.TRUE;
            Boolean correct = Boolean.TRUE;
            UserJdbcDao userJdbcDao = new UserJdbcDao();
            User user = userJdbcDao.getUserByEmail(emailSP);
            if (user == null) {
                exist = Boolean.FALSE;
            } else {
                if (!user.getPasscode().equals(passcodeSP)) {
                    correct = Boolean.FALSE;
                }
            }

            if (exist && correct) {
                Intent myIntent = new Intent(MainActivity.this, MainPage.class);
                myIntent.putExtra("email", emailSP);
                startActivity(myIntent);
            } else {
                Toast.makeText(MainActivity.this, "Please enter your email and password.", Toast.LENGTH_LONG).show();
            }
        }

        logIn = findViewById(R.id.log_in_button);
        logIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String eStr = email.getText().toString();
                String pStr = password.getText().toString();
                Boolean exist = Boolean.TRUE;
                Boolean correct = Boolean.TRUE;
                //TODO:check whether email exists and whether the password is correct
                UserJdbcDao userJdbcDao = new UserJdbcDao();
                User user = userJdbcDao.getUserByEmail(eStr);
                if (user == null) {
                    exist = Boolean.FALSE;
                } else {
                    if (!user.getPasscode().equals(pStr)) {
                        correct = Boolean.FALSE;
                    }
                }

                if (!exist) {
                    Toast.makeText(MainActivity.this, "unidentified email, please sign up first.", Toast.LENGTH_LONG).show();
                    //Todo: send an intent to sign up
                } else {
                    if (!correct) {
                        Toast.makeText(MainActivity.this, "wrong password", Toast.LENGTH_LONG).show();
                    } else {
                        SharedPreferences sharedPreferences = getSharedPreferences("config", 0);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("email", eStr);
                        editor.putString("passcode", pStr);
                        editor.commit();
                        Toast.makeText(MainActivity.this, "login status saved", Toast.LENGTH_LONG).show();
                        Intent myIntent = new Intent(v.getContext(), MainPage.class);
                        myIntent.putExtra("email", eStr);
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent home = new Intent(Intent.ACTION_MAIN);
            home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            home.addCategory(Intent.CATEGORY_HOME);
            startActivity(home);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
