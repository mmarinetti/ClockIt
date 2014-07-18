function validate(form) {
	var firstName = form.first.value;
	var lastName = form.last.value;
	var age = form.age.value;
	var gender = document.getElementById("genderSelect");
	var user = form.username.value;
	var pass = form.password.value;
	var passConfirm = form.passwordConfirm.value;
	var errors = [];
	
	if(!checkLength(firstName)) {
		errors.push("'First Name:' field is invalid.");
	}
	
	if(!checkLength(lastName)) {
		errors.push("'Last Name:' field is invalid.");
	}
	
	if(isNaN(age) || !checkLength(age)) {
		var msg = "'Age:' field is invalid.";
		if(isNaN(age)) {
			msg += " Not a number.";
		}
		errors.push(msg);
	}
	
	if(!checkGender(gender)) {
		errors.push("'Gender:' field is invalid.");
	}
	
	if(!checkUser(user)) {
		errors.push("'User Name:' field is invalid. Must be between" +
				" 3 and 20 characters.");
	}
	
	if(!checkLength(pass)) {
		errors.push("'Password:' field is invalid.");
	}
	
	if(pass != passConfirm) {
		errors.push("Password entries must match.");
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

function checkUser(text) {
	var min = 3;
	var max = 20;
	
	if(text.length < min || text.length > max) {
		return false;
	}
	
	return true;
}

function checkGender(select) {
	return (select.selectedIndex > 0);
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