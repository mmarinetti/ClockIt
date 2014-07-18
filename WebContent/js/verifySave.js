function validate(form) {
	var firstName = form.first.value;
	var lastName = form.last.value;
	var age = form.age.value;
	var totalHours = form.totalHours.value;
	var errors = [];
	
	if(!checkLength(firstName)) {
		errors.push("'First Name' field is invalid.");
	}
	
	if(!checkLength(lastName)) {
		errors.push("'Last Name' field is invalid.");
	}
	
	if(isNaN(age) || !checkLength(age)) {
		var msg = "'Age' field is invalid.";
		if(isNaN(age)) {
			msg += " Not a number.";
		}
		errors.push(msg);
	}
	
	if(isNaN(totalHours) || !checkLength(totalHours)) {
		var msg = "'Total Hours' field is invalid.";
		if(isNaN(totalHours)) {
			msg += " Not a number.";
		}
		errors.push(msg);
	}
	
	if(errors.length > 0) {
		reportErrors(errors);
		return false;
	}
	
	return true;
}

function checkLength(text) {
	var min = 1;
	var max = 100;
	
	if(text.length < min || text.length > max) {
		return false;
	}
	
	return true;
}

function reportErrors(errors) {
	var msg = "Oops! There was an error.";
	var numError;
	for(var i=0; i<errors.length; i++) {
		numError = i + 1;
		msg += "\n" + numError + ": " + errors[i];
	}
	alert(msg);
}