BEGIN;

CREATE TABLE daty (
  id serial PRIMARY KEY,
  rok int,
  miesiac int,
  dzien int,
  czy_dokladna boolean NOT NULL
);

CREATE TABLE typy_miejsc (
  id serial PRIMARY KEY,
  nazwa varchar NOT NULL,
  nadtyp int REFERENCES typy_miejsc(id)
);

CREATE TABLE miejsca (
  id serial PRIMARY KEY,
  nazwa varchar NOT NULL,
  nadmiejsce int REFERENCES miejsca(id),
  typ_miejsca int NOT NULL REFERENCES typy_miejsc(id)
);

CREATE TABLE osoby (
  id serial PRIMARY KEY,
  imie varchar,
  pozostale_imiona varchar,
  nazwisko_rodowe varchar,
  data_ur int REFERENCES daty(id),
  miejsce_ur int REFERENCES miejsca(id),
  wciaz_zyje boolean NOT NULL,
  miejsce_sm int REFERENCES miejsca(id),
  data_sm int REFERENCES daty(id),
  plec boolean
);

CREATE TABLE typy_rs (
  id serial PRIMARY KEY,
  nazwa varchar NOT NULL
);

CREATE TABLE typy_rns (
  id serial PRIMARY KEY,
  nazwa varchar NOT NULL
);

CREATE TABLE relacje_symetryczne (
  id serial PRIMARY KEY,
  osoba1 int REFERENCES osoby(id),
  osoba2 int REFERENCES osoby(id),
  typ_rs int NOT NULL REFERENCES typy_rs(id),
  miejsce int REFERENCES miejsca(id),
  data int REFERENCES daty(id)
);

CREATE TABLE relacje_niesymetryczne (
  id serial PRIMARY KEY,
  osoba1 int REFERENCES osoby(id),
  osoba2 int REFERENCES osoby(id),
  typ_rns int NOT NULL REFERENCES typy_rns(id),
  miejsce int REFERENCES miejsca(id),
  data int REFERENCES daty(id)
);

CREATE TABLE typy_uwag (
  id serial PRIMARY KEY,
  nazwa varchar NOT NULL
);

CREATE TABLE uwagi (
  id serial PRIMARY KEY,
  zawartosc varchar NOT NULL,
  typ_uwagi int REFERENCES typy_uwag(id)
);

CREATE TABLE uwagi_osoby (
  id_uwagi int REFERENCES uwagi(id),
  id_osoby int REFERENCES osoby(id),
  PRIMARY KEY (id_uwagi, id_osoby)
);

CREATE TABLE zawody (
  id serial PRIMARY KEY,
  nazwa varchar NOT NULL
);

CREATE TABLE zawody_osoby (
  id_osoby int REFERENCES osoby(id),
  id_zawodu int REFERENCES zawody(id),
  PRIMARY KEY (id_osoby, id_zawodu)
);

CREATE TABLE miejsce_pracy (
  id_zawody_osoby int, --???
  stanowisko varchar,
  miejsce int REFERENCES miejsca(id),
  data_od int REFERENCES daty(id),
  data_do int REFERENCES daty(id)
);

CREATE TABLE tytuly (
  id serial PRIMARY KEY,
  nazwa int NOT NULL,
  nadtytul int REFERENCES tytuly(id),
  podtytul int REFERENCES tytuly(id) --???
);

CREATE TABLE tytuly_osoby (
  id_osoby int REFERENCES osoby(id),
  id_tytulu int REFERENCES tytuly(id),
  PRIMARY KEY (id_osoby, id_tytulu)
);

CREATE TABLE grupy (
  id serial PRIMARY KEY,
  nazwa int NOT NULL,
  nadgrupa int REFERENCES grupy(id)
);

CREATE TABLE grupy_osoby (
  id_grupy int REFERENCES grupy(id),
  id_osoby int REFERENCES osoby(id),
  PRIMARY KEY (id_grupy, id_osoby)
);

CREATE TABLE uwagi_grupy (
  id_grupy int REFERENCES grupy(id),
  id_uwagi int REFERENCES uwagi(id),
  PRIMARY KEY (id_grupy, id_uwagi)
);

CREATE SEQUENCE nazwiska_kolejnosc_seq
  START WITH 100
  INCREMENT BY 100;

CREATE TABLE nazwiska (
  id_osoby int REFERENCES osoby(id),
  wartosc varchar NOT NULL,
  data_od int REFERENCES daty(id),
  kolejnosc int NOT NULL DEFAULT nextval('nazwiska_kolejnosc_seq'),
  PRIMARY KEY (id_osoby, wartosc) --???
);

COMMIT;
