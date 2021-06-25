package realm.vendingmachines.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.bson.Document;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;

public class WriteActivity extends AppCompatActivity {
    TextView textView,textView1;
    Button button;
    String data1,data2;
    MongoClient mongoClient;
    MongoDatabase mongoDatabase;
    private App app;
    User user;
    MongoCollection<Document> mongoCollection;
    ArrayList<String> strings = new ArrayList<>();
   // String json="{ Name: \"Pink Friday\", Image: \"https://upload.wikimedia.org/wikipedia/en/thumb/f/f1/Pink_Friday_album_cover.jpg/220px-Pink_Friday_album_cover.jpg\" ,Year: 2010}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        textView=findViewById(R.id.write);
        textView1=findViewById(R.id.writedata);
        button=findViewById(R.id.writeDB);

        Realm.init(this); // context, usually an Activity or Application
        String appid="application-0-fsuyc";
        //String appid="authapp-jppdi";
        app = new App(new AppConfiguration.Builder(appid).build());
        user=app.currentUser();
        mongoClient = user.getMongoClient("mongodb-atlas");
        mongoDatabase = mongoClient.getDatabase("test");
        mongoCollection = mongoDatabase.getCollection("test");



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data1 = textView.getText().toString().trim();
                data2 = textView1.getText().toString().trim();
                Document document = new Document().append("data1",data1).append("data2", data2).append("userid", user.getId());
                Document document1=new Document();
                document1.append("data2","Second Data").put("sa",document);

                List<Document> docs = new ArrayList<>();
                docs.add(document);
                docs.add(1,document1);



                mongoCollection.insertOne(document1).getAsync(result -> {
                    if (result.isSuccess()) {
                        Log.v("adding", "result");
                        Toast.makeText(getApplicationContext(), "Inserted", Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(WriteActivity.this,DisplayDb.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(WriteActivity.this, "Error"+result.getError(), Toast.LENGTH_SHORT).show();
                        //Log.v("adding", "result failed" + result.getError().toString());
                        //Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();

                    }
                });


            }
        });
    }
}