# Rapport – innlevering 3
**Team:** 
*beta krigerne* – *Mikal Arolkar, Andreas Aasheim Farup, Vegard Gjertsen Gonsholt, Olav Hilland Lidal*

## Prosjektrapport

### Rollefordeling
Vi valgte følgende roller i starten av prosjektet:
* Mikal Arolkar: Møteansvarlig 
    * Innebærer å finne tid og sted for møtene vi holder, samt hovedansvar for å skrive møtereferat og oblig.md.
* Andreas Aasheim Farup: Utvikler
    * Innebærer et ansvar for kodestruktur og holde styr på prosjektmetodikken. 
* Vegard Gjertsen Gonsholt: Gruppeleder
    * Innebærer et overordnet ansvar for arbeidsflyten i gruppen og fordeler oppgaver mellom oss.
* Olav Hilland Lidal: Grafisk designer
    * Innebærer et ansvar for det grafiske ved spillet.

Vi synes fortsatt at rollene fungerer bra. Selv om vi har hatt ansvar for ulike områder har alle bidratt med kodingen. 
Vi synes ikke det er noen grunn til å endre på noen roller så langt i prosjektet vårt, ettersom det har fungert godt. 

### Prosjektmetodikk
Siden innlevering 2 har vi benyttet oss av Kanban i form av git issues og issue boards, og vi opplever at dette fungerer svært godt. Etter at gruppen ble enig om å bruke issueboardet aktivt har det gitt oss bedre oversikt over hvem som jobber med hva, samt å holde styr på det som er av uferdige oppgaver.

### Gruppedynamikk
Ikke noe nytt å melde om gruppedynamikken. Dynamikken har vært god. Vi kjenner hverandre godt fra før og kommuniserer godt sammen. Vi snakker sammen regelmessig gjennom gruppechat i discord. I tillegg har Andreas, Vegard og Olav møttes regelmessig på lesesal og har dermed også hatt enkelt for å kommunisere med hverandre fysisk. Det har imidlertid vært verre for Mikal som ikke har kunnet komme på lesesal grunnet operasjon i begge legger. 
Vi er generelt fornøyd med arbeidet hittil og har ingen uenigheter i arbeidsprossessen. Eventuelle uenigheter i forhold til implementasjon av kode har fort blitt løst med drøfting av løsninger.  

Prosjektstrukturen har blitt veldig mye bedre etter vi begynte å bruke Kanban aktivt. Da blir det veldig tydelig hvem som jobber med hva, og man har alltid noe å ta tak i.

### Commits
Siden sist innlevering har det blitt en noe større forskjell i commits. Andreas og Vegard har hatt hovedansvar for å utvikle powerups samt å videreutvikle spillet ved å forbedre allerede implementerte klasser. De har dermed utført flere små endringer og hatt flere commits. 
Olav har hatt hovedsansvar for å utvikle fot og sparke funksjon, og har jobbet mye lokalt med dette. Derav færre commits. 
Mikal har vært gjennom en operasjon for begge bein og vært preget av dette. Han har nylig gått av sterke smertestillende og har oppi alt dette ikke kunne vært like aktiv med commits.  

### Møtereferater
Møtereferatene består av punkter som omhandler status, avgjørelser og videre tiltak.

Link til møtereferater: https://docs.google.com/document/d/1ZWcazwmUgAseS5O9PUcpke30jC4rHr28SO9JqplW1AA/edit?usp=sharing

### Forbedringspunkter
1. Få til flere møter hvor hele gruppen kan stille. Selvom vi møtes regelmessig for å diskutere diverse rundt oppgaven, så har det vært veldig godt å ha møter hvor hele gruppen samles. Dette bør vi få til hyppigere nå i siste innspurt. 
2. Til tross for at vi har blitt flinkere til å bruke issue board så har det vært korte perioder hvor man ikke har hatt en oppgave(issue) på alle personer. Dette kan forbedres. Alle bør alltid ha noe å gjøre. 

## Krav og spesifikasjon

### Krav og MVP
Vi har kommet forbi MVP, og har nå et fungerende spill. Siden forrige innlevering har vi egentlig prioritert samtlige av kravene fra brukerhistoriene. Vi har oppnådd alle krav med unntak av krav 4:  

4. Som utvikler ønsker jeg at spillere og ballen skal ha en fysikkmotor, slik at bevegelser og kollisjoner føles naturlige.

Vi har fortsatt en del bugs knyttet til kollisjon. Akkurat nå kan ballen "gå gjennom" spiller 2 dersom det blir en kollisjon mellom spiller-ball-spiller. Dette gjør naturligvis spillopplevelsen vesentlig dårligere, og er derfor høyt oppe på prioriteringslisten av videre forbedringer.

I tillegg har vi et par nye krav: 
1. Som spiller ønsker jeg at spillerne har en fot som kan sparke ballen opp fra bakken (foreløpig blir det bare en "boost") 
    * Akseptansekriterier:
      - Spillerne skal ha en visuell fot som beveger seg når man sparker, helst i en pendelbevegelse.
      - Spark skal kunne utføres med en egen knapp eller tastekombinasjon.
      - Når ballen er på bakken og spark aktiveres, skal ballen skytes opp i lufta med merkbar kraft.
    * Arbeidsoppgaver:
      - Legge til en ny animasjon for spark (den vi foreløpig har fungerer ikke helt som ønsket).
      - Implementere fysikk for å skyte ballen opp fra bakken når spark aktiveres.
      - Teste at sparking fungerer og gir ønsket ballbane.
2. Som spiller ønsker jeg å ha bakgrunnslyd til spillet, samt flere lydeffekter for begivenheter i spillet. 
    * Akseptansekriterier:
      - Det skal være bakgrunnslyd i spillet som starter når spillet begynner.
      - Lydnivåene skal være balanserte og ikke forstyrrende.
      - Vi vurderer lydeffekter for følgende hendelser:
        - Spiller hopper
        - Game Over
    * Arbeidsoppgaver:
      - Finne eller lage passende bakgrunnslyd.
      - Finne eller lage lydeffekter for de ulike hendelsene.
      - Legge til kode for å spille av lydeffekter ved relevante hendelser.

### Prioritering framover
1. Forbedre kollisjon mellom spiller-ball-spiller.
2. Endre implementasjon av fot og spark. Vi ønsker å ha en fot som beveger seg i en pendel-bevegelse og sparker ballen frem og opp
3. Legge til ytterligere lydeffekter/musikk. 

### Bugs
* Som sagt er vår største bug kollisjonshåndteringen mellom ball og spiller når ballen havner mellom spillerne. Da ender ballen opp med å gå gjennom en av spillerne.
* Foten sin størrelse og animasjon er ikke lang nok utfra kroppen til å gi ballen en boost både opp og frem (foreløpig bare fremover, og veldig lite synlig).
* En annen bug er at ballen stopper litt opp når den treffer en powerup. Vi vil at ballen skal bare skal plukke den opp og fortsette på sin bane.
* Vi har også en liten bug med at hvis en spiller står oppå en annen så vil øverste spiller sakte synke ned i nederste spiller. Dette skjer imidlertid sakte og vil trolig ikke ha så voldsomt mye å si for selve spillopplevelsen. 

## Produkt og kode

### Utbedring av feil
Vi har endret store deler av fysikkimplementasjonen, og det har ført til forbedring av mye av feilene knyttet til kollisjon:
- Dersom en spiller står oppå en annen spiller vil ikke nederste spiller synke ned i bakken. 
- Ballen har en mer naturlig fysikk og bevegelsesbane
- Registrering av mål skjer nå umiddelbart etter at ballen kommer i mål. 

### Statiske analyseverktøy - SonarQube
Vi har brukt SonarQube og det har vært et godt verktøy for å få en ytterligere oversikt over uløste issues, hvor mye test coverage vi har og eventuell duplikatkode. 

### Kjente feil og README
Vi har så langt ikke rettet tidligere påpekte feil, da vi ikke tidligere har påpekt noen feil. 

README.md inneholder hvordan programmet bygges, kjøres, og testes. 

### Klassediagram
![class_diagram_2.png](class_diagram_2.png)