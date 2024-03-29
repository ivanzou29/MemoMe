package cs.hku.hk.memome;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

import cs.hku.hk.memome.ui.ProcessingDialog;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
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

    ProcessingDialog processing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
//                        .setDefaultFontPath("font/cute.ttf")
//                        .setFontAttrId(R.attr.fontPath)
//                        .build()
//        );
        setContentView(R.layout.log_in);
        boolean loggingOut = false;
        try {
            Bundle extras = getIntent().getExtras();
            loggingOut = extras.getBoolean("loggingOut");
        } catch (Exception e) {

        }
        email = findViewById(R.id.get_email);
        password = findViewById(R.id.get_password);

        View view = View.inflate(this,R.layout.log_in, null);
        processing = new ProcessingDialog(view, R.string.login_waiting);

        if (isConnected()) {
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
        } else {
            Toast.makeText(MainActivity.this, "Please ensure your phone is connected to the Internet!", Toast.LENGTH_LONG).show();
        }

        logIn = findViewById(R.id.log_in_button);
        logIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {

                processing.show();

                if (isConnected()) {

                    findViewById(R.id.login_root).post(new Runnable()
                    {
                        String eStr = email.getText().toString();
                        String pStr = password.getText().toString();
                        Boolean exist = Boolean.TRUE;
                        Boolean correct = Boolean.TRUE;

                        @Override
                        public void run()
                        {
                            UserJdbcDao userJdbcDao = new UserJdbcDao();
                            User user = userJdbcDao.getUserByEmail(eStr);
                            processing.dismiss();
                            if (user == null) {
                                exist = Boolean.FALSE;
                            } else {
                                if (!user.getPasscode().equals(pStr)) {
                                    correct = Boolean.FALSE;
                                }
                            }

                            if (!exist) {
                                Toast.makeText(MainActivity.this, "Unidentified email, please sign up first.", Toast.LENGTH_LONG).show();
                            } else {
                                if (!correct) {
                                    Toast.makeText(MainActivity.this, "Wrong password!", Toast.LENGTH_LONG).show();
                                } else {
                                    SharedPreferences sharedPreferences = getSharedPreferences("config", 0);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("email", eStr);
                                    editor.putString("passcode", pStr);
                                    editor.apply();
                                    Toast.makeText(MainActivity.this, "Login status saved!", Toast.LENGTH_LONG).show();
                                    Intent myIntent = new Intent(v.getContext(), MainPage.class);
                                    myIntent.putExtra("email", eStr);
                                    startActivity(myIntent);
                                }
                            }
                        }
                    });
                } else {
                    Toast.makeText(MainActivity.this, "Please ensure your phone is connected to the Internet!", Toast.LENGTH_LONG).show();
                }

            }
        });

        signUp = findViewById(R.id.sign_up_button);
        signUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (isConnected()) {
                    Intent myIntent = new Intent(v.getContext(), SignUpActivity.class);
                    startActivity(myIntent);
                } else {
                    Toast.makeText(MainActivity.this, "Please ensure your phone is connected to the Internet!", Toast.LENGTH_LONG).show();
                }
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

    public boolean isConnected() {
        try {
            String command = "ping -c 1 baidu.com";
            return (Runtime.getRuntime().exec(command).waitFor() == 0);
        } catch (Exception e) {
            return false;
        }
    }
}
