/**
 * 
 */
function selectExplorerPage(type){
	switch(type.id){
	case 'blog':
		document.login.flag.value = "blog";
		document.login.action = "coThreeHExplorer.jsp";
		document.login.submit();
		break;
	case 'pub':
		document.login.flag.value="public_chat";
		document.login.action = "coThreeHExplorer.jsp";
		document.login.submit();
		break;
	case 'priv':
		document.login.flag.value="private_chat";
		document.login.action = "coThreeHExplorer.jsp";
		document.login.submit();
		break;
	case 'portal':
		document.login.flag.value="portal";
		document.login.action = "coThreeHExplorer.jsp";
		document.login.submit();
		break;
	case 'utube':
		document.login.flag.value="youtube";
		document.login.action = "coThreeHExplorer.jsp";
		document.login.submit();
	case 'mail':
		document.login.flag.value="email";
		document.login.action = "coThreeHExplorer.jsp";
		document.login.submit();
		
	}
}

function inputControl(instr){
	document.editEnv.selectBox.value = "false";
	switch(instr.name){
	case 'next':
		document.editEnv.actionFlag.value="next";
		document.editEnv.action="coThreeHExplorer.jsp";
		document.editEnv.submit();
		break;
	case 'previous':
		document.editEnv.actionFlag.value="previous";
		document.editEnv.action="coThreeHExplorer.jsp";
		document.editEnv.submit();
		break;
	}
}

function selectOption() {
	document.editEnv.selectBox.value = "true";
	document.editEnv.action="coThreeHExplorer.jsp";
	document.editEnv.submit();
}