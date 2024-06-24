
    $(document).ready(function() {
    // "품목코드" 셀을 클릭했을 때 이벤트 리스너 추가
    $(".itemList").on("click", function() {
        var itCode = $(this).find("#showPanel").text().trim();
        let selectCheck = [];
        $("#checkBox:checked").each(function(index){
            var value = $(this).val();
            selectCheck.push(value);
        })
        // AJAX 요청으로 품목 정보를 가져옴

        $.ajax({
            url: "/routing/" + itCode,
            method: "GET",
            success: function (data) {
                var tableContent = "";
                data.process.forEach(function(item, index) {
                    tableContent += "<tr>" +
                        "<td>" + (index + 1) + "</td>" +
                        "<td>" + item.pcCode + "</td>" +
                        "<td>" + item.pcName + "</td>" +
                        "<td>" + item.pcCnt + "</td>" +
                        "<td>" + " " + "</td>" +
                        "</tr>";
                });
                $("#itemInfo").html(tableContent);
                // 패널을 보여줌
                $("#panelshow").show();
            },
            error: function () {
                alert("품목 정보를 불러오는 데 실패했습니다.");
            }
        });
    });
});

    document.addEventListener("DOMContentLoaded", () => {
    const routingAddBtn = document.getElementById("routingAddBtn");

    if (routingAddBtn) {
    routingAddBtn.addEventListener("click", (event) => {

    //var itCode = document.getElementById("showPanel").value
    var itCode = $(this).find("#showPanel").text().trim();
    let selectCheck = [];
    $("#checkBox:checked").each(function(index){
    var value = $(this).val();
    selectCheck.push(value);
})

    selectCheck.forEach((pcCode, index) => {
    fetch("/routing", {
    method: "POST",
    headers: {
    "Content-Type": "application/json",
},
    body: JSON.stringify({
    itCode : "IT240619115849",
    pcCode : pcCode,
    sequence: index + 1
}),
}).then(() => {
    alert("등록 완료되었습니다. ");
    location.replace("/routing");
});
})
})
}
})

    //품목 등록
    document.addEventListener("DOMContentLoaded", () => {
    const createButton = document.getElementById("itemCreateBtn");
    if (createButton) {
    createButton.addEventListener("click", (event) => {
    fetch("/items", {
    method: "POST",
    headers: {
    "Content-Type": "application/json",
},
    body: JSON.stringify({
    itName: document.getElementById("itName").value,
    itType: document.getElementById("itType").value
}),
}).then(() => {
    alert("등록 완료되었습니다. ");
    location.replace("/routing");
});
});
}
});
    //공정 등록
    document.addEventListener("DOMContentLoaded", () => {
    const createButton = document.getElementById("processBtn");

    if (createButton) {
    createButton.addEventListener("click", (event) => {
    fetch("/addProcess", {
    method: "POST",
    headers: {
    "Content-Type": "application/json",
},
    body: JSON.stringify({
    pcName: document.getElementById("pcName").value,
    pcCnt: document.getElementById("pcCnt").value
}),
}).then(() => {
    alert("등록 완료되었습니다. ");
    location.replace("/routing");
});
});
}
});
