'use strict';

/**
 * The root dreamApp module.
 * 
 * @type {dreamApp|*|{}}
 */
var dreamApp = dreamApp || {};

/**
 * @ngdoc module
 * @name dreamControllers
 * 
 * @description Angular module for controllers.
 * 
 */
dreamApp.controllers = angular.module('dreamControllers', [ 'ui.bootstrap' ]);

/**
 * @ngdoc controller
 * @name MyProfileCtrl
 * 
 * @description A controller used for the My Profile page.
 */
dreamApp.controllers.controller('MyProfileCtrl', function($scope, $log,
		oauth2Provider, HTTP_ERRORS) {
	$scope.submitted = false;
	$scope.loading = false;

	/**
	 * The initial profile retrieved from the server to know the dirty state.
	 * 
	 * @type {{}}
	 */
	$scope.initialProfile = {};

	/**
	 * Candidates for the day select box.
	 * 
	 * @type {string[]}
	 */

	$scope.days = [ '1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17',
			'18', '19', '20', '21', '22', '23', '24', '25', '26', '27', '28', '29', '30', '31' ];

	/**
	 * Candidates for the month select box.
	 * 
	 * @type {string[]}
	 */

	$scope.months = [ 'Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug',
			'Sep', 'Oct', 'Nov' ];

	/**
	 * Candidates for the year select box.
	 * 
	 * @type {string[]}
	 */
	$scope.years = [ '1975', '1976', '1977', '1978', '1979', '1980', '1981',
			'1982', '1983', '1984', '1985', '1986', '1987', '1988', '1989',
			'1990', '1991', '1992', '1993', '1994', '1995', '1996', '1997',
			'1998', '1999', '2000', '2001', '2002', '2003', '2004', '2005',
			'2006', '2007', '2008', '2009', '2010', '2011', '2012', '2013',
			'2014', '2015' ];

	/**
	 * Initializes the My profile page. Update the profile if the user's profile
	 * has been stored.
	 */
	$scope.init = function() {
		var retrieveProfileCallback = function() {
			$scope.profile = {};
			$scope.loading = true;
			gapi.client.dreamhood.getProfile().execute(function(resp) {
				$scope.$apply(function() {
					$scope.loading = false;
					if (resp.error) {
						// Failed to get a user profile.
					} else {
						// Succeeded to get the user profile.
						$scope.profile.name = resp.result.name;
						$scope.profile.surname = resp.result.surname;
						$scope.profile.city = resp.result.city;
						$scope.profile.country = resp.result.country;
						$scope.profile.day = resp.result.day;
						$scope.profile.month = resp.result.month;
						$scope.profile.year = resp.result.year;

						$scope.initialProfile = resp.result;
					}
				});
			});
		};
		if (!oauth2Provider.signedIn) {
			var modalInstance = oauth2Provider.showLoginModal();
			modalInstance.result.then(retrieveProfileCallback);
		} else {
			retrieveProfileCallback();
		}
	};

	/**
	 * Invokes the conference.saveProfile API.
	 * 
	 */
	$scope.saveProfile = function() {
		alert("I am here");
		$scope.submitted = true;
		$scope.loading = true;
		gapi.client.dreamhood.saveProfile($scope.profile).execute(
				function(resp) {
					$scope.$apply(function() {
						$scope.loading = false;
						if (resp.error) {
							// The request has failed.
							var errorMessage = resp.error.message || '';
							$scope.messages = 'Failed to update a profile : '
									+ errorMessage;
							$scope.alertStatus = 'warning';
							$log.error($scope.messages + 'Profile : '
									+ JSON.stringify($scope.profile));

							if (resp.code
									&& resp.code == HTTP_ERRORS.UNAUTHORIZED) {
								oauth2Provider.showLoginModal();
								return;
							}
						} else {
							alert("I am here");
							// The request has succeeded.
							$scope.messages = 'The profile has been updated';
							$scope.alertStatus = 'success';
							$scope.submitted = false;
							$scope.initialProfile = {
								name : $scope.profile.name,
								surname : $scope.profile.surname,
								city : $scope.profile.city,
								country : $scope.profile.country,
								day : $scope.profile.day,
								month : $scope.profile.month,
								year : $scope.profile.year
							};

							$log.info($scope.messages
									+ JSON.stringify(resp.result));
						}
					});
				});
	};
});



/**
 * @ngdoc controller
 * @name ProfileDetailCtrl
 *
 * @description
 * A controller used for the profile detail page.
 */
/*
dreamApp.controllers.controller('ProfileDetailCtrl', function ($scope, $log, $routeParams, HTTP_ERRORS) {
	$scope.profile = {};
	*/

       /**
     * Initializes the profile detail page.
     * Invokes the profile.getProfile method and sets the returned profile in the $scope.
     *
     */
/*
    $scope.init = function () {
        $scope.loading = true;
        gapi.client.dreamhood.getProfile().execute(function(resp) {
            $scope.$apply(function () {
                $scope.loading = false;
                if (resp.error) {
                    // The request has failed.
                    var errorMessage = resp.error.message || '';
                    $scope.messages = 'Failed to get the conference : '  + ' ' + errorMessage;
                    $scope.alertStatus = 'warning';
                    $log.error($scope.messages);
                } else {
                    // The request has succeeded.
                    $scope.alertStatus = 'success';
                    $scope.profile = resp.result;
                }
            });
        });

       
    };


});

*/

/**
 * @ngdoc controller
 * @name ProfileDetailCtrl
 *
 * @description
 * A controller used for the profile detail page.
 */

dreamApp.controllers.controller('ProfileDetailCtrl',function($scope, $log,
		oauth2Provider, HTTP_ERRORS) {
	 $scope.profile = {};
	
	/**
	 * Initializes the My profile page.
	 */
	$scope.init = function() {
		var retrieveProfileCallback = function() {
			$scope.loading = true;
			gapi.client.dreamhood.getProfile().execute(function(resp) {
				$scope.$apply(function() {
					$scope.loading = false;
					if (resp.error) {
						// Failed to get a user profile.
					} else {
						// Succeeded to get the user profile.
						  $scope.alertStatus = 'success';
		                    $scope.profile = resp.result;
		                    
						/*$scope.profile.name = resp.result.name;
						$scope.profile.surname = resp.result.surname;
						$scope.profile.city = resp.result.city;
						$scope.profile.country = resp.result.country;
						$scope.profile.day = resp.result.day;
						$scope.profile.month = resp.result.month;
						$scope.profile.year = resp.result.year;

						/*$scope.initialProfile = resp.result;*/
					}
				});
			});
		};
		if (!oauth2Provider.signedIn) {
			var modalInstance = oauth2Provider.showLoginModal();
			modalInstance.result.then(retrieveProfileCallback);
		} else {
			retrieveProfileCallback();
		}
	};

});



/**
 * @ngdoc controller
 * @name RootCtrl
 * 
 * @description The root controller having a scope of the body element and
 *              methods used in the application wide such as user
 *              authentications.
 * 
 */
// registration
dreamApp.controllers.controller('RootCtrl', function($scope, $location,
		oauth2Provider) {

	/**
	 * Returns if the viewLocation is the currently viewed page.
	 * 
	 * @param viewLocation
	 * @returns {boolean} true if viewLocation is the currently viewed page.
	 *          Returns false otherwise.
	 */
	$scope.isActive = function(viewLocation) {
		return viewLocation === $location.path();
	};

	/**
	 * Returns the OAuth2 signedIn state.
	 * 
	 * @returns {oauth2Provider.signedIn|*} true if siendIn, false otherwise.
	 */
	$scope.getSignedInState = function() {
		return oauth2Provider.signedIn;
	};

	/**
	 * Calls the OAuth2 authentication method.
	 */
	$scope.signIn = function() {
		oauth2Provider.signIn(function() {
			gapi.client.oauth2.userinfo.get().execute(function(resp) {
				$scope.$apply(function() {
					if (resp.email) {
						oauth2Provider.signedIn = true;
						$scope.alertStatus = 'success';
						$scope.rootMessages = 'Logged in with ' + resp.email;
					}
				});
			});
		});
	};

	/**
	 * Render the signInButton and restore the credential if it's stored in the
	 * cookie. (Just calling this to restore the credential from the stored
	 * cookie. So hiding the signInButton immediately after the rendering)
	 */
	$scope.initSignInButton = function() {
		gapi.signin.render('signInButton', {
			'callback' : function() {
				jQuery('#signInButton button').attr('disabled', 'true').css(
						'cursor', 'default');
				if (gapi.auth.getToken() && gapi.auth.getToken().access_token) {
					$scope.$apply(function() {
						oauth2Provider.signedIn = true;
					});
				}
			},
			'clientid' : oauth2Provider.CLIENT_ID,
			'cookiepolicy' : 'single_host_origin',
			'scope' : oauth2Provider.SCOPES
		});
	};

	/**
	 * Logs out the user.
	 */
	$scope.signOut = function() {
		oauth2Provider.signOut();
		$scope.alertStatus = 'success';
		$scope.rootMessages = 'Logged out';
	};

	/**
	 * Collapses the navbar on mobile devices.
	 */
	$scope.collapseNavbar = function() {
		angular.element(document.querySelector('.navbar-collapse'))
				.removeClass('in');
	};

});

/**
 * @ngdoc controller
 * @name OAuth2LoginModalCtrl
 * 
 * @description The controller for the modal dialog that is shown when an user
 *              needs to login to achive some functions.
 * 
 */
dreamApp.controllers.controller('OAuth2LoginModalCtrl', function($scope,
		$modalInstance, $rootScope, oauth2Provider) {
	$scope.singInViaModal = function() {
		oauth2Provider.signIn(function() {
			gapi.client.oauth2.userinfo.get().execute(function(resp) {
				$scope.$root.$apply(function() {
					oauth2Provider.signedIn = true;
					$scope.$root.alertStatus = 'success';
					$scope.$root.rootMessages = 'Logged in with ' + resp.email;
				});

				$modalInstance.close();
			});
		});
	};
});

/**
 * @ngdoc controller
 * @name DatepickerCtrl
 * 
 * @description A controller that holds properties for a datepicker.
 */
dreamApp.controllers
		.controller('DatepickerCtrl',
				function($scope) {
					$scope.today = function() {
						$scope.dt = new Date();
					};
					$scope.today();

					$scope.clear = function() {
						$scope.dt = null;
					};

					// Disable weekend selection
					$scope.disabled = function(date, mode) {
						return (mode === 'day' && (date.getDay() === 0 || date
								.getDay() === 6));
					};

					$scope.toggleMin = function() {
						$scope.minDate = ($scope.minDate) ? null : new Date();
					};
					$scope.toggleMin();

					$scope.open = function($event) {
						$event.preventDefault();
						$event.stopPropagation();
						$scope.opened = true;
					};

					$scope.dateOptions = {
						'year-format' : "'yy'",
						'starting-day' : 1
					};

					$scope.formats = [ 'dd-MMMM-yyyy', 'yyyy/MM/dd',
							'shortDate' ];
					$scope.format = $scope.formats[0];
				});
