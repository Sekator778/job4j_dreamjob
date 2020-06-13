function addRow2() {
    const name = $('#inputName').val();
    const email = $('#inputEmail').val();
    const desc = $('#inputDescription').val();
    if ((name || email || desc).length > 0) {
        $('#t2 tr:last').after('<tr><td>' + name + '</td>' +
            ' <td>' + email + '</td>' +
            ' <td>' + desc + '</td>' +
            '</tr>');
    }
}