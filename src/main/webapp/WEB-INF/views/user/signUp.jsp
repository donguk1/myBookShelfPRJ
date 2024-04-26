<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title> 회원가입</title>
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Do+Hyeon&family=Hahmlet:wght@400;500&family=Nanum+Gothic+Coding&family=Noto+Sans+KR:wght@100;300;400&display=swap" rel="stylesheet">
    <style th:inline="css">
        input {
            margin: 0 auto;
            position: relative;
        }


        ::-webkit-calendar-picker-indicator {
            position: absolute;
            right: 0;
            top: 25%;
            transform: translateX(-10px);
            padding-left: 3000px;
            height: 100%;
        }
    </style>

    <script type="text/javascript" src="/js/jquery-3.6.0.min.js"></script>
    <script type="text/javascript" src="/js/bootstrap.bundle.min.js"></script>
    <script type="text/javascript" src="/js/move.js"></script>
    <script th:inline="javascript">


        // 아이디 중복체크여부 (중복 Y / 중복아님 : N)
        let userIdCheck = "Y";

        // 이메일 중복체크여부 (중복 Y / 중복아님 : N)
        let emailCheck = "Y";

        let authNumber;

        let authCheck = "Y"

        $(document).ready(function() {

            let f = document.getElementById("f");

            // userId 엔터키
            $("#userId").on("keypress", function (event) {
                if (event.key === "Enter") {
                    event.preventDefault(); // 폼 제출 방지
                    $("#btnId").click();
                }
            });

            // email 엔터키
            $("#email").on("keypress", function (event) {
                if (event.key === "Enter") {
                    event.preventDefault(); // 폼 제출 방지
                    $("#btnEmail").click();
                }
            });

            // email 엔터키
            $("#authNumber").on("keypress", function (event) {
                if (event.key === "Enter") {
                    event.preventDefault(); // 폼 제출 방지
                    $("#btnAuthNumber").click();
                }
            });

            // userId 중복체크
            $("#btnId").on("click", function () {

                if (f.userId.value === "") {
                    alert("아이디를 입력하세요.");
                    f.userId.focus();
                    return;
                }

                userIdExists(f);


            });

            // email 중복체크
            $("#btnEmail").on("click", function () {

                console.log($("#email").val());

                if ($("#email").val() === "") {
                    alert("이메일을 입력하세요");
                    $("#email").focus();
                    return;
                }

                emailExists(f);

            });

            // 인증번호 확인
            $("#btnAuthNumber").on("click", function () {

                if ($("#authNumber").val() === "") {
                    alert("인증번호를 입력하세요");
                    $("#btnAuthNumber").focus();
                    return;
                }

                authNumberCheck(f);

            });

            // 회원가입
            $("#btnSend").on("click", function () { // 버튼 클릭했을때, 발생되는 이벤트 생성함(onclick 이벤트와 동일함)
                doSubmit(f);
            })
        })

        // 비밀번호 확인
        function pwCheck() {
            const element = document.getElementById('password2');
            element.classList.remove('is-invalid')
            element.classList.remove('is-valid')
            if ($('#password').val() === $('#password2').val()) {
                $('#pwConfirm').text('비밀번호 일치').css('color', 'green');
                document.getElementById('password2').className += ' is-valid'

            } else {
                $('#pwConfirm').text('비밀번호 불일치').css('color', 'red');
                document.getElementById('password2').className += ' is-invalid'
            }
        }

        // 아이디 중복 체크
        function userIdExists(f) {

            // Ajax 호출해서 회원가입하기
            $.ajax({
                url: "/user/getUserIdExists",
                type: "post", // 전송방식은 Post
                dataType: "JSON", // 전송 결과는 JSON으로 받기
                data: $("#f").serialize(), // form 태그 내 input 등 객체를 자동으로 전송할 형태로 변경하기
                success: function (json) { // 호출이 성공했다면..

                    console.log(json);

                    if (json.exists === "N") {
                        f.userId.focus();
                        userIdCheck = "Y";
                        alert("사용할 수 없는 아이디 입니다.")

                    } else {
                        userIdCheck = "N";
                        alert("사용 가능한 아이디 입니다.")
                    }

                },
                error: function(xhr, status, error) {
                    console.error("AJAX 호출 중 에러 발생:", error);
                }
            })
        }

        // 이메일 중복 체크
        function emailExists(f) {

            // Ajax 호출해서 회원가입하기
            $.ajax({
                url: "/user/getEmailExists",
                type: "post", // 전송방식은 Post
                dataType: "JSON", // 전송 결과는 JSON으로 받기
                data: $("#f").serialize(), // form 태그 내 input 등 객체를 자동으로 전송할 형태로 변경하기
                success: function (json) { // 호출이 성공했다면..

                    console.log(json);

                    if (json.exists === "Y") {
                        emailCheck = "N";
                        authNumber = json.authNumber
                        alert("사용 가능한 이메일 입니다.\n" +
                            "인증번호를 발송하였습니다.")
                        console.log(authNumber);

                    } else {

                        $("#email").focus();
                        emailCheck = "Y";
                        alert("사용할 수 없는 이메일 입니다.")
                    }

                },
                error: function(xhr, status, error) {
                    console.error("AJAX 호출 중 에러 발생:", error);
                }
            })
        }

        // 이메일 인증 확인
        function authNumberCheck(f) {

            if (authNumber === $("#authNumber").val()) {

                authCheck = "N"

                alert("확인되었습니다.")

            } else {
                alert("일치하지 않습니다.\n" +
                    "다시 확인해주세요")
            }

        }

        // 회원가입
        function doSubmit(f) {
            if (f.userId.value === "") {
                alert("아이디를 입력하세요.");
                f.userId.focus();
                return;
            }

            if (userIdCheck !== "N") {
                alert("아이디 중복 체크 및 중복되지 않은 아이디로 가입 바랍니다.");
                f.userId.focus();
                return;
            }

            if (f.nickname.value === "") {
                alert("닉네임을 입력하세요.");
                f.nickname.focus();
                return;
            }

            if (f.password.value === "") {
                alert("비밀번호를 입력하세요.");
                f.password.focus();
                return;
            }

            if (f.password2.value === "") {
                alert("비밀번호확인을 입력하세요.");
                f.password2.focus();
                return;
            }

            if (f.password.value !== f.password2.value) {
                alert("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
                f.password.focus();
                return;
            }

            if ($("#email").val() === "") {
                alert("이메일을 입력하세요.");
                f.email.focus();
                return;
            }

            if (authCheck === "Y") {
                alert("이메일 인증을 완료해주세요.")
                f.authNumber.focus()
                return;
            }

            if (confirm("회원가입을 하시겠습니까?")) {

                $.ajax({
                    url: "/user/insertUserInfo",
                    type: "post", // 전송방식은 Post
                    dataType: "JSON", // 전송 결과는 JSON으로 받기
                    data: $("#f").serialize(), // form 태그 내 input 등 객체를 자동으로 전송할 형태로 변경하기
                    success: function (json) { // 호출이 성공했다면..

                        console.log(json);

                        alert(json.msg)

                        if (json.result === 1) {
                            location.href="login"

                        }

                    },
                    error: function(xhr, status, error) {
                        console.error("AJAX 호출 중 에러 발생:", error);
                    }
                })
            }

        }
    </script>

</head>
<body>
<!-- 회원가입 페이지 -->
<!-- 상단바 부분 -->
<div id="header"></div>
<br>
<br>

<!-- 내용 부분 -->
<div class="card mb-3 mx-auto" style=" width: 95%; font-family: 'Noto Sans KR', sans-serif;">
    <div class="card-body mx-auto" style="width: 80%">
        <form name="signup" id="f" style="font-family: 'Do Hyeon', sans-serif; font-size: 20px;">
            <fieldset>
                <legend style="font-size: 34px;">회원가입</legend>

                <!-- 아이디 입력 영역 -->
                <label for="userId" class="form-label mt-4">아이디</label>
                <div class="form-group has-success">
                    <div class="input-group mb-3">
                        <input type="text" name="userId" id="userId" class="form-control" placeholder="ID" aria-label="Recipient's id" aria-describedby="btnId">
                        <button class="btn btn-primary" type="button" id="btnId">확인</button>
                    </div>
                </div>

                <!-- 비밀번호 입력 영역 -->
                <div class="form-group">
                    <label for="password" class="form-label mt-4">비밀번호</label>
                    <input type="password" name="password" class="form-control" id="password" placeholder="PASSWORD" oninput="pwCheck()">
                </div>

                <!-- 비밀번호 재확인 영역 -->
                <div class="form-group">
                    <label for="password2" class="form-label mt-4">비밀번호 확인</label>
                    <input type="password" name="password2" class="form-control" id="password2" placeholder="PASSWORD" oninput="pwCheck()">
                    <span id="pwConfirm">비밀번호를 입력하세요</span>
                </div>

                <!-- 이메일 입력 영역 -->
                <label for="email" class="form-label mt-4">이메일</label>
                <div class="form-group has-success">
                    <div class="input-group mb-3">
                        <input type="email" name="email" id="email" class="form-control" aria-describedby="email" placeholder="email">
                        <button class="btn btn-primary" type="button" id="btnEmail">확인</button>
                    </div>
                    <label for="authNumber" class="form-label mt-4">인증번호</label>
                    <div class="input-group mb-3" id="toggleEmailVerificationInput">
                        <input type="text" name="authNumber" class="form-control" id="authNumber" aria-describedby="emailHelp" placeholder="인증번호">
                        <button class="btn btn-primary" type="button" id="btnAuthNumber">확인</button>
                    </div>
                </div>

                <!-- 닉네임 입력 영역 -->
                <label for="nickname" class="form-label mt-4">닉네임</label>
                <div class="input-group mb-3">
                    <input type="text" name="nickname" id="nickname" class="form-control" placeholder="Nickname" aria-label="Recipient's nickname" aria-describedby="btnNick">
                </div>

                <!-- 이름 입력 영역 -->
                <div class="form-group">
                    <label for="userName" class="form-label mt-4">이름</label>
                    <input type="text" name="userName" class="form-control" id="userName" aria-describedby="emailHelp" placeholder="이름">
                </div>

            </fieldset>
                <br>
        </form>
        <!-- 가입 버튼 영역 -->
        <button type="submit" id="btnSend" class="btn btn-primary" style="float: right; ">가입</button>
        <button type="button" id="btnLogin" class="btn btn-primary" style="float: right;margin-right: 5px">로그인</button>

    </div>
</div>

</body>
</html>