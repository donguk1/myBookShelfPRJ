let choiceDay = ""; // 선택일
let containerSeq = 1;
let editContainerSeq = 1;
let today = new Date();
$(document).ready(function () {


    // 일정 버튼 눌렀을때 일정 추가 모달 열기
    $("#registerBookShelf").on("click", function () {
        $("#bookShelfModal").modal("show");
    });

    // 일정 추가 모달에서 저장 버튼 눌렀을때
    $("#modalBtnSc").on("click", function () {
        insertBookShelf(); // 일정 추가 함수 실행
    })

    // 도서 수정
    $("#editBtnSc").on("click", function () {
        updateMyBook()
    })

    // 오늘 날짜를 자동으로 선택하는 블록 추가
    let todayCell = getTodayCell(); // 오늘 날짜에 해당하는 셀을 가져오는 함수
    if (todayCell) {
        calendarChoiceDay(todayCell);
    }


})

/* 도서 추가 */
function insertBookShelf() {

    let bookShelfTitle = document.getElementById("bookShelfTitle").value;


    // 요청 데이터를 JavaScript 객체로 구성
    const requestData = {
        choiceDay: choiceDay,
        title: bookShelfTitle,

    };

    console.log(requestData);


    $.ajax({
        url: "/bookShelf/insertBook",
        type: "post",
        dataType: "JSON",
        data: requestData,
        success: function (json) {
            if (json.result === 1) {
                alert(json.msg);
                location.reload();
            } else {
                alert(json.msg);
            }

        }
    })
}

/* 도서 조회 */
function getBookShelfList(choiceDay) {

    $.ajax({
        url: "/bookShelf/getMyBookList",
        type: "post",
        data: {date: choiceDay},
        dataType: "JSON",
        success: function (json) {

            console.log(json);
            let bookShelfDiv = $("#bookShelf");
            bookShelfDiv.empty();

            json.forEach(function (bookShelf) {

                let bookShelfItem = $("<div class=\"listOne\" id='listOneSc'>")
                    .addClass("bookShelfItem");

                let etcBtn = $("<div class='btn-group' role='group'>")
                    .addClass("editButtons");
                etcBtn.addClass("float-end");

                let editBtn = $("<i type='button' class=\"fa-solid fa-ellipsis-vertical fa-lg\" data-bs-toggle='dropdown' aria-haspopup='true' aria-expanded='false'></i>")
                let dropdownMenu = $("<div class='dropdown-menu' aria-labelledby='btnGroupDrop3'>");
                let editLink = $("<div class='dropdown-item'>").text("수정");
                let deleteLink = $("<div class='dropdown-item'>").text("삭제");
                let cancelLink = $("<div class='dropdown-item'>").text("취소");

                // Edit 링크 클릭 이벤트
                editLink.on("click", function (event) {
                    event.preventDefault();
                    $("#bookShelfEditModal").modal("show");
                    setBookShelfInfoInModal(bookShelf);
                });

                // Delete 링크 클릭 이벤트
                deleteLink.on("click", function (event) {
                    event.preventDefault();
                    deleteBookShelf(bookShelf);
                });

                // Cancel 링크 클릭 이벤트
                cancelLink.on("click", function (event) {
                    event.preventDefault();
                    cancelEditButtons();
                });

                function cancelEditButtons() {
                    // 수정, 삭제, 취소 버튼을 숨김.
                    $(".editButtons .btn-group").toggle(); // editButtons 클래스 안의 btn-group 요소들을 토글
                }

                dropdownMenu.append(editLink, deleteLink, cancelLink);
                etcBtn.append(editBtn, dropdownMenu);

                let bookShelfTime = $("<strong>")
                    .addClass("bookShelfTime")
                    .text(bookShelf.time);

                let bookShelfTitle = $("<div>")
                    .addClass("bookShelfTitle")
                    .text(bookShelf.title);

                let bookShelfCon = $("<div>")
                    .addClass("bookShelfCon")
                    .text(bookShelf.contents);

                bookShelfItem.append(etcBtn, bookShelfTime, bookShelfTitle, bookShelfCon);
                bookShelfDiv.append(bookShelfItem);
            });
        },
        error: function (xhr, status, error) {
            alert("일정 정보를 불러오는 데 실패했습니다.");
            console.error("에러: " + error);
        }
    });
}

/* 도서 여부 확인 */
function checkBookShelf(doMonth , callback) {

    const Month = doYYMonth(doMonth);
    const nextMonth = nextYYMonth(doMonth)

    $.ajax({
        url: "/bookShelf/checkMyBook",
        type: "post",
        dataType: "JSON",
        data:  {doMonth : Month,
            nextMonth: nextMonth},
        success: function (json) {



            callback(json);
        }
    })
}

/* 일정 수정창 팝업시 값 입력 */
function setBookShelfInfoInModal(bookShelf) {

    console.log("Setting bookShelf info in modal: ", bookShelf);


    $("#newTitle").val(bookShelf.title);
    $("#oldTitle").val(bookShelf.title);
}

/* 도서 수정 */
function updateMyBook() {

    $.ajax({
        url: "/bookShelf/updateMyBook",
        type: "post",
        dataType: "JSON",
        data: {
            newTitle : document.getElementById("newTitle").value,
            oldTitle : document.getElementById("oldTitle").value,
            choiceDay : choiceDay
        },
        success: function (json) {
            alert(json.msg);
            location.reload();

        }
    })
}



/* 일정 삭제 */
function deleteBookShelf(bookShelf) {
    // 선택한 일정을 삭제하는 동작을 추가합니다.
    // 예: Ajax 요청을 보내서 서버에 삭제 요청을 보냅니다.

    console.log(bookShelf)
    console.log(choiceDay);

    if (confirm("삭제 하시겠습니까?")) {
        $.ajax({
            url: "/bookShelf/deleteBookShelf",
            type: "post",
            dataType: "JSON",
            data: {
                title : bookShelf.title,
                choiceDay : choiceDay
            },
            success: function (json) {
                alert(json.msg);
                location.reload();

            }
        })
    }
}

// 개수
$(document).ready(function(){
    $('.btn-number').click(function(e){
        e.preventDefault();

        let fieldName = $(this).attr('data-field');
        let type      = $(this).attr('data-type'); /* 버튼 타입 */
        let input = $("input[name='"+fieldName+"']"); /* 입력 값 */
        let currentVal = parseInt(input.val()); /* 최신 값 정수 변환 */

        if (!isNaN(currentVal)) {
            if(type == 'minus') { /* 마이너스 버튼 */
                if(currentVal<1){ /* 값이 음수면 */
                    input.val(currentVal); /* 더 이상 빼지 않고 0 출력 */
                } else {
                    input.val(currentVal - 1); /* 음수가 아니라면 1씩 줄임 */
                }
            } else if(type == 'plus') { /* 플러스 버튼 */
                input.val(currentVal + 1); /* 현재 값에서 1씩 늘림 */
            }
        } else {
            input.val(0);
        }
    });
});


/* --------------------달력--------------------- */

// 오늘 날짜에 해당하는 셀을 가져오는 함수
function getTodayCell() {

    let day = today.getDate();

    // 셀을 순회하면서 오늘 날짜에 해당하는 셀을 찾음
    let cells = document.querySelectorAll(".scriptCalendar > tbody td");
    for (let i = cells.length - 1; i >= 0; i--) {
        if (cells[i].innerText == day && cells[i].style.color !== "#A9A9A9") {
            return cells[i];
        }
    }

    return null;
}


/* 현재 년월 가져오기 */
function doYYMonth(date) {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0'); // 월은 0부터 시작하므로 1을 더하고 2자리로 포맷팅
    const day = 1

    return `${year}-${month}-${day}`;
}

/* 다음 년월 가져오기 */
function nextYYMonth(date) {
    let year = date.getFullYear();
    let month = String(date.getMonth() + 1).padStart(2, '0'); // 월은 0부터 시작하므로 1을 더하고 2자리로 포맷팅

    if (month === '12') {
        month = '01';
        year += 1;
    } else {
        month = String(date.getMonth() + 2).padStart(2, '0');
    }

    const day = 1

    return `${year}-${month}-${day}`;
}

document.addEventListener("DOMContentLoaded", function () {
    buildCalendar();
});

let date = new Date();  // @param 전역 변수, today의 Date를 세어주는 역할

/**
 * @brief   이전달 버튼 클릭
 */
function prevCalendar() {
    today = new Date(today.getFullYear(), today.getMonth() - 1, today.getDate());
    buildCalendar();    // @param 전월 캘린더 출력 요청
    console.log("prev")
}

/**
 * @brief   다음달 버튼 클릭
 */
function nextCalendar() {
    today = new Date(today.getFullYear(), today.getMonth() + 1, today.getDate());
    buildCalendar();    // @param 명월 캘린더 출력 요청
    console.log("next")
}

/**
 * @brief   캘린더 오픈
 * @details 날짜 값을 받아 캘린더 폼을 생성하고, 날짜값을 채워넣는다.
 */
function buildCalendar() {
    let doMonth = new Date(today.getFullYear(), today.getMonth(), 1);
    let lastDate = new Date(today.getFullYear(), today.getMonth() + 1, 0);
    let calendarBody = document.getElementById("calendar-body");

    document.getElementById("calYear").innerText = today.getFullYear(); // @param YYYY월
    document.getElementById("calMonth").innerText = autoLeftPad((today.getMonth() + 1), 2); // @param MM월

    while (calendarBody.firstChild) {
        calendarBody.removeChild(calendarBody.firstChild);
    }

    let dom = 1;
    let daysLength = (Math.ceil((doMonth.getDay() + lastDate.getDate()) / 7) * 7) - doMonth.getDay();

    for (let day = 1 - doMonth.getDay(); daysLength >= day; day++) {
        let column = document.createElement("div");
        column.classList.add("calendar-cell");

        if (Math.sign(day) == 1 && lastDate.getDate() >= day) {
            column.innerText = autoLeftPad(day, 2);

            if (dom % 7 == 1) {
                column.style.color = "#FF4D4D";
            }

            if (dom % 7 == 0) {
                column.style.color = "#4D4DFF";
            }

            column.onclick = function () {
                calendarChoiceDay(this);
            };

            if (today.getFullYear() == new Date().getFullYear() &&
                today.getMonth() == new Date().getMonth() &&
                day == new Date().getDate()) {
                column.classList.add("today");
            }

        } else {
            let exceptDay = new Date(doMonth.getFullYear(), doMonth.getMonth(), day);
            column.innerText = autoLeftPad(exceptDay.getDate(), 2);
            column.classList.add("disabled");
        }

        calendarBody.appendChild(column);
        dom++;
    }

    checkBookShelf(doMonth, function (sList) {
        sList.forEach(function (bookShelf) {
            const bookShelfDate = new Date(bookShelf.regDt);
            const bookShelfDay = bookShelfDate.getDate();

            const cellIndex = bookShelfDay + doMonth.getDay() - 1;
            const cell = calendarBody.children[cellIndex];

            if (cell && !cell.classList.contains('disabled')) {
                const circleSc = document.createElement('div');
                circleSc.classList.add('circleSc');
                cell.appendChild(circleSc);
            }
        });
    });
}

function calendarChoiceDay(column) {
    let prevChoice = document.querySelector(".calendar-cell.choiceDay");
    if (prevChoice) {
        prevChoice.classList.remove("choiceDay");
    }

    column.classList.add("choiceDay");
    choiceDay = today.getFullYear() + "-" + (today.getMonth() + 1) + "-" + column.innerText.trim();
    console.log("Selected date:", choiceDay);

    // $("#btnradio4").click(); // 날짜 클릭했을때 일정이 기본으로 뜨게
    getBookShelfList(choiceDay);


}

function autoLeftPad(num, digit) {
    if (String(num).length < digit) {
        num = new Array(digit - String(num).length + 1).join("0") + num;
    }
    return num;
}