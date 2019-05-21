function confirmDelete(){
    var resp = confirm("Are you sure you want to delete the team member?");
    if(resp){
        document.getElementById("delete-form").submit();
    }
}

function validateForm(){
    var name = document.getElementById("name").value;
    var role = document.getElementById("role").value;

    if(isAlphabetic(name.trim()) && isAlphabetic(role.trim())){
        return true;
    } else {
        alert('Only non-empty alphabetic characters are allowed in the name and role fields');
    }
    return false;
}

function isAlphabetic(str){
    return (/^[a-zA-Z ]+$/).test(str);
}