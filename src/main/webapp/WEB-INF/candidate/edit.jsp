<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="ru.job4j.dream.model.Candidate" %>
<%@ page import="ru.job4j.dream.store.PsqlStore" %>
<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
            integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
            crossorigin="anonymous"></script>
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
    <%--    проверка на корректность ввода--%>
    <%--    <script>--%>
    <%--        function validate() {--%>
    <%--            var name = $('#name').val();--%>
    <%--            if (name == '') {--%>
    <%--                alert($('#name').attr('placeholder'));--%>
    <%--            }--%>
    <%--        }--%>
    <%--    </script>--%>
    <script>
        function checkParams() {
            var name = $('#name').val();
            if (name.length > 0) {
                $('#bt').removeAttr('disabled');
            } else {
                $('#bt').attr('disabled', 'disabled');
            }
        }
    </script>
    <script>
        $(document).ready(function () {
            $.ajax({
                type: 'GET',
                url: 'http://localhost:8081/dreamjob/city',
                datatype: 'text'
            }).done(function (data) {
                var cities = data;
                var text = "<option selected value=\"\">Select city</option>";
                for (var i = 0; i !== cities.length; ++i) {
                    text += "<option value='" + cities[i] + "'>" + cities[i] + "</option>";
                }
                document.getElementById("cities").innerHTML = text;
            })
        });
    </script>
    <title>Работа мечты</title>
</head>
<body>
<%
    String id = request.getParameter("id");
    Candidate candidate = new Candidate(0, "", "", 1);
    if (id != null) {
        candidate = PsqlStore.instOf().findByIdCandidate(Integer.valueOf(id));
    }
%>
<div class="container pt-3">
    <div class="row">
        <div class="card" style="width: 100%">
            <div class="card-header">
                <% if (id == null) { %>
                Новый кандидат.
                <% } else { %>
                Редактирование кандидата.
                <% } %>
            </div>
            <div class="card-body">
                <form action="<%=request.getContextPath()%>/candidates.do" method="post"
                      enctype="multipart/form-data">
                    <%--                    field name--%>
                    <div class="form-group">
                        <label>Имя</label>
                        <input type="text" class="form-control" name="name" id="name" placeholder="Enter name"
                               value="<%=candidate.getName()%>" required>
                    </div>
                    <%--                    field photoId--%>
                    <div class="form-group">
                        <label>Фото</label>
                        <input type="file" class="checkbox" name="photoId" onchange="checkParams()"
                               value="<%=candidate.getPhotoId()%>">
                    </div>
                    <%--                    field city--%>
                        <div class="form-group">
                            <label class="control-label col-sm-2" for="city">City:</label>
                            <div class="col-sm-10" id="city">
                                <select id="cities" class="form-control" name="cities">
                                    <option selected value="">Select city</option>
                                </select>
                            </div>
                        </div>


                    <%--                    field Save button--%>

                    <button type="submit" class="btn btn-primary" id="bt" disabled>Сохранить</button>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
