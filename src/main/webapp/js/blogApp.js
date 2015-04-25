'use strict';
/*

Simple blog front end demo in order to learn AngularJS - You can add new posts, add comments, and like posts.

*/


(function(){
  var app = angular.module('blogApp',[]);
  
  app.controller('BlogController', ['$http', function($http){
    
    var blog = this;
        
    blog.posts = {};
    
    //витяг постів з бази даних
    
    
 $http.get('https://s3-us-west-2.amazonaws.com/s.cdpn.io/110131/posts_1.json').success(function(data){
        blog.posts = data;
      });
    

 
//significant for us=)
 /**
  * Invokes the conference.getConferencesCreated method.
  */
 /*
 $scope.getConferencesCreated = function () {
 	//завантаження методу
     $scope.loading = true;
     gapi.client.conference.getConferencesCreated().
         execute(function (resp) {
             $scope.$apply(function () {
                 $scope.loading = false;
                 if (resp.error) {
                     // The request has failed.
                     var errorMessage = resp.error.message || '';
                     $scope.messages = 'Failed to query the conferences created : ' + errorMessage;
                     $scope.alertStatus = 'warning';
                     $log.error($scope.messages);

                     if (resp.code && resp.code == HTTP_ERRORS.UNAUTHORIZED) {
                         oauth2Provider.showLoginModal();
                         return;
                     }
                 } else {
                     // The request has succeeded.
                     $scope.submitted = false;
                     $scope.messages = 'Query succeeded : Conferences you have created';
                     $scope.alertStatus = 'success';
                     $log.info($scope.messages);

                     $scope.conferences = [];
                     angular.forEach(resp.items, function (conference) {
                         $scope.conferences.push(conference);
                     });
                 }
                 $scope.submitted = true;
             });
         });
 };
 */
    
   /* $http.success(function(data){
      blog.posts = data;
    });
   */
    
    blog.tab = 'blog';
    
    blog.selectTab = function(setTab){
      blog.tab = setTab;
      console.log(blog.tab)
    };
    
    blog.isSelected = function(checkTab){
      return blog.tab === checkTab;
    };
    //збереження постів в базу даних
    
    blog.post = {};
    
    
    
    blog.addPost = function(){
        blog.post.createdOn = Date.now();
        blog.post.comments = [];
        blog.post.likes = 0;
        blog.posts.unshift(this.post);
        blog.tab = 0;
        blog.post ={};
      };   
      
    /**
     * @ngdoc controller
     * @name CreateConferenceCtrl
     *
     * @description
     * A controller used for the Create conferences page.
     */
  
            /**
             * The conference object being edited in the page.
             * @type {{}|*}
             */
           // $scope.conference = $scope.conference || {};

            

            /**
             * Invokes the conference.createConference API.
             *
             * @param conferenceForm the form object.
             */
     /* 
            blog.createIdea = function (ideaform) {
                

                $scope.loading = true;
                gapi.client.dreamhood.createIdea($scope.conference).
                    execute(function (resp) {
                        $scope.$apply(function () {
                            $scope.loading = false;
                            if (resp.error) {
                                // The request has failed.
                                var errorMessage = resp.error.message || '';
                                $scope.messages = 'Failed to create a conference : ' + errorMessage;
                                $scope.alertStatus = 'warning';
                                $log.error($scope.messages + ' Conference : ' + JSON.stringify($scope.conference));

                                if (resp.code && resp.code == HTTP_ERRORS.UNAUTHORIZED) {
                                    oauth2Provider.showLoginModal();
                                    return;
                                }
                            } else {
                                // The request has succeeded.
                                $scope.messages = 'The conference has been created : ' + resp.result.name;
                                $scope.alertStatus = 'success';
                                $scope.submitted = false;
                                $scope.conference = {};
                                $log.info($scope.messages + ' : ' + JSON.stringify(resp.result));
                            }
                        });
                    });
            };
      
*/   
    
    
  }]);
  
  app.controller('CommentController', function(){
    this.comment = {};
    this.addComment = function(post){
      this.comment.createdOn = Date.now();
      post.comments.push(this.comment);
      this.comment ={};
    };
  });
 
})();

