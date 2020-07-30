import csv
import os

cur_dir = os.getcwd()

with open ('train_file_bis.conll', 'w') as f_write:
	for cur_file in os.listdir():
		if cur_file.endswith('.txt'):
			first = True
			with open (cur_file) as f:
				for line in f:
					if 'ID\tValue' not in line:
						row = line.split('\t')
						data = row[1].split()
						for word_tag in data:
							sep_index = word_tag.find('\\')
							word = word_tag[:sep_index]
							tag = word_tag[sep_index+1:]
							f_write.write (word + ' ' + tag + '\n')
						f_write.write('\n')
