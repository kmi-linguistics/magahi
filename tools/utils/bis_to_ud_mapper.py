import csv
import os

cur_dir = os.getcwd()

map_dict = {'N_NN': 'NOUN', 'N_NNP': 'PROPN', 'N_NST': 'NOUN', 'PR_PRP': 'PRON', 'PR_PRF': 'PRON', 'PR_PRL': 'PRON', 'PR_PRC': 'PRON', 'PR_PRQ': 'PRON', 'PR_PRI': 'PRON', 'DM_DMD': 'DET', 'DM_DMR': 'DET', 'DM_DMQ': 'DET', 'DM_DMI': 'DET', 'V_VM': 'VERB', 'V_VM_VNP':'VERB','V_VM_VF':'VERB', 'V_VM_VNF':'VERB', 'V_VAUX': 'AUX', 'JJ': 'ADJ', 'RB': 'ADV', 'PSP': 'ADP', 'CC_CCD': 'CCONJ', 'CC_CCS': 'SCONJ', 'RP_RPD': 'PART', 'RP_INJ': 'INTJ', 'RP_INTF': 'PART', 'RP_NEG': 'PART', 'RP_CL':'NUM', 'QT_QTF': 'DET', 'QT_QTC': 'NUM', 'QT_QTO': 'NUM', 'RD_RDF': 'X', 'RD_SYM': 'SYM', 'RD_PUNC': 'PUNCT', 'RD_UNK': 'X', 'RD_ECH': 'Suffix'}

f_path = input	('Enter file path of the input file (in CONLL Format - one token per line): ')
with open (f_path[:f_path.rfind('.')]+'_ud', 'w') as f_write:
	with open (f_path, 'r') as f:
		for line in f:
			if line.strip() is '':
				f_write.write('\n')
			else:
				#print (line)
				row = line.split()
				if len(row) >= 2:
					word = row[0]
					tag = row[1]
					new_tag = map_dict[tag]
					f_write.write (word + ' ' + new_tag + '\n')
