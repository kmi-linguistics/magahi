<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
		<%@ page isThreadSafe="false" %>
    <%@ page import="co3h.*" %>
    <%@ page import="corpusUtils.*" %>
    <%@ page import="java.io.*" %>
    <%@ page import="java.util.*" %>
    <%@ page import="java.nio.file.*" %>
    <%!String corpusType = "", text = "";%>
    <%!boolean regexSupport = false;%>
    <%!int fileId = 0, totalFiles = 0;%> 
    <%-- <%!List<String> previousList = new ArrayList<String>();%> --%>
    <%!List<String> prevFiles = new Vector<String>();%>
    <%!Map<Integer, String> chatCompleteMap = Collections
			.synchronizedMap(new LinkedHashMap<Integer, String>());%>
    <%!Map<Integer, String> asyncCompleteMap = Collections
			.synchronizedMap(new LinkedHashMap<Integer, String>());%>
  	<%!Map<String, String> searchResultMap = Collections
			.synchronizedMap(new LinkedHashMap<String, String>());%>
	<%!List<String> allFiles = new ArrayList<String>(); %>
    <%!File[] files;%> 

<%
	request.setCharacterEncoding("UTF-8");

	try {
		corpusType = request.getParameter("corpusType").trim();
		System.out.println("corpus type is: " + corpusType);
	} catch (Exception e) {
		System.out.println("Into corpus type catch: " + e);
		corpusType = "blog";
	}

	try {
		text = request.getParameter("searchText");
		System.out.println("text received: " + text);
	} catch (Exception e) {
		System.out.println("Into currentFileName catch: " + e);
		response.sendRedirect("index.jsp");
	}
	
	try {
		regexSupport = Boolean.parseBoolean(request.getParameter("regexSupport"));
		System.out.println ("Regex support: " + regexSupport);
	} catch (Exception e ) {
		System.out.println ("Into regexSupport catch: " + e);
		regexSupport = false;
	}
//This is to be used if the corpus is inside WEB-INF
	String corpusPath = request.getSession().getServletContext()
	.getRealPath("/")
	+ "WEB-INF/datafiles/"
	+ corpusType;
	
//This is hardcoded for my laptop
//	String corpusPath = "/home/ritesh/Documents/hindi-corpus-processor/xml-data/latest-versions";
	
	File files = new File (corpusPath);
	File [] fileList = files.listFiles();
	allFiles.clear();
	for (File file:fileList) {
		allFiles.add(file.getName());
	}
	Collections.sort(allFiles, new SortAlphaNumeric());
	searchResultMap.clear();
	for (String file: allFiles) {
		asyncCompleteMap.clear();
		System.out.println("Reading and search of file " + corpusPath
				+ "/" + file + " started!");
		if (searchResultMap.size() < 30000) {
			//InputStream is = request.getSession().getServletContext().getResourceAsStream();  
			XmlReader xrd = new XmlReader(corpusPath
					+ "/" + file);
			SimpleFileReader sfr = new SimpleFileReader(corpusPath + "/" + file);
			
			if (corpusType.indexOf ("magahi_blog") > -1 || corpusType.indexOf ("magahi_story") > -1 || corpusType.indexOf("facebook") > -1 || corpusType.indexOf("khs") > -1) {
				asyncCompleteMap = sfr.sFileReader(); 
			}	else	if (corpusType.indexOf("public_chat") > -1) {
				chatCompleteMap = xrd.readChat("public");
				System.out.println("reading of public chat files complete");
			} else if (corpusType.indexOf("private_chat") > -1) {
				System.out.println ("Reading private chats!");
				chatCompleteMap = xrd.readChat("private");
				System.out.println ("Total private chats in JSP: " + chatCompleteMap.size());
			} else if (corpusType.indexOf("email") > -1) {
				asyncCompleteMap = xrd.readAsync(1);
			} else if (corpusType.indexOf("blog2") > -1 || corpusType.indexOf("blog") > -1 || corpusType.indexOf("portal") > -1) {
				asyncCompleteMap = xrd.readAsync(2);
			} else if (corpusType.indexOf("youtube") > -1) {
				asyncCompleteMap = xrd.readAsync(3); 
			} else if (corpusType.indexOf("ilci") > -1) {
				asyncCompleteMap = sfr.sFileReader(); 
			}
			
			
			
			//Currently hardcoded to work for comments (1 as an argument to searchTheText tells to start at id 1 and not 0, 
			//		which is for main content) in blogs
			CorpusSearchEngine cse = new CorpusSearchEngine(corpusPath
					+ "/" + file); 
			System.out.println ("Size of chat map: " + chatCompleteMap.size());
			if (chatCompleteMap.size() > 0) {
 				searchResultMap.putAll(cse.searchTheText(text, 0,
 						chatCompleteMap, regexSupport));
 			} else {
 				searchResultMap.putAll(cse.searchTheText(text, 1,
 						asyncCompleteMap, regexSupport));
 			}
		} else { 
			break;
		}
	}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Magahi Search Engine</title>
<script language = "JavaScript" src= "jsFunctions.js"> </script>
<style type=text/css>
textarea {
	font-size: 21px;
	background-color: #D6D6D6;
	width:800px;
	text-align:justify;
	height:420px; 
	border:none;
	color:#FF1919
}
input {
	font-size: 16px;
	color: maroon;
	background: white;
	width: 90%;
	height: 30px;
}
#ahead {
	color: green;
	background-color: #FFFFFF;
	border: 2px outset #FFFFFF;
	border-width: 6px;
	padding: 0.20em;
	font-size: 1.3em;
	display: block;
	width: 230px;
	height: 80px;
	white-space: normal;
}

#rev {
	color: #B86500;
	background-color: #FFFFFF;
	border: 2px outset #FFFFFF;
	border-width: 6px;
	padding: 0.20em;
	font-size: 1.3em;
	display: block;
	width: 230px;
	height: 70px;
}

#prev {
	color: red;
	background-color: #FFFFFF;
	border: 2px outset #FFFFFF;
	border-width: 6px;
	padding: 0.20em;
	font-size: 1.3em;
	display: block;
	width: 230px;
	height: 80px;
	white-space: normal;
}
</style>
</head>
<body>
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
					<td>
						<table style="text-align: left; width: 100%;" align="center"
							border="1" cellpadding="2" cellspacing="2">
							<tr style="color: maroon; font-size: 35px">
								<td colspan = "3"  align = "center"> Total Results Found: <%=searchResultMap.size() %> </td>
							</tr>
							<tr style="background-color: rgb(102, 0, 0); color: white">
								<td><b>File Name</b></td>
								<td><b>Comment Id</b></td>
								<td><b>Search Text</b></td>
							</tr>
							<%
								Set<String> stringDescriptors = searchResultMap.keySet();
										String fileName = "", commentId = "", searchString = "";
										if (stringDescriptors.size() > 0) {
									for (String stringDescriptor : stringDescriptors) {
										fileName = stringDescriptor.substring(0,
												stringDescriptor.indexOf("\t"));
										commentId = stringDescriptor.substring(stringDescriptor
												.indexOf("\t") + 1);
										searchString = searchResultMap.get(stringDescriptor);
							%>
							<tr style="color: maroon">
								<td><b><%=fileName%></b></td>
								<td><b><%=commentId%></b></td>
								<td style="word-wrap:break-word;"><%=searchString%></td>
							</tr>
							<%
								}
								} else {
							%>
							<tr style="color: maroon; size: 25px">
								<td colspan="3" align="center"><b>The text <%=text%>
										was not found in the corpus :(<br> But you are welcome to
										try something else :)
								</b></td>
							</tr>
							<%
								}
							%>
							<tr style="color: maroon; font-size: 35px">
								<td colspan = "3"  align = "center"> Total Results Found: <%=searchResultMap.size() %> </td>
							</tr>

						</table>
					</td>
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
