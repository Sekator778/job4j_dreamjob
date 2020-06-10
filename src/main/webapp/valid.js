function validate() {
    const name = $('#name').val();
    const email = $('#email').val();
    const password = $('#pwd').val();
    if ((name === '')) {
        alert("Поле имя не может быть пустым");
    }
    if (name.length < 2) {
        alert("Длина имени должна быть минимум 2 символа");
    }
    if ((password === '')) {
        alert("Поле Password не может быть пустым");
    }

   checkEmail(email);
    return false;
}

function checkEmail(value) {
    let reg = /[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?/;
    if (!value.match(reg)) {
        alert("Пожалуйста, введите свой настоящий e-mail");
        document.getElementById('email').value = "";
        return false;
    }
}
