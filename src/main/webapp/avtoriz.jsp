<!DOCTYPE html>
<html lang="en">
<head>
    <title>Bootstrap Example</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
    <script src="../jss/addRow2.js"></script>
    <style>
        body {
            background-color: azure;
        }
    </style>
</head>

<body>

<div class="container">
    <h2>Заполните поля</h2>
    <h4>для добавления нового пользователя</h4>
    <div class="container" >
        <form action="<%=request.getContextPath()%>/reg.do" method="post">

            <!--// name обращаемсья к #inputName-->
            <div class="form-group">
                <label class="control-label" for="inputName" >Name</label>
                <input type="text" class="form-control " id="inputName" placeholder="Enter name" required>
            </div>

            <!--// description обращаемсья к #inputDescription-->
            <div class="form-group">
                <label class="control-label" for="inputDescription" >Description</label>
                <input type="text" class="form-control " id="inputDescription" placeholder="Enter description">
            </div>

            <!--// email обращаемсья к #inputEmail-->
            <div class="form-group">
                <label for="inputEmail" class="control-label">Email</label>
                <input type="email" class="form-control" id="inputEmail" placeholder="Enter Email" data-error="Bruh, that email address is invalid" required name="email">
                <div class="help-block with-errors"></div>
            </div>

            <!--// email обращаемсья к #inputPassword-->
            <div class="form-group">
                <label for="inputPassword" class="control-label">Password</label>
                <div class="form-inline row">
                    <div class="form-group col-sm-3">
                        <input type="password" data-minlength="1" class="form-control" id="inputPassword" placeholder="Password" required name="password">
                        <div class="help-block">Minimum of 1 characters</div>
                    </div>
                    <div class="form-group col-sm-3">
                        <input type="password" class="form-control" id="inputPasswordConfirm" data-match="#inputPassword" data-match-error="Whoops, these don't match" placeholder="Confirm" required>
                        <div class="help-block with-errors"></div>
                    </div>
                </div>
            </div>

            <!--// email обращаемсья к # -->
            <div class="form-group">
                <label for="inputPassword" class="control-label">Sex:</label>

                <div class="radio">
                    <label>
                        <input type="radio" name="sex" required>
                        Female
                    </label>
                </div>
                <div class="radio">
                    <label>
                        <input type="radio" name="sex" required>
                        Male
                    </label>
                </div>
            </div>

            <div class="form-group">
                <div class="checkbox">
                    <label>
                        <input type="checkbox" id="terms" required>
                        Check yourself
                    </label>
                </div>
            </div>
            <div class="form-group">
                <button type="submit" class="btn btn-primary">Submit</button>
            </div>
            <button type="submit" class="btn btn-primary" onclick="return addRow2();">Add Row</button>
        </form>



    </div>
</div>
<!--table table-->
<div class="container">
    <h2>Текущие кандидаты</h2>
    <table class="table" id="t2">
        <thead>
        <tr>
            <th>Name</th>
            <th>Email</th>
            <th>Description</th>
            <th>Sex</th>
        </tr>
        </thead>
        <tbody>
<!--        <tr>-->
<!--            <td>Bob</td>-->
<!--            <td>fr65@gmail.com</td>-->
<!--            <td>java first description</td>-->
<!--            <td>Male</td>-->
<!--        </tr>-->
<!--        <tr>-->
<!--            <td>Dilon</td>-->
<!--            <td>john@example.com</td>-->
<!--            <td>java experience 4 year</td>-->
<!--            <td>Male</td>-->

<!--        </tr>-->
<!--        <tr>-->
<!--            <td>Salma</td>-->
<!--            <td>mary@example.com</td>-->
<!--            <td>java developer + CSS</td>-->
<!--            <td>Female</td>-->
<!--        </tr>-->
<!--        <tr class="info">-->
<!--            <td>Info</td>-->
<!--            <td>Dooley</td>-->
<!--            <td>july@example.com</td>-->
<!--            <td>Female</td>-->
<!--        </tr>-->
<!--        <tr class="danger">-->
<!--            <td>Petr</td>-->
<!--            <td>java guru + more</td>-->
<!--            <td>bo@example.com</td>-->
<!--            <td>Male</td>-->
<!--        </tr>-->
<!--        <tr class="active">-->
<!--            <td>Active</td>-->
<!--            <td>java developer + angular experience</td>-->
<!--            <td>act@example.com</td>-->
<!--            <td>Male</td>-->
<!--        </tr>-->
        </tbody>
    </table>
</div>
</body>
</html>