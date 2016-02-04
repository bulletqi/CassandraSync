@echo on
@ECHO campaign
java -jar ../lib/CassandraSync.jar -sh 172.16.8.221,172.16.8.222,172.16.8.223,172.16.8.224,172.16.8.225,172.16.8.226,172.16.8.227,172.16.8.228,172.16.8.229,172.16.8.230 -sk amber -sf campaign -th 172.16.8.81,172.16.8.82,172.16.8.83,172.16.8.84,172.16.8.85,172.16.8.86,172.16.8.87,172.16.8.88,172.16.8.89 -tk amber -tf campaign
@ECHO images
java -jar ../lib/CassandraSync.jar -sh 172.16.8.221,172.16.8.222,172.16.8.223,172.16.8.224,172.16.8.225,172.16.8.226,172.16.8.227,172.16.8.228,172.16.8.229,172.16.8.230 -sk amber -sf images -th 172.16.8.81,172.16.8.82,172.16.8.83,172.16.8.84,172.16.8.85,172.16.8.86,172.16.8.87,172.16.8.88,172.16.8.89 -tk amber -tf images
@ECHO pageResource
java -jar ../lib/CassandraSync.jar -sh 172.16.8.221,172.16.8.222,172.16.8.223,172.16.8.224,172.16.8.225,172.16.8.226,172.16.8.227,172.16.8.228,172.16.8.229,172.16.8.230 -sk amber -sf pageResource -th 172.16.8.81,172.16.8.82,172.16.8.83,172.16.8.84,172.16.8.85,172.16.8.86,172.16.8.87,172.16.8.88,172.16.8.89 -tk amber -tf pageResource
@ECHO setting
java -jar ../lib/CassandraSync.jar -sh 172.16.8.221,172.16.8.222,172.16.8.223,172.16.8.224,172.16.8.225,172.16.8.226,172.16.8.227,172.16.8.228,172.16.8.229,172.16.8.230 -sk amber -sf setting -th 172.16.8.81,172.16.8.82,172.16.8.83,172.16.8.84,172.16.8.85,172.16.8.86,172.16.8.87,172.16.8.88,172.16.8.89 -tk amber -tf setting
@ECHO template
java -jar ../lib/CassandraSync.jar -sh 172.16.8.221,172.16.8.222,172.16.8.223,172.16.8.224,172.16.8.225,172.16.8.226,172.16.8.227,172.16.8.228,172.16.8.229,172.16.8.230 -sk amber -sf template -th 172.16.8.81,172.16.8.82,172.16.8.83,172.16.8.84,172.16.8.85,172.16.8.86,172.16.8.87,172.16.8.88,172.16.8.89 -tk amber -tf template
@ECHO userSite
java -jar ../lib/CassandraSync.jar -sh 172.16.8.221,172.16.8.222,172.16.8.223,172.16.8.224,172.16.8.225,172.16.8.226,172.16.8.227,172.16.8.228,172.16.8.229,172.16.8.230 -sk amber -sf userSite -th 172.16.8.81,172.16.8.82,172.16.8.83,172.16.8.84,172.16.8.85,172.16.8.86,172.16.8.87,172.16.8.88,172.16.8.89 -tk amber -tf userSite
@ECHO watchNumber
java -jar ../lib/CassandraSync.jar -sh 172.16.8.221,172.16.8.222,172.16.8.223,172.16.8.224,172.16.8.225,172.16.8.226,172.16.8.227,172.16.8.228,172.16.8.229,172.16.8.230 -sk amber -sf watchNumber -th 172.16.8.81,172.16.8.82,172.16.8.83,172.16.8.84,172.16.8.85,172.16.8.86,172.16.8.87,172.16.8.88,172.16.8.89 -tk amber -tf watchNumber
@ECHO webSite
java -jar ../lib/CassandraSync.jar -sh 172.16.8.221,172.16.8.222,172.16.8.223,172.16.8.224,172.16.8.225,172.16.8.226,172.16.8.227,172.16.8.228,172.16.8.229,172.16.8.230 -sk amber -sf webSite -th 172.16.8.81,172.16.8.82,172.16.8.83,172.16.8.84,172.16.8.85,172.16.8.86,172.16.8.87,172.16.8.88,172.16.8.89 -tk amber -tf webSite