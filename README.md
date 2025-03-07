# INF112 libGDX + Maven template 
Simple skeleton with [libGDX](https://libgdx.com/). 

# INF112 Project – *Big Head Kickers*

* Team: *Beta Krigerne* (Gruppe 6): *Mikal Arolkar, Andreas Aasheim Farup, Vegard Gjertsen Gonsholt, Olav Hilland Lidal*
* Lenke til https://git.app.uib.no/inf112/25v/proj/beta-krigerne.

## Om spillet
*Velkommen til Big Head Kickers – spillet der fysikk er valgfritt og hodestøt er lov! Du er en karikert fotballspiller med et ENORMT hode og en besettelse for å score mål. Ingen regler, ingen dommere, bare ren kaos-fotball der du hopper, sparker og head'er ballen (og motstanderen) i jakten på seier. Bruk spesialangrep som ildkuler, lynnedslag og eksploderende bananer for å utmanøvrere motstanderne. Men pass deg – én feil, og du blir knocket ut som en oppblåsbar badeball i en orkan!*

## Beskrivelse av spillet
Big Head Kickerz er et 2D fotballspill hvor to spillere konkurrerer om å score mest mål.
Spiller skal beveges ved bruk av piltastene og sparke med "p". 

## Kjøring
* Kompileres med `mvn package`.
* Kjøres med `java -jar target/gdx-template-25v-1.0-SNAPSHOT-fat.jar`
* Testes med `mvn verify`
* Krever Java 21 eller senere

## Kjente feil
Trøbbel med fysikken til ballen, og kollisjon mellom spillere. Når en spiller hopper oppå den andre så forsvinner spilleren ned gjennom bakken. 

## Credits
Inspirasjon hentet fra https://dnddream.com/dnddream_web/app_game_06.htm 
