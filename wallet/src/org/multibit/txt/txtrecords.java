package org.multibit.txt;

import java.lang.ref.WeakReference;

import org.xbill.DNS.Lookup;
import org.xbill.DNS.MXRecord;
import org.xbill.DNS.Record;
import org.xbill.DNS.TextParseException;
import org.xbill.DNS.Type;

import android.R;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;




/*
 * Txt Records
 *----------------------------------------
 * This class fetches a txt records with-in a dns zone
 * Author - Ed Evanosich
 * 
 * 
 * 
 * 
 */

   




public class txtrecords extends AsyncTask<String, Integer, String> {

	private Context mContext;
	private TextView textbox;
	
	public void PassTextBox(AutoCompleteTextView textbox){
	    //this.mContext=context;
		this.textbox = textbox;
	}
	
	
	public void MyAsyncTask( Activity activity ) {
		this.mContext = activity;
    }
	
	
	
	 protected String doInBackground(String... Hostname) {
		 String results = "What up bitches2!";   
		
		 
		 results = getTxtRecord(Hostname[0].toLowerCase().trim());
		 
		 return results;
		 
	        
	    }
	
	 
	 
	 
	 protected void onPostExecute(String results) {
		 
		 
		 this.textbox.setText(results);
	    }
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	public String getTxtRecord(String Hostname) {
	
		//Pull List of TLDs. 
		String[] ListofDomains = new DomainNames().GetDomainList();
		//Boolean -- Keep track 
		boolean HasTLD = false;
		
		//Loop through all domain names.
		for(String CurrentDomain: ListofDomains)
		{
			//Check if Hostname contains a domain within the list.
			if(Hostname.toLowerCase().endsWith("." + CurrentDomain.trim().toLowerCase()))
			{
				//We have a TLD
				HasTLD = true;
				
			}
			
			
			
		}
		
		if(Hostname.contains("@"))
		{
			Hostname = Hostname.replaceAll("@", ".");
		}
		
		
		//If we have a TLD, we can continue. 
		if(HasTLD == true)
		{
		
			try {
				
				//Fetch Record
				Lookup lookup = new Lookup(Hostname, Type.TXT);
	            Record[] records = lookup.run();
				
	            if (lookup.getResult() == Lookup.SUCCESSFUL) {
		        	 
		        	
					//Pull The address from the string -- String comes with Input data, and TTL
					String Addresswithquotes = records[0].toString().substring(records[0].toString().lastIndexOf("TXT") + 4);
					//Remove Quotes from the address.
					Addresswithquotes = Addresswithquotes.replace("\"", "").trim();
					//Return Address
					return Addresswithquotes;
					
				
		        	 
		         }else {
		        	 
		        	 return Hostname;
		         }
	            
				
				
			} catch (TextParseException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				//The was an error. Forget about it, Return what was submitted.
				return Hostname;
			}
		
		}
		else
		{
			return Hostname;
		}
	}
	
	
	
}//End Class
