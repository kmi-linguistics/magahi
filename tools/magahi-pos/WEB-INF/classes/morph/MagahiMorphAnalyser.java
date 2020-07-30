package morph;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;

/**
 * 
 * @author ritesh
 *
 */
public class MagahiMorphAnalyser {
	
	/**
	 * Stores the path to the directory where rules, dictionary and tag mappings are stored
	 */
	String dataDirectory = "";
	/**
	 * Mapping of words in the dictionary to its POS category
	 */
	HashMap<String, String> wordToPosDict = new HashMap<String, String>();
	
	/**
	 * Mapping of words in the dictionary to its Morphological features
	 */
	HashMap<String, String> wordToMorphDict = new HashMap<String, String>();
	
	/**
	 * Mapping of each POS category to its rules which do not have a pre-condition;
	 * multiple rules are separated by ""#" symbol 
	 */
	HashMap<String, String> posToRules = new HashMap<String, String>();
	
	/**
	 * Mapping of all the rules without pre-condition to its Morphological Analysis
	 */
	HashMap<String, String> rulesToMorph = new HashMap<String, String>();
	
	/**
	 * Mapping of each pre-condition to its complete rule [includes regex, POS and morphological analysis]; 
	 * multiple rules are separated by ""#" symbol 
	 */
	HashMap<String, String> preCondToRules = new HashMap<String, String>();
	
	/**
	 * Mapping of each POS category to its rules which have a pre-condition;
	 * multiple rules are separated by "#" symbol;
	 * changes for every sentence 
	 */
	HashMap<String, String> preCondPosToRules = new HashMap<String, String>();
	
	/**
	 * Mapping of rules which have a pre-condition to its Morphological Analysis; 
	 * changes for every sentence
	 */
	HashMap<String, String> preCondRulesToMorph = new HashMap<String, String>();
	
	/**
	 * Mapping of POS category to its default Morphological Analysis
	 */
	HashMap<String, String> posToDefaultMorph = new HashMap<String, String>();
	
	/**
	 * Mapping of short form of tags to its expansion (to be used when the results are to be
	 * displayed to the users in a meaningful way)
	 */
	HashMap<String, String> tagMap = new HashMap<String, String>();
	
	/**
	 * Mapping of POS category with condition to its default Morphological Analysis
	 */
	HashMap<String, String> preCondPosToDefaultMorph = new HashMap<String, String>();
	
	/**
	 * 
	 * @param directoryPath
	 */
	public MagahiMorphAnalyser (String directoryPath) {
		dataDirectory = directoryPath;
		readDictionary (dataDirectory + "dictionary");
		readTagMap (dataDirectory + "tagMap");
	}
	/**
	 * 
	 * @param path
	 */
	private void readTagMap(String path) {
		// TODO Auto-generated method stub
		String tagDescription = "";
		try {
			BufferedReader br = new BufferedReader (new FileReader (path));
			while ((tagDescription = br.readLine()) != null) {
				String shortTag = tagDescription.substring(tagDescription.indexOf("\t") + 1).trim();
				String longTag = tagDescription.substring(0, tagDescription.indexOf("\t")).trim();
				tagMap.put(shortTag, longTag);
			}
			//System.out.println ("Reading tag map done");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println ("Error in MagahiMorphAnalyser.readTagMap: " + e);
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @param path
	 */
	private void readRules(String path, String tag) {
		// TODO Auto-generated method stub
		String ruleEntry = "";
		posToRules.clear();
		rulesToMorph.clear();
		preCondToRules.clear();
		preCondPosToRules.clear();
		preCondRulesToMorph.clear();
		posToDefaultMorph.clear();
		preCondPosToDefaultMorph.clear();
		
		try {
			BufferedReader br = new BufferedReader (new FileReader (path));
			while ((ruleEntry = br.readLine()) != null) {
				if (!ruleEntry.startsWith("#")) {		//Comments start with "#" in rules file
					String[] entryAtts = ruleEntry.split("\t");
					String rule = entryAtts[0];
					String pos = entryAtts[1];
					String morph = entryAtts[2];

					if (pos.equals(tag)) {
						if (rule.equals("%default%")) {			//Adds default rule
							posToDefaultMorph.put(pos, morph);
						}
						else if (rule.contains("and")) {		//Adds rules with one or multiple pre-conditions
							String preConditions = rule.substring(0, rule.lastIndexOf("and"));
							String compRule = ruleEntry.substring(ruleEntry.lastIndexOf("and") + 3);

							if (preCondToRules.containsKey(preConditions)) {
								String allRules = preCondToRules.get(preConditions) + "##" + compRule;
								preCondToRules.put(preConditions, allRules);
							}
							else {
								preCondToRules.put(preConditions, compRule);
							}
						}
						else {									//Adds rules without pre-condition
							if (posToRules.containsKey(pos)) {
								String newRule = posToRules.get(pos) + "#" + rule;
								posToRules.put(pos, newRule);
							}
							else {
								posToRules.put(pos, rule);
							}
							rulesToMorph.put(rule, morph);
						}
					}
				}
			}
			//System.out.println ("Reading rules done");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println ("Error in MagahiMorphAnalyser.readRules: " + e);
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param path
	 */
	private void readDictionary(String path) {
		// TODO Auto-generated method stub
		String dictEntry = "";
		try {
			BufferedReader br = new BufferedReader (new FileReader (path));
			while ((dictEntry = br.readLine()) != null) {
				if (!dictEntry.startsWith("#")) {
					String[] entryAtts = dictEntry.split("\t");
					wordToPosDict.put(entryAtts[0], entryAtts[1]);
					wordToMorphDict.put(entryAtts[0], entryAtts[2]);
				}
			}
			//System.out.println ("Reading dictionary done");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println ("Error in MagahiMorphAnalyser.readDictionary: " + e + ": Every entry in the dictionary must contain exactly 3 tab-separated columns");
			e.printStackTrace();
		}		
	}

	/**
	 * 
	 * @param sentence
	 * @param tags
	 * @return
	 */
	public LinkedHashMap<String, String> analyseSentence(String[] sentence, String[] tags) {
		String analysis = "";
		//boolean dataStructuresEmpty = true;
		/*
		System.out.println ("Chcking pre-requisites.....");
		System.out.println ("Tag Map length: " + tagMap.size());
		System.out.println ("Word to POS dict length: " + wordToPosDict.size());
		System.out.println ("Word to Morph dict length: " + wordToMorphDict.size());
	*/
		LinkedHashMap<String, String> results = new LinkedHashMap<String, String>();
		
		ArrayList<String> tagsWithCount = transformTags(tags);
		System.out.println ("Tag transformation done: " + tagsWithCount.size() + tagsWithCount);
		
	//	System.out.println ("Sentence length: " + sentence.length);
		for (int i = 0; i < sentence.length; i++) {
			String word = sentence[i].trim();
			String tag = tagsWithCount.get(i).trim();
			if (tag.equals("RD-PUNC")) {
				results.put(word+"_"+expandAnalysis(tag), word + "\t" + "Punctuation");
			}
			else {
				//if (!tag.contains("#")) {
					analysis = searchDictionary (word, tag).trim();
					System.out.println ("Dictionary analysis: " + analysis);
					if (analysis.contains("\t")) {
						results.put(word+"_"+expandAnalysis(tag), expandAnalysis(analysis, "\t"));
					}
				//}
				else {
					//if (dataStructuresEmpty) {
					//System.out.println ("Rules empty...adding them..");
					readRules (dataDirectory + "rules", tag);
					//System.out.println ("Rules read successfully");
					/*
					System.out.println ("Deafult and non pre-condition rules added...");
					System.out.println ("POS to rules dict length: " + posToRules.size());
					System.out.println ("Rules to Morph dict length: " + rulesToMorph.size());
					System.out.println ("pre-cond to rules dict length: " + preCondToRules.size());
					System.out.println ("POS to default morph dict length: " + posToDefaultMorph.size());
					 */
					addRulesWithPreCondition (sentence, tagsWithCount, tags);
					/*
					System.out.println ("Pre-condition rules added!");
					System.out.println ("Pre Cond POS to rule dict length: " + preCondPosToRules.size());
					System.out.println ("Pre Cond rules to morph dict length: " + preCondRulesToMorph.size());
					 */
					//dataStructuresEmpty = false;
					//}
					//System.out.println ("Rules added successfully");
					analysis = searchRules (word, tag).trim();
					System.out.println ("Rules analysis: " + analysis);
					if (analysis.contains("\t")) {
						results.put(word+"_"+expandAnalysis(tag), expandAnalysis(analysis, "\t"));
					}
					else {
						String expandedTag = expandAnalysis(tag);
						if (expandedTag.contains("-")) {
							analysis = expandedTag.substring (expandedTag.indexOf("-") + 1) + " " + expandedTag.substring (0, expandedTag.indexOf("-")); 
						}
						else {
							analysis = expandedTag;
						}
						
						results.put(word+"_"+expandedTag, word + "\t" + analysis);
					}
				}
			}
		}
		return results;
	}
	/**
	 * @param searchRules
	 * @return
	 */
	private String expandAnalysis(String analysis) {
		// TODO Auto-generated method stub
		String expanded = "";
		if (!analysis.equals("")) {
			String[] categories = analysis.split("\\.");
			for (String category: categories) {
				if (tagMap.containsKey(category)) {
					expanded = expanded + tagMap.get(category) +  ", ";
				}
				else {
					expanded = expanded + category + ", ";
				}
			}
		}
		return stripLast(expanded.trim());
	}
	
	/**
	 * @param original
	 * @return
	 */
	private String stripLast(String original) {
		// TODO Auto-generated method stub		
		return original.substring(0, original.length() - 1);
	}
	/**
	 * @param searchRules
	 * @return
	 */
	private String expandAnalysis(String analysis, String separator) {
		// TODO Auto-generated method stub
		String expanded = "", prefix = "";
			prefix = analysis.substring(0, analysis.indexOf(separator)).trim(); 
			analysis = analysis.substring(analysis.indexOf(separator) + 1).trim();
			String[] categories = analysis.split("\\.");
			for (String category: categories) {
				if (tagMap.containsKey(category)) {
					expanded = expanded + tagMap.get(category) +  ", ";
				}
				else {
					expanded = expanded + category + ", ";
				}
			}
		return stripLast(prefix + separator + expanded.trim());
	}
	
	/**
	 * Currently the implementation of this method is just for the count of tags
	 * as these are the only kinds of rule that have been entered into the rule file
	 * Also it will work only for the rule with 'and' keyword i.e. only if all the pre-conditions are satisfied
	 * However this method will be made general over the period of time to cover all kinds of rules that could
	 * be included in the rule file
	 * 
	 * @param sentence
	 * @param tagsWithCount
	 * @param tags
	 */
	private void addRulesWithPreCondition(String[] sentence,
			ArrayList<String> tagsWithCount, String[] tags) {
		// TODO Auto-generated method stub
		boolean precondition = false;
		ArrayList<String> preConds = new ArrayList<String>(preCondToRules.keySet());
		//System.out.println ("pre cond to rules: " + preCondToRules);
		for (String preCond: preConds) {
			//System.out.println ("pre cond: " + preCond);
			/*
			String [] curntConds = preCondToRules.get(preCond).split("##");
			for (String curntCond: curntConds) {
				System.out.println ("curnt cond: " + curntCond);
				String[] allConds = curntCond.split("and");
				for (String cond: allConds) {
					System.out.println ("cond: " + cond);
			 */
			if (preCond.contains("@tag")) {
				String relevntTag = preCond.substring(preCond.indexOf("#")+1);
				int tagCount = Integer.parseInt(preCond.substring(0, preCond.indexOf("@")));
				precondition = isTagCountSatisfied(tagCount, relevntTag, tagsWithCount);
				if (precondition) {
					addRulesForSatisfiedPreCondition (preCond);
				}
			}
			else if (preCond.contains("@word")) {
				//TODO to be implemented
			}
			//}
		}
	}
	//}
	
	/**
	 * @param preCond
	 */
	private void addRulesForSatisfiedPreCondition(String preCond) {
		// TODO Auto-generated method stub
		String[] allRuleEntry = preCondToRules.get(preCond).split("##");
		for (String ruleEntry: allRuleEntry) {
			//System.out.println ("Rule entry: " + ruleEntry);
			String[] entryAtts = ruleEntry.split("\t");
			String rule = entryAtts[0];
			String pos = entryAtts[1];
			String morph = entryAtts[2];
			if (rule.equals("%default%")) {
				preCondPosToDefaultMorph.put(pos, morph);
			}
			else {
				if (preCondPosToRules.containsKey(pos)) {

					String newRule = preCondPosToRules.get(pos) + "#" + rule;
					preCondPosToRules.put(pos, newRule);
				}
				else {
					preCondPosToRules.put(pos, rule);
				}
				preCondRulesToMorph.put(rule, morph);
			}
		}			
	}
	
	/**
	 * @param tagCount
	 * @param relevntTag
	 * @param tagsWithCount
	 * @return
	 */
	private boolean isTagCountSatisfied(int tagCount, String relevntTag,
			ArrayList<String> tagsWithCount) {
		// TODO Auto-generated method stub
		boolean satisfied = false;
		//System.out.println ("Relevant tag: " + relevntTag + "\t" + tagsWithCount);
		switch (tagCount) {
		case 0:
			if (!tagsWithCount.contains(relevntTag)) {
				satisfied = true;
			}
			break;
		case 1:
			if (tagsWithCount.contains(relevntTag)) {
				satisfied = true;
			}
			break;
		default:
			if (tagsWithCount.contains(relevntTag + "#" + tagCount)) {
				satisfied = true;
			}
		}
		return satisfied;
	}
	
	/**
	 * @param tags
	 * @return
	 */
	private ArrayList<String> transformTags(String[] tags) {
		// TODO Auto-generated method stub
		LinkedHashMap<String, Integer> tagCount = new LinkedHashMap<String, Integer>();
		ArrayList<String> mergedTagCount = new ArrayList<String>();
		//System.out.println ("Tags length in transform: " + tags.length);
		for (String tag: tags) {
			if (tag != null) {
				if (tagCount.containsKey(tag) && tag.equals("V-VAUX")) {
					int count = tagCount.get(tag)  + 1;
					tagCount.put(tag, count);
					mergedTagCount.add(tag + "#" +count);
				}
				else {
					tagCount.put(tag, 1);
					mergedTagCount.add(tag);
				}
			}
		}		
		return mergedTagCount;
	}
	
	/**
	 * @param word
	 * @param tag
	 * @return
	 */
	private String searchRules(String word, String tag) {
		// TODO Auto-generated method stub
		String rules = "", morphDesc = "";
		//System.out.println ("Word: " + word + "\ttag: " + tag);
		if (preCondPosToRules.containsKey(tag)) {
			rules = preCondPosToRules.get(tag);
			//System.out.println ("Rules for word: " + rules);
			morphDesc = getMorphFromRules(word, rules, preCondRulesToMorph).trim();
		}
		if (preCondPosToDefaultMorph.containsKey(tag) && morphDesc.equals("")) {
			morphDesc = word + "\t" + preCondPosToDefaultMorph.get(tag).trim();
		}
		if (posToRules.containsKey(tag) && morphDesc.equals("")) {
			rules = posToRules.get(tag);
			morphDesc = getMorphFromRules(word, rules, rulesToMorph).trim();
		}
		if (posToDefaultMorph.containsKey(tag) && morphDesc.equals("")) {
			morphDesc = word + "\t" + posToDefaultMorph.get(tag).trim();
		}		
		return morphDesc;
	}

	/**
	 * @param word
	 * @param rules
	 * @return
	 */
	private String getMorphFromRules(String word, String rulePatterns, HashMap<String, String> ruleMap) {
		// TODO Auto-generated method stub
		String morphDesc = "", analysedWord = "";
		String [] rules = rulePatterns.split("#");
		for (String rule: rules) {
			Pattern rulePattern = Pattern.compile(rule);
			//System.out.println ("Rule: " + rule);
			Matcher mt = rulePattern.matcher(word.trim());
			if (mt.matches()) {
				int groups = mt.groupCount();				
				//System.out.println ("Group length: " + groups);
				for (int i = 1; i <= groups; i++) {
					String curntGroup = mt.group(i);
					//System.out.println ("Current group: " + curntGroup);
					if (i == groups || curntGroup.trim().equals("")) {
						analysedWord = analysedWord + curntGroup;
					}
					else {
						analysedWord = analysedWord + curntGroup + "-";
					}
				}
				//System.out.println ("Map: " + ruleMap);
				//System.out.println ("Rule: " + rule);
				morphDesc = ruleMap.get(rule);				
				//System.out.println("Morph: " + morphDesc);
				break;
			}
		}
		return analysedWord + "\t" + morphDesc;
	}
	/**
	 * @param word
	 * @param tag
	 * @return
	 */
	private String searchDictionary(String word, String tag) {
		// TODO Auto-generated method stub
		String morph = "";
		//System.out.println ("Word in dict: " + word + tag);
		if (wordToMorphDict.containsKey(word)) {
			//System.out.println ("Word to  morph in dict: " + wordToMorphDict.get(word));
			//System.out.println ("Word to  pos in dict: " + wordToPosDict.get(word) + "\t" + tag);
			if (wordToPosDict.get(word).equals(tag)) {
				morph = word + "\t" + wordToMorphDict.get(word);
			}
		}
		//System.out.println ("Morph analysis in dictionary: " + morph);
		return morph;
	}
	
}
