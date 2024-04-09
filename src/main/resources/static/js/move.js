$(document).ready(function () {

    // 회원가입
    $("#btnSignUp").on("click", function () { // 버튼 클릭했을때, 발생되는 이벤트 생성함(onclick 이벤트와 동일함)
        location.href = "../login/signUp.html";
    })

    // 로그인
    $("#btnLogin").on("click", function () {
        location.href = "../login/login.html";
    })

    // 아이디 찾기
    $("#btnFindId").on("click", function () {
        location.href = "../login/findId.html";
    })

    // 비번 찾기
    $("#btnFindPassword").on("click", function () {
        location.href = "../login/findPassword.html";
    })


})