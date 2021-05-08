project=$(pwd | cut -d / -f 4)

mvn clean package -P default -Dmaven.test.skip=true wagon:upload-single wagon:sshexec -f $(cd `dirname $0`; pwd)/pom.xml
