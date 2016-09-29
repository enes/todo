$(function () {
    $('.datepicker').each(function () {
        $(this).datepicker({
            dayNames: ["pazar", "pazartesi", "salı", "çarşamba", "perşembe", "cuma", "cumartesi"],
            dayNamesMin: ["pa", "pzt", "sa", "çar", "per", "cum", "cmt"],
            monthNames: ["Ocak", "Şubat", "Mart", "Nisan", "Mayıs", "Haziran", "Temmuz", "Ağustos", "Eylül", "Ekim", "Kasım", "Aralık"],
            monthNamesShort: ["Ocak", "Şubat", "Mart", "Nisan", "Mayıs", "Haziran", "Temmuz", "Ağustos", "Eylül", "Ekim", "Kasım", "Aralık"]

        });
    });
});

$(document).ready(function (e) {
    $("#todoForm").submit(function (e) {
        var url = "/createtodo";
        $.ajax({
            type: "POST",
            url: url,
            data: $("#todoForm").serialize(),
            success: function (data) {
                $("#todolist").html(data);
                bindButtonClick();
            },
            error: function () {
                alert('Lütfen girdiğiniz değerleri kontrol ediniz');
            }
        });
        e.preventDefault();
    });
});


function bindButtonClick() {
    $(".removeTodo").click(function () {
        var url = "/delete/" + $(this).data('id');
        $.ajax({
            type: "GET",
            url: url,
            success: function (data) {
                $("#todolist").html(data);
                bindButtonClick();
            },
            error: function () {
                alert('Lütfen girdiğiniz değerleri kontrol ediniz');
            }
        });

    });
}

function redirect() {
    var redirectUrl = $('#redirectUrl').val();
    if (redirectUrl) {
        window.location.href = redirectUrl;
    }
};

function loadTodos() {
    $.ajax({
        type: "GET",
        url: "/list",
        success: function (data) {
            $("#todolist").html(data);
            bindButtonClick();
        },
        error: function () {
            alert('Hata oluştu');
        }
    });
};

function redirectAndLoad() {
    redirect();
    loadTodos();
};

