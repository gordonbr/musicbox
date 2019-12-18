# musicbox

<div>
REST API to search for JukeBoxes that support a given Setting, which is searched by settingId.
</div>
<H6>API</H6>
<div>
- http://localhost:8080
</div>
<div>
GET /jukeboxes
</div>
<div>settingId: jukebox setting id (required) </div>
<div>model: filter the result by model of jukebox</div>
<div>offset: offset of pagination</div>
<div>limit: limit of pagination</div>
<div>ERROR: 400 bad request - settingId is required</div>
<div>ERROR: 404 not found - setting was not found</div>
<h6>SWAGGER</h6>
http://localhost:8080/swagger-ui.html

<h6>RUN LOCAL</h6>
- RUN: mvn build clean package spring-boot:repackage<br>
- Go to target folder<br>
- RUN: java -Dspring.profiles.active=default -jar musicbox-1.0-SNAPSHOT.jar

