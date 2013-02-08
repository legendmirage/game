package willpath;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

import util.Logger;

import NetworkUtil.NTPClient;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class Fitbit {
    private static final String encodedLogin ="bWl0X2dyb3VwMzp3eTBzbHR4ZTB3MTZyenJ1cnh1bA==";

    public static ArrayList<FitBitSummary> getFBS(String UUID)
    {		
    	String url="https://willpath.com/api/v1/people/"+UUID+"/observations/fitbit_summaries.json";
    	String ret="";
    	ArrayList<FitBitSummary> details = new ArrayList<FitBitSummary>();

    	
    		try {
    			URLConnection urlConnection = new URL(url).openConnection();
    			
    			urlConnection.setRequestProperty("Accept", "application/json");
    			
    			urlConnection.setRequestProperty("Authorization", "Basic " + encodedLogin);

    			HttpsURLConnection connection = (HttpsURLConnection) urlConnection;
    	        
    	        connection.setRequestMethod("GET");
    	        
    	        InputStream is =connection.getInputStream();
    	        
    		    int len=0;
    		    byte[] buffer = new byte[4096*16];
    		    ByteArrayOutputStream bos= new ByteArrayOutputStream();

    		    
    		    while (-1 != (len = is.read(buffer))) {
    		    	bos.write(buffer,0, len);
    		    	ret+=bos.toString();
    		    	bos.reset();
    		    }
            	Gson gb = new Gson();
            	JsonElement json = new JsonParser().parse(ret);

            	JsonArray array= json.getAsJsonArray();

            	Iterator iterator = array.iterator();


            	while(iterator.hasNext()){
            	    JsonElement json2 = (JsonElement)iterator.next();
            	    FitBitSummary fbs = gb.fromJson(json2, fbsContainer.class).fitbit_summary;
            	    //can set some values in contact, if required 
            	    details.add(fbs);
            	}
    		} catch (MalformedURLException e) {
    			// TODO Auto-generated catch block
    		} catch (IOException e) {
    		}
    		return details;
    }
    private class fbsContainer
    {
    	public FitBitSummary fitbit_summary;
    }
    public static ArrayList<Person> getPeople(){
		String url="https://willpath.com/api/v1/people.json";
		String ret="";
    	ArrayList<Person> details = new ArrayList<Person>();
		try {
			URLConnection urlConnection = new URL(url).openConnection();
			
			urlConnection.setRequestProperty("Accept", "application/json");
			
			urlConnection.setRequestProperty("Authorization", "Basic " + encodedLogin);

			HttpsURLConnection connection = (HttpsURLConnection) urlConnection;
	        
	        connection.setRequestMethod("GET");
	        
	        InputStream is =connection.getInputStream();
	        int canRead=is.available();
		    int i =0;
		    byte[] buffer = new byte[4096*16];
		    ByteArrayOutputStream bos= new ByteArrayOutputStream();
		    int len;
		    
		    while (-1 != (len = is.read(buffer))) {
		    	bos.write(buffer,0, len);
		    	ret+=bos.toString();
		    	bos.reset();
		    }

        	Gson gb = new Gson();
        	JsonElement json = new JsonParser().parse(ret);

        	JsonArray array= json.getAsJsonArray();

        	Iterator iterator = array.iterator();


        	while(iterator.hasNext()){
        	    JsonElement json2 = (JsonElement)iterator.next();

        	    Person contact = gb.fromJson(json2, PersonHolder.class).person;
        	    //can set some values in contact, if required 
        	    details.add(contact);
        	}
        	Logger.log(details);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
		} catch (IOException e) {
		} catch (Exception e)
		{
			return null;
		}
		return details;
	}
    public static int getSteps(String UUID)
    {
    	return getSteps(UUID, null);
    }
    public static int getSteps(String UUID, Date dt)
    {
    	;
    	if (dt==null){
    		long a = (long)(NTPClient.getDelta()+ new Date().getTime()- 7*24*60*60*1000);
    		dt = new Date(a);
    	}
    	int toRet =0;
    	DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T01:00:00Z'");
    	String s= df.format(dt);
    	
    	String url="https://willpath.com/api/v1/people/"+UUID+"/observations/fitbit_summaries.json?&updated_since="+s;
    	String ret="";
    	
    	ArrayList<FitBitSummary> details = new ArrayList<FitBitSummary>();

    	
    		try {
    			URLConnection urlConnection = new URL(url).openConnection();
    			
    			urlConnection.setRequestProperty("Accept", "application/json");
    			
    			urlConnection.setRequestProperty("Authorization", "Basic " + encodedLogin);

    			HttpsURLConnection connection = (HttpsURLConnection) urlConnection;
    	        
    	        connection.setRequestMethod("GET");
    	        
    	        InputStream is =connection.getInputStream();
    	        
    		    int len=0;
    		    byte[] buffer = new byte[4096*16];
    		    ByteArrayOutputStream bos= new ByteArrayOutputStream();

    		    
    		    while (-1 != (len = is.read(buffer))) {
    		    	bos.write(buffer,0, len);
    		    	ret+=bos.toString();
    		    	bos.reset();
    		    }
    		    Logger.log(ret);
            	Gson gb = new Gson();
            	JsonElement json = new JsonParser().parse(ret);
            	
            	JsonArray array= json.getAsJsonArray();

            	Iterator iterator = array.iterator();


            	while(iterator.hasNext()){
            	    JsonElement json2 = (JsonElement)iterator.next();
            	    FitBitSummary fbs = gb.fromJson(json2, fbsContainer.class).fitbit_summary;
            	    //can set some values in contact, if required 
            	    toRet+=fbs.steps;
            	    
            	}
            	
    		}catch (MalformedURLException e) {
        			// TODO Auto-generated catch block
        		} catch (IOException e) {
        		}
            return toRet;
    }
	public static void main(String args[])
	{

		getPeople();
		getSteps("50c9cf66-64de-4936-97c8-6bed83b96e7e");

		
	}
	
}
