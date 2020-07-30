package corpusUtils;
import co3h.*;

import java.util.*;
import javax.xml.bind.*;
import java.io.*;

public class XmlReader {

	String fileName;
	public XmlReader(String readFile) {
		fileName=readFile;
	}
	
	//Reads data only from original script
	public Map<Integer, String> readChat(String chatType) {
		Map<Integer, String> allChats = new LinkedHashMap<Integer, String>();
		List<SyncMedia> chats = new ArrayList<SyncMedia>();
		String data = "";
		int id = 0;
		try {
			JAXBContext chatContext = JAXBContext.newInstance("co3h");
			Unmarshaller unmarshaller = chatContext.createUnmarshaller();
			//CmcCorpus corpusData = (CmcCorpus)unmarshaller.unmarshal (new File(fileName));
			JAXBElement<?> element = (JAXBElement<?>) unmarshaller.unmarshal (new File(fileName));
			CmcCorpus corpusData = (CmcCorpus)element.getValue ();

	        //   studentType student= (studentType) element.getValue ();
			MediaType sync = corpusData.getSynchronous();
			if (chatType.equals("public")) {
				chats = sync.getPublicChat();
			}
			else {
				chats = sync.getPrivateChat();
				System.out.println ("Private Chats retrieved: " + chats.size());
			}
			for (SyncMedia chatInstance: chats) {
				List<Turn> turns = chatInstance.getChatTurn();
				System.out.println ("All turns retrieved: " + turns.size());
				for (Turn turn: turns) {
					data = turn.getOriginalScript().getValue();
					//id = Integer.parseInt(turn.getId());\
					id++;
					allChats.put(id, data);
				}
			}
			System.out.println ("xmlReader.readChat \t Reading of chat file: " + fileName + " completed");
		}
		catch (Exception e) {
			System.out.println ("xmlReader.readChat error: " + e);
		}
		System.out.println ("All chats size returned from java: " + allChats.size());
		return allChats;
	}
	
	public Map<Integer, String> readAsync(int corpusType) {
		Map<Integer, String> allData = new LinkedHashMap<Integer, String>();
		List<AsyncMedia> data = new ArrayList<AsyncMedia> ();
		String completeData = "", completeComment="";
		//int id = 0;
		int idComment = 0;
		//System.out.println ("In readAsync");
		try {
			JAXBContext emailContext = JAXBContext.newInstance("co3h");
			Unmarshaller unmarshaller = emailContext.createUnmarshaller();
			//CmcCorpus corpusData = (CmcCorpus)unmarshaller.unmarshal(new File(fileName));
			JAXBElement<?> element = (JAXBElement<?>) unmarshaller.unmarshal (new File(fileName));
			CmcCorpus corpusData = (CmcCorpus)element.getValue ();
			MediaType async = corpusData.getAsynchronous();
			
			switch (corpusType) {
			case 1:
				//System.out.println ("Email in class");
				data = async.getEmail();
				break;
			case 2:
				//System.out.println ("Blog in class");
				data = async.getBlog();
				break;
			case 3:
				//System.out.println ("YouTube in class");
				data = async.getYoutubeVideo();
				break;
			}
			
			boolean addFlag = false;
			for (AsyncMedia datum: data) {
				//id = Integer.parseInt(datum.getId());
				//System.out.println ("Id: " + id);
				ScriptName sn = datum.getMainContent().getOriginalScript();
				if (!(sn.getName().contains("Devanagari")) && datum.getMainContent().getOtherScript().size() > 0) {
					for (ScriptName scripts: datum.getMainContent().getOtherScript()) {
						if (scripts.getName().contains("Devanagari")) {
							addFlag = true;
							completeData = scripts.getValue() ;
						}
					}
				}
				if (!addFlag)
					completeData = sn.getValue();
				
				allData.put(0, completeData);
				List<Comment> comments = datum.getAsyncComment();
				for (Comment comment: comments) {
					boolean commentAddFlag = false;
					idComment = Integer.parseInt(comment.getId());
					//System.out.println ("Comment Id: " + idComment);
					ScriptName snComment = comment.getCommentContent().getOriginalScript();
					if (!(snComment.getName().contains("Devanagari")) && comment.getCommentContent().getOtherScript().size() > 0) {
						for (ScriptName scripts: comment.getCommentContent().getOtherScript()) {
							if (scripts.getName().contains("Devanagari")) {
								commentAddFlag = true;
								completeComment = scripts.getValue() ;
							}
						}
					}
										
					if (!commentAddFlag)
						completeComment = snComment.getValue();
					//System.out.println ("Comment Data: " + completeComment);

					allData.put(idComment, completeComment);
				}
			}
			//System.out.println ("xmlReader.readAsync \t Reading of async file: " + fileName + " completed");
		}
		catch (Exception e) {
			System.out.println ("xmlReader.readAsync error: " + e);
		}
		return allData;
	}
}
