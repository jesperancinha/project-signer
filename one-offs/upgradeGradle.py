import os
from os.path import exists
import requests
import re

rootdir = '../../'

api_url = "https://services.gradle.org/versions/current"
response = requests.get(api_url)
latest_gradle = response.json()
version = latest_gradle['version']
print(latest_gradle)
print(version)


def upgradeGradle():
    for file in os.listdir(rootdir):
        testMakeFile = file + '/Makefile'
        if (exists(os.path.join(rootdir, testMakeFile))):
            makefile = os.path.join(rootdir, testMakeFile)
            with open(makefile) as f:
                originalFileContent = f.read()
                if 'GRADLE_VERSION' in originalFileContent:
                    print(makefile)
                    with open(makefile, 'w') as f:
                        newFile = re.sub('[0-9]+\.[0-9]+\.?[0-9]*',
                                         version, originalFileContent, 1)
                        f.write(newFile)


upgradeGradle()
