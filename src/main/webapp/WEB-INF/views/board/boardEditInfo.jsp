<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Board</title>
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Do+Hyeon&family=Hahmlet:wght@400;500&family=Nanum+Gothic+Coding&family=Noto+Sans+KR:wght@100;300;400&display=swap" rel="stylesheet">
    <style type="text/css">
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
    <script type="text/javascript">

        let ssUserId;

        let arr = []; // 이미지 배열 선언

        // HTML로딩이 완료되고, 실행됨
        $(document).ready(function () {

            getSsUserId();
            getBoardInfo();

            // 버튼 클릭했을때, 발생되는 이벤트 생성함(onclick 이벤트와 동일함)
            $("#btnEdit").on("click", function () {
                doSubmit(); // 메모 수정하기 실행
            })

            // 버튼 클릭했을때, 발생되는 이벤트 생성함(onclick 이벤트와 동일함)
            $("#btnDelete").on("click", function () {
                doDelete(); // 메모 삭제하기 실행
            })

            // 버튼 클릭했을때, 발생되는 이벤트 생성함(onclick 이벤트와 동일함)
            $("#btnList").on("click", function () {
                location.href = "boardList"; // 메모 리스트 이동
            })

            $("input[type='file']").change(function(e){
                //div 내용 비워주기
                // $('#preview').empty();

                let files = e.target.files;
                arr = Array.prototype.slice.call(files);

                //업로드 가능 파일인지 체크
                for(let i=0;i<files.length;i++){
                    if(!checkExtension(files[i].name,files[i].size)){
                        return false;
                    }
                }
                preview(arr);
            });//file change
        })

        // 세션 아이디 가져오기
        function getSsUserId() {

            $.ajax({
                url: "/user/getSsUserId",
                type: "post",
                dataType: "JSON",
                data: $("#f").serialize(),
                success: function (json) {

                    ssUserId = json.userId
                }

            });
        }

        //삭제하기
        function doDelete() {

            if (ssUserId === document.getElementById("regId").value) {

                if (confirm("작성한 메모를 삭제하시겠습니까?")) {
                    $.ajax({
                        url: "/board/deleteBoard",
                        type: "post",
                        dataType: "JSON",
                        data: {
                            "boardSeq" : document.getElementById("bSeq").value
                        },
                        success: function (json) {

                            console.log(json);

                            location.href = "/board/boardList";

                        },
                        error: function(xhr, status, error) {
                            console.error("AJAX 호출 중 에러 발생:", error);
                        },
                        timeout: 5000
                    })

                }

            } else if (ssUserId === "") {
                alert("로그인 하시길 바랍니다.");

            } else {
                alert("본인이 작성한 메모만 삭제 가능합니다.");
            }
        }

        // 본문 내용 가져오기
        function getBoardInfo() {

            const urlParams = new URL(location.href).searchParams;
            const bSeq = urlParams.get('bSeq');

            const data = {
                "bSeq" : bSeq,
                "type" : false
            }

            console.log(data);

            $.ajax({
                url: "/board/getBoardInfo",
                type: "post",
                dataType: "JSON",
                data: data,
                success: function (json) {

                    console.log(json);
                    document.getElementById("bSeq").value = bSeq;
                    document.getElementById("regId").value = json.regId;
                    document.getElementById("category").value = json.category;
                    document.getElementById("title").value = json.title;
                    document.getElementById("contents").value  = json.contents;
                }

            });
        }

        // 파일 확인
        function checkExtension(fileName, fileSize) {
            let regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$");
            let maxSize = 20971520; // 20MB

            if (fileSize >= maxSize) {
                alert('파일 사이즈 초과');
                $("input[type='file']").val("");
                return false;
            }

            if (regex.test(fileName)) {
                alert('업로드 불가능한 파일이 있습니다.');
                $("input[type='file']").val("");
                return false;
            }
            return true;
        }

        // 미리보기
        function preview(arr) {
            arr.forEach(function (f) {
                let fileName = f.name;
                if (fileName.length > 10) {
                    fileName = fileName.substring(0, 4) + "...";
                }

                let str = '<div style="display: inline-flex; padding: 10px;"><li>';
                str += '<span>' + fileName + '</span>&nbsp';

                if (f.type.match('image.*')) {
                    if (arr.indexOf(f) === -1) { // 이미지 배열에 존재하지 않으면 추가
                        arr.push(f); // 이미지를 배열에 추가
                    }

                    let reader = new FileReader();
                    reader.onload = function (e) {
                        str += '<button type="button" class="btn btn-danger btn-sm delBtn" style="margin-bottom: 2px;" onclick="removeImage(this)" value="' + f.name + '">x</button><br>';
                        str += '<img src="' + e.target.result + '" title="' + f.name + '" width=100 height=100 />';
                        str += '</li></div>';
                        $(str).appendTo('#preview');
                    }
                    reader.readAsDataURL(f);
                } else {
                    str += '<button type="button" class="btn btn-danger btn-sm delBtn" style="margin-bottom: 2px;" onclick="removeImage(this)" value="' + f.name + '">x</button><br>';
                    str += '<img src="/resources/img/fileImg.png" title="' + f.name + '" width=100 height=100 />';
                    str += '</li></div>';
                    $(str).appendTo('#preview');
                }
            });
            console.log(arr);
        }

        // 이미지 삭제
        function removeImage(button) {
            let fileName = $(button).val();

            arr = arr.filter(function(file) {
                return file.name !== fileName;
            });

            console.log(arr); // 파일 목록 확인

            $(button).closest('div').remove(); // 부모 div를 삭제하도록 수정
        }

        //글자 길이 바이트 단위로 체크하기(바이트값 전달)
        function calBytes(str) {
            let tcount = 0;
            let tmpStr = String(str);
            let strCnt = tmpStr.length;
            let onechar;
            for (let i = 0; i < strCnt; i++) {
                onechar = tmpStr.charAt(i);
                if (escape(onechar).length > 4) {
                    tcount += 2;
                } else {
                    tcount += 1;
                }
            }
            return tcount;
        }

        // 저장하기
        function doSubmit() {

            let f = document.getElementById("f"); // form 태그

            if (f.category.value === "") {
                alert("카테고리를 선택하시기 바랍니다.");
                f.category.focus();
                return;
            }

            if (f.title.value === "") {
                alert("제목을 입력하시기 바랍니다.");
                f.title.focus();
                return;
            }
            if (calBytes(f.title.value) > 200) {
                alert("최대 200Bytes까지 입력 가능합니다.");
                f.title.focus();
                return;
            }
            if (f.contents.value === "") {
                alert("내용을 입력하시기 바랍니다.");
                f.contents.focus();
                return;
            }
            if (calBytes(f.contents.value) > 4000) {
                alert("최대 4000Bytes까지 입력 가능합니다.");
                f.contents.focus();
                return;
            }

            // let formData = new FormData($("#f")[0]);
            let formData = new FormData();
            formData.append('title', f.title.value);
            formData.append('contents', f.contents.value);
            formData.append('category', f.category.value);
            formData.append('bSeq', f.bSeq.value);
            // 이미지 파일들을 formData에 추가
            for (let i = 0; i < arr.length; i++) {
                formData.append('file', arr[i]);
            }

            $.ajax({
                url: "/board/updateBoard",
                type: "post", // 전송방식은 Post
                data: formData, // form 태그 내 input 등 객체를 자동으로 전송할 형태로 변경하기
                dataType: "JSON",
                processData: false,  // 중요: processData를 false로 설정해야 폼 데이터가 자동으로 변환되지 않습니다.
                contentType: false,  // 중요: contentType을 false로 설정해야 멀티파트로 제대로 전송됩니다.
                success: function (json) {

                    console.log(json.msg);
                    console.log(json.result);
                    alert(json.msg); // 메시지 띄우기

                    if (json.result === 1) {
                        location.href = "boardInfo?bSeq=" + f.bSeq.value; // 공지사항 리스트 이동
                    }
                },
                error: function(xhr, status, error) {
                    console.error("AJAX 호출 중 에러 발생:", error);
                },
                timeout: 5000
            });
        }

    </script>
</head>
<body>
<!-- 메모 상세보기 페이지 -->
<!-- 상단바 부분 -->
<%@include file="../header.jsp" %>
<br/>
<br/>

<!-- 내용 부분 -->
<form name="f" id="f">
    <div class="card mb-3 mx-auto" style=" width: 95%; font-family: 'Noto Sans KR', sans-serif;">
        <div class="card-body">
            <div class="card mb-3">

                <!-- PK -->
                <input type="hidden" id="bSeq">
                <input type="hidden" id="regId">

                <!-- 타이틀 입력 영역 -->
                <h3 class="card-header" style="font-family: 'Do Hyeon', sans-serif;">
                    <input class="form-control me-sm-2" type="text" placeholder="제목을 입력하세요." name="title" id="title">
                </h3>
                <select class="form-select" name="category" id="category" style="width: 150px; margin: 10px 5px">
                    <option selected value="기타">기타</option>
                    <option value="철학">철학</option>
                    <option value="종교">종교</option>
                    <option value="과학">과학</option>
                    <option value="예술">예술</option>
                    <option value="언어">언어</option>
                    <option value="문학">문학</option>
                    <option value="역사">역사</option>
                </select>
                <br>
                <!-- 내용 입력 영역 -->
                <div class="form-group">
                    <textarea class="form-control mx-auto" id="contents" rows="14" placeholder="내용을 입력하세요." name="contents" style="width: 98%"></textarea>
                    <br>
                </div>

                <!-- 파일 -->
                <div class="card-body ">
                    <input class="form-control" type="file" id="file" multiple name="file" accept=".jpeg, .jpg, .gif, .png">
                    <div id="preview"></div>
                </div>
            </div>
        </div>

        <!-- 버튼 영역 -->
        <div class="ms-auto" style="padding: 0 20px;">
            <button id="btnEdit" type="button" class="btn btn-outline-dark">수정</button>
            <button id="btnDelete" type="button" class="btn btn-outline-dark">삭제</button>
            <button id="btnList" type="button" class="btn btn-outline-dark">목록</button>
        </div>
        <br>
    </div>
</form>

</body>
</html>