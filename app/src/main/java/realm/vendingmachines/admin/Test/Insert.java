package realm.vendingmachines.admin.Test;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;
import realm.vendingmachines.admin.R;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class Insert  extends AppCompatActivity {
    MongoClient mongoClient;
    MongoDatabase mongoDatabase;
    private App app;
    User user;
    MongoCollection<Document> mongoCollection;
    ArrayList<String> strings = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity);

        Realm.init(this); // context, usually an Activity or Application
        String appid = "application-0-fsuyc";

        Credentials anonymousCredentials = Credentials.anonymous();
        AtomicReference<User> user1 = new AtomicReference<User>();
        app.loginAsync(anonymousCredentials, it -> {
            if (it.isSuccess()) {
                Log.v("AUTH", "Successfully authenticated anonymously.");
            } else {
                Log.e("AUTH", it.getError().toString());
            }
        });



        //String appid="authapp-jppdi";
        app = new App(new AppConfiguration.Builder(appid).build());

        user = app.currentUser();
        MongoClient mongoClient =
                user.getMongoClient("mongodb-atlas");
        MongoDatabase mongoDatabase =
                mongoClient.getDatabase("test");
        // registry to handle POJOs (Plain Old Java Objects)
        //CodecRegistry pojoCodecRegistry = fromRegistries(AppConfiguration.DEFAULT_BSON_CODEC_REGISTRY,
          //      fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        MongoCollection<Plant> mongoCollection =
                mongoDatabase.getCollection(
                        "test",
                        Plant.class);
        mongoCollection.insertMany(Arrays.asList(
                new Plant(new ObjectId(),
                        "venus flytrap",
                        "full",
                        "white",
                        "perennial",
                        "Store 42"),
                new Plant(new ObjectId(),
                        "sweet basil",
                        "partial",
                        "green",
                        "annual",
                        "Store 42"),
                new Plant(new ObjectId(),
                        "thai basil",
                        "partial",
                        "green",
                        "perennial",
                        "Store 42"),
                new Plant(new ObjectId(),
                        "helianthus",
                        "full",
                        "yellow",
                        "annual",
                        "Store 42"),
                new Plant(new ObjectId(),
                        "petunia",
                        "full",
                        "purple",
                        "annual",
                        "Store 47")));
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        //Log.v("EXAMPLE", "Successfully inserted the sample data.");

    }
}