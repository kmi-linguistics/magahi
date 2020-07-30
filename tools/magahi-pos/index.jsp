<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="morph.*"%>
<%@ page isThreadSafe="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<META NAME="Author" CONTENT="Ritesh Kumar, Bornini Lahiri, Deepak Alok">
<META NAME="Keywords" CONTENT="Magahi, Magahi POS, Magahi Part-of-Speech Tagger, Part-of-Speech Tagger, POS Tagger, Computational Linguistics, NLP, Natural Language Processing, Lesser known languages, Lesser known languages of India, LRL, Less resourced languages, Non scheduled languages, Bihari languages, rule based, language technology, language technology in India">
<META NAME="Description" CONTENT="Magahi, Magahi POS, Magahi Part-of-Speech Tagger, Part-of-Speech Tagger, POS Tagger, Computational Linguistics, NLP, Natural Language Processing, Lesser known languages, Lesser known languages of India, LRL, Less resourced languages, Non scheduled languages, Bihari languages, rule based, language technology, language technology in India">

<title>Magahi Part-of-Speech Tagger</title>
<script language="JavaScript" src="jsFunctions_magahiPos.js"></script>
<style type="text/css">
table {
	font-size: 20px;
	!
	important
}

input {
	font-size: 30px;
	color: maroon;
	background: white;
	width: 95%;
	height: 60px;
}

textarea {
	font-size: 20px;
	text-align: justify;
	width: 90%;
	height: 180px;
}
</style>

</head>
<body>
	<% request.setCharacterEncoding("UTF-8"); %>
	<form name="morphanal" method="post" action="coThreeHSearch.jsp"
		accept-Charset="UTF-8">

		<input type="hidden" name="flag" value=""> <input
			type="hidden" name="fileId" value="0">

		<table style="text-align: left; width: 95%" align="center" border="0"
			cellpadding="2" cellspacing="2">
			<tbody>
				<tr valign="middle" style="background-color: rgb(102, 0, 2)">
					<td align="center"><br>
						<table style="text-align: left; width: 100%;" align="center"
							border="0" cellpadding="2" cellspacing="2">
							<tbody>
								<tr style="color: white;">
									<td
										style="background-color: rgb(102, 0, 0); text-align: center; vertical-align: middle;"
										colspan="3"><br> <big><big><big><big><span
														style="font-weight: bold;">मगही के शब्द भेद 
															विश्लेषक </span></big></big></big></big><br> <br></td>
									<td
										style="background-color: rgb(102, 0, 0); text-align: center; vertical-align: middle;"
										colspan="3"><br> <big><big><big><big><span
														style="font-weight: bold;">Magahi Part-of-Speech Tagger
															</span></big></big></big></big><br> <br></td>
								</tr>
								<tr style="color: white;">
									<td colspan="3" align="center" style="font-size: 25px;"> टैग 
										करे लगी कुछ लिखअ! <br/><br/></td>
									<td colspan="3" align="center" style="font-size: 25px;">Enter
										something to tag!<br/><br/></td>
								</tr>
								<tr>
									<td colspan="6" align="center"><textarea
											name="analyseText" id="searchBox"></textarea><br/><br/></td>
								</tr>
								<tr>
									<td colspan="6" align="center"><input type="button"
										value="टैग करअ!                                                              Tag the data!"
										onClick="analyseData()" onkeypress="handleEnter(this, event)">
								</tr>
							</tbody>
						</table></td>
				</tr>
		</table>
		<div id="anal"></div>
		<table  align="center">
			<tbody>
				<tr style="text-align: center">
					<td><a rel="license"
						href="http://creativecommons.org/licenses/by-sa/3.0/"><img
							alt="Creative Commons License" style="border-width: 0"
							src="http://i.creativecommons.org/l/by-sa/3.0/88x31.png" /></a><br />
						<span xmlns:dct="http://purl.org/dc/terms/"
						href="http://purl.org/dc/dcmitype/Dataset" property="dct:title"
						rel="dct:type">Magahi Part-of-Speech Tagger</span> is licensed under a <a rel="license"
						href="http://creativecommons.org/licenses/by-sa/3.0/">Creative
							Commons Attribution-ShareAlike 3.0 Unported License</a>.</td>
				</tr>
			</tbody>
		</table>
	</form>
</body>
</html>
