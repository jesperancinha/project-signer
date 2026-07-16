# Project signer claude updates


```text
can you make a bash script called updateAllClaude.sh? This script will look, one folder upwards, and per project, for the existence of the .claude directory. If that directory exists, it should remove all folders that do not existe in the .claude folder of the project-signer project. Then it should update the folders and files of each project with the folders and files of the project signer. project-signer, should be excluded from the iteration and no change should be made to it. No new folder should be added to the .claude folder of each target project. For example if a folder exists in .claude in project-signer, but not in the .claude folder of the target folder, that folder should not be copied. The result consists only in an update of the different claude skills and no addition should be added. only .claude subsfolders should be updated in each target project. Nothing else should be updated.
```