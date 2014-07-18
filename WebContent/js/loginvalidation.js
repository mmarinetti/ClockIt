function validate(form){
	var userName = form.username.value;
	var password = form.password.value;

	if (userName.length === 0) {
		alert("You must enter a username.");
		return false;
	}

	if (password.length === 0) {
		alert("You must enter a password.");
		return false;
	}

	return true;
}