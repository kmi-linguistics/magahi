import csv
import os

cur_dir = os.getcwd()

f_path = input	('Enter file path of the input file (in CONLL Format - one token per line): ')
with open (f_path[:f_path.rfind('.')]+'.mxpost', 'w') as f_write:
	with open (f_path, 'r') as f:
		lines = f.read().split('\n\n')
		
		for line in lines:
			if line.strip() != '':
				print (line)
				tokens = line.split('\n')
				l_str = ''
				for token in tokens:
					print (token)
					word_pos = token.split()
					if len(word_pos) == 2:
						word = word_pos[0]
						pos = word_pos[1]
						l_str = l_str + word + '_' + pos + ' '
					
				f_write.write (l_str.strip()+'\n')
