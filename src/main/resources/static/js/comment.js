let userId = "";

function setUserId(ssUserId) {

    userId = ssUserId;
}

// 댓글 달기
function insertComment(dept, targetSeq) {

    if (userId === "") {
        alert("로그인 후 이용가능합니다");
        return;
    }


    // 댓글용
    if (targetSeq === 0) {
        if (document.getElementById("commentContents").value === "") {
            alert("내용 입력 후 등록 가능합니다.")
            document.getElementById("commentContents").focus()
            return

        }

        $.ajax({
            url: "/comment/insertComment",
            type: "post",
            dataType: "JSON",
            data: {
                'boardSeq': document.getElementById("boardSeq").value,
                'contents': document.getElementById("commentContents").value,
                'dept': dept,
                'targetSeq': targetSeq
            },
            success: function (json) {

                alert(json.msg)
                if (json.result === 1) {
                    getCommentList()

                    document.getElementById("commentContents").value = ""
                }

            }
        })
        // 대댓글용
    }  else if (document.getElementById("commentReContents_" + targetSeq).value === "") {
        alert("내용 입력 후 등록 가능합니다.")
        document.getElementById(targetSeq).focus()
        return
    }

    $.ajax({
        url: "/comment/insertComment",
        type: "post",
        dataType: "JSON",
        data: {
            'boardSeq': document.getElementById("boardSeq").value,
            'contents': document.getElementById("commentReContents_" + targetSeq).value,
            'dept': dept,
            'targetSeq': targetSeq
        },
        success: function (json) {

            alert(json.msg)
            if (json.result === 1) {
                getCommentList()

                document.getElementById("commentContents").value = ""
            }

        }

    });
}

// 댓글 리스트 가져오기
function getCommentList() {
    $.ajax({
        url: "/comment/getCommentList",
        type: "post",
        dataType: "JSON",
        data: {
            "boardSeq" : urlParams.get('bSeq'),
        },
        success: function (json) {

            console.log(json);
            insertCommentList(json)
        }
    });
}

// 댓글 내용 입력
function insertCommentList(json) {
    let commentList = $("#commentList");
    commentList.empty();

    json.forEach(function(data) {
        let row = $("<div>")
            .addClass("comment-row");

        let td = $("<div>")
            .addClass("comment-col")
            .css({
                width: data.dept === 1 ? '95%' : '',
                float: data.dept === 1 ? 'right' : ''
            });

        if (data.dept === 1) { // 뎁스가 1인 경우 좌측 아이콘 추가
            let div = $("<div>")
                .css({
                    "width": "2%",
                    "float": "left",
                    "margin-right": "5px"
                });
            let i = $("<i>")
                .addClass("fa-solid fa-turn-up fa-rotate-90 fa-lg")
                .css("color", "#3A3A3A");
            div.append(i);
            td.append(div);
        }

        // 사용자 아이디(hidden), 댓글 일련번호(hidden) 추가
        let userId = $("<input>")
            .attr({
                "type": "hidden",
                "name": "commentUser",
                "id": "commentUser_" + data.commentSeq,
                "value": data.regId
            });
        td.append(userId);

        let commentSeq = $("<input>")
            .attr({
                "type": "hidden",
                "name": "commentSeq",
                "id": "commentSeq_" + data.commentSeq,
                "value": data.commentSeq
            });
        td.append(commentSeq);

        // 작성자 정보 표시 (닉네임, 작성일)
        let strong = $("<strong>").text("작성자 : " + data.nickname);
        let span = $("<span>")
            .css("float", "right")
            .text("작성일 : " + data.regDt);
        td.append($("<p>").append(strong, span));

        // 댓글 내용 표시 (읽기 전용 textarea)
        let baseComment = $("<div>").attr("id", "baseComment_" + data.commentSeq).addClass("comment-content");
        let readComment = $("<textarea>")
            .prop("readonly", true)
            .addClass("readTextarea")
            .text(data.contents);
        baseComment.append(readComment);

        // 답글 쓰기 버튼 추가 (댓글 뎁스가 0인 경우에만)
        if (data.dept === 0) {
            let btnShowReRegArea = $("<button>")
                .addClass("btn btn-link text-dark btn-box-shadow-none")
                .attr("type", "button")
                .text("답글 쓰기")
                .css("padding-left", "0")
                .click(function() {
                    showReRegArea(data.commentSeq);
                });
            baseComment.append($("<div>")
                .css("float", "left")
                .append(btnShowReRegArea));
        }

        // 수정 버튼
        let btnShowEditArea = $("<button>")
            .addClass("btn btn-light btn-sm btn-rounded-none")
            .attr("type", "button")
            .text("수정")
            .css("margin-right", "5px")
            .click(function() {
                showEditArea(data.commentSeq);
            });

        // 삭제 버튼
        let btnDoCoDelete = $("<button>")
            .addClass("btn btn-light btn-sm btn-rounded-none")
            .attr("type", "button")
            .text("삭제")
            .click(function() {
                doCoDelete(data.commentSeq);
            });

        baseComment.append($("<div>")
            .css("float", "right")
            .append(btnShowEditArea, btnDoCoDelete));

        td.append(baseComment);

        // 업데이트 상태
        let updateComment = $("<div>")
            .attr("id", "updateComment_" + data.commentSeq)
            .css("text-align", "center")

        // 수정 가능한 댓글 내용
        let updateTextarea = $("<textarea>")
            .addClass("form-control btn-rounded-none")
            .attr({
                "name" : "upComment",
                "id" : "upComment_" + data.commentSeq,
                "row": "3",
            })
            .css({
                "resize": "none",
                "display": "initial",
                "width": "95%",
                "margin-top": "5px"
            })
            .text(data.contents)

        updateComment.append(updateTextarea, $("<br>"))

        // 수정
        let btnDoCoEdit = $("<button>")
            .addClass("btn btn-light btn-sm btn-rounded-none")
            .attr("type", "button")
            .text("수정")
            .css("margin-right", "5px")
            .click(function () {
                doCoEdit(data.commentSeq)
            })

        // 취소
        let btnShowHideArea = $("<button>")
            .addClass("btn btn-light btn-sm btn-rounded-none")
            .attr("type", "button")
            .text("취소")
            .click(function () {
                showHideArea(data.commentSeq)
            })
        updateComment.append($("<div>")
            .css({
                "float": "right",
                "margin-bottom": "5px"
            })
            .append(btnDoCoEdit, btnShowHideArea)
        )

        td.append(updateComment)

        // 답글 영역 추가 (댓글 뎁스가 0인 경우에만)
        if (data.dept === 0) {
            let reCommentArea = $("<div>")
                .addClass("")
                .css("text-align", "center")
                .attr("id", "reCommentArea_" + data.commentSeq);

            let reContent = $("<textarea>")
                .addClass("form-control btn-rounded-none")
                .attr({
                    "name": "commentReContents",
                    "id": "commentReContents_" + data.commentSeq
                })
                .css({
                    "width": "95%",
                    "height": "80px",
                    "resize": "none",
                    "display": "initial"
                });
            reCommentArea.append(reContent);

            let btnReComment = $("<button>")
                .addClass("btn btn-dark btn-rounded-none")
                .attr("type", "button")
                .css({
                    "width": "95%",
                    "margin-bottom" : ".5%"
                })
                .click(function() {
                    insertComment(1, data.commentSeq);
                })
                .text("답글 등록");
            reCommentArea.append(btnReComment);
            td.append(reCommentArea);
        }

        row.append(td); // 행(row) 요소에 열(td) 요소 추가
        commentList.append(row); // commentList에 행(row) 요소 추가
    });

    // 초기에 모든 수정 및 답글 영역 숨김
    $("div[id^='updateComment']").hide();
    $("div[id^='updateRecomment']").hide();
    $("div[id^='reCommentArea']").hide();

    // 읽기 전용 텍스트영역 자동 높이 조절
    $(".readTextarea").each(function() {
        this.style.height = 'auto';
        this.style.height = (this.scrollHeight) + 'px';
    });
}


// 댓글 수정
function doCoEdit(commentSeq) {

    const coUser = document.getElementById("commentUser_" + commentSeq).value;
    const upComment = document.getElementById("upComment_" + commentSeq).value;
    const boardSeq = document.getElementById("boardSeq").value;

    console.log("commentSeq : " + commentSeq);
    console.log("ssUserId : " + ssUserId);
    console.log("coUser : " + coUser);
    console.log("upComment : " + upComment);
    console.log("boardSeq : " + boardSeq);

    if (ssUserId === "") {
        if (confirm("로그인 정보가 없습니다. \n로그인 하시겠습니까?")) {
            location.href = "/user/login";
        }
        return;

    } else if (ssUserId === coUser) {

        if (upComment === "") {
            alert("내용을 작성하세요.");
            document.getElementById("upComment_" + commentSeq).focus();
            return;

        }

        // 요청 데이터를 JavaScript 객체로 구성
        const requestData = {
            upComment: upComment,
            boardSeq: boardSeq,
            commentSeq: commentSeq,
        };

        $.ajax({
            url: "/comment/updateComment",
            type: "post",
            dataType: "JSON",
            data: requestData,
            success: function (json) {

                alert(json.msg);

                if (json.result === 1) {
                    getCommentList()
                }

            }
        })
    } else {
        alert("본인이 쓴 글만 수정 가능합니다.")
    }
}

// 댓글 삭제
function doCoDelete(commentSeq) {

    const coUser = document.getElementById("commentUser_" + commentSeq).value;
    const upComment = document.getElementById("upComment_" + commentSeq).value;
    const boardSeq = document.getElementById("boardSeq").value;

    console.log("commentSeq : " + commentSeq);
    console.log("ssUserId : " + ssUserId);
    console.log("coUser : " + coUser);
    console.log("upComment : " + upComment);
    console.log("boardSeq : " + boardSeq);

    if (ssUserId === "") {
        if (confirm("로그인 정보가 없습니다. \n로그인 하시겠습니까?")) {
            location.href = "/user/login";
        }
        return;

    } else if (ssUserId === coUser) {

        if (!confirm("삭제 하시겠습니까?")) {
            return;
        }

        // 요청 데이터를 JavaScript 객체로 구성
        const requestData = {
            upComment: upComment,
            boardSeq: boardSeq,
            commentSeq: commentSeq,
        };

        $.ajax({
            url: "/comment/deleteComment",
            type: "post",
            dataType: "JSON",
            data: requestData,
            success: function (json) {

                alert(json.msg);

                if (json.result === 1) {
                    getCommentList()
                }

            }
        })
    } else {
        alert("본인이 쓴 글만 수정 가능합니다.")
    }
}

// 답글쓰기 버튼 클릭 시 답글 폼 보여주기
function showReRegArea(commentSeq) {
    $("#reCommentArea_" + commentSeq).show();
}

// 댓글 수정 버튼 클릭 시 수정 폼 보여주기
function showEditArea(commentSeq) {
    $("#baseComment_" + commentSeq).hide();
    $("#updateComment_" + commentSeq).show();
    console.log("commentSeq : " + commentSeq);
}

// 댓글 수정 취소 버튼 클릭 시 수정 폼 숨기기
function showHideArea(commentSeq) {
    $("#baseComment_" + commentSeq).show();
    $("#updateComment_" + commentSeq).hide();
    console.log("commentSeq : " + commentSeq);
}