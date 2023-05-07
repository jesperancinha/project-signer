import os
from os.path import exists

root_dir = '../../'


def replace_makefile_by_prefix_files(prefix):
    print("* Starting fix with '" + prefix + "':")
    with open(prefix + ".txt", "r") as file:
        data = file.read()
    with open(prefix + "Replacement.txt", "r") as file2:
        new_data = file2.read()
    for file in os.listdir(root_dir):
        test_make_file = file + '/Makefile'
        if exists(os.path.join(root_dir, test_make_file)):
            makefile = os.path.join(root_dir, test_make_file)
            with open(makefile) as f:
                original_file_content = f.read()
                if data in original_file_content:
                    print(makefile)
                    with open(makefile, 'w') as f:
                        f.write(original_file_content.replace(data, new_data))


replace_makefile_by_prefix_files("upgradeGradlePattern")
replace_makefile_by_prefix_files("gradleVersion")
replace_makefile_by_prefix_files("shellAddition")
replace_makefile_by_prefix_files("shellFix")
replace_makefile_by_prefix_files("shellGitHubConflictFix")
