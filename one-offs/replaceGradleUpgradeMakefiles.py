import os
from os.path import exists


rootdir = '../../'
with open("upgradeGradlePattern.txt", "r") as file:
    data = file.read()
for file in os.listdir(rootdir):
    testMakeFile = file + '/Makefile'
    if(exists(os.path.join(rootdir, testMakeFile))):
        makefile = os.path.join(rootdir, testMakeFile)
        with open(makefile) as f:
            if data in f.read():
                 print(makefile)
