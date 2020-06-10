function addRow() {
    const name = $('#name').val();
    const email = $('#email').val();
    const desc = $('#desc').val();
    $('#t1 tr:last').after('<tr><td>' + name +'</td>' +
        ' <td>' + email +'</td>' +
        ' <td>' + desc +'</td>' +
        '</tr>');
}