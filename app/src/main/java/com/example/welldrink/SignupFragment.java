package com.example.welldrink;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.welldrink.data.repository.user.IUserRepository;
import com.example.welldrink.model.Result;
import com.example.welldrink.model.User;
import com.example.welldrink.ui.viewModel.UserViewModel;
import com.example.welldrink.ui.viewModel.UserViewModelFactory;
import com.example.welldrink.util.ServiceLocator;

public class SignupFragment extends Fragment {

    private UserViewModel userViewModel;

    public SignupFragment() {

    }

    public static SignupFragment newInstance() { return new SignupFragment(); }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("SignupFragment", "onCreate");
        IUserRepository userRepository = ServiceLocator.getInstance().getUserRepository();
        userViewModel = new ViewModelProvider(
                requireActivity(),
                new UserViewModelFactory(userRepository)).get(UserViewModel.class);
        Log.d("SignupFragment", "onCreateEnd");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button button = view.findViewById(R.id.signup_btnLogin);
        button.setOnClickListener(view1 -> {
            Navigation.findNavController(requireView()).navigate(R.id.action_signupFragment_to_loginFragment);
        });
        Button registration = view.findViewById(R.id.signup_btnSignup);
        registration.setOnClickListener(v -> {
            String username = ((TextView) view.findViewById(R.id.signup_txtUsername)).getText().toString();
            String email = ((TextView) view.findViewById(R.id.signup_txtEmail)).getText().toString();
            String password = ((TextView) view.findViewById(R.id.signup_txtPsw)).getText().toString();
            String passwordConf = ((TextView) view.findViewById(R.id.signup_txtConfPsw)).getText().toString();
            if(checkData(username, email, password, passwordConf)){
                Log.d("AUTH", "checkData done");

                userViewModel.getUserMutableLiveData(email, password, false).observe(
                    getViewLifecycleOwner(), result -> {
                        if(result.isSuccess()){
                            Log.d("AUTH", "result.isSuccess()");
                            User user = ((Result.Success<User>) result).getData();
                        }
                    }
                );
            }
        });
    }

    private boolean checkData(String username, String mail, String password, String passwordConf){
        if(password.equals(passwordConf)){
            return true;
        }
        return false;
    }
}