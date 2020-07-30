import csv
import os

cur_dir = os.getcwd()
s_id = 4577
#tot_sets = 3
tot_sets = 1
cur_s_id = 1
f_s = 1
f_path = input	('Enter file path of the input file (in CONLL Format - one token per line): ')
f_pre = f_path[:f_path.rfind('.')]
f_write =  open (f_pre + '_' + str(f_s) + '.conllu', 'w')

print ('Writing to {f_write}')

with open (f_path, 'r') as f:
		lines = f.read().split('\n\n')
		set_size = len(lines) // tot_sets #use it to divide into a fixed number of sets
		#set_size = 2000 #use it to make sets of equal size
		for line in lines:
			if line.strip() != '':
				#print (line)
				#sent = line.replace('\n', ' ')
				l1 = '# sent_id = s'+str(s_id) + '\n'
				l2 = '# text ='
				tokens = line.split('\n')
				l_id = 1
				l_str = ''
				for token in tokens:
					#print (token)
					word_pos = token.split()
					if len(word_pos) == 2:
						word = word_pos[0]
						pos = word_pos[1]
						l2 = l2 + ' ' + word
						l_str = l_str + str(l_id) + '\t' + word + '\t' + '_\t'*2 + pos + '\t_'*5 + '\n'
						l_id+=1
					
				f_write.write (l1 + l2 + '\n' + l_str+'\n')
				s_id+=1
				cur_s_id+=1
				if tot_sets > 1 and cur_s_id % set_size == 0:
					f_s += 1
					f_write =  open (f_pre + '_' + str(f_s) + '.conllu', 'w')
					
print ('Total sentences written:', cur_s_id)
