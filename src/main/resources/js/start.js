var option = {
    delay : 5000,
    animation: true,
    autohide: true
}
$('.toast').toast(option)

function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}
function deleteTransaction(transactionInstant, name) {
    // TODO refresh list after it's been done

    addNewToast(transactionInstant, name);
    const xhr = new XMLHttpRequest();
    const url = "deletetransaction/" + transactionInstant;
    xhr.open("DELETE", url);
    xhr.send();
}
function myFunction() {
    const checkBox = document.getElementById("half");
    const text = document.getElementById("moneyAmount");
    if (checkBox.checked === true){
        text.placeholder = "Total money";
    } else {
        text.placeholder = "Owed amount";
    }
}
function addNewToast(transactionInstant, name) {
    let toastID;
    if (transactionInstant === undefined) {
        toastID = "undefinedID";
    } else {
        toastID = transactionInstant
            .toString()
            .replaceAll(':','')
            .replaceAll('-','')
            .replaceAll('.','');
    }

    const toast = document.createElement("DIV");
    toast.setAttribute("class", "toast");
    toast.setAttribute("role", "alter");
    toast.setAttribute("id", toastID);

    const toasterHeader = document.createElement("DIV");
    toasterHeader.setAttribute("class", "toast-header");

    const img = document.createElement("IMG");
    img.setAttribute("class", "rounded mr-2");
    img.setAttribute("src", "../static/img/receipt-solid.svg");
    img.setAttribute("width", "16px");

    const titleText = document.createElement("STRONG");
    const strongTextNode = document.createTextNode("Bootstrap");
    titleText.appendChild(strongTextNode);

    var timeText = document.createElement("SMALL");
    timeText.setAttribute("class", "text-muted");
    timeText.setAttribute("style", "margin-left: 5px; margin-right: 5px");
    const smallTextNode = document.createTextNode("just now");
    timeText.appendChild(smallTextNode);


    const button = document.createElement("BUTTON");
    button.setAttribute("class", "ml-2 mb-1 close");
    button.setAttribute("type", "button");
    button.setAttribute("data-dismiss", "toast");
    button.setAttribute("aria-label", "Close");
    const span = document.createElement("SPAN");
    span.setAttribute("aria-hidden", "true");
    const imgCross = document.createElement("IMG");
    imgCross.setAttribute("src", "../static/img/cross.svg");
    imgCross.setAttribute("width", "16px");
    span.appendChild(imgCross);
    button.appendChild(span);

    const toasterHeaderLeft = document.createElement("DIV");
    const toasterHeaderRight = document.createElement("DIV");

    toasterHeaderLeft.appendChild(img);
    toasterHeaderLeft.appendChild(titleText);
    toasterHeaderRight.appendChild(timeText);
    toasterHeaderRight.appendChild(button);

    toasterHeader.appendChild(toasterHeaderLeft);
    toasterHeader.appendChild(toasterHeaderRight);

    toast.appendChild(toasterHeader);

    const toastBody = document.createElement("DIV");
    toastBody.setAttribute("class", "toast-body");
    const toastBodyTextnode = document.createTextNode("Deleting " + name);

    const undoButton = document.createElement("BUTTON");
    undoButton.setAttribute("class", "ml-2 mb-1 close");
    undoButton.onclick = function() {undoDelete(transactionInstant);};
    undoButton.setAttribute("type", "button");
    button.setAttribute("data-dismiss", "toast");
    const spanUndu = document.createElement("SPAN");
    spanUndu.setAttribute("aria-hidden", "true");
    const imgUndo = document.createElement("IMG");
    imgUndo.setAttribute("class", "undo-image");
    imgUndo.setAttribute("src", "../static/img/undo-solid.svg");
    imgUndo.setAttribute("width", "16px");
    spanUndu.appendChild(imgUndo);
    undoButton.appendChild(spanUndu);


    toastBody.appendChild(toastBodyTextnode);
    toastBody.appendChild(undoButton);

    toast.appendChild(toastBody);

    document.getElementById("toasts").appendChild(toast);

    $(".toast").toast(option);
    $('#' + toastID).toast("show");
}

function undoDelete(transactionInstant) {
    const xhr = new XMLHttpRequest();
    const url = "undeletetransaction/" + transactionInstant;
    xhr.open("DELETE", url);
    xhr.send();
}

async function viewDebt(sel) {
    const name = sel.options[sel.selectedIndex].text


    const url = "getdebt/" + name;
    let response = await fetch(url);

    if (response.ok) {  // if HTTP-status is 200-299
        // get the response body (the method explained below)
        let json = await response.json();
        console.log(json)
        const debtDiv = document.getElementById("sidebar-debt");
        // empty div
        while (debtDiv.firstChild) {
            debtDiv.removeChild(debtDiv.firstChild);
        }
        for (const d in json) {
            const userDebtJSON = json[d]

            const userDebt = document.createElement("DIV");

            const userDebtName = document.createElement("SPAN");
            const debtTextName = document.createTextNode("Owes " + userDebtJSON["name"]);
            userDebtName.setAttribute("class", "sidebar-text-small");
            userDebtName.appendChild(debtTextName);

            const userDebtDebt = document.createElement("SPAN");
            const debtTextDebt = document.createTextNode(userDebtJSON["debt"] + " kr");
            userDebtDebt.setAttribute("class", "sidebar-text-small");
            userDebtDebt.appendChild(debtTextDebt);

            userDebt.setAttribute("color", "cyan")
            userDebt.setAttribute("background-color", "red")
            userDebt.setAttribute("display", "flex");
            userDebt.setAttribute("justify-content", "space-between");
            userDebt.appendChild(userDebtName);
            userDebt.appendChild(userDebtDebt);
            debtDiv.appendChild(userDebt);
        }
    } else {
        alert("HTTP-Error: " + response.status);
    }

}