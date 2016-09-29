$(document).ready(function (e) {
    $("#loginForm").submit(function (e) {
        var url = "/validateMail/";
        var email = $('#email').val();
        url = url + email;
        if (email == "") {
            $('.errorEmailMessage').html("email alani bos gecilemez")
        } else {
            $.ajax({
                type: "GET",
                url: url,
                success: function (response) {
                    var obj = JSON.parse(response);
                    if (!obj.success) {
                        $(".errorMessage").html(obj.message)
                    } else {
                        $.ajax({
                            type: "POST",
                            url: "/save",
                            data: $("#loginForm").serialize(),
                            success: function (data) {
                                var obj = JSON.parse(data);
                                if (obj.success) {
                                    window.location.href = "/login?result=true";
                                } else {
                                    window.location.href = "/signup?result=false";
                                }
                            },
                            error: function () {
                                window.location.href = "/login?error";
                            }
                        });
                    }
                },
                error: function () {
                    alert('hata oluştu');
                }
            });
        }
        e.preventDefault();
    });
});
