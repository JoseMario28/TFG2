package com.example.tfg2.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.tfg2.fragments.LoginFragment;
import com.example.tfg2.fragments.RegisterFragment;

public class AdapterFragment extends FragmentStateAdapter {

    public AdapterFragment(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch(position){
            case 0: return new LoginFragment();
            default: return new RegisterFragment();

        }
    }


    @Override
    public int getItemCount() {
        return 2;
    }
}
