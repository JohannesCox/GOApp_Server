# GOApp_Server

Auf https://i43pc164.ipd.kit.edu/PSESoSe17Gruppe4/GOApp_Server/ sind aktuell 3 Servlets aktiv: 

1.) /FrontServlet - Authentification und Request Handling. Das eigentliche Servlet für unsere App

und 2 Testservlets:

2.) /AuthentificationTestServlet - testet nur die Authentifizierung. Es wird ein idToken verifiziert. Besteht eine gültige session mit dem Server, muss kein idToken übergeben werden. Gibt die userId zurück.

3.) /RequestTestServlet - Testet nur die Server-Komponenten ohne Authentifizierung. Es kann unter "UserId" ein beliebiger String angegeben werden.

Dokumentation der Request/Responses:

Neben der Authentifizierung muss bei jedem request ein Parameter "request" übergeben werden, der die Art des Request spezifiziert. Es existieren aktuell folgende Requests:

1. "createEvent" (noch nicht implementiert) Unter "event" muss das JsonObject, das die .serialze() Funktion der Client-Event Klasse erzeugt übergeben werden. Ich richte mich bei dem Format an die bereits implementierte Methode auf der CLient Seite.
2. "joinEvent" (noch nicht implementiert) "eventId" - Gibt ein Event als JsonObject zurück.
3. "deleteEvent" zusätzlicher Parameter ("eventId"), gibt JsonObject als String zurück {"successful":true/false}
4." "deleteUser" kein zusätzlicher Parameter, gibt JsonObject als String zurück {"successful":true/false}
5. "getEvents" (noch nicht implementiert) übergeben werden muss das Attribut "eventList": HashMap<String,Integer> (mit eventId, lastModified). Es wird dieselbe bearbeitete HashMap zurückgegeben als String (erzeugt mit der serialize() Klasse von HashMap).
6. "getMembers" (noch nicht implementiert) übergeben werden muss Parameter "eventId". Zurückgegebn wird ein JsonArray. Jedes Objekt des Arrays ist ein JsonObject {"username":"aName"}.
7. "leaveEvent" "eventId" muss als Parameter übergeben werden. gibt JsonObject als String zurück {"successful":true/false}.
8. "SignUp" : zur registrierung/erstmaliger anmeldung. Als parameter muss "username" üebrgeben werden. gibt JsonObject als String zurück {"successful":true/false}.
9. "startEvent" (noch nciht implementiert)
10. "udateEvent" (noch nciht implementiert) wie bei createEvent
