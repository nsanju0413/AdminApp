package realm.vendingmachines.admin.Final;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.bson.Document;

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
import realm.vendingmachines.admin.R;

public class Query_Filter extends AppCompatActivity {


    TextView t1,t2,t3,t4,t5,t6,t8;
    Button t7;
    private App app;
    String Appid="testapp-hsrwy";
    //String Appid="authapp-jppdi";
    //String Appid = "application-0-fsuyc";
    MongoClient mongoClient;
    MongoDatabase mongoDatabase;
    EditText editText;
    String[] data;
    User user;
    MongoCollection<Document> mongoCollection;
    ArrayList<String> strings = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_display);

        t1=findViewById(R.id.q_text);
        t2=findViewById(R.id.price_text);
        t7=findViewById(R.id.getdata);
        Realm.init(this); // context, usually an Activity or Application
        app = new App(new AppConfiguration.Builder(Appid).build());
//        Credentials anonymousCredentials = Credentials.anonymous();
//        AtomicReference<User> user1 = new AtomicReference<User>();
//        t8.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                app.loginAsync(anonymousCredentials, it -> {
//                    if (it.isSuccess()) {
//                        Log.v("AUTH", "Successfully authenticated anonymously.");
//                        user1.set(app.currentUser());
//                    } else {
//                        Log.e("AUTH", it.getError().toString());
//                    }
//                });
//
//            }
//        });

        t7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = app.currentUser();
                MongoClient mongoClient = user.getMongoClient("mongodb-atlas");
                MongoDatabase mongoDatabase = mongoClient.getDatabase("test");
                mongoCollection=mongoDatabase.getCollection("test");

                Document queryFilter  = new Document("_id","32");
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
                            String data1= String.valueOf(currentDoc.get("quantity"));
                            String data2= String.valueOf(currentDoc.get("price"));
                            Log.v("Task Found", String.valueOf(currentDoc));
                            
                            t1.setText(data1.toString());
                            t2.setText(data2.toString());
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