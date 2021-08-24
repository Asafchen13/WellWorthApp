package com.example.loginregisterfirebase.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.loginregisterfirebase.R;
import com.example.loginregisterfirebase.managers.DatabaseManager;

public class ChangePasswordDialog extends DialogFragment {

    EditText old_password;
    EditText new_password;
    EditText new_password_repeat;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        View dialogView = inflater.inflate(R.layout.dialog_change_password, null);
        builder.setView(dialogView);

        old_password = dialogView.findViewById(R.id.old_password);
        new_password = dialogView.findViewById(R.id.new_password_repeat);
        new_password_repeat = dialogView.findViewById(R.id.new_password_repeat);

        builder.setTitle(R.string.change_password)
                .setPositiveButton(R.string.done_btn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String old_p = old_password.getText().toString();
                        String new_p = new_password.getText().toString();
                        String r_new_p = new_password_repeat.getText().toString();
                        String toast_message;

                        if (old_p.isEmpty()||new_p.isEmpty()||r_new_p.isEmpty()) {
                            toast_message = "Must fill-in all fields";
                        } else if (old_p.length() < 6||new_p.length() < 6||r_new_p.length() < 6) {
                            toast_message = "Password must include at least 6 characters";
                        }else if(!new_p.equals(r_new_p)) {
                            toast_message = "New passwords are not the same";
                        } else if (old_p.equals(new_p)) {
                            toast_message = "New password must be different from old password";
                        } else {
                            toast_message = "Password changed successfully";

                            DatabaseManager.getInstance().updatePassword(new_p);
                        }

                        Toast.makeText(getContext(), toast_message, Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton(R.string.cancel_btn, (dialog, which) -> ChangePasswordDialog.this.getDialog().cancel());


        return builder.create();
    }
}
