package edu.cs.uga.roommatesshopping;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import edu.cs.uga.roommatesshopping.databinding.ActivityMainBinding;

// This class facilitates the Navigation component
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NavController controller = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(binding.bottomNav, controller);

        /*
            DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);

            AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph())
                .setDrawerLayout(drawerLayout)
                .build();
         */
    }

}
