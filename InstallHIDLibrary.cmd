set JAVA_HOME=%JAVA_HOME11%
mvn deploy:deploy-file -Dfile=./libs/purejavahidapi-0.0.14.jar -Dsources=./libs/purejavahidapi-0.0.14-sources.jar -Djavadoc=./libs/purejavahidapi-0.0.14-javadoc.jar -DgroupId=purejavahidapi -DartifactId=purejavahidapi -Dversion=0.0.14 -Dpackaging=jar -Durl=file:./maven-local-repository/ -DrepositoryId=maven-repository -DupdateReleaseInfo=true