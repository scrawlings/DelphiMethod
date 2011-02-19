<html>
<head>
  <title>Delphi Method - Manage Survey</title>
  <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/prototype/1.6.1/prototype.js"></script>
  <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/scriptaculous/1.8.3/scriptaculous.js"></script>
</head>
<body>
  <div id="${survey.id}">
    <p>${survey.title}: ${survey.description}</p>

    <form method="post" action="">
      <ol id="options">
        <#list survey.options as option>
        <li class="drag_option"><input type='text' name='option' id='option' size='140' value='${option}' />(<span style="color:#D00" class="delete">delete</span>)</li>
        </#list>
      </ol>
      <p>(<span style="color:#00D" id="add_option">add an option</span>)<input type="submit" value="Submit" /></p>
    </form>
  </div>

  <div><p>(<a href="/delphi">home</a>|<a href="/delphi/surveys">surveys</a>|<a href="/delphi?action=logout">logout</a>)</p></div>

  <script type="text/javascript">

    var options_container = $('options');
    Sortable.create('options');

    var list_item = "<li class='drag_option'><input type='text' name='option' id='option' size='140' value=''>" +
                    "(<span style='color:#D00' class='delete'>delete</span>)</li>";

    $('add_option').observe('click', function(event) {
      options_container.insert(list_item);
      Sortable.create('options');
    });

    options_container.observe('click', function(event) {
      var element = Event.element(event);
      if (element.hasClassName('delete')) {
        element.up('li').remove();
      }
    });

  </script>
</body>
</html>