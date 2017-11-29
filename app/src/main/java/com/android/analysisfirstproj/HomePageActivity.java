package com.android.analysisfirstproj;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomePageActivity extends AppCompatActivity {

    FirebaseUser fuser;
    FirebaseAuth mauth;
    FirebaseDatabase firebaseDatabase;
    Button buttonSignOut;
    Intent intent;
    private MovieAdapter mAdapter;
    private DatabaseReference databaseReference;
    private ChildEventListener mChildEventListener;
    private int aks = 0,fant = 0,i=0,bk=0,temp = 0;
    UserMovieRelationship userMovieRelationship;
    private String value;
    private String us;

    public class FuncTask extends AsyncTask<Void,Void,String>
    {

        @Override
        protected String doInBackground(Void... voids) {

            if(aks > temp)
            {
                temp = aks;
            }
            if(bk > temp)
            {
                temp = bk;
            }
            if(fant > temp)
            {
                temp = fant;
            }
            runOnUiThread(new   Runnable() {
                public void run() {


                    if (temp == aks) {
                        Toast.makeText(getApplicationContext(), "Anlaşılan Aksiyon ilgini çekiyor", Toast.LENGTH_LONG).show();

                    } else if (temp == fant) {
                        Toast.makeText(getApplicationContext(), "Anlaşılan Fantastik ilgini çekiyor", Toast.LENGTH_LONG).show();

                    } else if (temp == bk) {
                        Toast.makeText(getApplicationContext(), "Anlaşılan Bilim Kurgu ilgini çekiyor", Toast.LENGTH_LONG).show();


                    }
                    else if(temp == 0)
                    {
                        Toast.makeText(getApplicationContext(), "ok", Toast.LENGTH_LONG).show();
                        us = "null";
                    }
                }
            });



            if (temp == aks) {

                us = "Aksiyon";
            } else if (temp == fant) {

                us = "Fantastik";
            } else if (temp == bk) {


                us = "Bilim Kurgu";

            }
            else if(temp == 0)
            {
                us = "equal";
            }



            return us;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Once once = new Once();
            value = s;

            once.run(new Runnable() {
                @Override
                public void run() {
                    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    Date d = new Date();
                    String dat = dateFormat.format(d);
                    String date = String.valueOf(d.getTime());
                    firebaseDatabase = FirebaseDatabase.getInstance();
                    databaseReference = firebaseDatabase.getReference().child("moviesRelationship");
                    final UserMovieRelationship character = new  UserMovieRelationship(userMovieRelationship.getEmail(),value,dat);
                    databaseReference.push().setValue(character);

                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        intent = getIntent();
      //  buttonSignOut = (Button) findViewById(R.id.buttonSignOut);
        mauth = FirebaseAuth.getInstance();
        fuser = mauth.getCurrentUser();
        userMovieRelationship = new UserMovieRelationship();
        userMovieRelationship.setEmail(fuser.getEmail());

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("movies");

          /*  final Movies character = new                                   // bu yorum satırını dikkate almayın hocam, key ile veritabanında oluşturmak için kod üzerinden ekliyorum
                    Movies(R.drawable.matrix,"yep","Bilim Kurgu","Başlangıç filmi, doğaüstü ve büyülü gerçekçi tonları, bilim kurgu ve fantazmatik sinema estetiği ile harmanlayan ilginç senaryosuyla, Leonardo DiCaprio'nun üst düzey performans sergilediği değişik bir film. Bu yapımda sanatçı, çok yetenekli bir hırsız olan \"Dom Cobb \" ile karşımızda. Uzmanlık alanı, zihnin en karanlık ve savunmasız olduğu rüya görme anında, bilinçaltının derinliklerindeki değerli sırları çekip çıkarmak ve onları çalmaktır. Cobb'un bu nadir insanlarda görülebilecek yeteneği, bu ender rastgelinebilecek mahareti, onu kurumsal casusluğun tehlikeli yeni dünyasında aranan bir oyuncu yapmıştır. Aynı zamanda bu durum onu uluslararası bir kaçak yapmış ve sevdiği herşeye malolmuştur. Cobb'a içinde bulunduğu durumdan kurtulmasını sağlayacak bir fırsat sunulur. Ona hayatını geri verebilecek son bir iş; tabi eğer imkansız ?başlangıç'ı tamamlayabilirse. Mükemmel soygun yerine, Cobb ve takımındaki profesyoneller bu sefer tam tersini yapmak zorundadır; görevleri bir fikri çalmak değil onu yerleştirmektir. Eğer başarırlarsa, mükemmel suç bu olacaktır.");
        databaseReference.push().setValue(character);*/

        List<Movies> mList = new ArrayList<>();





        final ListView listemiz=(ListView) findViewById(R.id.listview);



        mAdapter = new MovieAdapter(this,0, (ArrayList<Movies>) mList);
        listemiz.setAdapter(mAdapter);


        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s)
            {
                Movies character1 = dataSnapshot.getValue(Movies.class);
                mAdapter.add(character1);  //veritabanındaki filmleri listeler

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        databaseReference.addChildEventListener(mChildEventListener);


        listemiz.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {


                Movies char1 = (Movies) listemiz.getItemAtPosition(position);
               // char1.setMovieId(i++);

               // final String ID = databaseReference.push().getKey();
               // databaseReference.child(ID).child("movieId").setValue(i++);

                // databaseReference.child("movies").child("movieId").push().setValue(char1);
                //al filmin tipini at sharedprefe sonra al onu
                switch (char1.getMovieType())
                {
                    case "Aksiyon":
                        aks = aks + 1;
                        break;
                    case "Fantastik":
                        fant = fant + 1;
                        break;
                    case "Bilim Kurgu":
                        bk = bk + 1;
                        break;

                }

    Once once = new Once();


    once.run(new Runnable() {
        @Override
        public void run() {
            new java.util.Timer().schedule(

                    new java.util.TimerTask() {
                        @Override
                        public void run() {

                            runOnUiThread(new Runnable() {
                                public void run() {
                                    new FuncTask().execute();
                                }
                            });
                        }


                    },
                    30000
            );
        }
    });

                Intent intent = new Intent(getApplicationContext(),Text.class);
                intent.putExtra("name",char1);
                startActivity(intent);

              /*  transaction.replace(R.id.content, new MessageF());
                transaction.setTransition(transaction.TRANSIT_FRAGMENT_OPEN);
                transaction.commit();*/
                // intent = new Intent(ChatFragment.this,HomePage.class);
                //  intent.putExtra("Character",  char1);    //yeni ekran açıldığında o karakterin bilgisinide almak için kullandım
                //  startActivity(intent);


            }
        });


















    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.signOut)
        {
            mauth.signOut();
            Toast.makeText(getApplicationContext(), "Logged Out", Toast.LENGTH_SHORT).show();
            fuser = null;
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(i);

        }
        return super.onOptionsItemSelected(item);
    }


}
