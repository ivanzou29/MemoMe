package cs.hku.hk.memome;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import cs.hku.hk.memome.jdbc.OwnJdbcDao;
import cs.hku.hk.memome.jdbc.UserJdbcDao;
import cs.hku.hk.memome.model.Own;
import cs.hku.hk.memome.model.User;

/**
 * This is the activity for sign up. Being created when some one click the "register" button in the
 * main activity (login page).
 */
public class SignUpActivity extends AppCompatActivity {

    Button confirm;
    TextView email_first;
    TextView email_second;
    TextView password_first;
    TextView password_second;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        confirm = findViewById(R.id.confirm_sign_up);
        confirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                email_first = findViewById(R.id.email_first);
                email_second = findViewById(R.id.email_second);
                password_first = findViewById(R.id.password_first);
                password_second = findViewById(R.id.password_second);
                String efStr = email_first.getText().toString();
                String esStr = email_second.getText().toString();
                String psStr = password_second.getText().toString();
                String pfStr = password_first.getText().toString();

                if (!efStr.equals(esStr)) {
                    Toast.makeText(SignUpActivity.this, "input the same email", Toast.LENGTH_LONG).show();
                } else {
                    if (!pfStr.equals(psStr)) {
                        Toast.makeText(SignUpActivity.this, "input the same password", Toast.LENGTH_LONG).show();
                    } else {
                        User user = new User();
                        user.setEmail(efStr);
                        user.setPasscode(pfStr);
                        user.setCoin(10);
                        user.setUsername("me!");
                        String s = "nothing";
                        byte[] b = s.getBytes();
                        user.setProfilePhoto(b);

                        UserJdbcDao userJdbcDao = new UserJdbcDao();
                        userJdbcDao.insertUser(user);

                        OwnJdbcDao ownJdbcDao = new OwnJdbcDao();
                        ownJdbcDao.insertGiftOwnership(new Own(efStr, "Flower", 0));
                        ownJdbcDao.insertGiftOwnership(new Own(efStr, "Cake", 0));
                        ownJdbcDao.insertGiftOwnership(new Own(efStr, "Teddy Bear", 0));

                        SharedPreferences sharedPreferences = getSharedPreferences("config", 0);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("email", efStr);
                        editor.putString("passcode", pfStr);
                        editor.commit();
                        Toast.makeText(SignUpActivity.this, "login status saved", Toast.LENGTH_LONG).show();

                        Intent myIntent = new Intent(v.getContext(), MainPage.class);
                        myIntent.putExtra("email",efStr);
                        startActivity(myIntent);
                    }
                }
            }
        });
    }
}
