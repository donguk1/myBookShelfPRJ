<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>로그인</title>
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Do+Hyeon&family=Hahmlet:wght@400;500&family=Nanum+Gothic+Coding&family=Noto+Sans+KR:wght@100;300;400&display=swap" rel="stylesheet">

    <script type="text/javascript" src="/js/jquery-3.6.0.min.js"></script>
    <script type="text/javascript" src="/js/bootstrap.bundle.min.js"></script>
    <script type="text/javascript" src="/js/move.js"></script>
    <script type="text/javascript">

        // HTML로딩이 완료되고, 실행됨
        $(document).ready(function () {

            // // 네이버 로그인
            // $("#btnNaverLogin").on("click", function () {
            //     location.href = naverHref;
            // })
            //
            // // 카카오 로그인
            // $("#btnKakaoLogin").on("click", function () {
            //     location.href = kakaoHref;
            // })

            // 로그인
            $("#btnSend").on("click", function () {
                login()
            })

            $("#password").on("keypress", function (event) {
                if (event.key === "Enter") {
                    event.preventDefault(); // 폼 제출 방지
                    $("#btnSend").click();
                }
            });

        })

        function login() {
            let f = document.getElementById("f"); // form 태그

            if (f.userId.value === "") {
                alert("아이디를 입력하세요.");
                f.userId.focus();
                return;
            }

            if (f.password.value === "") {
                alert("비밀번호를 입력하세요.");
                f.password.focus();
                return;
            }

            console.log($("#f").serialize());

            $.ajax({
                url: "/user/loginProc",
                type: "post", // 전송방식은 Post
                dataType: "JSON", // 전송 결과는 JSON으로 받기
                data: $("#f").serialize(),
                success: function (json) {

                    console.log(json);

                    if (json === 1) {
                        location.href = "/main";

                    } else { // 회원가입 실패
                        alert("실패하였습니다. \n" +
                            "다시 확인해주세요"); // 메시지 띄우기
                    }
                }
            })
        }
    </script>
</head>
<body>
<!-- 로그인 페이지 -->
<%@include file="../header.jsp" %>
<br/>
<br/>

<!-- 내용 부분 -->
<div class="card mb-3 mx-auto" style=" width: 95%; font-family: 'Noto Sans KR', sans-serif;">

    <div class="card-body mx-auto">
        <h1 style="font-family: 'Do Hyeon', sans-serif;">로그인</h1>
    </div>
    <br>

    <form id="f" class="mx-auto" style="width:80%;">
        <!-- 아이디 입력 영역 -->
        <div class="form-group">
            <div class="form-floating mb-3">
                <input type="text" class="form-control" name="userId" id="userId" placeholder="아이디">
                <label for="userId">아이디</label>
            </div>
        </div>
        <br>

        <!-- 비밀번호 입력 영역 -->
        <div class="form-group">
            <div class="form-floating mb-3">
                <input type="password" class="form-control" name="password" id="password" placeholder="비밀번호">
                <label for="password">비밀번호</label>
            </div>
        </div>
        <br>
        <br>
        <br>

        <!-- 로그인 버튼 영역 -->
        <div class="d-grid gap-2">
            <button type="button" id="btnSend" class="btn btn-primary btn-lg">로그인</button>
        </div>
        <br>

        <!-- 다른 계정으로 로그인 영역 -->
        <div id="other_login" class="otherButton text-center">
            <span class="text-secondary">다른 계정으로 로그인</span>
            <!-- 네이버 로그인 API -->
            <button type="button" id="btnNaverLogin" class="btn"><img src="/img/naverIcon.png" alt="네이버로그인" style="width: 40px;height: 40px;"></button>
            <!-- 카카오 로그인 API -->
            <button type="button" id="btnKakaoLogin" class="btn"><img src="/img/kakaoIcon.png" alt="카카오로그인" style="width: 40px;height: 40px;"></button>
        </div>
        <br>

        <div class="row" style="text-align: center">
            <!-- 회원가입으로 넘어가는 버튼 영역 -->
            <div class="mx-auto">
                <button type="button" class="btn btn-link text-secondary" id="btnSignUp">회원가입</button>
            </div>
            <!-- 아이디/비밀번호 찾기 영역 -->
            <div class="mx-auto">
                <button type="button" class="btn btn-link text-secondary" id="btnFindId">아이디 찾기</button>
                <button type="button" class="btn btn-link text-secondary" id="btnFindPassword">비밀번호 찾기</button>
            </div>
        </div>
        <br>
    </form>
</div>

</body>
</html>