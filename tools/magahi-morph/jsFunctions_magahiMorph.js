function handleEnter(inField, e) {
	var charCode = 0;
//	Get key code (support for all browsers)
	if(e && e.which){
		charCode = e.which;
	}else if(window.event){
		e = window.event;
		charCode = e.keyCode;
	}

	if(charCode == 13) {
		//Call your submit function
		analyseData();
	}
}

function analyseData(){
	//alert ("In function");
	var data = "";
	var inputData = document.morphanal.analyseText.value;
	//alert ("Input data: " + inputData);

	if (inputData.trim() == "") {
		alert ("Please enter some data to analyse");
	}
	else {
		try {
			xmlHttp = new XMLHttpRequest ();
		}
		catch (e) {
			try {
				xmlHttp = new ActiveXObject ("Microsoft.XMLHTTP");
			}
			catch (el) {
				try {
					xmlHttp = new ActiveXObject ("Msxml2.XMLHTTP");
				}
				catch (el1) {
					alert ("Your browser does not support AJAX! Please use a compatible browser!!");
				}
			}
		}

		xmlHttp.onreadystatechange = function () {
			//alert (xmlHttp.readyState);
			//alert (xmlHttp.status);
			if (xmlHttp.readyState == 4 && xmlHttp.status == 200) {
				//alert ("Page returned ok");
				var sp = document.getElementById ("anal");
				var data = "" +  xmlHttp.responseText;			
				sp.innerHTML = data;
				//alert ("Returned data: " + xmlHttp.responseText);
			}		
		};


		xmlHttp.open("POST", "magahiMorphAnalysis.jsp", true);
		xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;");
		xmlHttp.send("data=" + inputData);
	}
}

function getData(form) {
	var sentence = "";
	var data = form.getElementById("complete").value;
	//alert(data);
	var tag = getTagName(form.editEnv.textTag);
	if (tag) {
		//alert(tag.value);
		sentence = data + "##" + tag.value;
	}
	return sentence;
}