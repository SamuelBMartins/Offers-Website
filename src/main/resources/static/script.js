window.onload = function(){
    hideHelps();

    nameBool = false;
    surnameBool = false;
    usernameBool = false;
    passwordBool = false;
    confirmPasswordBool = false;

    var submitButton = document.getElementById("register-submit");
    clnSubmit = submitButton.cloneNode(true);
    submitButton.remove();

    var name = document.getElementById("nameInput");
    name.oninput = function(){
        var nameHelpBlock = document.getElementById("nameHelpBlock");
        if (name.value.length > 0 && /^[a-zA-Z]*$/.test(name.value)) {
            nameHelpBlock.style.display = 'none';
            isValid(name.classList);
            nameBool = true;
        } else {
            nameHelpBlock.style.display = 'inline';
            isInvalid(name.classList);
            nameBool = false;
        }
        submit();
    };

    var surname = document.getElementById("surnameInput");
    surname.oninput = function(){
        var surnameHelpBlock = document.getElementById("surnameHelpBlock");
        if (surname.value.length > 0 && /^[a-zA-Z]*$/.test(surname.value)) {
            surnameHelpBlock.style.display = 'none';
            isValid(surname.classList);
            surnameBool = true;
        } else {
            surnameHelpBlock.style.display = 'inline';
            isInvalid(surname.classList);
            surnameBool = false;
        }
        submit();
    };

    var username = document.getElementById("usernameInput");
    username.oninput = function(){
        var usernameHelpBlock = document.getElementById("usernameHelpBlock");
        if (username.value.length > 0 && /^[a-zA-Z0-9_]*$/.test(username.value)) {
            usernameHelpBlock.style.display = 'none';
            isValid(username.classList);
            usernameBool = true;
        } else {
            usernameHelpBlock.style.display = 'inline';
            isInvalid(username.classList);
            usernameBool = false;
        }
        submit();
    };

    var confirmPassword = document.getElementById("confirmPasswordInput");
    confirmPassword.oninput = function(){
        var confirmPasswordHelpBlock = document.getElementById("confirmPasswordHelpBlock");
        if (confirmPassword.value === password.value) {
            confirmPasswordHelpBlock.style.display = 'none';
            isValid(confirmPassword.classList);
            confirmPasswordBool = true;
        } else {
            confirmPasswordHelpBlock.style.display = 'inline';
            isInvalid(confirmPassword.classList);
            confirmPasswordBool = false;
        }
        submit();
    };

    var password = document.getElementById("passwordInput");
    password.oninput = function(){
        var passwordHelpBlock = document.getElementById("passwordHelpBlock");
        if (password.value.length >= 8 && password.value.length <= 15 && /^[a-zA-Z0-9_]*$/.test(password.value)) {
            passwordHelpBlock.style.display = 'none';
            isValid(password.classList);
            passwordBool = true;
        } else {
            passwordHelpBlock.style.display = 'inline';
            isInvalid(password.classList);
            passwordBool = false;
        }
        confirmPassword.oninput();
        submit();
    };

};

function submit() {
    var submit = document.getElementById("register-submit");
    var register = document.getElementById("register-form");
    if (nameBool && surnameBool && usernameBool && passwordBool && confirmPasswordBool) {
        if (submit == null) {
            register.appendChild(clnSubmit);
        }
    } else {
        if (submit != null) {
            register.removeChild(submit);
        }
    }
}

function isValid(classList) {
    if (classList.contains("is-invalid")) {
        classList.remove("is-invalid");
    }

    if (!classList.contains("is-valid")) {
        classList.add("is-valid");
    }
}

function isInvalid(classList) {
    if (!classList.contains("is-invalid")) {
        classList.add("is-invalid");
    }

    if (classList.contains("is-valid")) {
        classList.remove("is-valid");
    }
}

function hideHelps() {
    document.getElementById("nameHelpBlock").style.display = 'none';
    document.getElementById("surnameHelpBlock").style.display = 'none';
    document.getElementById("usernameHelpBlock").style.display = 'none';
    document.getElementById("passwordHelpBlock").style.display = 'none';
    document.getElementById("confirmPasswordHelpBlock").style.display = 'none';

}
