<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>title</title>
<script type="text/javascript">

    function login() {
        var loginName = "qiuj";
        var loginPas = "qiuj";
        if (loginName == "") {
            $.messager.alert('提示','请输入用户名！','warning');
            loginForm.username.focus();
            return;
        } else if(loginPas == "") {
            $.messager.alert('提示','请输入密码！','warning');
            loginForm.password.focus();
            return;
        } else {
            loginForm.action = "login";
            loginForm.submit();
        }
    }
    
</script>
</head>
<body>
    <div>
        <form id="loginForm" name="loginForm" action="" method="post">
            <input type="text" name="username" id="username"/>
            <input type="password" name="password" id="password"/>
            <input type="button" value="登录" onclick="login();"/>
        </form>
    </div>
</body>
</html>