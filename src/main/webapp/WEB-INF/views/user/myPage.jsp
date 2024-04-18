<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>내 정보</title>
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

            let f = document.getElementById("f"); // form 태그

            getMyInfo();

            // 정보 수정
            $("#btnSend").on("click", function () { // 버튼 클릭했을때, 발생되는 이벤트 생성함(onclick 이벤트와 동일함)
                doSubmit(f);
            })

            // 비밀번호 재설정 팝업창 열기 클릭 이벤트
            $("#btnChgPw").on("click", function () {
                $("#chgPw").modal("show");
            })

            // 모달 내의 버튼 클릭 이벤트 설정
            $('#chgPw').on('shown.bs.modal', function (e) {
                $("#modalBtnChgPw").on("click", function () {
                    doSubmitPw(document.forms["chgPw"]);
                });
            });

            // 마이페이지에서 탈퇴하기 팝업창 열기 클릭 이벤트
            $("#btnDel").on("click", function () {
                // userDelete 팝업을 보이도록 설정
                $("#userDelete").modal("show");
            });

            // 탈퇴하기 버튼 클릭 이벤트
            $("#modalBtnDel").click(function() {
                let res = confirm("정말로 탈퇴하시겠습니까?");

                if (res === false) {
                    return ;
                }

                if (res === true) {
                    doSubmitDel(del);
                }

            });

        })

        // 내 정보 가져오기
        function getMyInfo() {

            $.ajax({
                url: "/user/getUserInfo",
                type: "post",
                dataType: "JSON",
                data: $("#f").serialize(),
                success: function (json) {

                    $("#userId").val(json.userId)
                    $("#email").val(json.email)
                    $("#userName").val(json.userName)
                    $("#nickname").val(json.nickname)
                }
            })
        }

        // 비밀번호 확인
        function pwCheck() {
            const element = document.getElementById('newPassword1');
            element.classList.remove('is-invalid')
            element.classList.remove('is-valid')
            if ($('#newPassword').val() === $('#newPassword1').val()) {
                $('#pwConfirm').text('비밀번호 일치').css('color', 'green');
                document.getElementById('newPassword1').className += ' is-valid'

            } else {
                $('#pwConfirm').text('비밀번호 불일치').css('color', 'red');
                document.getElementById('newPassword1').className += ' is-invalid'
            }
        }

        // 회원 정보의 유효성 체크하기
        function doSubmit(f) {

            if (f.nickname.value === "") {
                alert("닉네임을 입력하세요.");
                f.nickname.focus();
                return;
            }

            // Ajax 호출해서 회원 정보 수정하기
            $.ajax({
                url: "/user/updateUserInfo",
                type: "post", // 전송방식은 Post
                dataType: "JSON", // 전송 결과는 JSON으로 받기
                data: $("#f").serialize(), // form 태그 내 input 등 객체를 자동으로 전송할 형태로 변경하기
                success: function (json) { // /notice/noticeUpdate 호출이 성공했다면..

                    console.log(json);
                    alert(json.msg); // 메시지 띄우기

                    if (json.result === 1) { // 수정 성공
                        location.reload()

                    }

                }
            })
        }

        // 비번 수정
        function doSubmitPw(fo) {

            if (fo.password.value === "") {
                alert("현재 비밀번호를 입력하세요.");
                fo.password.focus();
                return;
            }

            if (fo.newPassword.value === "") {
                alert("새 비밀번호 입력하세요.");
                fo.newPassword.focus();
                return;
            }

            if (fo.newPassword1.value === "") {
                alert("새 비밀번호 확인을 입력하세요.");
                fo.newPassword1.focus();
                return;
            }

            if (confirm("회원정보를 수정하시겠습니까?")) {

                const requestData = {
                    email : document.getElementById("email").value,
                    userName : document.getElementById("userName").value,
                    password : document.getElementById("password").value,
                    newPassword : document.getElementById("newPassword").value
                }

                console.log(requestData);

                // Ajax 호출해서 회원 정보 수정하기
                $.ajax({
                    url: "/user/updatePassword",
                    type: "post", // 전송방식은 Post
                    dataType: "JSON", // 전송 결과는 JSON으로 받기
                    data: requestData,
                    success: function (json) { // /notice/noticeUpdate 호출이 성공했다면..

                        console.log(json);
                        alert(json.msg); // 메시지 띄우기

                        if (json.result === 1) { // 수정 성공
                            location.reload()

                        }

                    }
                })
            }
        }

        function doSubmitDel() {            // Ajax 호출해서 회원 정보 수정하기
            $.ajax({
                url: "/user/deleteUserInfo",
                type: "post", // 전송방식은 Post
                dataType: "JSON",
                success: function (json) {
                    console.log(json);
                    alert(json.msg); // 메시지 띄우기
                    location.href = "../main.html"

                }
            })
        }
    </script>
</head>
<body>

<!-- 상단바 부분 -->
<div id="header"></div>
<br>
<br>
<div class="card mb-3 mx-auto" style=" width: 95%; font-family: 'Noto Sans KR', sans-serif;">

    <div class="card-body mx-auto">
        <h1 style="font-family: 'Do Hyeon', sans-serif;">내 정보</h1>
    </div>
    <br>

    <form id="f" class="mx-auto" style="width: 80%;">

        <div class="form-group">
            <label class="form-label mt-4">아이디</label>
            <div class="input-group mb-3">
                <input type="text" class="form-control" id="userId" name="userId" disabled="">
            </div>
        </div>

        <div class="form-group">
            <label class="form-label mt-4">이메일</label>
            <div class="input-group mb-3">
                <input type="email" class="form-control" id="email" name="email" disabled="">
            </div>
        </div>

        <div class="form-group">
            <label class="form-label mt-4">이름</label>
            <div class="input-group mb-3">
                <input type="text" class="form-control" id="userName" name="userName" disabled="">
            </div>
        </div>

        <div class="form-group">
            <label class="form-label mt-4">닉네임</label>
            <div class="input-group mb-3">
                <input type="text" class="form-control" id="nickname" name="nickname">
            </div>
        </div>

    </form>
    <br/>
    <div class="mx-auto" style="text-align: center">
        <button id="btnSend" type="button" class="btn btn-primary">내정보 수정</button>
        <button id="btnChgPw" type="button" class="btn btn-primary">비밀번호 수정</button>
    </div>
    <button type="button" id="btnDel" class="btn btn-link" data-bs-toggle="modal" data-bs-target="userDelete">회원 탈퇴</button>
</div>

<!-- 비밀번호 재설정 팝업 -->
<div id="chgPw" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">비밀번호 변경</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <form name="chgPw"id="fo">
                <br/>
                <br/>
                <div class="form-group mx-auto" style="width: 90%;">
                    <div class="form-floating mb-3" >
                        <input type="password" class="form-control" name="password" id="password" placeholder="현재 비밀번호">
                        <label for="password">현재 비밀번호</label>
                    </div>
                    <div class="form-floating mb-3">
                        <input type="password" class="form-control" name="newPassword" id="newPassword" placeholder="새 비밀번호" oninput="pwCheck()">
                        <label for="newPassword">새 비밀번호</label>
                    </div>
                    <div class="form-floating">
                        <input type="password" class="form-control" name="newPassword1" id="newPassword1" placeholder="새 비밀번호 확인" oninput="pwCheck()">
                        <label for="newPassword1">새 비밀번호 확인</label>
                    </div>
                </div>
            </form>
            <br/><br/>
            <div class="modal-footer">
                <button type="submit" id="modalBtnChgPw" class="btn btn-primary">수정</button>
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
            </div>
        </div>
    </div>
</div>
<!-- 회원 탈퇴 팝업 -->
<div id="userDelete" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">회원 탈퇴</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <form id="del">
                <div class="modal-body">
                    <p>탈퇴를 진행하시려면 비밀번호를 입력해주세요.</p>
                    <div class="form-floating mb-3 mx-auto" style="width: 98%">
                        <input type="password" id="userDeletePw" name="userDeletePw" class="form-control" placeholder="비밀번호" required>
                        <label for="password">비밀번호</label>
                    </div>
                </div>
            </form>
            <div class="modal-footer">
                <button type="submit" class="btn btn-primary" id="modalBtnDel">탈퇴하기</button>
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
            </div>
        </div>
    </div>
</div>

</body>
</html>
