<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<%@ page isThreadSafe="false" %>
    <%@ page import="morph.*" %>
    <%@ page import="pos.*" %> 
    <%@ page import="opennlp.tools.postag.POSModel" %>
    <%@ page import="opennlp.tools.postag.POSTaggerME" %>
    <%@ page import="java.io.*" %>
    <%@ page import="java.util.*" %>
    <%!String query = "", text = "";%>
    <%!LinkedHashMap<String, String> morphResults = new LinkedHashMap<String, String>();%>

<table style="align: center; text-align: center; width: 95%;" align="center" border="1"
	cellpadding="2" cellspacing="2">
	<tr style="color: maroon; font-size: 35px">
		<td colspan="4">Part-of-Speech Analysis Results
		</td>
	</tr>
<%
	request.setCharacterEncoding("UTF-8");
	System.out.println("Entering");
	try {
		query = request.getParameter("data").trim();
		System.out.println ("Data: " + query);
	} catch (Exception e) {
		System.out.println("Into corpus type catch: " + e);
		query = "";
	}
	//System.out.println("Data read");
	String resourcesPath = request.getRealPath("/")
			+ "WEB-INF/datafiles/magahi-morph/";

	MagahiMorphAnalyser mma = new MagahiMorphAnalyser(resourcesPath); 
	String[] sentences = query.split("\n");
	System.out.println("Sentence length: " + sentences.length);
	int totalSent = sentences.length;

	int i = 0;
	for (String sentence : sentences) {
		i++;
		System.out.println("Processing Sentence \t" + i
				+ "\t out of a total of \t" + totalSent);
		String[] words;
		String[] tags;
		MagahiPOSTagger mpt = new MagahiPOSTagger();
		if (sentence.contains("_") || sentence.contains("\\")) {
			System.out.println("Already tagged...going for analysis");
			sentence = sentence.replace("\\", "_");
			words = sentence.split(" ");
			//System.out.println 
			tags = mpt.getTagsArray(words);
			words = mpt.stripTags(words);
			System.out.println ("Last word: " + words[words.length-1] + "\t" + words.length);
		} else {
			System.out.println("Not tagged...going for tagging");
			words = sentence.split(" ");
			String modelPath = resourcesPath + "mag-pos-maxent_0.model";
			POSModel pmdl = mpt.loadModel(modelPath);
			POSTaggerME tagger = new POSTaggerME(pmdl);
			tags = mpt.tagSentence(tagger, words, false);
			System.out.println("Tagging done..." + tags[0]
					+ ".......going for analysis");
		}
		morphResults = mma.analyseSentence(words, tags);
		System.out.println("Morph analysis done: " + morphResults);
%>
	<tr style="color: yellow; font-size: 20px; align: center; background: green">
		<td colspan = "4" style = "font-size: 25px"><b> Sentence <%=i%> </b></td>
	</tr>
	<tr style="background-color: rgb(102, 0, 0); color: white">
		<td><b>Word</b></td>
		<td><b>Part of Speech</b></td>
	</tr>
	<%
		ArrayList<String> wordTags = new ArrayList<String>(
					morphResults.keySet());
			String word = "", pos = "", analysis = "", analWord = "", analMorph = "";
			for (String wordTag : wordTags) {
				word = wordTag.substring(0, wordTag.indexOf("_"));
				pos = wordTag.substring(wordTag.indexOf("_") + 1);
	%>
	<tr style="color: maroon">
		<td><b><%=word%></b></td>
		<td><b><%=pos%></b></td>
	</tr>
	<%
		}
	%>

<%
	}
%>
<tr>
		<td colspan = "2" style="color: maroon; font-size: 20px" >Thanks for using the Magahi Part-of-Speech Analyser! Do give us your feedback and come back again!!</td>
	</tr> 

</table>