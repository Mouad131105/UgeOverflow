# UGEOverFlow-Android

# Manuel utilisation : 

Afin de lancer l’application proprement, un fichier .jar correspondant au Back-end de notre application J2E a été déposer sur E-learning, télécharger le fichier jar et lancer la commande suivante : (obligatoire pour faire fonctionner l’appli)

java -jar UGEOverflow-0.0.1-SNAPSHOT.jar


Une fois le jar exécuter, il faut spécifier dans le fichier ApiService (app/src/main/java/fr/uge/ugeoverflow/session/ApiService.kt)
la bonne adresse ipv4 local pour que l’appli pointe correctement vers le back-end, comme utiliser localhost ne fonctionne pas. Ex: "http://192.168.1.69:8080"

Ensuite, il suffit de lancer l’application sur Android Studio.

