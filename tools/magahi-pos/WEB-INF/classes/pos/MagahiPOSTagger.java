package pos;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSSample;
import opennlp.tools.postag.POSTaggerFactory;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.postag.WordTagSampleStream;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.TrainingParameters;

/**
 * @author ritesh
 *
 */
public class MagahiPOSTagger {

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String trainFile = "/home/ritesh/Documents/magahi-nlp-tools/pos-tagger/mxpost/trainFile.mxpost";
		String modelFile = "/home/ritesh/Documents/magahi-nlp-tools/pos-tagger/mag-pos-maxent_0.model";
		MagahiPOSTagger mpt = new MagahiPOSTagger();
		POSModel model = mpt.trainTagger(trainFile);
		mpt.writeModel(model, modelFile);

	}
	
	/**
	 * 
	 * @param filePath
	 * @return
	 */
	public POSModel trainTagger(String filePath) {
		POSModel model = null;

		InputStream dataIn = null;
		try {
		  dataIn = new FileInputStream(filePath);
		  ObjectStream<String> lineStream =
				new PlainTextByLineStream(dataIn, "UTF-8");
		  ObjectStream<POSSample> sampleStream = new WordTagSampleStream(lineStream);
		  
		  TrainingParameters tp = TrainingParameters.defaultParams();
		  //System.out.println ("Settings" + tp.getSettings());
		  tp.put("Iterations", "200");
		  tp.put("Cutoff", "0");
		  System.out.println ("Training Parameters" + tp.getSettings());
		  
		  POSTaggerFactory ptf = new POSTaggerFactory();

		  model = POSTaggerME.train("mag", sampleStream, tp, ptf);
		}
		catch (IOException e) {
		  // Failed to read or parse training data, training failed
		  e.printStackTrace();
		}
		finally {
		  if (dataIn != null) {
		    try {
		      dataIn.close();
		    }
		    catch (IOException e) {
		      // Not an issue, training already finished.
		      // The exception should be logged and investigated
		      // if part of a production system.
		    	e.printStackTrace();
		    }
		  }
		}
		return model;
	}

	/**
	 * 
	 * @param model
	 * @param writePath
	 */
	public void writeModel(POSModel model, String writePath){
		OutputStream modelOut = null;
		try {
			modelOut = new BufferedOutputStream(new FileOutputStream(writePath));
			model.serialize(modelOut);
		}
		catch (IOException e) {
			// Failed to save model
			e.printStackTrace();
		}
		finally {
			if (modelOut != null) {
				try {
					modelOut.close();
				}
				catch (IOException e) {
					// Failed to correctly save model.
					// Written model might be invalid.
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 
	 * @param readPath
	 * @return
	 */
	public POSModel loadModel (String readPath) {
		InputStream modelIn = null;
		POSModel model = null;

		try {
		  modelIn = new FileInputStream(readPath);
		  model = new POSModel(modelIn);
		}
		catch (IOException e) {
		  // Model loading failed, handle the error
		  e.printStackTrace();
		}
		finally {
		  if (modelIn != null) {
		    try {
		      modelIn.close();
		    }
		    catch (IOException e) {
		    }
		  }
		}
		return model;
	}
	
	/**
	 * 
	 * @param tagger
	 * @param sentence
	 * @param merged
	 * @return
	 */
	//Returns an array of word with tags separated by "_"
	public String [] tagSentence (POSTaggerME tagger, String[] sentence, boolean merged) {
		String [] taggedSentence = null;
		String [] tags = tagger.tag(sentence);
		if (merged) {
			taggedSentence = mergeTagSentenceArrays (sentence, tags);
		}
		else {
			taggedSentence = Arrays.copyOf(tags, tags.length);
		}
		return taggedSentence;
	}
	
	/**
	 * 
	 * @param taggedSentence
	 * @return
	 */
	//Returns a separate array of tags for a sentence which is already tagged and merged
	public String [] getTagsArray (String [] taggedSentence) {
		int length = taggedSentence.length;
		String[] tags = new String[length + 1];
		for (int i = 0; i < length; i++) {
			String wordTag = taggedSentence[i];
			String tag = wordTag.substring(wordTag.indexOf("_") + 1);
			tags[i] = tag;
		}
		return tags;
	}
	
	public String [] stripTags(String [] taggedSentence) {
		int length = taggedSentence.length;
		String[] words = new String[length];
		for (int i = 0; i < length; i++) {
			String wordTag = taggedSentence[i];
			String word = wordTag.substring(0, wordTag.indexOf("_"));
			words[i] = word;
		}
		return words;
	}

	/**
	 * 
	 * @param sentence
	 * @param tags
	 * @return
	 */
	private String[] mergeTagSentenceArrays(String[] sentence, String[] tags) {
		// TODO Auto-generated method stub
		int length = sentence.length;
		String [] mergedArray = new String[length];
		for (int i = 0; i < length; i++) {
			String word = sentence[i];
			String tag = tags[i];
			String merge = word + "_" + tag;
			mergedArray[i] = merge;
		}
		return mergedArray;
	}
}
