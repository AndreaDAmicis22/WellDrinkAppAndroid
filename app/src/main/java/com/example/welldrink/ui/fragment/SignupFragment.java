package com.example.welldrink.ui.fragment;

import static com.example.welldrink.util.Constants.MINIMUM_PASSWORD_LENGTH;

import android.content.Intent;
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

import com.example.welldrink.R;
import com.example.welldrink.model.Result;
import com.example.welldrink.model.User;
import com.example.welldrink.ui.Activity.FirstStartActivity;
import com.example.welldrink.ui.Activity.MainActivity;
import com.example.welldrink.ui.viewModel.UserViewModel;
import com.google.android.material.snackbar.Snackbar;

public class SignupFragment extends Fragment {

    private UserViewModel userViewModel;

    public SignupFragment() {}

    public static SignupFragment newInstance() { return new SignupFragment(); }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("SignupFragment", "onCreate");
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        userViewModel.setAuthError(false);
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
            String username = ((TextView) view.findViewById(R.id.signup_txtUsername)).getText().toString().trim();
            String email = ((TextView) view.findViewById(R.id.signup_txtEmail)).getText().toString().trim();
            String password = ((TextView) view.findViewById(R.id.signup_txtPsw)).getText().toString().trim();
            String passwordConf = ((TextView) view.findViewById(R.id.signup_txtConfPsw)).getText().toString().trim();
            if(checkData(username, email, password, passwordConf)){
                if(!userViewModel.isAuthError()){
                    Log.d("AUTH", "checkData done");
                    userViewModel.getUserMutableLiveData(email, password, false, username).observe(
                            getViewLifecycleOwner(), result -> {
                                Log.d("AUTH", "observer");
                                if(result.isSuccess()){
                                    Log.d("AUTH", "result.isSuccess()");
                                    User user = ((Result.Success<User>) result).getData();
                                    userViewModel.setAuthError(false);
                                    switchActivities();
                                }else{
                                    userViewModel.setAuthError(true);
                                    Log.d("AUTH", "ERROR in registration");
                                    Snackbar.make(requireActivity().findViewById(android.R.id.content),
                                            ((Result.Error) result).getMessage(),
                                            Snackbar.LENGTH_SHORT).show();
                                }
                            }
                    );
                }else{
                    userViewModel.getUser(email, password, false, username);
                }
            }else{
                Snackbar.make(requireActivity().findViewById(android.R.id.content),
                        getErrorInserction(username, email, password, passwordConf),
                        Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private boolean checkData(String username, String mail, String password, String passwordConf){
        if(password.equals(passwordConf) && isPasswordOk(password)){
            return true;
        }
        return false;
    }

    private String getErrorInserction(String username, String email, String password, String passwordConf){
        if(password.isEmpty() || passwordConf.isEmpty()){
            return "Empty password";
        } else if (!password.equals(passwordConf)) {
            return "Password not matching confermation";
        } else if (password.length() < MINIMUM_PASSWORD_LENGTH) {
            return "Password must be at least " + MINIMUM_PASSWORD_LENGTH + " characters";
        }else if (email.isEmpty()){
            return "Empty mail";
        }else if (username.isEmpty()){
            return "Empty username";
        } else{
            return "Mail error";
        }
    }

    private boolean isPasswordOk(String password){
        return !password.isEmpty() && password.length() >= MINIMUM_PASSWORD_LENGTH;
    }

    private void switchActivities() {
        Intent switchActivityIntent = new Intent(getContext(), FirstStartActivity.class);
        switchActivityIntent.putExtra("prev", false);
        switchActivityIntent.putExtra("forward", true);
        startActivity(switchActivityIntent);
    }
}