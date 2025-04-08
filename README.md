# INF112 libGDX + Maven template 
Simple skeleton with [libGDX](https://libgdx.com/). 

# INF112 Project – *Big Head Kickers*

* Team: *Beta Krigerne* (Gruppe 6): *Mikal Arolkar, Andreas Aasheim Farup, Vegard Gjertsen Gonsholt, Olav Hilland Lidal*
* Lenke til prosjektet: https://git.app.uib.no/inf112/25v/proj/beta-krigerne.

## Om spillet
Velkommen til Big Head Kickers – spillet der fysikk er valgfritt og hodestøt er lov! 
Du er en karikert fotballspiller med et ENORMT hode og en besettelse for å score mål. 
Ingen regler, ingen dommere, bare ren kaos-fotball der du hopper, sparker og head'er ballen (og motstanderen)
i jakten på seier.

## Beskrivelse av spillet
Big Head Kickerz er et 2D fotballspill hvor to spillere konkurrerer om å score mest mål.
Den ene spilleren beveges ved bruk av piltastene og sparker med "p". Den andre spilleren beveges med
A, D og W og sparker med mellomromstasten. 

## Kjøring
* Kompileres med `mvn package`.
* Kjøres med `java -jar target/gdx-template-25v-1.0-SNAPSHOT-fat.jar`
* Testes med `mvn verify`
* Krever Java 21 eller senere

## Kjente feil
- Problemer med sparking av ballen, foten er ikke langt nok ut fra kroppen til at sparkingen gjør noe særlig forskjell.
- Litt trøbbel med fysikk, særlig når ballen er helt inntil veggen med en spiller som dytter ballen inn mot veggen. 
Da kan ballen sprette gjennom spilleren.
- Powerups reagerer på kontakt med spiller istedenfor med ballen. Ønsker å endre slik at den spilleren som skøyt
ballen på powerup'en får den. Endre slik at kollisjon mellom ball og powerup ikke påvirker ballens bevegelse. 

## Credits
- Inspirasjon hentet fra https://dnddream.com/dnddream_web/app_game_06.htm
- Bilde av spiller hentet fra https://headsoccer.fandom.com/wiki/Characters
- Bilde av ball hentet fra https://no.m.wikipedia.org/wiki/Fil:Soccer_ball.svg
- Bilde av mål hentet fra https://creazilla.com/media/clipart/21507/soccer-goal
- Bilde av bakgrunnen hentet fra https://eu.indystar.com/story/sports/soccer/2017/01/25/manchester-united-old-trafford-largest-stadium-england/97032978/
- Bilde av skilt hentet fra https://pngtree.com/free-png-vectors/wood-sign

