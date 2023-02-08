package com.example.welldrink.ui.fragment;

import static com.example.welldrink.util.ErrorSnackbars.handleRegistrationError;

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
import com.example.welldrink.data.repository.user.IUserRepository;
import com.example.welldrink.model.Result;
import com.example.welldrink.model.User;
import com.example.welldrink.ui.Activity.MainActivity;
import com.example.welldrink.ui.viewModel.UserViewModel;
import com.example.welldrink.ui.viewModel.UserViewModelFactory;
import com.example.welldrink.util.ServiceLocator;
import com.google.android.material.snackbar.Snackbar;

public class LoginFragment extends Fragment {

    private UserViewModel userViewModel;

    public LoginFragment() {
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IUserRepository userRepository = ServiceLocator.getInstance().getUserRepository();
        userViewModel = new ViewModelProvider(
                requireActivity(),
                new UserViewModelFactory(userRepository)).get(UserViewModel.class);
        if (userViewModel.getLoggedUser() != null) {
            this.switchActivities();
        }
        Log.d("LogIn", String.valueOf(userViewModel.getLoggedUser()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        Button button = view.findViewById(R.id.login_btnSignUp);
        button.setOnClickListener(view1 -> Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_signupFragment));
        return view;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button loginButton = view.findViewById(R.id.login_btnSignup);
        loginButton.setOnClickListener(v -> {
            String email = ((TextView) view.findViewById(R.id.login_txtEmail)).getText().toString().trim();
            String password = ((TextView) view.findViewById(R.id.login_txtPsw)).getText().toString().trim();
            if (userViewModel.isAuthError()) {
                userViewModel.getUser(email, password, true, "");
            } else {
                if (checkData(email, password)) {
                    userViewModel.getUserMutableLiveData(email, password, true, "").observe(
                            getViewLifecycleOwner(), result -> {
                                Log.d("AUTH", "observer");
                                if (result.isSuccess()) {
                                    Log.d("AUTH", "result.isSuccess()");
                                    User user = ((Result.Success<User>) result).getData();
                                    userViewModel.setAuthError(false);
                                    Log.d("AUTH", "Login with user: " + user.toString());
                                    switchActivities();
                                } else {
                                    Log.e("AUTH", "ERROR login result.isSuccess()");
                                    userViewModel.setAuthError(true);
                                    handleRegistrationError(requireActivity().findViewById(android.R.id.content),
                                            ((Result.Error) result).getMessage());
                                }
                            }
                    );
                } else {
                    Log.e("AUTH", "data empty");
                }
            }
        });
    }

    private boolean checkData(String email, String password) {
        return !email.isEmpty() && !password.isEmpty();
    }

    private void switchActivities() {
        Intent switchActivityIntent = new Intent(getContext(), MainActivity.class);
        startActivity(switchActivityIntent);
    }

}