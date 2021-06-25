package realm.vendingmachines.admin.Final;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;
import realm.vendingmachines.admin.R;

public class FinalUploadData extends AppCompatActivity {
    TextView textView,textView1;
    Button button;
    String data1,data2;
    MongoClient mongoClient;
    MongoDatabase mongoDatabase;
    private App app;
    User user;
    MongoCollection<Document> mongoCollection;
    ArrayList<String> strings = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        textView=findViewById(R.id.write);
        textView1=findViewById(R.id.writedata);
        button=findViewById(R.id.writeDB);

        Realm.init(this); // context, usually an Activity or Application
        //String appid="application-0-fsuyc";
        String appid="testapp-hsrwy";

        app = new App(new AppConfiguration.Builder(appid).build());
        user=app.currentUser();
        mongoClient = user.getMongoClient("mongodb-atlas");
        mongoDatabase = mongoClient.getDatabase("test");
        mongoCollection = mongoDatabase.getCollection("test");

        Document items=new Document();
                items.append("Brand1_name", "Bristol").
                append("Brand2_name", "Kings").
                append("Brand3_name", "Gold Flake").
                append("Brand4_name", "Marlboro").
                append("Brand5_name", "Gold Flake big").
                append("Brand6_name", "Paris");

                Document c_qunatity=new Document();
                c_qunatity.append("Brand1_single", "20").
                        append("Brand2_single", "50").
                        append("Brand3_single", "81").
                        append("Brand4_single", "16").
                        append("Brand5_single", "20").
                        append("Brand6_single", "22").
                        append("Brand1_box", "20").
                        append("Brand2_box", "4").
                        append("Brand3_box", "35").
                        append("Brand4_box", "52").
                        append("Brand5_box", "0").
                        append("Brand6_box", "2");
                Document price=new Document();
                price. append("Brand1_single", "10").
                        append("Brand2_single", "11").
                        append("Brand3_single", "12").
                        append("Brand4_single", "13").
                        append("Brand5_single", "14").
                        append("Brand6_single", "15").
                        append("Brand1_box", "95").
                        append("Brand2_box", "105").
                        append("Brand3_box", "115").
                        append("Brand4_box", "125").
                        append("Brand5_box", "135").
                        append("Brand6_box", "145");

        List<Document> list=new ArrayList<>();
            list.add(items);
            list.add(price);
            list.add(c_qunatity);



                Document d2=new Document();
                d2.append("_id", "32").append(
                        "Machine Type", "Cigarette Vending Machine").append(
                        "Machine Code", "adfadf").append("Items",items).append("price",price).append("quantity",c_qunatity);






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



                mongoCollection.insertOne(d2).getAsync(result -> {
                    if (result.isSuccess()) {
                        Log.v("adding", "result");
                        Toast.makeText(getApplicationContext(), "Inserted Successful", Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(FinalUploadData.this, "Error"+result.getError(), Toast.LENGTH_SHORT).show();
                        //Log.v("adding", "result failed" + result.getError().toString());
                        //Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();

                    }
                });


            }
        });
    }
}