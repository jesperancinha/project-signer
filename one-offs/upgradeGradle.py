import os
from os.path import exists
import requests
import re

root_dir = '../../'

api_url = "https://services.gradle.org/versions/current"
response = requests.get(api_url)
latest_gradle = response.json()
version = latest_gradle['version']
print(latest_gradle)
print(version)


def upgrade_gradle():
    for file in os.listdir(root_dir):
        test_make_file = file + '/Makefile'
        if exists(os.path.join(root_dir, test_make_file)):
            makefile = os.path.join(root_dir, test_make_file)
            with open(makefile) as original_file:
                original_file_content = original_file.read()
                if 'GRADLE_VERSION' in original_file_content:
                    print(makefile)
                    with open(makefile, 'w') as new_file:
                        new_file_content = re.sub(r'[0-9]+\.[0-9]+\.?[0-9]*',
                                                  version, original_file_content, 1)
                        new_file.write(new_file_content)


upgrade_gradle()
