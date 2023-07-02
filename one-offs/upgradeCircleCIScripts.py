import os
from os.path import exists

root_dir = '../../'


def replace_circle_ci_by_prefix(prefix):
    print("* Starting fix with '" + prefix + "':")
    with open(prefix + ".txt", "r") as file:
        data = file.read()
    with open(prefix + "Replacement.txt", "r") as file2:
        new_data = file2.read()
    for file in os.listdir(root_dir):
        test_make_file = file + '/.circleci/config.yml'
        if exists(os.path.join(root_dir, test_make_file)):
            makefile = os.path.join(root_dir, test_make_file)
            with open(makefile) as f:
                original_file_content = f.read()
                if data in original_file_content:
                    print(makefile)
                    with open(makefile, 'w') as f:
                        f.write(original_file_content.replace(data, new_data))


replace_circle_ci_by_prefix("circleci/jdk20/circleciToJDK20")
replace_circle_ci_by_prefix("circleci/jdk20/circleciImage")
replace_circle_ci_by_prefix("circleci/jdk20/circleciTOJDK20a")
replace_circle_ci_by_prefix("circleci/jdk20/circleciTOJDK20b")
replace_circle_ci_by_prefix("circleci/jdk20/circleciTOJDK20c")
replace_circle_ci_by_prefix("circleci/jdk20/circleciToResource")
