package com.craigsc.hacku2010.domain;

import java.util.Random;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class Subscription {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;
	
	@Persistent
	private String number;
	
	@Persistent
	private double lat;
	
	@Persistent
	private double lon;
	
	@Persistent
	private String artist;
	
	@Persistent
	private String verificationCode;
	
	@Persistent
	private boolean active;
	
	@Persistent
	private String username;
	
	public Subscription(final String number, final double lat, 
						final double lon, final String artist,
						final String username) {
		this.number = number;
		this.lat = lat;
		this.lon = lon;
		this.artist = artist;
		this.verificationCode = generateVerificationCode();
		this.active = false;
		this.username = username;
	}

	/**
	 * Generates a random string of length 6 consisting of all
	 * capital letters.
	 * @return verification code to be sent to the user
	 */
	private String generateVerificationCode() {
		String code = "";
		Random ran = new Random();
		for (int i = 0; i < 6; i++) {
			code += (char) (ran.nextInt(26) + 65);
		}
		return code;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public Key getKey() {
		return key;
	}
	
	public boolean getActive() {
		return active;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public String getVerificationCode() {
		return verificationCode;
	}
	
	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}
	
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}
}
