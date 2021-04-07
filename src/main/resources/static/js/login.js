function register() {
    $(".alert").html("").hide();
    var formData = $('form').serialize();
    $.ajax({
        url: '/login',
        type: 'POST',
        data: formData,
        success: function (data) {
            if (data === "success") {
                close();
                document.location.href = "redirect:/";
            }
            if (data === "duplicatedEmailError") {
                $("#duplicatedEmailError").show();
            }
        }
    });
}