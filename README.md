# Project Signer

[![Generic badge](https://img.shields.io/static/v1.svg?label=GitHub&message=Project%20Signer&color=informational)](https://github.com/jesperancinha/project-signer)
[![GitHub release](https://img.shields.io/github/release-pre/jesperancinha/project-signer.svg)](https://github.com/jesperancinha/project-signer/releases)

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/d423415df34f42bf821ae13a078094c9)](https://www.codacy.com/app/jofisaes/project-signer?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=jesperancinha/project-signer&amp;utm_campaign=Badge_Grade)
[![CircleCI](https://circleci.com/gh/jesperancinha/project-signer.svg?style=svg)](https://circleci.com/gh/jesperancinha/project-signer)
[![Build Status](https://travis-ci.org/jesperancinha/project-signer.svg?branch=master)](https://travis-ci.org/jesperancinha/project-signer)
[![codebeat badge](https://codebeat.co/badges/bfb0987b-e483-4954-9c3b-24ac488006bd)](https://codebeat.co/projects/github-com-jesperancinha-project-signer-master)
[![BCH compliance](https://bettercodehub.com/edge/badge/jesperancinha/project-signer?branch=master)](https://bettercodehub.com/)
[![Build status](https://ci.appveyor.com/api/projects/status/eyx7uhjenc7m6s9j/branch/master?svg=true)](https://ci.appveyor.com/project/jesperancinha/project-signer/branch/master)

[![GitHub language count](https://img.shields.io/github/languages/count/jesperancinha/project-signer.svg)]()
[![GitHub top language](https://img.shields.io/github/languages/top/jesperancinha/project-signer.svg)]()
[![GitHub top language](https://img.shields.io/github/languages/code-size/jesperancinha/project-signer.svg)]()

## Description

This project serves the purpose of automatically signing all projects from the root

The idea is to use something like:

-   Short switches
```text
java -jar project-signer.jar -t <template> <tag1>...<tagn> -d <root_path> -ne <no empty>
```
-   Long switches
```text
java -jar project-signer.jar --template-location <template> <tag1>...<tagn> --root-directory <root_path> --no-empty <no empty>
```

To summarize, this program will scan all your readme files and standardize name. You may want a generic signature in all your projects and this little runner will do just that!

This command line runner will complete several boiler plate tasks:

-   Creates all Readme.md files missing - Wherever there is a pom or a package.json file, there should be a Readme.md to explain the purpose of your project. Title will be calculated according to your architecture. The title string is to be extracted from the build files. If multiple build files are present, it will find the name of the project by prioritizing the name of the project in detriment of the artifact name. In order of priority it will look for a title according to the following priority list: Maven, Gradle, SBT and finally NPM.
-   All indicated paragraph which start on a certain tag will be removed - We consider a whole paragraph according to the '#' notation of the markdown.
-   Once all Readme.md files have been created or updated, we will finally add the template signature AS IS to the footer of all found cases.

All tags are case sensitive, which means you do need to add extra tags in case you have issues with word casing.
Once you finally run the above command, please make sure to double check the given signature before committing and pushing to your repos.

### Usage in an IDE

The most common form of using this plugin would be something like this as command line parameters:

```text
-t "../project-signer-templates/Readme.md" License "About me" -d ../../
```

Remember to user profiles dev/prod. No difference at the moment between the different profiles.

## Tools

```text
gpg --keyserver hkp://keyserver.ubuntu.com --send-keys <your GPG key>
gpg --list-keys
export GPG_TTY=$(tty)
mvn clean deploy -Prelease
mvn nexus-staging:release  -Prelease
```
```xml
<settings>
    <servers>
        <server>
            <id>ossrh</id>
            <username>your-jira-id</username>
            <password>your-jira-pwd</password>
        </server>
    </servers>
</settings>
```

## References

-   [App Icon Maker](https://appiconmaker.co/)
-   [Dillinger](https://dillinger.io/)
-   [JQ](https://stedolan.github.io/jq/download/)

## About me

[![Generic badge](https://img.shields.io/static/v1.svg?label=Homepage&message=joaofilipesabinoesperancinha.nl&color=informational)](http://joaofilipesabinoesperancinha.nl)
[![Generic badge](https://img.shields.io/static/v1.svg?label=Homepage&message=Time%20Disruption%20Studios&color=informational)](http://tds.joaofilipesabinoesperancinha.nl/)
[![Generic badge](https://img.shields.io/static/v1.svg?label=Homepage&message=Image%20Train%20Filters&color=informational)](http://itf.joaofilipesabinoesperancinha.nl/)
[![Generic badge](https://img.shields.io/static/v1.svg?label=Homepage&message=MancalaJE&color=informational)](http://mancalaje.joaofilipesabinoesperancinha.nl/)
[![Generic badge](https://img.shields.io/static/v1.svg?label=Homepage&message=Project%20Status&color=informational)](https://github.com/jesperancinha/project-signer/blob/master/project-signer-templates/Status.md)
[![Generic badge](https://img.shields.io/static/v1.svg?label=Homepage&message=Badges&color=informational)](https://github.com/jesperancinha/project-signer/blob/master/project-signer-templates/Badges.md)
[![Generic badge](https://img.shields.io/static/v1.svg?label=Google%20Apps&message=Joao+Filipe+Sabino+Esperancinha&color=informational)](https://play.google.com/store/apps/developer?id=Joao+Filipe+Sabino+Esperancinha)
[![GitHub followers](https://img.shields.io/github/followers/jesperancinha.svg?label=jesperancinha&style=social)](https://github.com/jesperancinha)
[![Twitter Follow](https://img.shields.io/twitter/follow/joaofse?label=João%20Esperancinha&style=social)](https://twitter.com/joaofse)
[![Generic badge](https://img.shields.io/static/v1.svg?label=DEV&message=Profile&color=informational)](https://dev.to/jofisaes)
[![Generic badge](https://img.shields.io/static/v1.svg?label=Medium&message=@jofisaes&color=informational)](https://medium.com/@jofisaes)
[![Generic badge](https://img.shields.io/static/v1.svg?label=Free%20Code%20Camp&message=jofisaes&color=informational)](https://www.freecodecamp.org/jofisaes)
[![Generic badge](https://img.shields.io/static/v1.svg?label=Hackerrank&message=jofisaes&color=informational)](https://www.hackerrank.com/jofisaes)
[![Generic badge](https://img.shields.io/static/v1.svg?label=Acclaim%20Badges&message=joao-esperancinha&color=informational)](https://www.youracclaim.com/users/joao-esperancinha/badges)

---

[![GitHub Logo](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/JEsperancinhaOrg-32.png)](https://github.com/JEsperancinhaOrg)
[![Generic badge](https://img.shields.io/static/v1.svg?label=GitHub&message=ITF%20Chartizate%20Android&color=informational)](https://github.com/JEsperancinhaOrg/itf-chartizate-android)
[![Generic badge](https://img.shields.io/static/v1.svg?label=GitHub&message=ITF%20Chartizate%20Java&color=informational)](https://github.com/JEsperancinhaOrg/itf-chartizate-modules/tree/master/itf-chartizate-java)
[![Generic badge](https://img.shields.io/static/v1.svg?label=GitHub&message=ITF%20Chartizate%20API&color=informational)](https://github.com/JEsperancinhaOrg/itf-chartizate/tree/master/itf-chartizate-api)
[![Generic badge](https://img.shields.io/static/v1.svg?label=GitHub&message=Markdowner%20Core&color=informational)](https://github.com/jesperancinha/markdowner/tree/master/markdowner-core)
[![Generic badge](https://img.shields.io/static/v1.svg?label=GitHub&message=Markdowner%20Filter&color=informational)](https://github.com/jesperancinha/markdowner/tree/master/markdowner-filter)

## License

```text
Copyright 2016-2019 João Esperancinha (jesperancinha)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
