function confirmDelete(form) {
	var c = confirm("Are you sure you want to delete this employee?");
	
	if(c == true) {
		return true;
	} else {
		return false;
	}
}