package com.google.devrel.training.conference.spi;

import static com.google.devrel.training.conference.service.OfyService.factory;
import static com.google.devrel.training.conference.service.OfyService.ofy;

import java.util.List;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.response.UnauthorizedException;
import com.google.appengine.api.users.User;
import com.google.devrel.training.conference.Constants;
import com.google.devrel.training.conference.domain.Idea;
import com.google.devrel.training.conference.domain.Profile;
import com.google.devrel.training.conference.form.IdeaForm;
import com.google.devrel.training.conference.form.ProfileForm;
import com.google.devrel.training.conference.form.ProfileForm.Month;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Work;

/**
 * Defines Dream'Breaker APIs.
 */
@Api(name = "dreamhood", version = "v1", scopes = { Constants.EMAIL_SCOPE }, clientIds = {
		Constants.WEB_CLIENT_ID, Constants.API_EXPLORER_CLIENT_ID }, description = "API for the DreamHood application.")
public class DreamApi {

	private static String extractDefaultDisplayNameFromEmail(String email) {
		return email == null ? null : email.substring(0, email.indexOf("@"));
	}

	// create and save profile
	@ApiMethod(name = "saveProfile", path = "saveProfile", httpMethod = HttpMethod.POST)
	public Profile saveProfile(final User user, ProfileForm profileForm)
			throws UnauthorizedException {

		if (user == null) {
			throw new UnauthorizedException("Authorization required");
		}

		String mainEmail = user.getEmail();
		String userId = user.getUserId();

		String name = profileForm.getName();
		String surname = profileForm.getSurname();
		String city = profileForm.getCity();
		String country = profileForm.getCountry();
		String day = profileForm.getDay();
		Month month = profileForm.getMonth();
		String year = profileForm.getYear();

		// Get the Profile from the datastore if it exists
		// otherwise create a new one
		Profile profile = ofy().load().key(Key.create(Profile.class, userId))
				.now();

		if (profile == null) {
			// Populate the atributes with default values
			// if not sent in the request
			if (name == null) {
				name = extractDefaultDisplayNameFromEmail(user.getEmail());
			}
			if (surname == null) {
				surname = "";
			}
			if (city == null) {
				city = "";
			}
			if (country == null) {
				country = "";
			}
			if (day == null) {
				day = "NOT_SPECIFIED";
			}
			if (month == null) {
				month = Month.NOT_SPECIFIED;
			}
			if (year == null) {
				year = "NOT_SPECIFIED";
			}
			// Now create a new Profile entity
			profile = new Profile(userId, name, surname, mainEmail, city,
					country, day, month, year);
		} else {
			// The Profile entity already exists
			// Update the Profile entity
			profile.update(name, surname, city, country, day, month, year);
		}

		// Save the entity in the datastore
		ofy().save().entity(profile).now();

		// Return the profile
		return profile;
	}

	@ApiMethod(name = "getProfile", path = "profile", httpMethod = HttpMethod.GET)
	public Profile getProfile(final User user) throws UnauthorizedException {
		if (user == null) {
			throw new UnauthorizedException("Authorization required");
		}

		// load the Profile Entity
		String userId = user.getUserId();
		Key key = Key.create(Profile.class, userId);

		Profile profile = (Profile) ofy().load().key(key).now();
		return profile;
	}

	private static Profile getProfileFromUser(User user) {
		// First fetch the user's Profile from the datastore.
		Profile profile = ofy().load()
				.key(Key.create(Profile.class, user.getUserId())).now();
		if (profile == null) {
			// Create a new Profile if it doesn't exist.
			String email = user.getEmail();

			profile = new Profile(user.getUserId(),
					extractDefaultDisplayNameFromEmail(email), "", email, "",
					"", "NOT_SPECIFIED", Month.NOT_SPECIFIED, "NOT_SPECIFIED");
		}
		return profile;
	}

	@ApiMethod(name = "createIdea", path = "idea", httpMethod = HttpMethod.POST)
	public Idea createIdea(final User user, final IdeaForm ideaForm)
			throws UnauthorizedException {
		if (user == null) {
			throw new UnauthorizedException("Authorization required");
		}
		// Allocate Id first, in order to make the transaction idempotent.
		final String userId = user.getUserId();
		Key<Profile> profileKey = Key.create(Profile.class, userId);
		final Key<Idea> ideaKey = factory().allocateId(profileKey, Idea.class);
		final long ideaId = ideaKey.getId();
		// final Queue queue = QueueFactory.getDefaultQueue();

		// Start a transaction.
		Idea idea = ofy().transact(new Work<Idea>() {
			@Override
			public Idea run() {
				// Fetch user's Profile.
				Profile profile = getProfileFromUser(user);
				Idea idea = new Idea(ideaId, userId, ideaForm);
				// Save Conference and Profile.
				ofy().save().entities(idea, profile).now();

				/*
				 * queue.add(ofy().getTransaction(),
				 * TaskOptions.Builder.withUrl("/tasks/send_confirmation_email")
				 * .param("email", profile.getMainEmail()) .param("ideaInfo",
				 * idea.toString()));
				 */
				return idea;
			}
		});
		return idea;
	}

	/*
	 * @ApiMethod(name = "createIdea", path = "createIdea", httpMethod =
	 * HttpMethod.POST) public Idea createIdea(final User user, final IdeaForm
	 * ideaForm) throws UnauthorizedException { if (user == null) { throw new
	 * UnauthorizedException("Authorization required"); } // Allocate Id first,
	 * in order to make the transaction idempotent. final String userId =
	 * user.getUserId(); Key<Profile> profileKey = Key.create(Profile.class,
	 * userId); final Key<Idea> ideaKey = factory().allocateId(profileKey,
	 * Idea.class); final long ideaId = ideaKey.getId(); final Queue queue =
	 * QueueFactory.getDefaultQueue(); final String ideaDescription =
	 * ideaForm.getDescription();
	 * 
	 * // Start a transaction. Idea idea = ofy().transact(new Work<Idea>() {
	 * 
	 * @Override public Idea run() { // Fetch user's Profile. Profile profile =
	 * getProfileFromUser(user);
	 * 
	 * // Idea idea = ofy().load().key(Key.create(Profile.class, // userId)) //
	 * .now(); Idea idea = ofy().load().key(ideaKey).now();
	 * 
	 * if (idea == null) { Preconditions.checkNotNull(ideaForm.getDescription(),
	 * "The topic of idea is required");
	 * 
	 * // Populate the atributes with default values // if not sent in the
	 * request
	 * 
	 * // Now create a new Idea entity idea = new Idea(ideaId, userId,
	 * ideaForm.getDescription());
	 * 
	 * } else { // The Idea entity already exists // Update the Idea entity
	 * idea.update(ideaForm.getDescription()); }
	 * 
	 * // Save Conference and Profile. ofy().save().entities(idea,
	 * profile).now(); // email sending /* queue.add( ofy().getTransaction(),
	 * TaskOptions.Builder .withUrl("/tasks/send_confirmation_email")
	 * .param("email", profile.getMainEmail()) .param("conferenceInfo",
	 * idea.toString()));
	 */
	/*
	 * return idea; } }); return idea; }
	 */

	@ApiMethod(name = "getIdeasCreated", path = "getIdeasCreated", httpMethod = HttpMethod.POST)
	public List<Idea> getIdeasCreated(final User user)
			throws UnauthorizedException {
		// If not signed in, throw a 401 error.
		if (user == null) {
			throw new UnauthorizedException("Authorization required");
		}
		String userId = user.getUserId();
		Key<Profile> userKey = Key.create(Profile.class, userId);
		return ofy().load().type(Idea.class).ancestor(userKey).list();
	}

	/**
	 * Returns a list of Conferences that the user created. In order to receive
	 * the websafeConferenceKey via the JSON params, uses a POST method.
	 *
	 * @param user
	 *            A user who invokes this method, null when the user is not
	 *            signed in.
	 * @return a list of Conferences that the user created.
	 * @throws UnauthorizedException
	 *             when the user is not signed in.
	 */
	@ApiMethod(name = "getIdeaCreated", path = "getIdeaCreated", httpMethod = HttpMethod.POST)
	public List<Idea> getIdeaCreated(final User user)
			throws UnauthorizedException {
		// If not signed in, throw a 401 error.
		if (user == null) {
			throw new UnauthorizedException("Authorization required");
		}
		String userId = user.getUserId();
		Key<Profile> userKey = Key.create(Profile.class, userId);
		return ofy().load().type(Idea.class).ancestor(userKey).list();
		// .order("name").list();
	}

	/*
	 * @ApiMethod(name = "getIdea", path = "idea/{websafeIdeaKey}", httpMethod =
	 * HttpMethod.GET) public Idea getIdea(@Named("websafeIdeaKey") final String
	 * websafeIdeaKey) throws NotFoundException { Key<Idea> ideaKey =
	 * Key.create(websafeIdeaKey); Idea idea = ofy().load().key(ideaKey).now();
	 * if (idea == null) { throw new
	 * NotFoundException("No Idea found with key: " + websafeIdeaKey); } return
	 * idea; }
	 */

}
