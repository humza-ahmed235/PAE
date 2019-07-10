package org.pacadengg.pae;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class ContactUs extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
TextView contactUsText;

    private DrawerLayout drawer;
    private NavigationView naviView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        Toolbar my_toolbar = findViewById(R.id.toolbarContactUs);
        setSupportActionBar(my_toolbar);
        drawer = findViewById(R.id.drawerContactUs);
        naviView = findViewById(R.id.naviViewContactUs);
        naviView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,my_toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);

        toggle.syncState();


new contactUsList().execute();

    }
    protected static String contactUsParagraph = "";

    private class contactUsList extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if(!UsefulMethods.hasActiveInternetConnection())
            {  runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_LONG ).show();
                }
            });
                // Checks if there is internet. Then runs toast. Toast can only be run from a ui/main thread. Not async

            }

            else{runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getApplicationContext(),"Fetching Data",Toast.LENGTH_LONG ).show();
                }
            });}


           Document doc = null;

            try {
                doc = Jsoup.connect("https://www.pacadengg.org/contactus.html").get();


                Elements ec = doc.select("div.col-2.myfonts p"); // . added to make up for spaces
                for (Element e : ec) {
                    contactUsParagraph = contactUsParagraph + e.select("p").text() + "\n";
                    contactUsParagraph = contactUsParagraph.replace("Feedback", "");

                }

            }
            catch(IOException e){
                e.printStackTrace();

            }

            catch (NullPointerException e){

                e.printStackTrace();
            }

            return null;

        }
        @Override
        protected void onPostExecute (Void aVoid){

            super.onPostExecute(aVoid);

            contactUsText = (TextView) findViewById(R.id.contactUsText);
            contactUsText.setMovementMethod(new ScrollingMovementMethod());

            contactUsText.setText(contactUsParagraph);
            contactUsParagraph = ""; // SO that the next we open the activity paragraph does not become double the size and so on;

        }



    }



    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.my_menu,menu);

        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        int theID = item.getItemId();

        if(theID == R.id.home)
        { Intent intent = new Intent(this,Home.class);
            startActivity(intent);
            return true;
        }
        else if(theID == R.id.symposiums)
        { Intent intent = new Intent(this,Symposiums.class);
            startActivity(intent);
            return true;
        }

        else if(theID == R.id.fellows)
        { Intent intent = new Intent(this,Fellows.class);
            startActivity(intent);
            return true;
        }

        else if(theID == R.id.contactUs)
        { Intent intent = new Intent(this,ContactUs.class);
            startActivity(intent);
            return true;
        }


        else {return super.onOptionsItemSelected(item);}
    }


    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START))
        { drawer.closeDrawer(GravityCompat.START);}
        else {super.onBackPressed();}

    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int theID = menuItem.getItemId();

        if(theID == R.id.home)
        { Intent intent = new Intent(this,Home.class);
            startActivity(intent);

        }
        if(theID == R.id.symposiums)
        { Intent intent = new Intent(this,Symposiums.class);
            startActivity(intent);

        }

        if(theID == R.id.fellows)
        { Intent intent = new Intent(this,Fellows.class);
            startActivity(intent);

        }

        if(theID == R.id.contactUs)
        { Intent intent = new Intent(this,ContactUs.class);
            startActivity(intent);

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
