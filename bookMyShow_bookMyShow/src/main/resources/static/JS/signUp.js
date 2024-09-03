function validateForm() {
    let isValid = true;

    // Username validation
    const userName = document.getElementById('userName');
    const userNameError = document.getElementById('userNameError');
    const userNamePattern = /^[a-zA-Z]+$/; // Only letters allowed
    if (!userNamePattern.test(userName.value)) {
        userNameError.style.display = 'block';
        isValid = false;
    } else {
        userNameError.style.display = 'none';
    }

    // Email validation
    const email = document.getElementById('email');
    const emailError = document.getElementById('emailError');
    const emailPattern = /^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$/;
    if (!emailPattern.test(email.value)) {
        emailError.style.display = 'block';
        isValid = false;
    } else {
        emailError.style.display = 'none';
    }

// Initialize variables
const password = document.getElementById('password');
const passwordError = document.getElementById('passwordError');

// Define password pattern for validation
const passwordPattern = new RegExp('(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[$!%*?&])[A-Za-z\\d$!%*?&]{8,}');

// Initialize isValid to true

// Validate password
/*if (!passwordPattern.test(password.value)) 
{
    passwordError.innerText = 'Password does not meet the security criteria.';
    passwordError.style.display = 'block';
    isValid = false;
}
else
{
    passwordError.style.display = 'none';
}*/

if (!passwordPattern.test(password.value)) {
    passwordError.innerText = 'Please meet the password requirements.';
    passwordError.classList.add('error-visible');
    passwordError.classList.remove('error-hidden');
    isValid = false;
} else {
    passwordError.classList.add('error-hidden');
    passwordError.classList.remove('error-visible');
}



// Final validation state
return isValid;
}