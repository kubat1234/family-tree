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
  nazwa int NOT NULL
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

COMMIT;
