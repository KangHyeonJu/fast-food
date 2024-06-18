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
                    itType: document.getElementById("itType").value,
                    itCode: document.getElementById("itCode").value
                }),
            }).then(() => {
                alert("등록 완료되었습니다. ");
                location.replace("/routing");
            });
        });
    }
});