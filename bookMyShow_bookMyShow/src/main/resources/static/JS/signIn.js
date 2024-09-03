 
        function validateForm() {
            var form = document.forms["signinForm"];
            var username = form["username"];
            var password = form["password"];

            if (!validateRequired(username)) return false;
            if (!validateRequired(password)) return false;

            return true;
        }

        function validateRequired(field) {
            if (field.value.trim() === "") {
                alert(field.name + " is required.");
                field.focus();
                return false;
            }
            return true;
        }
