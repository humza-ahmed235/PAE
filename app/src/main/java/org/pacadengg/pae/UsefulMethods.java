package org.pacadengg.pae;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class UsefulMethods {
protected static ArrayList <String> slideLinks = new ArrayList<String>();
  protected static ArrayList <Drawable> slideDrawables = new ArrayList<Drawable>();
    public static int slideCounter;




    public static boolean hasActiveInternetConnection() {

            try {
                HttpURLConnection urlc = (HttpURLConnection) (new URL("https://www.google.com").openConnection());
                urlc.setRequestProperty("User-Agent", "Test");
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(1500);
                urlc.connect();

                return (urlc.getResponseCode() == 200);
            } catch (IOException e) {

            }

        return false;
    }


    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        }  catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();

        }

        return null;


    }





public static class getDrawables extends AsyncTask<Void,Void,Void>  {


    @Override
    protected Void doInBackground(Void... voids) {
//(3) All the links have been converted into drawables using input stream method and stored in drawables arraylist
        Drawable tempdrawable;
        for(int i =0 ; i< slideLinks.size();i++) {

            tempdrawable = LoadImageFromWebOperations(slideLinks.get(i));
            slideDrawables.add(tempdrawable);

    }




return null;


    }

    @Override
    protected void onPostExecute(Void aVoid) {


        super.onPostExecute(aVoid);
/* (4) Now that all the drawables arraylist has been filled(thats why we are in postexecute) We create a new thread/run method but force into ui thread since
image view is part of the ui.
  We do this by using handler. Handler  has looper or reference of main ui. It forces the runnable run method to run on ui thread.
   The post delayed method at the bottommost area starts this process and has an (initial delay ) delay of zero. When the run mehtod is run then when it
   is about to be finished we call run mehtod again by using handler.postdelayed again but this time with a delay of (3 seconds)

   Since the run method is called again and again by postdelayed with a delay, it becomes a loop and so there is a counter.
   */




        final Handler handler = new Handler(Looper.getMainLooper());
        final Runnable runnable = new Runnable() {

            public void run() {

        if(slideCounter == slideDrawables.size()-1)
        {slideCounter = 0;}
                Home.slideshow.setImageDrawable(slideDrawables.get(slideCounter));
        slideCounter++;
        handler.postDelayed(this, 2000);  //for interval...

            }
        };
        handler.postDelayed(runnable, 0); // initial delay


    }
}





public static class getSlideLinks extends AsyncTask<Void,Void,Void> {
//(1)All the links fetched using jsoupPae method in background. To understand refer to jsoupPAE
    @Override
    protected Void doInBackground(Void... voids) {
        Document doc = null;
        try {
            doc = Jsoup.connect("https://www.pacadengg.org/index.html").get();
        } catch (
                IOException e) {
            e.printStackTrace();
        }


        Elements ec = doc.select("ul.bjqs li");



        for (
                Element e : ec) {
            slideLinks.add(e.select("img").attr("abs:src")); //This line fetches each link and stores it in arraylist


        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        new getDrawables().execute(); // (2) starts thread to get drawables one all links have been stored in arraylist(thats why i did it in postexecute)

    }
}


}


