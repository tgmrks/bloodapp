package com.bloodapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.bloodapp.Model.NotificationModel;
import com.bloodapp.Model.Profile;
import com.bloodapp.util.Mock;
import com.bloodapp.util.NotificationCustomAdapter;
import com.bloodapp.util.Utilities;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

public class NotificationAcitivity extends AppCompatActivity {

    private static final String TAG = "NotificationAcitivity";

    private DatabaseReference mDatabase;
    ArrayList notificationList;
    ListView listView;
    TextView nextDonation;
    private NotificationCustomAdapter adapter;
    Mock mock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        listView = (ListView) findViewById(R.id.listViewNotification);
        nextDonation = (TextView) findViewById(R.id.tv_next_donation);

        mock = new Mock(NotificationAcitivity.this);
        notificationList = new ArrayList();
        notificationList = fillNotificationMessages();
        /*//mockdata
        notificationList.add(new NotificationModel("Mensagem de BloodApp","Apple Pie", "23/11/2018 15:53", false));        notificationList.add(new NotificationModel("Mensagem de BloodApp","Banana Bread", "23/11/2018 15:53", false));
        notificationList.add(new NotificationModel("Mensagem de BloodApp","Cupcake", "23/11/2018 15:53",  false));        notificationList.add(new NotificationModel("Mensagem de BloodApp","Donut", "23/11/2018 15:53", false));
        notificationList.add(new NotificationModel("Mensagem de BloodApp","Eclair", "23/11/2018 15:53", false));        notificationList.add(new NotificationModel("Mensagem de BloodApp","Froyo", "23/11/2018 15:53", false));
        notificationList.add(new NotificationModel("Mensagem de BloodApp","Gingerbread", "23/11/2018 15:53", false));        notificationList.add(new NotificationModel("Mensagem de BloodApp","Honeycomb", "23/11/2018 15:53", false));
        notificationList.add(new NotificationModel("Mensagem de BloodApp","Ice Cream Sandwich", "23/11/2018 15:53", false));        notificationList.add(new NotificationModel("Mensagem de BloodApp","Jelly Bean", "23/11/2018 15:53", false));
        notificationList.add(new NotificationModel("Mensagem de BloodApp","Kitkat", "23/11/2018 15:53", false));        notificationList.add(new NotificationModel("Mensagem de BloodApp","Lollipop", "23/11/2018 15:53", false));
        notificationList.add(new NotificationModel("Mensagem de BloodApp","Marshmallow", "23/11/2018 15:53", false));        notificationList.add(new NotificationModel("Mensagem de BloodApp","Nougat", "23/11/2018 15:53", false)); */

        adapter = new NotificationCustomAdapter(notificationList, getApplicationContext());

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                //NotificationModel dataModel = (NotificationModel) notificationList.get(position);
                for (int i = 0; i < notificationList.size(); i++) {
                    NotificationModel dataModel = (NotificationModel) notificationList.get(i);
                    if(i == position ){
                        dataModel.setChecked(!dataModel.isChecked());

                        if(dataModel.isChecked()) {
                            fillPainelNextDonation(dataModel.getMsgDate());
                            mock.saveNotificationChecked(dataModel.getMsguid());
                        }
                        else {
                            nextDonation.setText("Você ainda não realizou doação!");
                            mock.saveNotificationChecked("");
                        }


                        Log.d("CHECK RESULT ","Position " + i + " wasClicked " + String.valueOf(dataModel.isChecked() + " ...msg: " + dataModel.getMsgBody()));
                    }
                    else {
                        dataModel.setChecked(false);
                        Log.d("CHECK RESULT ","Position " + i + " setToFalse " + String.valueOf(dataModel.isChecked() + " ...msg: " + dataModel.getMsgBody()));
                    }
                }
                //dataModel.setChecked(!dataModel.isChecked());
                adapter.notifyDataSetChanged();
            }
        });
    }

    private ArrayList fillNotificationMessages() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        final ArrayList list = new ArrayList();
        final String notificationChecked = mock.readNotificationChecked();

        mDatabase.child(Utilities.NOTIFICATIONS).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again  -  whenever data at this location is updated.
                Map<String, Object> value = (Map<String, Object>) dataSnapshot.getValue();  //Log.d(TAG, "Value is: " + value);

                Iterator it = value.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry entry = (Map.Entry)it.next(); //current entry in a loop
                    String uid = entry.getKey().toString();
                    //Log.d(TAG, "value is " + uid);Log.d(TAG, "value is " + entry.getValue());
                    //ternary = a ~ b ? a : b;
                    //if(notificationChecked.equals(uid) ? true : false) Log.d("NOTI_CHECKED", "Ternary works"); else Log.d("NOTI_CHECKED", "Ternary dont works");

                    list.add(new NotificationModel(
                            uid,
                            dataSnapshot.child(uid).child(Utilities.msg_buid_FIELD).getValue().toString(),
                            dataSnapshot.child(uid).child(Utilities.msg_address_FIELD).getValue().toString(),
                            dataSnapshot.child(uid).child(Utilities.msg_user_FIELD).getValue().toString(),
                            dataSnapshot.child(uid).child(Utilities.msg_body_FIELD).getValue().toString(),
                            dataSnapshot.child(uid).child(Utilities.msg_date_FIELD).getValue().toString(),
                            notificationChecked.equals(uid) ? true : false
                    ));

                    if(notificationChecked.equals(uid) ? true : false) {
                        fillPainelNextDonation(dataSnapshot.child(uid).child(Utilities.msg_date_FIELD).getValue().toString());
                    }
                }
                //for (int i = 0; i < list.size(); i++) { Log.d("notificationlist IN ", String.valueOf(i));System.out.println(list.get(i));}
                Log.d("NOTI_CHECKED", "> " + notificationChecked + " <");

                if(!notificationChecked.isEmpty()) {

                }

                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        return list;
    }

    public void fillPainelNextDonation(String date){

        String myDate = date + ":00";
        Log.d("MYDATE ", myDate);

        /*String oldFormat = "MM/dd/yyyy HH:mm:ss";
        String newFormat = "yyyy/MM/dd HH:mm:ss";
        SimpleDateFormat sdf1 = new SimpleDateFormat(oldFormat);
        SimpleDateFormat sdf2 = new SimpleDateFormat(newFormat);
        try { System.out.println(sdf2.format(sdf1.parse(myDate)));} catch (ParseException e) { e.printStackTrace(); }
        */
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date dateFormated = new Date(); long millisDonateDate = 0;

        try {
            dateFormated = sdf.parse(myDate);
            Log.d("formated DATE ", " - " + dateFormated);
            millisDonateDate = dateFormated.getTime();
            Log.d("MILLS ", " - " + millisDonateDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //today date
        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        dt = c.getTime(); //millis for today

        //next date
        Date dn = new Date();
        Calendar cn = Calendar.getInstance();
        cn.setTime(dateFormated);
        cn.add(Calendar.DATE, 30);
        dn = cn.getTime(); // millis for added date

        Log.d("today DATE ", " - " + dt);
        Log.d("added DATE ", " - " + dn);
        long nextDonateDate = dn.getTime() - dt.getTime();
        Log.d("sub.. DATE ", " - " + dt);

        nextDonation.setText(String.valueOf(nextDonateDate/1000/60/60/24) + " dias para proxima doação!");

        /*
        // 01 2 34 5 6789 A BC D EF G HI
        // dd / MM / yyyy   HH : mm : ss
        // 11 / 27 / 2018   16 : 23 : 00
        String dd = myDate.substring(0,1);
        String MM = myDate.substring(3,4);
        String yyyy = myDate.substring(6,9);
        String HH = myDate.substring(11,12);
        String mm = myDate.substring(14,15);
        String ss = "00";

        String concatDate = yyyy + "-" + MM + "-" + dd + " " + HH + ":" + mm + ":" + ss;
        Log.d("CONCAT DATE ", " - " + concatDate);

        String newFormat = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf2 = new SimpleDateFormat(newFormat);
        Date dateFormat2;

        try {
            dateFormat2 = sdf2.parse(concatDate);
            Log.d("formated DATE 2 ", " - " + dateFormat2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        */

    }
}