var userAccount;

function btnSignin() {
    let form = document.forms["signin"];

    $.ajax({
        url: 'http://localhost:8080/atm/signin/' + form.accountNumber.value + '/' + form.pin.value,
        contentType: "application/json; charset-utf-8",
        type: 'GET',
        success: function (data) {
            if (data != null) {
                userAccount = JSON.parse(data);
                $("#account-number-value")[0].textContent = userAccount.accountNumber;
                $("#balance-value")[0].textContent = userAccount.openingBalance;
                $("#overdraft-value")[0].textContent = userAccount.overdraft;
                retrivePageHome();
            } else {
                alert("Não foi possível logar.")
            }
        },
        error: function (err) {
            alert(err.responseText);
        }
    });
}

function attSignin() {
    if (userAccount != null) {
        $.ajax({
            url: 'http://localhost:8080/atm/signin/' + userAccount.accountNumber + '/' + userAccount.pin,
            contentType: "application/json; charset-utf-8",
            type: 'GET',
            success: function (data) {
                userAccount = JSON.parse(data);
                $("#account-number-value")[0].textContent = userAccount.accountNumber;
                $("#balance-value")[0].textContent = userAccount.openingBalance;
                $("#overdraft-value")[0].textContent = userAccount.overdraft;
            },
            error: function (err) {
                alert(err.responseText);
            }
        });
    }
}

function execOptBalance() {
    if (userAccount != null) {
        let balance = {
            accountNumber: userAccount.accountNumber,
            pin: userAccount.pin,
            optionType: 1
        }

        $.ajax({
            url: 'http://localhost:8080/atm/opt',
            type: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            contentType: "application/json; charset-utf-8",
            data: JSON.stringify(balance),
            success: function (data) {
                console.log(data);
            },
            error: function (err) {
                alert(err.responseText);
            }
        });
    } else {
        retrivePageSignin();
    }
}

function execOptWithdraw() {
    let form = document.forms["withdraw"];

    if (form.amount.value != null && form.amount.value > 0) {
        let withdraw = {
            accountNumber: userAccount.accountNumber,
            pin: userAccount.pin,
            optionType: 2,
            valueWithdraw: parseFloat(form.amount.value)
        };

        $.ajax({
            url: 'http://localhost:8080/atm/opt',
            type: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            data: JSON.stringify(withdraw),
            contentType: "application/json; charset-utf-8",
            success: function (data) {
                if (data.entity != null) {
                    alert(data.message);
                    $("#valueMaxMachine")[0].textContent = getTotalBanking();
                    $("#valueOverdraft")[0].textContent = data.entity.overdraft;
                } else {
                    alert(data.message);
                }
            },
            error: function (err) {
                alert(err.responseText);
            }
        });
    } else {
        $("#withdrawValue").focus();
    }
}

function execOptDeposit() {
    let form = document.forms["deposit"];

    if (form.amount.value != null && form.amount.value > 0) {
        let deposit = {
            accountNumber: userAccount.accountNumber,
            pin: userAccount.pin,
            optionType: 3,
            valueDeposit: parseFloat(form.amount.value)
        };

        $.ajax({
            url: 'http://localhost:8080/atm/opt',
            type: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            data: JSON.stringify(deposit),
            contentType: "application/json; charset-utf-8",
            success: function (data) {
                alert(data.message);
                $("#valueOpeningbalance")[0].textContent = data.entity.openingBalance;
                $("#valueDeposit")[0].textContent = form.amount.value;
            },
            error: function (err) {
                alert(err.responseText);
            }
        });
    } else {
        $("#depositValue").focus();
    }
}

function retrivePageHome() {
    view("home", true);
    view("withdraw", false);
    view("deposit", false);
    view("signin", false);

    attSignin();
}

function retrivePageSignin() {
    view("home", false);
    view("withdraw", false);
    view("deposit", false);
    view("signin", true);
}

function retrivePageWithdraw() {
    if (userAccount != null) {
        view("home", false);
        view("withdraw", true);
        view("deposit", false);
        view("signin", false);

        $("#valueMaxMachine")[0].textContent = getTotalBanking();
        $("#valueOverdraft")[0].textContent = userAccount.overdraft;
    } else {
        retrivePageSignin();
    }
}

function getTotalBanking() {
    let value = 0;
    $.ajax({
        url: 'http://localhost:8080/atm/bank/total',
        contentType: "application/json; charset-utf-8",
        type: 'GET',
        async: false,
        success: function (data) {
            value = data;
        },
        error: function (err) {
            alert(err.responseText);
        }
    });
    return value;
}

function retrivePageDeposit() {
    if (userAccount != null) {
        view("home", false);
        view("withdraw", false);
        view("deposit", true);
        view("signin", false);

        $("#valueOpeningbalance")[0].textContent = userAccount.openingBalance;
        $("#valueDeposit")[0].textContent = 0;
    } else {
        retrivePageSignin();
    }
}

function view(name, opt) {
    let title = $("#title-" + name);
    let atmMachine = $("#atm-machine-" + name);

    if (opt) {
        title.removeClass("hidden");
        atmMachine.removeClass("hidden");
    } else {
        if (!title.hasClass("hidden") && !atmMachine.hasClass("hidden")) {
            title.addClass("hidden");
            atmMachine.addClass("hidden");
        }
    }
}