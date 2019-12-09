#!/bin/sh
[ ! -d "ita-selfcare-bb-automation_API" ] && git clone https://github.com/sky-uk/ita-selfcare-bb-automation_API.git
cd ita-selfcare-bb-automation_API
git checkout develop
git pull
cd ..
java -jar Postman2Jmx.jar ita-selfcare-bb-automation_API/$1 perfCI_draftVersion.jmx
java -jar fromPostmanJsonInputDataToSedCmd.jar $3 selfcare ita-selfcare-bb-automation_API/$2

#ita-selfcare-bb-automation_API/$1

