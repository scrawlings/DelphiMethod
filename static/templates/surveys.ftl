<html>
<head>
  <title>Delphi Method - Manage Surveys</title>
  <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/prototype/1.6.1/prototype.js"></script>
</head>
<body>
  <div id="existing_surveys">
    <ul>
      <#list surveys as survey>
      <li>
        <a href="${survey.id}">${survey.title}</a>: ${survey.description} (<span style="color:#D00" class="delete" id="${survey.id}">delete</span>)
      </li>
      </#list>
    </ul>
  </div>

  <form method="post" action="">
    <p>Survey Title: <input type='text' name='title' id='title' size='40' /></p>
    <p>Survey Description: <br /><textarea  name='description' id='description' rows="4" cols="60"></textarea></p>
    <p><input type="submit" value="Submit" /></p>
  </form>

  <script type="text/javascript">
    $$('.delete').invoke('observe', 'click', function(event) {
      var element = Event.element(event);
      new Ajax.Request('/delphi/surveys/' + element.id, {
        method: 'delete',
        onSuccess: function() {
            element.up('li').remove();
        }
      });
    });
  </script>
</body>
</html>