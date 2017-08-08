# GOApp_Server

Auf https://i43pc164.ipd.kit.edu/PSESoSe17Gruppe4/GOApp_Server/ sind aktuell 3 Servlets aktiv: 

1.) /FrontServlet - Authentification und Request Handling. Das eigentliche Servlet für unsere App

und 2 Testservlets:

2.) /AuthentificationTestServlet - testet nur die Authentifizierung. Es wird ein idToken verifiziert. Besteht eine gültige session mit dem Server, muss kein idToken übergeben werden. Gibt die userId zurück.

3.) /RequestTestServlet - Testet nur die Server-Komponenten ohne Authentifizierung. Es kann unter "UserId" ein beliebiger String angegeben werden.

Dokumentation der Request/Responses:

Neben der Authentifizierung muss bei jedem request ein Parameter "request" übergeben werden, der die Art des Request spezifiziert. Es existieren aktuell folgende Requests:

1. "createEvent": Unter "event" muss das JsonObject.toString(), das die .serialze() Funktion der Client-Event Klasse erzeugt übergeben werden. Ich richte mich bei dem Format an die bereits implementierte Methode auf der CLient Seite. Die Einträge eventId und lastModified werden nicht abgefragt. Es wird ein JsonObjekt vom Typ "Event" zurückgegeben.
(JsonObject jo = new JsonObject();
		jo.addProperty("eventId", eventID);
		jo.addProperty("title", eventname);
		jo.addProperty("date", date.getTime());
		jo.addProperty("location", location);
		jo.addProperty("description", description);
		jo.addProperty("lastModified", lastmodified);)
    
2. "joinEvent" "eventId" - Gibt ein Event als JsonObject zurück.

3. "deleteEvent" zusätzlicher Parameter ("eventId"), gibt JsonObject als String zurück {"successful":true/false}

4." "deleteUser" kein zusätzlicher Parameter, gibt JsonObject als String zurück {"successful":true/false}

5. "getEvents" übergeben werden muss unter "events" ein JsonArray.toString(), jeder Eintrag ist ein JsonObject mit {"eventId":"theeventId", lastModified:12} . Es wird wieder ein JsonArray.toString() mit "Events" als JsonObject Einträge zurückgegeben.

6. "getMembers"  übergeben werden muss Parameter "eventId". Zurückgegebn wird ein JsonArray. Jedes Objekt des Arrays ist ein JsonObject {"username": "name", "isAdmin": boolean}, wobei der boolean Wert angibt, ob der username -Nutzer Admin ist.

7. "leaveEvent" "eventId" muss als Parameter übergeben werden. gibt JsonObject als String zurück {"successful":true/false}.

8. "SignUp" : zur registrierung/erstmaliger anmeldung. Als parameter muss "username" üebrgeben werden. gibt JsonObject als String zurück {"successful":true/false}.

9. "startEvent" Übergeben werden muss "eventId" , sowie zwei doubles bei "lat" und "lng". Zurückgegeben wird ein JsonArray, jedes inhaltende JsonObject representiert das Zentrum eines Clusters und hat folgende Struktur:
{"lat":0.0001,
"lng":21}

10."stopEvent": "eventId" - Rückgabe: {"successful":true/false}

11. "udateEvent" wie bei createEvent
