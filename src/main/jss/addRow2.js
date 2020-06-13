function addRow2() {
    const name = $('#inputName').val();
    const email = $('#inputEmail').val();
    const desc = $('#inputDescription').val();
    var value = document.getElementsByName("sex")
    var sex;
    if (value[0].checked == true) {
        sex = 'Female';
    }
    if (value[1].checked == true) {
        sex = 'Male';
    }
    if ((name || email || desc).length > 0) {
        $('#t2 tr:last').after('<tr><td>' + name + '</td>' +
            ' <td>' + email + '</td>'
            + ' <td>' + desc + '</td>'
            + ' <td>' + sex + '</td>'
            + '</tr>');
    }
}