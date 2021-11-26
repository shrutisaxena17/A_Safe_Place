package com.example.asafeplace;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

public class MainActivity extends AppCompatActivity {
    //initialise variable
    MeowBottomNavigation bottomNavigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Assign variable
        bottomNavigation = findViewById(R.id.bottom_navigation);
        //Add menu items
        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.ic_home) );
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.ic_profile) );
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.ic_notification) );
        bottomNavigation.add(new MeowBottomNavigation.Model(4, R.drawable.ic_search));

        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                // initialise fragment
                Fragment fragment= null;
                //check condition
                switch (item.getId()){
                    case 1:
                           //When id is 1
                            fragment = new HomeFragment();
                            break;

                    case 2:
                        //when id is 2
                        fragment= new ProfileFragment();
                        break;

                    case 3:
                        //when id is 3
                        fragment= new NotificationFragment();
                        break;

                    case 4:
                        //when id is 4
                        fragment = new SearchFragment();
                }
                loadFragment(fragment);
            }
        });
        //set home fragment initially selected
        bottomNavigation.show(1,true);

        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
                //Display toast
                Toast.makeText(getApplicationContext(), "You clicked" +item.getId(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        //Replace Fragment
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout,fragment)
                .commit();
    }
}