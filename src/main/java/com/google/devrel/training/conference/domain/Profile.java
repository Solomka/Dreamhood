package com.google.devrel.training.conference.domain;

import java.util.ArrayList;
import java.util.List;

import com.google.devrel.training.conference.form.ProfileForm.Month;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;


//TODO indicate that this class is an Entity
	 @Entity
	 @Cache
public class Profile {

	String name;
	String surname;
	String mainEmail;
	String city;
	String country;

	String day;
	String year;
	// Day day;
	Month month;
	// Year year;
	// TODO indicate that the userId is to be used in the Entity's key
	 @Id
	String userId;

	/**
	 * Keys of the ideas that this user will publish.
	 */
	private List<String> ideaKeys = new ArrayList<>(0);

	/**
	 * Public constructor for Profile.
	 * 
	 * @param userId
	 *            The user id, obtained from the email
	 * @param nickName
	 *            Any string user wants us to display him/her on this system.
	 * @param mainEmail
	 *            User's main e-mail address.
	 * @param city
	 *            The city where users live
	 * @param country
	 *            The country from where user is
	 *
	 */
	public Profile(String userId, String name, String surname,
			String mainEmail, String city, String country, String day,
			Month month, String year) {

		this.userId = userId;
		this.name = name;
		this.surname = surname;
		this.mainEmail = mainEmail;
		this.city = city;
		this.country = country;
		this.day = day;
		this.month = month;
		this.year = year;

	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public String getDay() {
		return day;
	}

	public Month getMonth() {
		return month;
	}

	public String getYear() {
		return year;
	}

	public String getMainEmail() {
		return mainEmail;
	}

	public String getCity() {
		return city;
	}

	public String getCountry() {
		return country;
	}

	public String getUserId() {
		return userId;
	}

	/**
	 * Getter for getIdeaKeys.
	 * 
	 * @return an immutable copy of getIdeaKeys.
	 */
	public List<String> getIdeaKeys() {
		return ideaKeys;
		// return ImmutableList.copyOf(ideaKeys);
	}

	/**
	 * Just making the default constructor private.
	 */
	private Profile() {
	}

	/**
	 * Update the Profile with the given nickName, city and country
	 *
	 * @param nickName
	 * @param city
	 * @param country
	 * 
	 */
	public void update(String name, String surname, String city,
			String country, String day, Month month, String year) {
		if (name != null) {
			this.name = name;
		}
		if (surname != null) {
			this.surname = surname;
		}
		if (city != null) {
			this.city = city;
		}
		if (country != null) {
			this.country = country;
		}
		if (day != null) {
			this.day = day;
		}
		if (month != null) {
			this.month = month;
		}
		if (year != null) {
			this.year = year;
		}

	}

	/**
	 * Adds a IdeaKey to IdeaKeys.
	 *
	 *
	 * @param ideaKey
	 *            a websafe String representation of the Idea Key.
	 */
	public void addToIdeaKeys(String ideaKey) {
		ideaKeys.add(ideaKey);
	}

}