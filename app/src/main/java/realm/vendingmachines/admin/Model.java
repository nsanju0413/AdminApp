package realm.vendingmachines.admin;

import io.realm.RealmObject;

public class Model extends RealmObject {
    String uid,data1,data2;
    public Model(){

    }

    public Model(String uid, String data1, String data2) {
        this.uid = uid;
        this.data1 = data1;
        this.data2 = data2;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getData1() {
        return data1;
    }

    public void setData1(String data1) {
        this.data1 = data1;
    }

    public String getData2() {
        return data2;
    }

    public void setData2(String data2) {
        this.data2 = data2;
    }
}
