<%@ page import="kopo.poly.dto.BoardDTO" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Objects" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>BookShelf</title>
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

    <script src="https://kit.fontawesome.com/5a07bfcb19.js" crossorigin="anonymous"></script>
    <script type="text/javascript" src="/js/jquery-3.6.0.min.js"></script>
    <script type="text/javascript" src="/js/bootstrap.bundle.min.js"></script>
    <script th:inline="javascript">

        // HTML로딩이 완료되고, 실행됨
        $(document).ready(function () {

            getRecommendBook()

        })

        function getRecommendBook() {
            $.ajax({
                url: "/recommend/getRecommendBook",
                type: "POST",
                dataType: "JSON",
                success: function (json) {
                    console.log(json);
                    document.getElementById("bookTitle").value = json.msg.replace(/^"|"$/g, '')

                    getShoppingList(json.msg.replace(/^"|"$/g, ''))
                },
                error: function(xhr, status, error) {
                    console.error("AJAX 호출 중 에러 발생:", error);
                }
            });
        }

        function getShoppingList(bookTitle) {
            $.ajax({
                url: "/recommend/getShoppingList",
                type: "POST",
                dataType: "JSON",
                data: {"bookTitle" : bookTitle},
                success: function (json) {
                    console.log(json);

                },
                error: function(xhr, status, error) {
                    console.error("AJAX 호출 중 에러 발생:", error);
                }
            });
        }


    </script>
</head>
<body>
<!-- 메모 목록 페이지 -->

<!-- 상단바 부분 -->
<%@include file="../header.jsp" %>
<br>
<br>
<div class="card mb-3 mx-auto" style=" width: 95%; font-family: 'Noto Sans KR', sans-serif;">
    <div class="card-body mx-auto">
        <h1 style="font-family: 'Do Hyeon', sans-serif;">무작위 도서 추천</h1>
    </div>

    <form id="f" class="mx-auto" style="width: 80%;">
        <div class="form-group">
            <label class="form-label mt-4">책 제목</label>
            <div class="input-group mb-3">
                <input type="text" class="form-control" id="bookTitle" name="bookTitle" disabled="">
            </div>
        </div>
    </form>

</div>


</body>
</html>