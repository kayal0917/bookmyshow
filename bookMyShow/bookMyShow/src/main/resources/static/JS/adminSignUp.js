
        function validateForm() {
            var userName = document.getElementById("userName").value;
            var password = document.getElementById("password").value;
            var location = document.getElementById("location").value;
            var theater = document.getElementById("theater").value;
            
            var userNamePattern = /^[a-zA-Z][a-zA-Z0-9_-]{3,19}$/;
            var passwordPattern = /(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}/;
            var theaterPattern = /^[a-zA-Z]+$/;

            if (!userNamePattern.test(userName)) {
                alert("Username must start with a letter and be between 4-20 characters long, containing only letters, numbers, underscores, or hyphens.");
                return false;
            }

            if (!passwordPattern.test(password)) {
                alert("Password must be at least 8 characters long, contain at least one uppercase letter, one lowercase letter, one number, and one special character.");
                return false;
            }

            if (location === "") {
                alert("Please select a location.");
                return false;
            }

            if (!theaterPattern.test(theater)) {
                alert("Theater name must contain only letters.");
                return false;
            }

            return true; // If all checks pass, allow the form to be submitted
        }
