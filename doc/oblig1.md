# Rapport – innlevering 1
**Team:** 
*beta krigerne* – *Mikal Arolkar, Andreas Aasheim Farup, Vegard Gjertsen Gonsholt, Olav Hilland Lidal*

## A1
**Rollefordeling:**
* Mikal Arolkar: Utvikler
* Andreas Aasheim Farup: Utvikler
* Vegard Gjertsen Gonsholt: Gruppeleder
* Olav Hilland Lidal: Kundekontakt 

## A2
**Konsept**
* To spillfigurer som kan styres, kan bevege seg mot høyre og venstre, hoppe og sparke
    * Spillfigurene består av et stort hode med et bein
* Todimensjonalt
    * Et mål på hver side
* En ball som spillfigurene skal sparke/treffe med hodet
* Spillerne får poeng for å få ballen i motstanderens mål
* Ulike power-ups som dukker opp på banen
    * Kan endre tilstanden til en spiller/målet/ballen dersom ballen treffer den
* Spillet går ut på å score flest mål i løpet av en gitt tid, eventuelt førstemann til et gitt antall mål


## A3
**Prosess/ prosjekt-organisering:**
* Vi møtes 2 ganger i uken til et fast tidspunkt for å jobbe med prosjektet
* Kommunikasjon mellom møter vil foregå på discord samt fysiske møter (møtes på lesesal)
* Vi vil fordele arbeidsoppgaver så likt som mulig, og slik at alle til enhver tid har noe å jobbe med. 

**MVP:**
1. Vise en fotballbane i 2D 
2. Vise 2 spillere og en ball
3. Flytte spiller ved bruk av piltaster og space 
4. Spiller interagerer med bakke, ball og motspiller
5. Spillet registrerer antall mål 
6. Start-skjerm ved oppstart / Game Over

**Nice to have:**
1. Ball interagerer med power-ups

**Brukerhistorier:**
1. Som spiller ønsker jeg å se en fotballbane i 2D, slik at jeg forstår spillområdet og målene.
    * Akseptansekriterier:
      En 2D-fotballbane vises ved oppstart av spillet.
      Banen har tydelig markerte mål på hver side.
      Bakgrunnen skiller seg visuelt fra spillområdet.
    * Arbeidsoppgaver:
      Lage en visuell representasjon av banen i Java (bruk f.eks. libGDX's SpriteBatch).
      Tegne mål og andre viktige elementer.
      Sørge for at ball og spillere plasseres innenfor banen.
      Teste at banen vises riktig i ulike skjermoppløsninger.
2. Som spiller ønsker jeg å kunne styre spilleren min med piltaster og space, slik at jeg kan bevege meg rundt på banen og spille spillet.
    * Akseptansekriterier:
      Spilleren kan bevege seg venstre/høyre med piltaster (← / →).
      Spilleren kan hoppe ved å trykke space.
      Spilleren stopper når ingen taster er trykket.
      Bevegelsene føles responsive og realistiske.
    * Arbeidsoppgaver:
      Implementere input-håndtering for spillerbevegelse.
      Legge til tyngdekraft og bakkekollisjon.
      Teste at spilleren beveger seg som forventet.
      Finjustere hastighet og hopp-høyde.
3. Som spiller ønsker jeg at spillet skal registrere antall mål, slik at jeg kan se hvem som leder og hvor mange mål jeg har scoret.
    * Akseptansekriterier:
      Når ballen krysser mållinjen, registreres et mål for riktig spiller.
      Poengtavlen oppdateres umiddelbart etter et mål.
      Spillet resettes (f.eks. ballen sendes til midten) etter scoring.
    * Arbeidsoppgaver:
      Implementere logikk for å sjekke når ballen er i målet.
      Lage et poengsystem og en visuell poengtavle.
      Resette ballen etter mål.
      Teste at mål registreres riktig.
4. Som utvikler ønsker jeg at spillere og ballen skal ha en fysikkmotor, slik at bevegelser og kollisjoner føles naturlige.
    * Akseptansekriterier:
      Spillere påvirkes av tyngdekraft.
      Ballen spretter realistisk ved kollisjon med bakken, vegger og spillere.
      Kollisjoner mellom spiller og ball påvirker ballens retning og hastighet.
    * Arbeidsoppgaver:
      Implementere tyngdekraft og bevegelsesfysikk.
      Lage kollisjonshåndtering mellom ball, spillere og bakken.
      Justere ballens sprett og friksjon for realistisk oppførsel.
      Teste fysikken i ulike scenarioer.
5. Som spiller ønsker jeg å ha en startskjerm ved oppstart og en Game Over-skjerm, slik at jeg vet når spillet starter og slutter.
    * Akseptansekriterier:
      Startskjermen vises ved oppstart og lar spilleren starte spillet.
      Game Over-skjermen vises når en spiller har vunnet (f.eks. først til 5 mål).
      Spilleren kan starte en ny runde fra Game Over-skjermen.
    * Arbeidsoppgaver:
      Lage en enkel startskjerm med "Start"-knapp.
      Implementere en Game Over-skjerm med "Spill igjen"-knapp.
      Sørge for at skjermene vises til riktig tid i spillet.
      Teste at skjermene fungerer korrekt.
6. Som spiller ønsker jeg at spillfigurene skal ha en unik visuell stil, slik at de er enkle å kjenne igjen og gir spillet en morsom identitet.
    * Akseptansekriterier:
      Spillfigurene har distinkte farger og design.
      Hver spiller ser unik ut på skjermen.
      Grafikken passer til spillstilen.
    * Arbeidsoppgaver:
      Designe og lage sprites/grafikk for spillerfigurene.
      Implementere figurene i spillet.
      Teste at figurene ser bra ut og er lett gjenkjennelige. 
