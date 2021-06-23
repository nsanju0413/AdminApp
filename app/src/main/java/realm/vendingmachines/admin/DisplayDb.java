package realm.vendingmachines.admin;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.bson.Document;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.RealmResultTask;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;
import io.realm.mongodb.mongo.iterable.MongoCursor;

public class DisplayDb extends AppCompatActivity {

    TextView t1,t2,t3,t4,t5,t6,t7,t8;
    private App app;
    String Appid="authapp-jppdi";
    //String Appid = "application-0-fsuyc";
    MongoClient mongoClient;
    MongoDatabase mongoDatabase;
    EditText editText;
    String data="";
    User user;
    MongoCollection<Document> mongoCollection;
    ArrayList<String> strings = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_db);

        t1=findViewById(R.id.t1);
        t2=findViewById(R.id.t2);
        t3=findViewById(R.id.t3);
        t4=findViewById(R.id.t4);
        t5=findViewById(R.id.t5);
        t6=findViewById(R.id.t6);
        t7=findViewById(R.id.t7);
        t8=findViewById(R.id.t8);

       Realm.init(this); // context, usually an Activity or Application
        app = new App(new AppConfiguration.Builder(Appid).build());
        Credentials anonymousCredentials = Credentials.anonymous();
        AtomicReference<User> user1 = new AtomicReference<User>();
        t8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app.loginAsync(anonymousCredentials, it -> {
                    if (it.isSuccess()) {
                        Log.v("AUTH", "Successfully authenticated anonymously.");
                        user1.set(app.currentUser());
                    } else {
                        Log.e("AUTH", it.getError().toString());
                    }
                });

            }
        });

t7.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        User user = app.currentUser();
        MongoClient mongoClient = user.getMongoClient("mongodb-atlas");
        MongoDatabase mongoDatabase = mongoClient.getDatabase("test");
        mongoCollection=mongoDatabase.getCollection("test");

        Document queryFilter  = new Document().append("data1","2580");
        RealmResultTask<MongoCursor<Document>> findTask = mongoCollection.find(queryFilter).iterator();
        findTask.getAsync(task->{
            if(task.isSuccess())
            {
                MongoCursor<Document> results = task.get();
                if(!results.hasNext())
                {
                    Log.v("Result","Couldnt Find");
                }
                while(results.hasNext())
                {
                    Document currentDoc = results.next();
                    strings = (ArrayList<String>) currentDoc.get("data1");
                    if(strings == null)
                    {
                        Log.v("FindTask","Strings has size 0");
                    }
                    else
                    {
                        int i=0;
                        for(i=0;i<strings.size();i++)
                        {
                            if(data == null)
                            {
                                data = "";
                                data = data + " & " + strings.get(i);
                                t6.setText(data);
                            }
                            else
                            {
                                data = data + " & " + strings.get(i);
                                t6.setText(data);
                            }
                        }
                    }
                }
            }
            else
            {
                Log.v("Task Error",task.getError().toString());
            }
        });
    }
});



    }

}
