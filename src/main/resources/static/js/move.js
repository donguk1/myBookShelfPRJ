$(document).ready(function () {

    // 회원가입
    $("#btnSignUp").on("click", function () { 
        location.href = "../user/signUp";
    })

    // 로그인
    $("#btnLogin").on("click", function () {
        location.href = "../user/login";
    })

    // 아이디 찾기
    $("#btnFindId").on("click", function () {
        location.href = "../user/findId";
    })

    // 비번 찾기
    $("#btnFindPassword").on("click", function () {
        location.href = "../user/findPassword";
    })

})