#include <Arduino_JSON.h>
#include <ESP8266WiFi.h>
#include <ESP8266HTTPClient.h>
#include <ESP8266WebServer.h>

// ip hospot nodemcu default 192.168.4.1

HTTPClient http ;

ESP8266WebServer server(80);

// ubah lewat jaringan public ( Internet )
// api yang terintegrasi dengan database
// request api nodemcu
boolean publicStatOpenDoor = false ;

// ubah lewat jaringan lokal
// hostpot nodemcu
// webserver nodemcu
boolean localStatOpenDoor = false ;

void setup() {
  // put your setup code here, to run once:
  Serial.begin(115200);

  // connect wifi
  WiFi.begin("hero", "thismydal");
  Serial.println("CONNECT WIFI ...");
  while(WiFi.status() != WL_CONNECTED){
    Serial.print(".");
    delay(500);
  }
  Serial.println("WIFI IS CONNETED");

  // create hostpot
  WiFi.softAP("OPEN_DOOR", "OPEN_DOOR") ;

  // create api with nodemcu
  server.on("/post_door", HTTP_POST, [](){
    String password = server.arg("password");
    if(!password.equalsIgnoreCase("")) {
      if(password.equals("@EDPASSWORD")){
        localStatOpenDoor = !localStatOpenDoor ;
        return server.send(200, "appliaction/json", "{\"stat\" : \"true\", \"statOpenDoor\" : \""+String(localStatOpenDoor)+"\" }");
      }
    }
    return server.send(200, "appliaction/json", "{\"stat\" : \"false\"}");
  });

  server.on("/stat_door", HTTP_GET, [](){
    return server.send(200, "appliaction/json", "{\"stat\" : \"true\", \"statOpenDoor\" : \""+String(localStatOpenDoor)+"\" }");
  });

  server.begin();
}

void loop() {
  server.handleClient();
  // dari api  
  loadAPIStatDoor();
  if (publicStatOpenDoor){
    Serial.println(" DARI PUBLIK BUKA PINTU ");
  } 
  else {
    Serial.println(" DARI PUBLIK TUTUP PINTU ");
  }

  //dari lokal
  if (localStatOpenDoor){
    Serial.println(" DARI LOCAL BUKA PINTU ");
  } 
  else {
    Serial.println(" DARI LOCAL TUTUP PINTU ");
  }
}


void loadAPIStatDoor(){
  http.begin("http://192.168.43.108:8000/");
  int statusCode = http.GET();
  if(statusCode == -1 ){
    Serial.println("REQUEST ERROR");
    return ; 
  }

  String outReqDataStr = http.getString();

  // Serial.println("REQUEST BERHASIL => "+outReqDataStr);

  // format data output request api example => {"id":"1","status":"1","keterangan":"BUKA"}

  JSONVar dataObj = JSON.parse(outReqDataStr) ;

  // ubah json object status menjadi string
  String strStatusDoor = (const char*) dataObj["status"];

  // persamaan string jikas sama dengan 1 maka publicStatOpenDoor kan berubah menjadi true
  // jika 0 akan berubah menjadi false
  if(strStatusDoor.equals("1")) publicStatOpenDoor = true ;
  else publicStatOpenDoor = false ;

  // Serial.println("STATUS INT DOOR => "+strStatusDoor+" PUBLICK STATE OPEN DOOR => "+String(publicStatOpenDoor));
}
