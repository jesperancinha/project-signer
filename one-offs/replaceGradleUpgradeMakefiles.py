import os
from os.path import exists

rootdir = '../../'


def replaceMakefileByPrefixFiles(prefix):
    print("* Starting fix with '" + prefix + "':")
    with open(prefix + ".txt", "r") as file:
        data = file.read()
    with open(prefix + "Replacement.txt", "r") as file2:
        newData = file2.read()
    for file in os.listdir(rootdir):
        testMakeFile = file + '/Makefile'
        if (exists(os.path.join(rootdir, testMakeFile))):
            makefile = os.path.join(rootdir, testMakeFile)
            with open(makefile) as f:
                originalFileContent = f.read()
                if data in originalFileContent:
                    print(makefile)
                    with open(makefile, 'w') as f:
                        f.write(originalFileContent.replace(data, newData))


replaceMakefileByPrefixFiles("upgradeGradlePattern")
replaceMakefileByPrefixFiles("gradleVersion")
replaceMakefileByPrefixFiles("shellAddition")
replaceMakefileByPrefixFiles("shellFix")
