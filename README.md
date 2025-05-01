# INF112 libGDX + Maven template 
Simple skeleton with [libGDX](https://libgdx.com/). 

# INF112 Project – *Big Head Kickerz*

* Team: *Beta Krigerne* (Gruppe 6): *Mikal Arolkar, Andreas Aasheim Farup, Vegard Gjertsen Gonsholt, Olav Hilland Lidal*
* Lenke til prosjektet: https://git.app.uib.no/inf112/25v/proj/beta-krigerne.

## Om spillet
Velkommen til Big Head Kickerz – spillet der fysikk er valgfritt og hodestøt er lov! 
Du er en karikert fotballspiller med et ENORMT hode og en besettelse for å score mål. 
Ingen regler, ingen dommere, bare ren kaos-fotball der du hopper, sparker og head'er ballen
i jakten på seier.

## Beskrivelse av spillet
Big Head Kickerz er et 2D fotballspill hvor to spillere konkurrerer om å score mest mål.
Den ene spilleren beveges ved bruk av piltastene og sparker med "p". Den andre spilleren beveges med
A, D og W og sparker med SPACE. 

## Kjøring
* Kompileres med `mvn package`.
* Kjøres med `java -jar target/big-head-kickerz-1.0-SNAPSHOT-fat.jar`
* Testes med `mvn verify`
* Krever Java 21 eller senere

## Kjente feil
- Problemer med sparking av ballen, foten er ikke langt nok ut fra kroppen til at sparkingen gjør noe særlig forskjell.
- Litt trøbbel med fysikk, særlig når ballen er mellom to spillere og det blir kollisjon mellom spiller-ball-spiller. Da kan ballen gå gjennom en av spillerne.

## Credits
- Inspirasjon hentet fra https://www.kongregate.com/games/mousebreaker/sports-heads-football-championship
- Bilde av spiller hentet fra https://headsoccer.fandom.com/wiki/Characters
- Bilde av ball hentet fra https://no.m.wikipedia.org/wiki/Fil:Soccer_ball.svg
- Bilde av mål hentet fra https://creazilla.com/media/clipart/21507/soccer-goal
- Bilde av bakgrunnen hentet fra https://eu.indystar.com/story/sports/soccer/2017/01/25/manchester-united-old-trafford-largest-stadium-england/97032978/
- Bilde av skilt hentet fra https://pngtree.com/free-png-vectors/wood-sign
- Lyd av fløyte hentet fra https://freesound.org/people/NebbiaOne/sounds/408417/
- Lyd av mål hentet fra https://freesound.org/people/paulw2k/sounds/196461/
- Lyd av menymusikk hentet fra https://freesound.org/people/neko_4444/sounds/744187/

