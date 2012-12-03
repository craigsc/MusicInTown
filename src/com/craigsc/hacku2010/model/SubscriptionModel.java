package com.craigsc.hacku2010.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.craigsc.hacku2010.PMF;
import com.craigsc.hacku2010.domain.Subscription;
import com.craigsc.hacku2010.service.SmsService;

@SuppressWarnings("unchecked")
public class SubscriptionModel {
	private static final SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss");
	private static final SimpleDateFormat smsFormat = new SimpleDateFormat("MM/dd/yy");
	private static final SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
	
	private static final String verifyMessage = "MusicInTown Code: ";
	
	
	public SubscriptionModel() {
		
	}
	
	public boolean saveSubscription(final Subscription sub) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			List<Subscription> matches = (List<Subscription>) pm.newQuery("select from " + Subscription.class.getName() 
					+ " where number == " + "'" + sub.getNumber() + "'").execute();
			if (matches.isEmpty()) {
				pm.makePersistent(sub);
				return true;
			} else if (!matches.get(0).getActive()) {
				matches.get(0).setVerificationCode(sub.getVerificationCode());
				return true;
			}
			return false;
		} finally {
			pm.close();
		}
	}
	
	public boolean sendVerificationCode(final Subscription sub) {
		return SmsService.sendSms(sub, verifyMessage + sub.getVerificationCode());
	}

	public void sendEventsSms(final Subscription sub, final String artist) {
		String stringResult = "";
		try {
            URL url = new URL("http://query.yahooapis.com/v1/public/yql?q=select%20events.event" +
    				"%20from%20lastfm.artist.getevents%20where%20api_key%20%3D%20%22c08c9ffc6968f3fb8eb98dfc9ca221ec%22%20and%20" +
    				"artist%20%3D%20%22" + artist + "%22%20and%20events.event.venue.location.city%20in%20(select%20locality1%20from" +
    				"%20geo.places%20where%20woeid%20in%20(select%20place.woeid%20from%20flickr.places%20where%20(lat%2Clon)%20in%20(" +
    				sub.getLat() + "%2C" + sub.getLon() + ")))%20limit%201&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys");
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                stringResult += line;
            }
            reader.close();
        } catch (MalformedURLException e) {
            // ...
        } catch (IOException e) {
            // ...
        }
        JSONObject result;
        try {
        	result = new JSONObject(stringResult);
        	System.out.println(result);
        	JSONObject event = result.getJSONObject("query").getJSONObject("results").getJSONObject("lfm").getJSONObject("events").getJSONObject("event");
        	String city = event.getJSONObject("venue").getJSONObject("location").getString("city");
        	
        	Date date = format.parse(event.getString("startDate"));
        	String venue = event.getJSONObject("venue").getString("name");
        	int cancelled = event.getInt("cancelled");
        	System.out.println(event);
        	String msg;
        	if (cancelled == 0) {
	        	msg = artist.replace("%20", " ") + " is coming to " + city + " on " + smsFormat.format(date) + 
	        		" to play at the " + venue + " at " + timeFormat.format(date) + "."+ " We'll keep you updated.";
        	} else {
        		msg = "Uh oh, we have bad news. The " + artist.replace("%20", " ") + " concert on " + smsFormat.format(date) + " at " +
        			"the " + venue + " has been cancelled.";
        	}
        	System.out.println(msg);
        	SmsService.sendSms(sub, msg);
        } catch (ParseException e) {
        	System.out.println("Parse exception");
        	e.printStackTrace();
        } catch (JSONException e) {
        	System.out.println("OH SHIIIIIT");
        }
        
	}
	
	public void sendEventsSms(final Subscription sub) {
		String stringResult = "";
		try {
            URL url = new URL("http://query.yahooapis.com/v1/public/yql?q=select%20events.event" +
    				"%20from%20lastfm.artist.getevents%20where%20api_key%20%3D%20%22c08c9ffc6968f3fb8eb98dfc9ca221ec%22%20and%20" +
    				"artist%20%3D%20%22" + sub.getArtist() + "%22%20and%20events.event.venue.location.city%20in%20(select%20locality1%20from" +
    				"%20geo.places%20where%20woeid%20in%20(select%20place.woeid%20from%20flickr.places%20where%20(lat%2Clon)%20in%20(" +
    				sub.getLat() + "%2C" + sub.getLon() + ")))%20limit%201&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys");
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                stringResult += line;
            }
            reader.close();
        } catch (MalformedURLException e) {
            // ...
        } catch (IOException e) {
            // ...
        }
        JSONObject result;
        try {
        	result = new JSONObject(stringResult);
        	System.out.println(result);
        	JSONObject event = result.getJSONObject("query").getJSONObject("results").getJSONObject("lfm").getJSONObject("events").getJSONObject("event");
        	String city = event.getJSONObject("venue").getJSONObject("location").getString("city");
        	
        	Date date = format.parse(event.getString("startDate"));
        	String venue = event.getJSONObject("venue").getString("name");
        	int cancelled = event.getInt("cancelled");
        	System.out.println(event);
        	String msg;
        	if (cancelled == 0) {
	        	msg = sub.getArtist().replace("%20", " ") + " is coming to " + city + " on " + smsFormat.format(date) + 
	        		" to play at the " + venue + " at " + timeFormat.format(date) + "."+ " We'll keep you updated.";
        	} else {
        		msg = "Uh oh, we have bad news. The " + sub.getArtist().replace("%20", " ") + " concert on " + smsFormat.format(date) + " at " +
        			"the " + venue + " has been cancelled.";
        	}
        	System.out.println(msg);
        	SmsService.sendSms(sub, msg);
        } catch (ParseException e) {
        	System.out.println("Parse exception");
        	e.printStackTrace();
        } catch (JSONException e) {
        	System.out.println("OH SHIIIIIT");
        }
        
	}
	
	public void sendEventsSmsForUsername(final Subscription sub) {
		String stringResult = "";
		try {
            URL url = new URL("http://query.yahooapis.com/v1/public/yql?q=select%20topartists.artist.name%20from%20" +
            		"lastfm.user.gettopartists%20where%20api_key%20%3D%20%22c08c9ffc6968f3fb8eb98dfc9ca221ec" +
            		"%22%20%20and%20period%20%3D%20%223month%22%20and%20user%3D%22" + sub.getUsername() + "%22%20" +
            		"limit%2010&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys");
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                stringResult += line;
            }
            reader.close();
        } catch (MalformedURLException e) {
            // ...
        } catch (IOException e) {
            // ...
        }
        JSONObject result;
        try {
        	System.out.println(stringResult);
        	result = new JSONObject(stringResult);
        	System.out.println(result);
        	JSONArray artists = result.getJSONObject("query").getJSONObject("results").getJSONArray("lfm");
        	for (int i = 0; i< artists.length(); i++) {
        		sendEventsSms(sub, artists.getJSONObject(i).getJSONObject("topartists").getJSONObject("artist").getString("name").replace(" ", "%20"));
        		
        	}
        } catch (JSONException e) {
        	System.out.println("OH SHIIIIIT");
        }
	}
	
	public Subscription getSubscription(final String phone) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			List<Subscription> matches = (List<Subscription>) pm.newQuery("select from " + Subscription.class.getName() 
					+ " where number == " + "'" + phone + "'").execute();
			if (matches.isEmpty()) {
				return null;
			}
			return matches.get(0);
		} finally {
			pm.close();
		}
	}
	
	public boolean setActive(final Subscription sub) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			List<Subscription> matches = (List<Subscription>) pm.newQuery("select from " + Subscription.class.getName() 
					+ " where number == " + "'" + sub.getNumber() + "'").execute();
			if (matches.isEmpty()) {
				return false;
			} 
			matches.get(0).setActive(true);
			return true;
		} finally {
			pm.close();
		}
	}
	
	public void updateEverybody() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			List<Subscription> matches = (List<Subscription>) pm.newQuery("select from " + Subscription.class.getName() 
					+ " where active == true").execute();
			for (Subscription s : matches) {
				if (s.getArtist() == null) {
					this.sendEventsSmsForUsername(s);
				} else {
					this.sendEventsSms(s);
				}
			}
		} finally {
			pm.close();
		}
	}
	
	public void deactivateUser(String phone) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			List<Subscription> matches = (List<Subscription>) pm.newQuery("select from " + Subscription.class.getName() 
					+ " where number == " + "'" + phone + "' and active == true").execute();
			if (!matches.isEmpty()) {
				matches.get(0).setActive(false);
			}
		} finally {
			pm.close();
		}
	}
	
}