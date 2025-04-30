BEGIN;

CREATE OR REPLACE FUNCTION dni_w_miesiacu(p_rok INT, p_miesiac INT)
RETURNS INT AS $$
BEGIN
    RETURN EXTRACT(DAY FROM (DATE_TRUNC('month', MAKE_DATE(p_rok, p_miesiac, 1)) + INTERVAL '1 month - 1 day'));
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION czy_rok_przestepny(p_rok INT)
RETURNS BOOLEAN AS $$
BEGIN
    RETURN (p_rok % 4 = 0 AND p_rok % 100 != 0) OR (p_rok % 400 = 0);
END;
$$ LANGUAGE plpgsql;

CREATE TABLE daty (
  id serial PRIMARY KEY,
  rok int NOT NULL CHECK(rok > 0),
  miesiac int CHECK(miesiac BETWEEN 1 AND 12),
  dzien int CHECK(dzien BETWEEN 1 AND 31), --TODO lepszy check na dzien
  czy_dokladna boolean NOT NULL,
  CHECK (miesiac is not null or dzien is null)
);

CREATE TABLE typy_miejsc (
  id serial PRIMARY KEY,
  nazwa varchar NOT NULL,
  nadtyp int REFERENCES typy_miejsc(id) --TODO check na cykle
);

CREATE TABLE miejsca (
  id serial PRIMARY KEY,
  nazwa varchar NOT NULL,
  nadmiejsce int REFERENCES miejsca(id), --TODO check na typ nadmiejsca
  typ_miejsca int NOT NULL REFERENCES typy_miejsc(id)
);

CREATE TABLE osoby (
  id serial PRIMARY KEY,
  imie varchar,
  pozostale_imiona varchar CHECK(imie is not null or pozostale_imiona is null),
  nazwisko_rodowe varchar,
  matka int REFERENCES osoby(id),
  ojciec int REFERENCES osoby(id),
  data_ur int REFERENCES daty(id),
  miejsce_ur int REFERENCES miejsca(id),
  wciaz_zyje boolean NOT NULL,
  miejsce_sm int REFERENCES miejsca(id),
  data_sm int REFERENCES daty(id),
  plec boolean
  --TODO  check data_ur < data_sm , własna funkcja porównująca daty
  -- check na wciaz_zyje = false, jeżeli data_sm < now()
  -- ckeck na cykle
  -- check na plec rodziców
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
  data int REFERENCES daty(id), --TODO check czy osoby wtedy żyły
  CHECK(osoba1 is not null or osoba2 is not null)
);

CREATE TABLE relacje_niesymetryczne (
  id serial PRIMARY KEY,
  osoba1 int REFERENCES osoby(id),
  osoba2 int REFERENCES osoby(id),
  typ_rns int NOT NULL REFERENCES typy_rns(id),
  miejsce int REFERENCES miejsca(id),
  data int REFERENCES daty(id), --TODO check czy osoby wtedy żyły
  CHECK(osoba1 is not null or osoba2 is not null)
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
  id serial PRIMARY KEY,
  id_osoby int NOT NULL REFERENCES osoby(id),
  id_zawodu int NOT NULL REFERENCES zawody(id),
  stanowisko varchar,
  miejsce int REFERENCES miejsca(id),
  data_od int REFERENCES daty(id),
  data_do int REFERENCES daty(id)
  --check data_od < data_do
  --check czy osoba wtedy żyła
);

CREATE TABLE tytuly (
  id serial PRIMARY KEY,
  nazwa varchar NOT NULL,
  skrot varchar,
  nadtytul int REFERENCES tytuly(id) --TODO check na cykle
);

CREATE TABLE tytuly_osoby (
  id_osoby int REFERENCES osoby(id),
  id_tytulu int REFERENCES tytuly(id),
  PRIMARY KEY (id_osoby, id_tytulu)
);

CREATE TABLE grupy (
  id serial PRIMARY KEY,
  nazwa varchar NOT NULL
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
  nazwisko varchar NOT NULL,
  data_od int REFERENCES daty(id), --TODO check czy osoba wtedy żyła
  kolejnosc int NOT NULL DEFAULT nextval('nazwiska_kolejnosc_seq'),
  PRIMARY KEY (id_osoby, nazwisko)
);

COPY daty(id, rok, miesiac, dzien, czy_dokladna) FROM stdin WITH DELIMITER ' ';
1 1899 \N \N 0
2 1921 \N \N 0
3 1949 \N \N 0
4 1956 4 \N 0
5 1925 1 \N 0
6 1935 9 19 0
7 1933 12 12 0
8 1966 7 18 1
9 1975 3 3 1
10 1983 5 5 1
11 1985 \N \N 0
12 1988 3 \N 0
13 1956 4 \N 0
14 1949 \N \N 0
15 2003 6 9 1
16 2005 1 1 1
17 2008 11 17 1
18 1010 3 3 1
19 2009 4 22 1
20 1925 1 \N 0
21 2013 11 13 1
22 2015 12 18 1
\.

COPY typy_miejsc (nazwa, nadtyp) FROM stdin WITH DELIMITER ' ';
kraj \N
miasto 1
ulica 2
dom 3
\.

COPY miejsca (nazwa, nadmiejsce, typ_miejsca) FROM stdin WITH DELIMITER ' ';
Polska \N 1
Kraków 1 2
Basztowa 2 3
8 3 4
\.

COPY osoby (id,imie,pozostale_imiona,nazwisko_rodowe,matka,ojciec,data_ur, miejsce_ur, wciaz_zyje, miejsce_sm, data_sm, plec) FROM stdin WITH DELIMITER ' ';
1 Stefania \N \N \N \N \N \N 0 \N \N 1
2 Jan \N Nowak 1 \N \N \N 0 \N 14 0
3 Genowefa \N Wilkoryj \N \N 1 \N 0 \N 13 1
4 Stanisław \N Nowak 3 2 20 \N 0 \N 19 0
5 Dorota \N Nowak 3 2 6 \N 0 \N 16 1
6 Krzysztof \N Kowal \N \N 7 \N 0 \N 21 0
7 Adam \N Kowal 5 6 9 \N 0 \N 15 0
8 Antoni \N Kowal 5 6 9 \N 0 \N 15 0
9 Konrad \N \N \N \N 11 \N 1 \N \N 0
10 Martyna Weronika Wielka \N \N 10 2 1 \N \N 1
11 Jakub Piotr Kowal 10 9 17 3 1 \N \N 0
12 Maria Martyna Kowal 10 9 22 4 1 \N \N 1
\.

COPY typy_rs (nazwa) FROM stdin WITH DELIMITER ' ';
małżeństwo
narzeczeństwo
\.

COPY typy_rns (nazwa) FROM stdin WITH DELIMITER ' ';
adopcja
\.

COPY relacje_symetryczne(osoba1, osoba2, typ_rs, miejsce, data) FROM stdin WITH DELIMITER ' ';
2 3 1 \N 2
5 6 1 2 8
9 10 2 2 18
\.

COPY relacje_niesymetryczne(osoba1, osoba2, typ_rns, miejsce, data) FROM stdin WITH DELIMITER ' ';
5 9 1 \N 12
6 9 1 \N 12
\.

COPY typy_uwag (nazwa) FROM stdin WITH DELIMITER ' ';
rozwód
\.

COPY uwagi (zawartosc, typ_uwagi) FROM stdin WITH DELIMITER ',';
Rozwód w 1990,1
Rodzina ich nie lubi,\N
\.

COPY uwagi_osoby (id_uwagi, id_osoby) FROM stdin WITH DELIMITER ' ';
1 5
1 6
\.

COPY grupy (nazwa) FROM stdin WITH DELIMITER ' ';
Bliźniaki
\.

COPY grupy_osoby(id_grupy, id_osoby) FROM stdin WITH DELIMITER ' ';
1 7
1 8
\.

COPY uwagi_grupy(id_grupy, id_uwagi) FROM stdin WITH DELIMITER ' ';
1 1
\.

COPY zawody(nazwa) FROM stdin WITH DELIMITER ',';
stolarz
informatyk
\.

COPY zawody_osoby(id_osoby, id_zawodu, stanowisko, miejsce) FROM stdin WITH DELIMITER ',';
2,1,\N,\N
9,2,senior project manager, 2
\.

COPY tytuly(nazwa, skrot, nadtytul) FROM stdin WITH DELIMITER ' ';
Ksiądz ks \N
Porucznik \N \N
Podporucznik \N 2
\.

COPY tytuly_osoby(id_osoby, id_tytulu) FROM stdin WITH DELIMITER ' ';
4 1
7 2
8 3
\.

COPY nazwiska(id_osoby, nazwisko) FROM stdin WITH DELIMITER ' ';
1 Nowak
2 Nowak
3 Nowak
4 Nowak
5 Nowak
5 Kowal
6 Kowal
7 Kowal
8 Kowal
9 Kowal
10 Kowal
11 Kowal
12 Kowal
\.

COMMIT;
