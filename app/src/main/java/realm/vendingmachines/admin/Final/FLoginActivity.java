package realm.vendingmachines.admin.Final;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.concurrent.atomic.AtomicReference;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;
import realm.vendingmachines.admin.R;

public class FLoginActivity extends AppCompatActivity {


    String appID="authapp-jppdi";
    private String  mail,password;
    private EditText emailtext,passwordtext;
    CardView button;
    TextView textView;
    ProgressBar progressBar;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.final_login);
        emailtext=findViewById(R.id.email);
        passwordtext=findViewById(R.id.password_);
        button=findViewById(R.id.login_button_card_view);

        Realm.init(this); // context, usually an Activity or Application
        App app = new App(new AppConfiguration.Builder(appID)
                .build());

        if(app.currentUser()!=null)
        {
            Intent intent=new Intent(FLoginActivity.this, FinalDashboard.class);
            startActivity(intent);

        }


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    mail = emailtext.getText().toString();
                    password = passwordtext.getText().toString();
                Credentials emailPasswordCredentials = Credentials.emailPassword(mail,password);
                AtomicReference<User> user = new AtomicReference<User>();
                app.loginAsync(emailPasswordCredentials, it -> {
                    if (it.isSuccess()) {
                        Toast.makeText(FLoginActivity.this, "Successful"+app.currentUser().toString(), Toast.LENGTH_SHORT).show();
                        user.set(app.currentUser());
                        Intent intent=new Intent(FLoginActivity.this, FinalDashboard.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(FLoginActivity.this, "Failed"+it.getError(), Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });




    }
}