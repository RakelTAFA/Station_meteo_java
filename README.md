# Station_meteo_java
Java, microcontrôleur (carte ESP32 avec un capteur de température et humidité), base de données PostgreSQL sur Raspberry Pi

Ce projet universitaire a été réalisé en équipe par M. Rabehi, M. Lagrevol et moi-même. L'objectif était de créer une station météo :
 - Un capteur relier à un microcontrôleur permet de récupérer des valeurs sur la température et l'humidité à des moments définis (tou-
   tes les 10 secondes par exemple) et d'envoyer ces valeurs dans une base de données créée en PostgreSQL qui recueillait donc la
   température, l'humidité et l'heure précise de la capture.
 - Ensuite, on peut choisir via l'interface graphique d'afficher différents diagrammes.
 - Mais la base de données sert également à stocker des informations de connexion. On peut se connecter en admin et accéder à des
   fonctionnalités indisponible en utilisateur lambda. Par exemple l'export csv des valeurs récupérer est impossible sans être admin.
