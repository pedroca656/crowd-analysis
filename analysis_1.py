from sys import argv
import re

script, filename = argv

fileContent = open(filename,"r")

content = fileContent.readline()

pattern = "(\((\d{0,3})\,(\d{0,3})\,(\d{0,3})\))"

matchObject = re.finditer(pattern,content)

if matchObject:
    while True:
        try:
            currentValue = matchObject.next()
            print "Group = ".__add__(currentValue.group(0))
            print "X = ".__add__(currentValue.group(2))
            print "Z = ".__add__(currentValue.group(3))
            print "frame = ".__add__(currentValue.group(4))
        except StopIteration:
            break
