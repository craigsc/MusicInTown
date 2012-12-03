var data = [];
$(document).ready(function() {
	//nice html5-styled placeholders
	$("input[placeholder]").placeholder();
	
	//artist autocomplete
	$("input#artist").autocomplete({
		source: function(tag, fn) {
					$.getJSON('http://query.yahooapis.com/v1/public/yql?q=select%20results.artistmatches.artist.name%20from%20lastfm.artist.search%20where%20api_key%20%3D%20%22c08c9ffc6968f3fb8eb98dfc9ca221ec%22%20and%20limit%20%3D%2010%20and%20artist%20%3D%20%22' + tag.term + '%22%20&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=?',
						function(json) {
						if (json.query.results && json.query.results.lfm) {
							var result = new Array();
							$.each(json.query.results.lfm, function(i,item) {
								if (item.results) {
									result[i] = item.results.artistmatches.artist.name;
								}
							});
							data = result;
							fn(result);
						}
						}
					);
				}
	});

	//attempt to get location
	if(navigator.geolocation) {
		navigator.geolocation.getCurrentPosition(function(position) {
			var lat = position.coords.latitude;
		    var lon = position.coords.longitude;
		    $("input#lat").val(lat);
		    $("input#long").val(lon);
		    $.getJSON('http://query.yahooapis.com/v1/public/yql?q=select%20postal%2C%20locality1%20from%20geo.places%20where%20woeid%20in%20(%0A%20%20select%20place.woeid%20from%20flickr.places%20where%20lat%3D' + lat + '%20and%20lon%3D' + lon + '%0A)&format=json&callback=?',
					function(json) {
		    			var displayResult = json.query.results.place.postal ? json.query.results.place.postal : json.query.results.place.locality1.content;
		    			//$("#town").val(displayResult);
		    			document.getElementById("town").innerHTML = displayResult;
		    			$("#manualLoc").hide();
		    			$("#autoLoc").show();
					}
			);
			}, function(error){
				//lookup by ip?
			}
		);
	}

	function validate_phone(){
    	var stripped = $("input#phone").val().replace(/[\(\)\.\-\ ]/g, '');     
   		if (stripped == "" || isNaN(parseInt(stripped)) || !(stripped.length == 10)) {
        	return false;
   		}
  		return true;
	}
	
	function validate_artist(){
		var valid = false;
		$.each(data, function(i, item) {
			if ($("input#artist").val().toLowerCase() == item.toLowerCase()) {
				valid = true;
			}
		});
		return valid;
	}
	
	function validate_geo() {
		if (!$("input#lat").val() || $("input#lat").val() == "" 
			|| !$("input#long").val() || $("input#long").val() == "") {
			var zip = $("input#zipcode").val();
			if (!zip || zip == "" || isNaN(parseInt(zip)) || zip.length !== 5) {
				return false;
			} else {
				var valid = true;
				$.getJSON('http://query.yahooapis.com/v1/public/yql?q=select%20centroid.latitude%2Ccentroid.longitude%20from%20geo.places%20where%20text%20%3D%20' + zip + '%20limit%201&format=json',
						function(json) {
							if (json.query.results != null) {
								$("input#lat").val(json.query.results.place.centroid.latitude);
								$("input#long").val(json.query.results.place.centroid.longitude);
							} else {
								valid = false;
							}
				});
				return valid;	
			}
		} else {
			return true;
		}
	}
	
	
	var isIn = true;
	$('#clickme').click(function() {
		if(isIn==false){
  			$('#tagLine').slideUp('slow', function() {
  				isIn = true;
    			// Animation complete.
  			});
		} else {
			$('#tagLine').slideDown('slow', function() {
  				isIn = false;
    			// Animation complete.
  			});
		}
	});

	function validate_form() {
		var valid = true;
		if (!validate_artist() && $("input#isUsername").val() !== "1") {
			valid = false;
			//$("input#fhArtist").removeClass('fieldHolder').addClass('validationError');
			$("input#artist").removeClass('textInput').addClass('validationError');
		}
		if (!validate_phone()) {
			valid = false;
			//$("input#fhPhone").removeClass('fieldHolder').addClass('validationError');
			$("input#phone").removeClass('textInput').addClass('validationError');
		}
		if (!validate_geo()) {
			valid = false;
			//$("input#fhZip").removeClass('fieldHolder').addClass('validationError');
			$("input#zipcode").removeClass('textInput').addClass('validationError');
		}
		if (!$("input#lat").val() || !("input#long").val()) {
			valid = false;
		}
		return valid;
	}
	
	$("#smallText").click(function() {
		manualGeo();
		return false;
	});
	
	function manualGeo() {
		$("input#lat").val("");
		$("input#long").val("");
		$("#autoLoc").hide();
		$("#manualLoc").show();
	}
	
	$('input#artist').blur(function() {
		$(this).removeClass('validationError').addClass('textInput');
		$.getJSON('http://ws.audioscrobbler.com/2.0/?method=user.getinfo&user=' + $("input#artist").val() + '&format=json&api_key=c08c9ffc6968f3fb8eb98dfc9ca221ec&callback=?',
				function(json) {
					if (json.user) {
						$("input#isUsername").val("1");
					} else {
						$("input#isUsername").val("0");
					}
		});
	});
	
	$('input#phone').blur(function() {
		$(this).removeClass('validationError').addClass('textInput');
	});
	
	$('input#zipcode').blur(function() {
		$(this).removeClass('validationError').addClass('textInput');
		/*var zip = $("input#zipcode").val();
		if (zip && zip !== "" && !isNaN(parseInt(zip)) && zip.length == 5) {
			$.getJSON('http://query.yahooapis.com/v1/public/yql?q=select%20centroid.latitude%2Ccentroid.longitude%20from%20geo.places%20where%20text%20%3D%20' + zip + '%20limit%201&format=json',
					function(json) {
						if (json.query.results != null) {
							$("input#lat").val(json.query.results.place.centroid.latitude);
							$("input#long").val(json.query.results.place.centroid.longitude);
						}
			});
		}*/
	});
	
	//validate and submit
	$('#form').submit(function(event) {
		return validate_form();
	});
});