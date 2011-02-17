<html>
<head>
<title>Login to TIM Reports</title>
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/prototype/1.6.1/prototype.js"></script>
</head>
<body>
  <p>User name: <input type='text' name='username' id='username' size='25' /></p>
  <p>Password: <input type='password' name='password' id='password' size='25' /></p>
  <p id="login">log in</p>

  <div id='returning' />

  <script type="text/javascript">
    $('login').observe('click', function() {
      new Ajax.Request('/Restlet/welcome', {
        method: 'post',
        postBody: $H({
          username: $('username').value,
          password: $('password').value
        }).toJSON(),
        onSuccess: function(transport) {
            $('returning').update(transport.responseText);
        }
      });
    });
  </script>
</body>
</html>