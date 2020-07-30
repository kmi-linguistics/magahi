import os
from sklearn.tree import DecisionTreeClassifier
from sklearn.feature_extraction import DictVectorizer
from sklearn.pipeline import Pipeline
from pathlib import Path
from sklearn import metrics

from sklearn.neural_network import MLPClassifier
from sklearn.neighbors import KNeighborsClassifier
from sklearn.svm import SVC
from sklearn.gaussian_process import GaussianProcessClassifier
from sklearn.gaussian_process.kernels import RBF
from sklearn.ensemble import RandomForestClassifier, AdaBoostClassifier
from sklearn.naive_bayes import GaussianNB
from sklearn.discriminant_analysis import QuadraticDiscriminantAnalysis

import traceback
import pickle

def features(sentence, index, tags):
    """ sentence: [w1, w2, ...], index: the index of the word """
    return {
        'word': sentence[index],
        'prefix-1': sentence[index][0],
        'prefix-2': sentence[index][:2],
        'prefix-3': sentence[index][:3],
        'suffix-1': sentence[index][-1],
        'suffix-2': sentence[index][-2:],
        'suffix-3': sentence[index][-3:],
        'prev_word': '' if index == 0 else sentence[index - 1],
        'next_word': '' if index == len(sentence) - 1 else sentence[index + 1],
        'is_numeric': sentence[index].isdigit(),
        'sec_prev_word': '' if index <= 1 else sentence[index - 2],
        'sec_next_word': '' if index >= len(sentence) - 2 else sentence[index+2],
        'prev_tag': '' if index == 0 else tags[index - 1],
        'sec_prev_tag': '' if index <= 1 else tags[index - 2],
        'third_prev_tag': '' if index <= 2 else tags[index - 3]
    }


f_path = input	('Enter file path of the input file (in CONLL Format - one token per line): ')
try:
	print ('Reading model file')
	with open('mag_ud_pos_model.pkl','rb') as f:
		clf = pickle.load(f)
	
	with open (f_path[:f_path.rfind('.')]+'_labelled', 'a') as f:
		with open (f_path, 'r') as f_r:
			all_data = f_r.read()
			for data in all_data.splitlines():
				words = data.split()
				tags = []
				tag_sent = ''
				for index in range(len(words)):
					if index > 0:
						tags.pop()
						tags.append (prev_tag)
						tags.append('')
					else:
						tags.append('')
					X = features(words, index, tags)
					prev_tag = clf.predict(X)
					tag_sent = tag_sent + words[index] + ' ' + str(prev_tag[0]) + ' '
					f.write (tag_sent)		
				f.write ('\n')
except Exception as e:
	traceback.print_exc()
