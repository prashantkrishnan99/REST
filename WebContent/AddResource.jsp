<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<<title>ADD RESOURCE</title>
<script src="js/ResourceView.js" type="text/javascript"></script>
<script src="http://code.jquery.com/jquery-1.10.1.min.js" type="text/javascript"></script>
</head>
<body>
<p>Enter the Job ID :</p>
<p>Enter the Job Type :</p>
<p>Enter Start Date :</p>
<p>Enter End Date :</p>
<script type="text/javascript">
    $(document).ready(function() {
        var JsonArr = {"d":${objArray}};
		$('#Resource').append(ResourceView(JsonArr.d,"")).fadeIn();	
    });
</script>
</body>
</html>