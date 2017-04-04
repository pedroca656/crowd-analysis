from sys import argv
import re

script, filename = video_1.txt

fileContent = open(filename,"r")

content = fileContent.readline()

pattern = "(\((\d{0,3})\,(\d{0,3})\,(\d{0,3})\))"

matchObject = re.finditer(pattern,content)

if matchObject:
    while True:
        try:
            currentValue = matchObject.next()
            print(currentValue)
            print("Group = " + currentValue.group(0))
            print("X = " + currentValue.group(2))
            print("Z = " + currentValue.group(3))
            print("frame = " + currentValue.group(4))
        except StopIteration:
            raw_input("Press any key to close.")
            break
    
