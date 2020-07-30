<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="corpusUtils.*"%>
<%@ page isThreadSafe="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Magahi Search Engine</title>
<script language="JavaScript" src= "jsFunctions.js"></script>
<style type="text/css">
table {
	font-size: 16px;
	!
	important
}

input {
	font-size: 16px;
	color: maroon;
	background: white;
	width: 90%;
	height: 30px;
}

textarea {
	font-size: 16px;
}

select {
	font-size: 16px;
	text-align: left;
}

#blog {
	color: rgb(102, 0, 2); /* #FF1493 */
	background-color: #FFFFFF;
	border: 2px outset #FFFFFF;
	border-width: 6px;
	padding: 0.20em;
	font-size: 1.3em;
	display: block;
	width: 300px;
	height:80px
}

#pub {
	color: rgb(102, 0, 2); /* #FF1493 */
	background-color: #FFFFFF;
	border: 2px outset #FFFFFF;
	border-width: 6px;
	pading: 0.20em;
	font-size: 1.3em;
	display: block;
	width: 300px;
	height:80px
}

#utube {
	color: rgb(102, 0, 2); /* #FF1493 */
	background-color: #FFFFFF;
	border: 2px outset #FFFFFF;
	border-width: 6px;
	padding: 0.20em;
	font-size: 1.3em;
	display: block;
	width: 300px;
	height:80px
}

#portal {
	color: rgb(102, 0, 2); /* #FF1493 */
	background-color: #FFFFFF;
	border: 2px outset #FFFFFF;
	border-width: 6px;
	padding: 0.20em;
	font-size: 1.3em;
	display: block;
	width: 300px;
	height:80px
}

#priv {
	color: rgb(102, 0, 2); /* #FF1493 */
	background-color: #FFFFFF;
	border: 2px outset #FFFFFF;
	border-width: 6px;
	padding: 0.20em;
	font-size: 1.3em;
	display: block;
	width: 300px;
	height:80px
}

#mail {
	color: rgb(102, 0, 2); /* #FF1493 */
	background-color: #FFFFFF;
	border: 2px outset #FFFFFF;
	border-width: 6px;
	padding: 0.20em;
	font-size: 1.3em;
	display: block;
	width: 300px;
	height:80px
}
</style>

</head>
<body>
	<% request.setCharacterEncoding("UTF-8"); %> 
	<form name="login" method="post" action="coThreeHSearch.jsp"
		accept-Charset="UTF-8">
		
		<input type = "hidden" name = "flag" value = "">
		<input type = "hidden" name = "fileId" value = "0">
		
		<table style="text-align: left; width: 95%" align="center" border="0"
			cellpadding="2" cellspacing="2">
			<tbody>
				<tr valign="middle" style="background-color: rgb(102, 0, 2)">
					<td align="center"><br>
						<table style="text-align: left; width: 100%;" align="center"
							border="1" cellpadding="2" cellspacing="2">
							<tbody>
								<tr style="color: white;">
									<td
										style="background-color: rgb(102, 0, 0); text-align: center; vertical-align: middle;"
										colspan="10"><br> <big><big><big><big><span
														style="font-weight: bold;">Corpus of
															Magahi</span></big></big></big></big><br>
										<div style="text-align: right;">
											<big><big style="font-style: italic;">The Search Engine</big></big><br>
										</div> <br></td>
								</tr>
								<tr valign="middle">
									<td
										style="background-color: rgb(102, 0, 0); text-align: center; vertical-align: middle;">
										<table style="table-layout: fixed;text-align: left; width: 98%;" align="center" border = "0" cellspacing="25">
											<tbody>
												<tr style="color: white;">
												<td colspan = "4" align = "center">
												<font size = "6"> What do you want to Search? </font>
												</tr>
												<tr>
												<td colspan = "4" align = "center">
												<input type = "text" name = "searchText" value = "" id = "searchBox">
												</td>
												</tr>
												<tr style="color: pink;">
													<td colspan = "4" align = "center"><font size = "5">Select Corpus to Search</font></td>
												</tr>
												<tr style="color: white;">
												<td></td>
												<td>
												<label for = "radio7"> Magahi Story Corpus<br/> </label><input type = "radio" id = "radio7" name = "corpusType" value = "magahi_story">
												</td>
												<td>
												<label for = "radio7"> Magahi Blog Corpus<br/> </label><input type = "radio" id = "radio7" name = "corpusType" value = "magahi_blog">
												</td>
												<td></td>
												</tr>
												<tr style="color: pink;">
												<td>
												</td>
												<td colspan = "2" align = "center">
												<font size = "5">Regex Support</font>
												</td>
												<td>
												</td>
												</tr>
												<tr style="color: white;">
												<td>
												</td>
												<td align = "left">
												<label for = "check1">Enabled</label><input type="radio" id = "check1" name = "regexSupport" value = "true"/>
												</td>
												<td align = "left">
												<label for = "check2">Disabled</label><input type="radio" id = "check2" name = "regexSupport" value = "false" checked="checked"/>
												</td>
												<td></td>
												</tr>
												<tr>
												<td colspan = "4" align = "center">
												<input type = "submit" value = "Come on! Search Me! ;)">
											</tbody>
										</table>
									</td>
								</tr>
							</tbody>
						</table></td>
				</tr>
				<tr>
					<td><br /> <br /></td>
				</tr>
				<tr style="text-align: center">
					<td><a rel="license"
						href="http://creativecommons.org/licenses/by-sa/3.0/"><img
							alt="Creative Commons License" style="border-width: 0"
							src="http://i.creativecommons.org/l/by-sa/3.0/88x31.png" /></a><br />
						<span xmlns:dct="http://purl.org/dc/terms/"
						href="http://purl.org/dc/dcmitype/Dataset" property="dct:title"
						rel="dct:type">Corpus of Computer-Mediated Communication in
							Hindi</span> is licensed under a <a rel="license"
						href="http://creativecommons.org/licenses/by-sa/3.0/">Creative
							Commons Attribution-ShareAlike 3.0 Unported License</a>.</td>
				</tr>
			</tbody>
		</table>
	</form>
</body>
</html>
