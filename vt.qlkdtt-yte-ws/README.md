# Getting Started
## build
	Environment required:
		- JDK 1.8 or later
		- Gradle 4+ or Maven 3.2+ 
	Dependencies
		- coong-web module
	Build the Project
		mvn clean package -DskipTests
	   	mvn clean install -DskipTests -Dmaven.wagon.http.ssl.insecure=true -Dmaven.wagon.http.ssl.allowall=true -Dmaven.wagon.http.ssl.ignore.validity.dates=true
        K:\01.Work\source\qlkdtt_yte_ws\vt.qlkd-ws\vt.qlkdtt-yte-ws\target
## deploy
	create dir and copy jar deploy file
		mkdir -p /app/{service-name}
		cp target/{service-name}-{version}.jar
	prepare config file and change config for target environment
		cp main/resource/bootstrap.yml /app/{service-name}
		cp main/resource/logback-spring.xml /app/{service-name}
	prepare db schema and seed/demo data
		ref mics dir
	start service
		nohup java -jar /app/{service-name}/{service-name}-{version}.jar >/dev/null 2>&1 &
		nohup java -jar /app/vt.qlkdtt-ws/vt.qlkdtt-yte-ws-0.0.1.jar >/dev/null 2>&1 &
	verify and log tracing
		tail -f /app/{service-name}/logs/{service-name}.log
		
		curl -X GET http://localhost:8080/actuator/health | grep json_pp
			output:	
				{
				   "details" : {
					  "db" : {
						 "details" : {
							"database" : "MySQL",
							"hello" : 1
						 },
						 "status" : "UP"
					  },
					  "refreshScope" : {
						 "status" : "UP"
					  },
					  "diskSpace" : {
						 "details" : {
							"free" : 16281149440,
							"total" : 26363904000,
							"threshold" : 10485760
						 },
						 "status" : "UP"
					  }
				   },
				   "status" : "UP"
				}
## huong dan deploy
    b1: Build project 
    
    mvn clean install -DskipTests3366d
    b2: Lay duong dan file jar , de day len server
    K:\01.Work\source\qlkdtt_yte_ws\vt.qlkd-ws\vt.qlkdtt-yte-ws\target
    b3: Stop service
    ps -ef|grep ws (lay thong tin port service dang chay) 
    kill -9 <port>
    b4: Copy file vt.qlkdtt-yte-ws-0.0.1.jar len thu muc tren server
    (Xoa file cu or replace)
    from : K:\01.Work\source\qlkdtt_yte_ws\vt.qlkd-ws\vt.qlkdtt-yte-ws\target
    to : /app/vt.qlkdtt-ws
    b4: Start service
    nohup java -jar /app/vt.qlkdtt-ws/vt.qlkdtt-yte-ws-0.0.1.jar >/dev/null 2>&1 &
    b5: check log tien trinh
    tail -f /app/vt.qlkdtt-ws/logs/vt.qlkdtt-yte-ws.log
    b6: check swagger
    http://202.182.111.45:8998/swagger-ui.html
