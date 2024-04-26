<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>비밀번호 찾기</title>
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Do+Hyeon&family=Hahmlet:wght@400;500&family=Nanum+Gothic+Coding&family=Noto+Sans+KR:wght@100;300;400&display=swap" rel="stylesheet">
    <style>
        .nav-item {
            visibility: hidden;
        }
    </style>

    <script type="text/javascript" src="/js/jquery-3.6.0.min.js"></script>
    <script type="text/javascript" src="/js/bootstrap.bundle.min.js"></script>
    <script type="text/javascript" src="/js/move.js"></script>
    <script type="text/javascript">

        // HTML로딩이 완료되고, 실행됨
        $(document).ready(function () {

            $("#header").load("../header.html")


            $("#btnSend").on("click", function () { // 버튼 클릭했을때, 발생되는 이벤트 생성함(onclick 이벤트와 동일함)
                checkUserData()

            })

        })

        // 아이디 찾기
        function checkUserData() {

            console.log("1");

            let f = document.getElementById("f");

            if (f.userId.value === "") {
                alert("아이디를 입력하세요.");
                f.userId.focus();
                return false;
            }

            if (f.userName.value === "") {
                alert("이름을 입력하세요.");
                f.userName.focus();
                return false;
            }

            if ($("#email").val() === "") {
                alert("이메일을 입력하세요.");
                $("#email").focus()
                return false;
            }

            // Ajax 호출해서 아이디 찾기
            $.ajax({
                url: "/user/newUserPassword",
                type: "post", // 전송방식은 Post
                dataType: "JSON", // 전송 결과는 JSON으로 받기
                data: $("#f").serialize(), // form 태그 내 input 등 객체를 자동으로 전송할 형태로 변경하기
                success: function (json) { // 호출이 성공했다면..

                    console.log(json);

                    alert(json.msg)

                    if (json.result === 1) {
                        location.href = "login";

                    }

                },
                error: function(xhr, status, error) {
                    console.error("AJAX 호출 중 에러 발생:", error);
                }
            });

        }

    </script>
</head>
<body>
<!-- 로그인 페이지 -->
<!-- 상단바 부분 -->
<div id="header"></div>
<br>
<br>
<div class="card mb-3 mx-auto" style="width: 90%; font-family: 'Noto Sans KR', sans-serif;">
    <br/>

    <div class="card-body mx-auto">
        <h1 style="font-family: 'Do Hyeon', sans-serif;">비밀번호 찾기</h1>
    </div>
    <hr/>

    <h6 style="text-align: center">* 회원정보에 등록한 정보를 입력해주세요.</h6>
    <form id="f" class="mx-auto" style="width:80%;" >

        <div class="form-group">
            <div class="form-floating mb-3">
                <input type="text" class="form-control" name="userId" id="userId" placeholder="아이디">
                <label for="userId">아이디</label>
            </div>
        </div>

        <div class="form-group">
            <div class="form-floating mb-3">
                <input type="text" class="form-control" name="userName" id="userName" placeholder="이름">
                <label for="userName">이름</label>
            </div>
        </div>

        <div class="form-group">
            <div class="form-floating mb-3">
                <input type="email" class="form-control" name="email" id="email" placeholder="email">
                <label for="email">이메일</label>
            </div>
        </div>

        <br/>
        <br/>

        <!-- 비밀번호 찾기 버튼 영역 -->
        <div class="d-grid gap-2">
            <button id="btnSend" type="button" class="btn btn-lg btn-primary">비밀번호 찾기</button>
        </div>
        <br/>

        <div class="row" style="text-align: center">
            <div class="mx-auto">
                <button id="btnSignUp" type="button" class="btn btn-link" style="justify-content: center">회원가입</button>
            </div>
            <div class="mx-auto">
                <button id="btnFindId" type="button" class="btn btn-link" style="justify-content: center">아이디 찾기</button>
                <button id="btnLogin" type="button" class="btn btn-link" style="justify-content: center">로그인</button>
            </div>
        </div>
    </form>
</div>
</body>
</html>