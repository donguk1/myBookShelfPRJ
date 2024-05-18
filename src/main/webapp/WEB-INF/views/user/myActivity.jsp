<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>myInfo</title>
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Do+Hyeon&family=Hahmlet:wght@400;500&family=Nanum+Gothic+Coding&family=Noto+Sans+KR:wght@100;300;400&display=swap" rel="stylesheet">
    <style th:inline="css">
        .dropdown-menu {
            right: 0;
            top: 60px;
        }
        .dropdown-menu a {
            display: block;
        }
        .dropdown-menu a:hover {background-color: #ddd;}

        .dropdown:hover .dropdown-menu {display: block;}

        /* 드롭다운 메뉴가 나타날 때 네비게이션 바가 밀리는 현상을 방지 */
        .navbar-nav {
            position: relative;
        }

        .navbar-nav .dropdown-menu {
            position: absolute;
            right: 0;
            z-index: 1000;
        }
    </style>

    <script type="text/javascript" src="/js/jquery-3.6.0.min.js"></script>
    <script type="text/javascript" src="/js/bootstrap.bundle.min.js"></script>
    <script th:inline="javascript">
        // HTML로딩이 완료되고, 실행됨
        $(document).ready(function () {

            $("#header").load("header")

            // 내 게시물 리스트
            $("#btnMyBoard").on("click", function () {
                location.href = "myBoard";
            })

            // 내 정보 보기
            $("#btnMyActivity").on("click", function () {
                location.href = "myPage";
            })

            // 내 댓글 리스트
            $("#btnMyComment").on("click", function () {
                location.href = "myComment";
            })

            // 북마크 리스트
            $("#btnBookmark").on("click", function () {
                location.href = "myBookmark";
            })

            // 내 도서 리스트
            $("#btnMyBook").on("click", function () {
                location.href = "btnMyBook";
            })
        })
    </script>
</head>
<body>
<!-- 메인 페이지 -->
<!-- 상단바 부분 -->
<%@include file="../header.jsp" %>
<br>
<br>

<!-- 내용 부분 -->
<div class="card mb-3 mx-auto" style=" width: 95%; font-family: 'Noto Sans KR', sans-serif;">


    <div class="card d-grid gap-2" style="margin: 3% 5%">
        <button id="btnMyActivity" class="btn btn-link btn-lg" style="height: 100px" type="button">내 정보 보기</button>
    </div>

    <div class="card d-grid gap-2" style="margin: 3% 5%">
        <button id="btnMyBoard" class="btn btn-link btn-lg" style=" height: 100px" type="button">내 게시글 보기</button>
    </div>

    <div class="card d-grid gap-2" style="margin: 3% 5%">
        <button id="btnMyComment" class="btn btn-link btn-lg" style="height: 100px" type="button">내 댓글 보기</button>
    </div>

    <div class="card d-grid gap-2" style="margin: 3% 5%">
        <button id="btnBookmark" class="btn btn-link btn-lg" style="height: 100px" type="button">내 북마크 보기</button>
    </div>

    <div class="card d-grid gap-2" style="margin: 3% 5%">
        <button id="btnMyBook" class="btn btn-link btn-lg" style="height: 100px" type="button">내 도서 보기</button>
    </div>

</div>
</body>
</html>
