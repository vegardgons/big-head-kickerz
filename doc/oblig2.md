# Rapport – innlevering 2
**Team:** 
*beta krigerne* – *Mikal Arolkar, Andreas Aasheim Farup, Vegard Gjertsen Gonsholt, Olav Hilland Lidal*

## Prosjektrapport

### Rollefordeling
Vi valgte følgende roller i starten av prosjektet:
* Mikal Arolkar: Møteansvarlig 
    * Innebærer å finne tid og sted for møtene vi holder, samt skrive møtereferat.
* Andreas Aasheim Farup: Utvikler
    * Innebærer et ansvar for kodestruktur og holde styr på prosjektmetodikken. 
* Vegard Gjertsen Gonsholt: Gruppeleder
    * Innebærer et overordnet ansvar for arbeidsflyten i gruppen og fordeler oppgaver mellom oss.
* Olav Hilland Lidal: Grafisk designer
    * Innebærer et ansvar for det grafiske ved spillet.

Vi synes at disse rollene har fungert bra. Selv om vi har hatt ansvar for ulike områder har alle har så langt bidratt med kodingen. Vi synes ikke det er noen grunn til å endre på noen roller så langt i prosjektet vårt, ettersom det har fungert godt. 

### Prosjektmetodikk
Vi hadde ikke valgt noen prosjektmetodikk ved første innlevering, men har nå kommet fram til at vi vil bruke kanban. Vi har laget et issue board på git som inneholder kolonner for åpne saker, saker som pågår, saker som må sees over, og saker som er avsluttet. Dette har så langt fungert godt, og vi har ikke noen tanker om ting som burde gjøres annerledes her, da vi fortsatt er i startgropa med bruk av denne metodikken. 

### Gruppedynamikk
Dynamikken i gruppa har vært god. Vi kjenner hverandre godt fra før som har gjort det lett for oss å samarbeide. I tillegg sitter vi daglig på samme lesesal, noe som gjør at vi møtes ofte og kan til enhver hjelpe hverandre om vi står fast. Kommunikasjonen kan bli litt bedre, og vi har slitt litt med å finne møtetidspunkter som passer for alle. Så langt vurderer vi at prosjektstrukturen har vært god, men at vi har litt å gå på her. I oppstartsfasen av prosjektet gitt det litt tregt, og vi visste ikke helt hvor vi skulle starte. Nå som vi har kommet godt i gang med kodedelen, og fått opp et issue board med saker på git har det gått mye lettere. Dette har hjulpet oss for at alle har noe å jobbe med. 

### Commits
I oppstartsfasen er det varierende hvor mye hver av oss har hatt tid til å bidra på kondingen. Dette fordi vi alle har tatt opp konte-eksamener som har blitt høyere prioritert. Dette kan ha gitt litt utslag på antall commits per person. I tillegg har vi holdt litt på med parprogrammering, siden vi daglig sitter sammen på lesesalen. Så langt har alle fire bidratt og vi har ikke hatt noe problem med at noen sluntrer unna. Vi squasher commits ved merging av en branch, og det vises derfor ganske få commits selv om mye har blitt gjort. 

### Møtereferater
Møtereferatene består av korte punkter, og er mye av det samme som er skrevet i dette dokumentet. 
Link til møtereferater: https://docs.google.com/document/d/1ZWcazwmUgAseS5O9PUcpke30jC4rHr28SO9JqplW1AA/edit?usp=sharing

### Forbedringspunkter
1. Planlegge møter lenger tid i forveien, slik at alle har mulighet til å stille.
2. Bli flinkere til å ta i bruk issue board.

## Krav og spesifikasjon

### Krav og MVP
Vi lagde følgende krav til innleveringen av oblig1:

1. Som spiller ønsker jeg å se en fotballbane i 2D, slik at jeg forstår spillområdet og målene.
2. Som spiller ønsker jeg å kunne styre spilleren min med piltaster og space, slik at jeg kan bevege meg rundt på banen og spille spillet.
3. Som spiller ønsker jeg at spillet skal registrere antall mål, slik at jeg kan se hvem som leder og hvor mange mål jeg har scoret.
4. Som utvikler ønsker jeg at spillere og ballen skal ha en fysikkmotor, slik at bevegelser og kollisjoner føles naturlige.
5. Som spiller ønsker jeg å ha en startskjerm ved oppstart og en Game Over-skjerm, slik at jeg vet når spillet starter og slutter.
6. Som spiller ønsker jeg at spillfigurene skal ha en unik visuell stil, slik at de er enkle å kjenne igjen og gir spillet en morsom identitet.

Akseptansekriterier og arbeidskrav ligger i doc/oblig1.md. Kravene vi så langt har prioritert er krav 1, 2 (som er ferdig), samt delvis 4 og 5. Disse to kravene krever litt mer arbeid. Når det gjelder MVP-en vi lagde så er vi omtrent i mål med de kravene. Vi må finpusse fysikken, men utifra MVP synes vi at det funker godt nok enn så lenge. Vi er heller ikke helt i mål med å registrere antall mål. 

## Produkt og kode

### Kjente feil og README
Vi har så langt ikke rettet tidligere påpekte feil, da vi ikke tidligere har påpekt noen feil. README.md inneholder hvordan programmet bygges, kjøres, og testes. 

### Klassediagram
