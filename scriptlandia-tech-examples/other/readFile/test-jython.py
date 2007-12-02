import sys

MAXLENGTH = 70

def process(filename) :
  f = open(filename)
  i = 0
  for line in f:
    i = i + 1
    if len(line) > MAXLENGTH:
      print filename, "line=", i, "chars=", len(line)

for f in sys.argv:
  process(f)