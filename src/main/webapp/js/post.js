'use strict';

$(function() {
	  
	  $('#post-form').submit(function(e) {
	    e.preventDefault();
	    
	    var textarea = this.getElementsByTagName('textarea')[0],
	        taHeight = textarea.offsetHeight,
	        taWidth  = textarea.offsetWidth,
	        taTop    = textarea.offsetTop,
	        taLeft   = textarea.offsetLeft,
	        taText   = textarea.value,
	        postTop  = document.getElementsByClassName('post')[0].offsetTop;
	    
	    var $hoverDiv = $('<div>')
	      .addClass('hover')
	      .text(taText)
	      .css({
	        height: taHeight,
	        width: taWidth,
	        top: taTop,
	        left: taLeft
	      });
	    var $postContent = $('<p>')
	      .addClass('post-content')
	      .text(taText)
	      .css('visibility', 'hidden');
	   /* var $postTimestamp = $('<p>')
	      .addClass('post-timestamp')
	      .text('Posted on: ' + new Date().toUTCString())
	      .css('visibility', 'hidden'); */
	    var $post = $('<li>')
	      .addClass('post')
	      .append($postContent)
	     // .append($postTimestamp)
	      .hide();
	    textarea.value = '';
	    $post.prependTo('#feed');
	    $hoverDiv.appendTo('body')
	      .delay(300)
	      .animate({top: postTop}, 1000, 'swing')
	    .queue(function() {
	      $postContent.css('visibility' ,'visible');
	      $hoverDiv.delay(200).stop().fadeOut();
	      $postTimestamp.hide().css('visibility', 'visible').fadeIn();
	    });
	    $post.delay(300).slideDown(1000);
	  });
	  
	});