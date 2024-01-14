# Project Signer

---

[![Generic badge](https://img.shields.io/static/v1.svg?label=GitHub&message=Project%20Signer%20🖋&color=informational)](https://github.com/jesperancinha/project-signer)
[![GitHub release](https://img.shields.io/github/release/jesperancinha/project-signer.svg)](https://github.com/jesperancinha/project-signer/releases)
[![GitHub License](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](https://www.apache.org/licenses/LICENSE-2.0)

[![CircleCI](https://circleci.com/gh/jesperancinha/project-signer.svg?style=svg)](https://circleci.com/gh/jesperancinha/project-signer)
[![project-signer](https://github.com/jesperancinha/project-signer/actions/workflows/project-signer.yml/badge.svg)](https://github.com/jesperancinha/project-signer/actions/workflows/project-signer.yml)

[![Build status](https://ci.appveyor.com/api/projects/status/eyx7uhjenc7m6s9j/branch/master?svg=true)](https://ci.appveyor.com/project/jesperancinha/project-signer/branch/master)

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/d423415df34f42bf821ae13a078094c9)](https://www.codacy.com/app/jofisaes/project-signer?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=jesperancinha/project-signer&amp;utm_campaign=Badge_Grade)
[![codebeat badge](https://codebeat.co/badges/bfb0987b-e483-4954-9c3b-24ac488006bd)](https://codebeat.co/projects/github-com-jesperancinha-project-signer-master)

[![Codacy Badge](https://app.codacy.com/project/badge/Coverage/d423415df34f42bf821ae13a078094c9)](https://www.codacy.com/gh/jesperancinha/project-signer/dashboard?utm_source=github.com&utm_medium=referral&utm_content=jesperancinha/project-signer&utm_campaign=Badge_Coverage)
[![Coverage Status](https://coveralls.io/repos/github/jesperancinha/project-signer/badge.svg?branch=master)](https://coveralls.io/github/jesperancinha/project-signer?branch=master)
[![codecov](https://codecov.io/gh/jesperancinha/project-signer/branch/master/graph/badge.svg?token=ErqcT1G5Tq)](https://codecov.io/gh/jesperancinha/project-signer)

[![GitHub language count](https://img.shields.io/github/languages/count/jesperancinha/project-signer.svg)]()
[![GitHub top language](https://img.shields.io/github/languages/top/jesperancinha/project-signer.svg)]()
[![GitHub top language](https://img.shields.io/github/languages/code-size/jesperancinha/project-signer.svg)]()

## Technologies used

Please check the [TechStack.md](TechStack.md) file for details.

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

Example:

```bash
-l "../project-signer-templates/licenses/APACHE2.template,../project-signer-templates/licenses/ISC.template,../project-signer-templates/licenses/MIT.template" -t "../project-signer-templates/Readme.md" -tr "../project-signer-templates/RedirectReadme.md" License "About me" "Achievements" -d ../../
```

To summarize, this program will scan all your readme files and standardize name. You may want a generic signature in all your projects and this little runner will do just that!

This command line runner will complete several boiler plate tasks:

-   Creates all Readme.md files missing - Wherever there is a pom or a package.json file, there should be a Readme.md to explain the purpose of your project. Title will be calculated according to your architecture. The title string is to be extracted from the build files. If multiple build files are present, it will find the name of the project by prioritizing the name of the project in detriment of the artifact name. In order of priority it will look for a title according to the following priority list: Maven, Gradle, SBT and finally NPM.
-   All indicated paragraph which start on a certain tag will be removed - We consider a whole paragraph according to the '#' notation of the markdown.
-   Once all Readme.md files have been created or updated, we will finally add the template signature AS IS to the footer of all found cases.
-   If a Readme file has been generated and a template option (-l) is not null, the it will copy the template file literally to the same destination.
-   It will also make sure the all legacy License.txt files will be removed.
-   When running the [signAll.sh](signAll.sh) script, the [yyyy] variable in all licenses will be replaced with the year of the first commit in that repo

All tags are case-sensitive, which means you do need to add extra tags in case you have issues with word casing. Once you finally run the above command, please make sure to double check the given signature before committing and pushing to your repos.

Check the [LogFile](./LogFile.md) to keep track of changes.

#### Stable releases

-   [1.0.0](https://github.com/jesperancinha/project-signer/tree/1.0.0) - [7dba832cd79eac82d28712f3674b8d5f02218985](https://github.com/jesperancinha/project-signer/tree/1.0.0)
-   [1.1.0](https://github.com/jesperancinha/project-signer/tree/1.1.0) - [53969831934cf25cdad9d36e7bbbbf7f8663ec71](https://github.com/jesperancinha/project-signer/tree/1.1.0)
-   [2.0.0](https://github.com/jesperancinha/project-signer/tree/2.0.0) - [ee876a4cf63fb238cb94c771b95a28646639c9b5](https://github.com/jesperancinha/project-signer/tree/2.0.0)
-   [2.0.1](https://github.com/jesperancinha/project-signer/tree/2.0.1) - [2130b790beab36ab534a0f6ead4c6a48cc6e723e](https://github.com/jesperancinha/project-signer/tree/2.0.1) - JDK17 Build - Java
-   [2.0.2](https://github.com/jesperancinha/project-signer/tree/2.0.2) - [db55a31555e3061c90aad5d02b2dd52cd922e271](https://github.com/jesperancinha/project-signer/tree/2.0.2) - JDK19 Build - Java
-   [3.0.0](https://github.com/jesperancinha/project-signer/tree/3.0.0) - [dce8b71d356fbf9f74a401dafa5998ec1a844904](https://github.com/jesperancinha/project-signer/tree/3.0.0) - JDK19 Build - Kotlin

Check the updated [ReleaseNotes.md](ReleaseNotes.md) file for details on each release.

> The maven Release has nothing to do with the tags. Since this project is not being pushed to Sonatype, the maven version has not meaning.

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

---

## My Android apps

<div align="center">

[![](https://img.shields.io/badge/Matrix%20Anywhere-%230077B5.svg?style=for-the-badge&logo=android&color=0a5d00)](https://github.com/jesperancinha/matrix-anywhere-android)
[![](https://img.shields.io/badge/Base%20Converter-%230077B5.svg?style=for-the-badge&logo=android&color=0a5d00)](https://github.com/jesperancinha/base-converter-android)
[![](https://img.shields.io/badge/Timezone%20Utility-%230077B5.svg?style=for-the-badge&logo=android&color=0a5d00)](https://github.com/jesperancinha/timezone-app-utility-android)
[![](https://img.shields.io/badge/Ping%20App-%230077B5.svg?style=for-the-badge&logo=android&color=0a5d00)](https://github.com/jesperancinha/ping-app-android)
[![](https://img.shields.io/badge/Catcher%20App-%230077B5.svg?style=for-the-badge&logo=android&color=0a5d00)](https://github.com/jesperancinha/catcher-app-android)

</div>


Production deployment location: [![](https://img.shields.io/badge/Google%20Play-%230077B5.svg?style=for-the-badge&logo=googleplay&color=purple)](https://play.google.com/store/apps/developer?id=Joao+Filipe+Sabino+Esperancinha)

---

## References

-   [github-readme-stats](https://github.com/anuraghazra/github-readme-stats)
-   [github-profile-views-counter](https://github.com/antonkomarev/github-profile-views-counter)
-   [App Icon Maker](https://appiconmaker.co/)
-   [Dillinger](https://dillinger.io/)
-   [JQ](https://stedolan.github.io/jq/download/)

## About me

<div align="center">

[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-100/JEOrgLogo-27.png "João Esperancinha Homepage")](http://joaofilipesabinoesperancinha.nl)
[![](https://img.shields.io/badge/youtube-%230077B5.svg?style=for-the-badge&logo=youtube&color=FF0000)](https://www.youtube.com/channel/UCzS_JK7QsZ7ZH-zTc5kBX_g)
[![](https://img.shields.io/badge/Medium-12100E?style=for-the-badge&logo=medium&logoColor=white)](https://medium.com/@jofisaes)
[![](https://img.shields.io/badge/Buy%20Me%20A%20Coffee-%230077B5.svg?style=for-the-badge&logo=buymeacoffee&color=yellow)](https://www.buymeacoffee.com/jesperancinha)
[![](https://img.shields.io/badge/Twitter-%230077B5.svg?style=for-the-badge&logo=twitter&color=white)](https://twitter.com/joaofse)
[![](https://img.shields.io/badge/Mastodon-%230077B5.svg?style=for-the-badge&logo=mastodon&color=afd7f7)](https://masto.ai/@jesperancinha)
[![](https://img.shields.io/badge/Facebook-%230077B5.svg?style=for-the-badge&logo=facebook&color=3b5998)](https://www.facebook.com/joaofisaes/)
[![](https://img.shields.io/badge/Sessionize-%230077B5.svg?style=for-the-badge&logo=sessionize&color=cffff6)](https://sessionize.com/joao-esperancinha)
[![](https://img.shields.io/badge/Instagram-%230077B5.svg?style=for-the-badge&logo=instagram&color=purple)](https://www.instagram.com/joaofisaes)
[![](https://img.shields.io/badge/Tumblr-%230077B5.svg?style=for-the-badge&logo=tumblr&color=192841)](https://jofisaes.tumblr.com)
[![](https://img.shields.io/badge/Spotify-1ED760?style=for-the-badge&logo=spotify&logoColor=white)](https://open.spotify.com/user/jlnozkcomrxgsaip7yvffpqqm)
[![](https://img.shields.io/badge/linkedin-%230077B5.svg?style=for-the-badge&logo=linkedin)](https://www.linkedin.com/in/joaoesperancinha/)
[![](https://img.shields.io/badge/Xing-%230077B5.svg?style=for-the-badge&logo=xing&color=064e40)](https://www.xing.com/profile/Joao_Esperancinha/cv)
[![](https://img.shields.io/badge/YCombinator-%230077B5.svg?style=for-the-badge&logo=ycombinator&color=d0d9cd)](https://news.ycombinator.com/user?id=jesperancinha)
[![GitHub followers](https://img.shields.io/github/followers/jesperancinha.svg?label=Jesperancinha&style=for-the-badge&logo=github&color=grey "GitHub")](https://github.com/jesperancinha)
[![](https://img.shields.io/badge/bitbucket-%230077B5.svg?style=for-the-badge&logo=bitbucket&color=blue)](https://bitbucket.org/jesperancinha)
[![](https://img.shields.io/badge/gitlab-%230077B5.svg?style=for-the-badge&logo=gitlab&color=orange)](https://gitlab.com/jesperancinha)
[![](https://img.shields.io/badge/Sonatype%20Search%20Repos-%230077B5.svg?style=for-the-badge&color=red)](https://central.sonatype.com/search?smo=true&q=org.jesperancinha)
[![](https://img.shields.io/badge/Stack%20Overflow-%230077B5.svg?style=for-the-badge&logo=stackoverflow&color=5A5A5A)](https://stackoverflow.com/users/3702839/joao-esperancinha)
[![](https://img.shields.io/badge/Credly-%230077B5.svg?style=for-the-badge&logo=credly&color=064e40)](https://www.credly.com/users/joao-esperancinha)
[![](https://img.shields.io/badge/Coursera-%230077B5.svg?style=for-the-badge&logo=coursera&color=000080)](https://www.coursera.org/user/da3ff90299fa9297e283ee8e65364ffb)
[![](https://img.shields.io/badge/Docker-%230077B5.svg?style=for-the-badge&logo=docker&color=cyan)](https://hub.docker.com/u/jesperancinha)
[![](https://img.shields.io/badge/Reddit-%230077B5.svg?style=for-the-badge&logo=reddit&color=black)](https://www.reddit.com/user/jesperancinha/)
[![](https://img.shields.io/badge/Hackernoon-%230077B5.svg?style=for-the-badge&logo=hackernoon&color=0a5d00)](https://hackernoon.com/@jesperancinha)
[![](https://img.shields.io/badge/Dev.TO-%230077B5.svg?style=for-the-badge&color=black&logo=dev.to)](https://dev.to/jofisaes)
[![](https://img.shields.io/badge/Code%20Project-%230077B5.svg?style=for-the-badge&logo=codeproject&color=063b00)](https://www.codeproject.com/Members/jesperancinha)
[![](https://img.shields.io/badge/Free%20Code%20Camp-%230077B5.svg?style=for-the-badge&logo=freecodecamp&color=0a5d00)](https://www.freecodecamp.org/jofisaes)
[![](https://img.shields.io/badge/Hackerrank-%230077B5.svg?style=for-the-badge&logo=hackerrank&color=1e2f97)](https://www.hackerrank.com/jofisaes)
[![](https://img.shields.io/badge/LeetCode-%230077B5.svg?style=for-the-badge&logo=leetcode&color=002647)](https://leetcode.com/jofisaes)
[![](https://img.shields.io/badge/Codewars-%230077B5.svg?style=for-the-badge&logo=codewars&color=722F37)](https://www.codewars.com/users/jesperancinha)
[![](https://img.shields.io/badge/CodePen-%230077B5.svg?style=for-the-badge&logo=codepen&color=black)](https://codepen.io/jesperancinha)
[![](https://img.shields.io/badge/HackerEarth-%230077B5.svg?style=for-the-badge&logo=hackerearth&color=00035b)](https://www.hackerearth.com/@jofisaes)
[![](https://img.shields.io/badge/Khan%20Academy-%230077B5.svg?style=for-the-badge&logo=khanacademy&color=00035b)](https://www.khanacademy.org/profile/jofisaes)
[![](https://img.shields.io/badge/Pinterest-%230077B5.svg?style=for-the-badge&logo=pinterest&color=FF0000)](https://nl.pinterest.com/jesperancinha)
[![](https://img.shields.io/badge/Quora-%230077B5.svg?style=for-the-badge&logo=quora&color=FF0000)](https://nl.quora.com/profile/Jo%C3%A3o-Esperancinha)
[![](https://img.shields.io/badge/Google%20Play-%230077B5.svg?style=for-the-badge&logo=googleplay&color=purple)](https://play.google.com/store/apps/developer?id=Joao+Filipe+Sabino+Esperancinha)
[![](https://img.shields.io/badge/Coderbyte-%230077B5.svg?style=for-the-badge&color=blue&logo=coderbyte)](https://coderbyte.com/profile/jesperancinha)
[![](https://img.shields.io/badge/InfoQ-%230077B5.svg?style=for-the-badge&color=blue&logo=coderbyte)](https://www.infoq.com/profile/Joao-Esperancinha.2/)
[![](https://img.shields.io/badge/OCP%20Java%2011-%230077B5.svg?style=for-the-badge&logo=oracle&color=064e40)](https://www.credly.com/badges/87609d8e-27c5-45c9-9e42-60a5e9283280)
[![](https://img.shields.io/badge/OCP%20JEE%207-%230077B5.svg?style=for-the-badge&logo=oracle&color=064e40)](https://www.credly.com/badges/27a14e06-f591-4105-91ca-8c3215ef39a2)
[![](https://img.shields.io/badge/VMWare%20Spring%20Professional%202021-%230077B5.svg?style=for-the-badge&logo=spring&color=064e40)](https://www.credly.com/badges/762fa7a4-9cf4-417d-bd29-7e072d74cdb7)
[![](https://img.shields.io/badge/IBM%20Cybersecurity%20Analyst%20Professional-%230077B5.svg?style=for-the-badge&logo=ibm&color=064e40)](https://www.credly.com/badges/ad1f4abe-3dfa-4a8c-b3c7-bae4669ad8ce)
[![](https://img.shields.io/badge/Deep%20Learning-%230077B5.svg?style=for-the-badge&logo=ibm&color=064e40)](https://www.credly.com/badges/8d27e38c-869d-4815-8df3-13762c642d64)
[![](https://img.shields.io/badge/Certified%20Neo4j%20Professional-%230077B5.svg?style=for-the-badge&logo=neo4j&color=064e40)](https://graphacademy.neo4j.com/certificates/c279afd7c3988bd727f8b3acb44b87f7504f940aac952495ff827dbfcac024fb.pdf)
[![](https://img.shields.io/badge/Certified%20Advanced%20JavaScript%20Developer-%230077B5.svg?style=for-the-badge&logo=javascript&color=064e40)](https://cancanit.com/certified/1462/)
[![](https://img.shields.io/badge/Kong%20Champions-%230077B5.svg?style=for-the-badge&logo=kong&color=064e40)](https://konghq.com/kong-champions)
[![Generic badge](https://img.shields.io/static/v1.svg?label=GitHub&message=JEsperancinhaOrg&color=064e40&style=for-the-badge "jesperancinha.org dependencies")](https://github.com/JEsperancinhaOrg)
[![Generic badge](https://img.shields.io/static/v1.svg?label=All%20Badges&message=Badges&color=064e40&style=for-the-badge "All badges")](https://joaofilipesabinoesperancinha.nl/badges)
[![Generic badge](https://img.shields.io/static/v1.svg?label=Status&message=Project%20Status&color=orange&style=for-the-badge "Project statuses")](https://github.com/jesperancinha/project-signer/blob/master/project-signer-quality/Build.md)

</div>
