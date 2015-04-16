package com.google.devrel.training.conference.domain;

import static com.google.devrel.training.conference.service.OfyService.ofy;

import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.ApiResourceProperty;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Parent;

//TODO indicate that this class is an Entity
@Entity
@Cache
public class Idea {

	@Id
	private Long id;

	private String topic;

	private String description;

	// private int mark;

	private boolean done = false;

	// private Object photo;

	/**
	 * Holds Profile key as the parent.
	 */
	@Parent
	@ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
	private Key<Profile> profileKey;

	/**
	 * The userId of the organizer.
	 */
	@ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
	private String organizerUserId;

	/**
	 * Just making the default constructor private.
	 */
	private Idea() {
	}

	/*
	 * public Idea(final long id, final String organizerUserId, final IdeaForm
	 * ideaForm) { Preconditions.checkNotNull(ideaForm.getTopic(),
	 * "The name is required"); this.id = id; this.profileKey =
	 * Key.create(Profile.class, organizerUserId); this.organizerUserId =
	 * organizerUserId; updateWithIdeaForm(ideaForm); }
	 */

	public Idea(final long id, final String organizerUserId, String topic,
			String description) {
		// Preconditions.checkNotNull(ideaForm.getTopic(),
		// "The name is required");
		this.id = id;
		this.profileKey = Key.create(Profile.class, organizerUserId);
		this.organizerUserId = organizerUserId;
		this.topic = topic;
		this.description = description;
		// updateWithIdeaForm(ideaForm);
	}

	public String getTopic() {
		return topic;
	}

	public String getDescription() {
		return description;
	}

	/*
	 * public Object getPhoto() { return photo; }
	 */

	public boolean isDone() {
		return done;
	}

	/*
	 * public int getMark() { return mark; }
	 */

	public long getIdeaId() {
		return id;
	}

	@ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
	public Key<Profile> getProfileKey() {
		return profileKey;
	}

	// Get a String version of the key
	public String getWebsafeKey() {
		return Key.create(profileKey, Idea.class, id).getString();
	}

	@ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
	public String getOrganizerUserId() {
		return organizerUserId;
	}

	/**
	 * Returns organizer's display name.
	 *
	 * @return organizer's display name. If there is no Profile, return his/her
	 *         userId.
	 */
	public String getOrganizerDisplayName() {
		// Profile organizer = ofy().load().key(Key.create(Profile.class,
		// organizerUserId)).now();
		Profile organizer = ofy().load().key(getProfileKey()).now();
		if (organizer == null) {
			return organizerUserId;
		} else {
			return organizer.getName();
		}
	}

	/**
	 * Updates the Idea with IdeaForm. This method is used upon object creation
	 * as well as updating existing Ideas.
	 *
	 * @param ideaForm
	 *            contains form data sent from the client.
	 */
	/*
	 * public void updateWithIdeaForm(IdeaForm ideaForm) { this.topic =
	 * ideaForm.getTopic(); this.description = ideaForm.getDescription(); //
	 * this.mark = ideaForm.getMark(); // this.done = ideaForm.getDone(); //
	 * this.photo = ideaForm.getPhoto();
	 * 
	 * }
	 */
	public void update(String topic, String description) {
		this.topic = topic;
		this.description = description;
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder("Id: " + id + "\n")
				.append("Idea: ").append(topic).append("\n");
		if (description != null) {
			stringBuilder.append("Description: ").append(description)
					.append("\n");
		}
		return stringBuilder.toString();
	}
}