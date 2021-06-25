package realm.vendingmachines.admin;

import io.realm.Realm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;
import realm.vendingmachines.admin.Final.FinalUploadData;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import java.util.concurrent.atomic.AtomicReference;

public class LoginActivity extends AppCompatActivity {
    //String appID="application-0-fsuyc";
    //String appID="authapp-jppdi";
    String appID="testapp-hsrwy";

    private String  mail,password;
    private TextInputLayout emailtext,passwordtext;
    Button button;
    TextView textView;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Realm.init(this); // context, usually an Activity or Application

        emailtext=findViewById(R.id.username);
        passwordtext=findViewById(R.id.password);
        button=findViewById(R.id.login);
        textView=findViewById(R.id.user);



        App app = new App(new AppConfiguration.Builder(appID)
                .build());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mail=emailtext.getEditText().getText().toString();
                password=passwordtext.getEditText().getText().toString();
                Credentials emailPasswordCredentials = Credentials.emailPassword(mail,password);
                AtomicReference<User> user = new AtomicReference<User>();
                app.loginAsync(emailPasswordCredentials, it -> {
                    if (it.isSuccess()) {
                        Toast.makeText(LoginActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                        //user.set(app.currentUser());
                        Intent intent=new Intent(LoginActivity.this, FinalUploadData.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this, "Failed"+it.getError(), Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });

    }
}
