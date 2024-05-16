<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Autenticação</title>
<link rel="stylesheet" href="pages_common/styles/default.css" type="text/css" media="screen" charset="utf-8">
<link rel="stylesheet" href="pages_common/styles/button.css" type="text/css" media="screen" charset="utf-8">
</head>
<body>
<div id="top">
<div class="logo"></div>
<div class="titulo"></div>
</div>
<div id="content">
<h2>Digite usuário e senha</h2>
<form action="j_security_check" method="post">
	<div class="field"><label for="usuario">Usuário</label> <input
		type="text" name="j_username" id="j_username" /></div>
	<div class="field"><label for="senha">Senha</label> <input
		type="password" name="j_password" id="j_password" /></div>
	<div class="actions">
		<input type="submit" value="Entrar" class="btn-primary" />
	</div>
</form>
</div>
</body>
</html>