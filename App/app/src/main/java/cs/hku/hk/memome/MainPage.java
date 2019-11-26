package cs.hku.hk.memome;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * The main activity which contains 5 fragments. Among the five, bottom navigation are used to conduct
 * the switching. Sliding is forbidden.
 */
public class MainPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle extras = getIntent().getExtras();
        String email = extras.getString("email");
        Toast.makeText(MainPage.this,"user with email " + email + " successfully log in", Toast.LENGTH_LONG).show();
        BottomNavigationView navView = findViewById(R.id.nav_view);
        Bundle bundle = new Bundle();
        bundle.putString("email", email);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_todo, R.id.navigation_community, R.id.navigation_plus, R.id.navigation_history, R.id.navigation_me)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        navController.setGraph(navController.getGraph(), bundle);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
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
