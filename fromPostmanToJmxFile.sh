#!/bin/sh
[ ! -d "ita-api-assistenza-automation_API" ] && git clone https://github.com/sky-uk/ita-api-assistenza-automation_API.git
cd ita-api-assistenza-automation_API
git checkout develop
git pull
cd ..
java -jar Postman2Jmx.jar ita-api-assistenza-automation_API/$1 perfCI_draftVersion.jmx
java -jar fromPostmanJsonInputDataToSedCmd.jar ita-api-assistenza-automation_API/$2

