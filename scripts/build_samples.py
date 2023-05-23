import argparse
from pathlib import Path
import random

'''
Helper script to extract samples from the greater `words_alpha.txt` file
TODO: automate creating sample files with increasing word counts
'''
parser = argparse.ArgumentParser()
parser.add_argument('source_path')
parser.add_argument('target_path')
parser.add_argument('sample_size')
args = parser.parse_args()

source = Path(args.source_path)
target = Path(args.target_path)
size = int(args.sample_size)

if not source.exists():
    print(f'source file `{source}` does not exist')
    exit(1)

with open(source, 'r') as fsource, open(target, 'w') as ftarget:
    source_list = [word.strip() for word in fsource]
    if (size > len(source_list)):
        print(f'size {size} exceeds the length of source {source}')
        exit(1)

    target_list = random.sample(source_list, size)
    ftarget.writelines([word+'\n' for word in sorted(target_list)])