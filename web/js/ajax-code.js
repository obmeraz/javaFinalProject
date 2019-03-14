$(document).on("click", "#likeLifehack", function () {
    $.ajax({
        url: "controller",
        data: 'command=like_lifehack&lifehack_id=' + lifehack_id,
        success: function (result) {
            $("#ajaxGetUserServletResponse").text(result);

            var oldSrc = $("#likeLifehack").attr('src');
            var index = oldSrc.lastIndexOf('/');

            var newSrc;
            if (oldSrc.indexOf('active') > 0) {
                newSrc = oldSrc.substr(0, index) + '/heart.png';
            } else {
                newSrc = oldSrc.substr(0, index) + '/active.png';
            }
            document.getElementById("likeLifehack").src = newSrc;
        }
    });
});

$(".classlike").on("click", function () {

    var idcomment = $(this).attr('id');
    var oldSrc = $(this).attr('src');
    var index = oldSrc.lastIndexOf('/');

    $.ajax({
        url: "controller",
        data: 'command=like_comment&comment_id=' + idcomment,
        success: function (result) {
            $("#ajaxGetCommentResponse" + idcomment).text(result);

            var newSrc;
            if (oldSrc.indexOf('active') > 0) {
                newSrc = oldSrc.substr(0, index) + '/heart.png';
            } else {
                newSrc = oldSrc.substr(0, index) + '/active.png';
            }
            document.getElementById(idcomment).src = newSrc;
        }
    });
});
