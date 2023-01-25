# lifeToy

Playing around with library developed under kagraph. 

A primitive implementation of the Life game for education and amusement.  Each turn grid cells spawn life into adjacent cells, then die off when overcrowded. 

## To Use

   1. Download and install the kagraph repository https://github.com/karel-rehor/kagraph 
   1. Setup influx connection properties in the file: `./src/test/resources/influx2.conf`
   1. To write data to influx start an influx container: `docker run --name influxdb -p 8086:8086 influxdb:2.3.0`
   1. Test (Optional): `mvn test`
   1. Compile: `mvn compile` 
   1. Install dependencies locally: `mvn dependency:copy-dependencies`
   1. Run: `java -classpath target/classes:./target/dependency/* org.kagaka.life.LifeRunner -w 15 -h 10 -t 10 -i`
   
   Or build and run the jar. 
   TODO setup jar build. 
   
__Command line arguments__   

   * `-w,--width`  - width of the life grid. 
   * `-h,--height` - height of the life grid. 
   * `-t,--turns`  - number of turns to play.  N.B. `-1` to run indefinitely. 
   * `-p,--pause`  - number of milliseconds to pause between turns. 
   * `-i,--influx` - toggle on writes of data to influxdb, which is switched off by default.  
   

### TODO 

   * Scripts
   * Tests
   * Cleanup
