function validate(form) {
	var firstName = form.firstname.value;
	var lastName = form.lastname.value;
	var id = form.id.value;
	var gender = document.getElementById("genderSelect");
	var working = document.getElementById("workingSelect");
	var errors = [];
	
	if(!isNaN(firstName) && firstName.length != 0) {
		errors.push("'First Name' field cannot be a number.");
	}
	
	if(!isNaN(lastName) && lastName.length != 0) {
		errors.push("'Last Name' field cannot be a number.");
	}
	
	if(isNaN(id)) {
		errors.push("'Employee Id' field must be a number.");
	}

	if(errors.length == 0 && firstName.length == 0 && lastName.length == 0 &&
			gender.selectedIndex == 0 && working.selectedIndex == 0) {
		errors.push("You must enter atleast one field.");
	}
	
	if(errors.length > 0) {
		reportErrors(errors);
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