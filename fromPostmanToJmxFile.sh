#!/bin/sh
[ ! -d "ita-selfcare-bb-automation_API" ] && git clone https://github.com/sky-uk/ita-selfcare-bb-automation_API.git
cd ita-selfcare-bb-automation_API
git checkout develop
git pull
cd ..
java -jar Postman2Jmx.jar ita-selfcare-bb-automation_API/$1 perfCI_draftVersion.jmx

#ita-selfcare-bb-automation_API/$1

