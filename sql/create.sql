BEGIN;

CREATE TYPE custom_date AS (
	rok integer,
	miesiac integer,
	dzien integer,
	czy_dokladna boolean
);

CREATE OR REPLACE FUNCTION date_check(d custom_date)
RETURNS boolean
    LANGUAGE plpgsql
    AS $$
BEGIN
IF d.czy_dokladna IS NULL THEN RETURN FALSE;
ELSEIF d IS NULL THEN RETURN FALSE;
ELSEIF d.czy_dokladna IS NULL THEN RETURN FALSE;
ELSEIF d.miesiac IS NOT NULL AND d.rok IS NULL THEN RETURN FALSE;
ELSEIF d.dzien IS NOT NULL AND (d.miesiac IS NULL OR d.rok IS NULL) THEN RETURN FALSE;
ELSEIF d.dzien IS NULL AND d.czy_dokladna THEN RETURN FALSE;
ELSEIF d.dzien IS NOT NULL AND  NOT (d.dzien BETWEEN 1 AND dni_w_miesiacu(d.rok, d.miesiac)) THEN RETURN FALSE;
ELSE RETURN TRUE;
END IF;
END;
$$;

CREATE OR REPLACE FUNCTION date_comp(a custom_date, b custom_date) RETURNS boolean as $$
BEGIN
    IF a.rok IS NULL THEN RETURN TRUE;
    ELSEIF b.rok IS NULL THEN RETURN TRUE;
    ELSEIF a.rok = b.rok THEN
        IF a.miesiac IS NULL THEN RETURN TRUE;
        ELSEIF b.miesiac IS NULL THEN RETURN TRUE;
        ELSEIF a.miesiac = b.miesiac THEN
            IF a.dzien IS NULL THEN RETURN TRUE;
            ELSEIF b.dzien IS NULL THEN RETURN TRUE;
            ELSEIF a.dzien = b.dzien THEN RETURN TRUE;
            ELSE RETURN a.dzien < b.dzien;
            END IF;
        ELSE RETURN a.miesiac < b.miesiac;
        END IF;
    ELSE RETURN a.rok < b.rok;
    END IF;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION date_to_custom_date(data date) returns custom_date as $$
begin
    return (EXTRACT(YEAR FROM data),EXTRACT(MONTH FROM data),EXTRACT(DAY FROM data),true)::custom_date;
end;
$$ language plpgsql;

CREATE OPERATOR < (
LEFTARG = custom_date,
RIGHTARG = custom_date,
PROCEDURE = date_comp
);

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

CREATE OR REPLACE FUNCTION czy_osoba_zyla(osoba int, data custom_date) returns boolean as $$
begin
    return data < (select data_sm from osoby where id = osoba) and (select data_ur from osoby where id = osoba) < data;
end;
$$ language plpgsql;

CREATE TABLE typy_miejsc (
  id serial PRIMARY KEY,
  nazwa varchar NOT NULL,
  nadtyp int REFERENCES typy_miejsc(id) on delete set null --TODO check na cykle
);

CREATE TABLE miejsca (
  id serial PRIMARY KEY,
  nazwa varchar NOT NULL,
  nadmiejsce int REFERENCES miejsca(id) on delete set null, --TODO check na typ nadmiejsca
  typ_miejsca int NOT NULL REFERENCES typy_miejsc(id) on delete cascade
);

CREATE TABLE osoby (
  id serial PRIMARY KEY,
  imie varchar,
  pozostale_imiona varchar,
  nazwisko_rodowe varchar,
  matka int REFERENCES osoby(id) on delete set null,
  ojciec int REFERENCES osoby(id) on delete set null,
  data_ur custom_date not null default row(null,null,null,false) check(date_check(data_ur)),
  miejsce_ur int REFERENCES miejsca(id) on delete set null,
  wciaz_zyje boolean NOT NULL default false,
  miejsce_sm int REFERENCES miejsca(id) on delete set null,
  data_sm custom_date not null default row(null,null,null,false) check(date_check(data_sm)),
  plec boolean,
  CHECK ( data_ur < data_sm ),
  check ((date_to_custom_date(now()::date) < data_sm) or not wciaz_zyje)
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
  osoba1 int REFERENCES osoby(id) on delete set null,
  osoba2 int REFERENCES osoby(id) on delete set null,
  typ_rs int NOT NULL REFERENCES typy_rs(id) on delete cascade,
  miejsce int REFERENCES miejsca(id) on delete set null,
  data custom_date not null default row(null,null,null,false) check(date_check(data)),
  CHECK(osoba1 is not null or osoba2 is not null),
  check(czy_osoba_zyla(osoba1,data))
);

CREATE TABLE relacje_niesymetryczne (
  id serial PRIMARY KEY,
  osoba1 int REFERENCES osoby(id) on delete set null,
  osoba2 int REFERENCES osoby(id) on delete set null,
  typ_rns int NOT NULL REFERENCES typy_rns(id) on delete cascade,
  miejsce int REFERENCES miejsca(id) on delete set null,
  data custom_date not null default row(null,null,null,false) check(date_check(data)),
  CHECK(osoba1 is not null or osoba2 is not null),
  check(czy_osoba_zyla(osoba1,data) and czy_osoba_zyla(osoba2,data))
);

CREATE TABLE typy_uwag (
  id serial PRIMARY KEY,
  nazwa varchar NOT NULL
);

CREATE TABLE uwagi (
  id serial PRIMARY KEY,
  zawartosc varchar NOT NULL,
  typ_uwagi int REFERENCES typy_uwag(id) on delete set null
);

CREATE TABLE uwagi_osoby (
  id_uwagi int REFERENCES uwagi(id) on delete cascade,
  id_osoby int REFERENCES osoby(id) on delete cascade,
  PRIMARY KEY (id_uwagi, id_osoby)
);

CREATE TABLE zawody (
  id serial PRIMARY KEY,
  nazwa varchar NOT NULL
);

CREATE TABLE zawody_osoby (
  id serial PRIMARY KEY,
  id_osoby int NOT NULL REFERENCES osoby(id) on delete cascade,
  id_zawodu int NOT NULL REFERENCES zawody(id) on delete cascade,
  stanowisko varchar,
  miejsce int REFERENCES miejsca(id) on delete set null,
  data_od custom_date not null default row(null,null,null,false) check(date_check(data_od)),
  data_do custom_date not null default row(null,null,null,false) check(date_check(data_do)),
    CHECK ( data_od < data_do ),
      check(czy_osoba_zyla(id_osoby,data_do) and czy_osoba_zyla(id_osoby,data_od))
);

CREATE TABLE tytuly (
  id serial PRIMARY KEY,
  nazwa varchar NOT NULL,
  skrot varchar,
  nadtytul int REFERENCES tytuly(id) on delete set null --TODO check na cykle
);

CREATE TABLE tytuly_osoby (
  id_osoby int REFERENCES osoby(id) on delete cascade,
  id_tytulu int REFERENCES tytuly(id) on delete cascade,
  PRIMARY KEY (id_osoby, id_tytulu)
);

CREATE TABLE grupy (
  id serial PRIMARY KEY,
  nazwa varchar NOT NULL
);

CREATE TABLE grupy_osoby (
  id_grupy int REFERENCES grupy(id) on delete cascade,
  id_osoby int REFERENCES osoby(id) on delete cascade,
  PRIMARY KEY (id_grupy, id_osoby)
);

CREATE TABLE uwagi_grupy (
  id_grupy int REFERENCES grupy(id) on delete cascade,
  id_uwagi int REFERENCES uwagi(id) on delete cascade,
  PRIMARY KEY (id_grupy, id_uwagi)
);

CREATE SEQUENCE nazwiska_kolejnosc_seq
  START WITH 100
  INCREMENT BY 100;

CREATE TABLE nazwiska (
  id_osoby int REFERENCES osoby(id) on delete cascade,
  nazwisko varchar NOT NULL,
  data_od custom_date not null default row(null,null,null,false) check(date_check(data_od)),
  data_do custom_date not null default row(null,null,null,false) check(date_check(data_do)),
  kolejnosc int NOT NULL DEFAULT nextval('nazwiska_kolejnosc_seq'),
  PRIMARY KEY (id_osoby, nazwisko),
    check(czy_osoba_zyla(id_osoby,data_od) and czy_osoba_zyla(id_osoby,data_do)),
    check(data_od < data_do)
);

--Dokumenty

create table typy_dokumentow(
	id serial primary key,
	nazwa VARCHAR
);

create table instytucje(
	id serial primary key,
	nazwa VARCHAR
);

create table dokumenty(
	id serial primary key,
	name varchar(50),
	osoba INTEGER references OSOBY(id),
	data_zawarcia custom_date not null default row(null,null,null,false) check(date_check(data_zawarcia)),
	miejsce_zawarcia INTEGER references miejsca(id),
	instytucja INTEGER references instytucje(id),
	typy_dokumentow INTEGER references typy_dokumentow(id)
);

create table swiadkowie(
	osoba INTEGER references OSOBY(id),
	dokument INTEGER references DOKUMENTY(id),
	primary key(osoba, dokument)
);

-- Funckje odpowiedzialnie za spójność tablicy osoby

CREATE OR REPLACE FUNCTION check_if_ancestor(child OSOBY, ancestor OSOBY)
RETURNS BOOLEAN AS $$
BEGIN
	IF ancestor.id = child.id THEN 
		RETURN true;
	END IF;
    IF child.matka IS NOT NULL 
    THEN 
		IF check_if_ancestor((
			SELECT o
			FROM osoby o
			WHERE o.id = child.matka
		)::osoby, ancestor)
		THEN 
			RETURN true;
		END IF;
    END IF;
	IF child.ojciec IS NOT NULL 
    THEN 
		IF check_if_ancestor((
			SELECT o
			FROM osoby o
			WHERE o.id = child.ojciec
		)::osoby, ancestor)
		THEN 
			RETURN true;
		END IF;
    END IF;
	RETURN false;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION check_if_ancestor_update() 
RETURNS trigger as $$
BEGIN
	IF NEW.matka IS NOT NULL 
    THEN 
		IF check_if_ancestor((
			SELECT os
			FROM osoby os
			where os.id = NEW.matka
		)::osoby, NEW)
		THEN 
			RAISE EXCEPTION 'TRY TO MODIFY PERSON - GET CYCLE'; 
			RETURN OLD;
		END IF;
    END IF;
	IF NEW.ojciec IS NOT NULL 
    THEN 
		IF check_if_ancestor((
			SELECT os
			FROM osoby os
			where os.id = NEW.ojciec
		)::osoby, NEW)
		THEN 
			RAISE EXCEPTION 'TRY TO MODIFY PERSON - GET CYCLE'; 
			RETURN OLD;
		END IF;
    END IF;
	RETURN NEW;
END;
$$ LANGUAGE plpgsql;

create or replace trigger dobry_poset before update
on OSOBY for each row execute procedure check_if_ancestor_update();

-- Funckje odpowiedzialnie za spójność tablicy tytuly

CREATE OR REPLACE FUNCTION check_if_nadtytul(child tytuly, ancestor tytuly)
RETURNS BOOLEAN AS $$
BEGIN
	IF ancestor.id = child.id THEN 
		RETURN true;
	END IF;
    IF child.nadtytul IS NOT NULL 
    THEN 
		IF check_if_ancestor((
			SELECT t
			FROM tytuly t
			WHERE t.id = child.nadtytul
		)::tytuly, ancestor)
		THEN 
			RETURN true;
		END IF;
    END IF;
	RETURN false;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION check_if_nadtytul_update() 
RETURNS trigger as $$
BEGIN
	IF NEW.nadtytul IS NOT NULL 
    THEN 
		IF check_if_nadtytul(
			(
				SELECT t
				FROM tytuly t
				WHERE t.id = NEW.nadtytul
			)::tytuly, NEW)
		THEN 
			RETURN OLD;
		END IF;
    END IF;
END;
$$ LANGUAGE plpgsql;

create or replace trigger dobry_poset_tytuly before update
on TYTULY for each row execute procedure check_if_nadtytul_update();

create or replace view praca as
select o.imie, o.nazwisko_rodowe, z.nazwa, zo.stanowisko
from osoby o inner join zawody_osoby zo on o.id = zo.id_osoby inner join zawody z on zo.id_zawodu = z.id
order by 3, 4, 1, 2;

-- Widok osoby_nazwiska

create or replace view osoby_nazwiska as
select osoby.*, string_agg(coalesce(nazwiska.nazwisko,''),' ' order by nazwiska.kolejnosc)
filter (where date_to_custom_date(now()::date) < data_do and data_od < date_to_custom_date(now()::date)) as "nazwiska"
from osoby left outer join nazwiska on osoby.id = nazwiska.id_osoby
group by osoby.id;

create rule osoby_nazwiska_insert as
on insert to osoby_nazwiska do instead
(
    insert into osoby values (new.id,
                              new.imie,
                              new.pozostale_imiona,
                              new.nazwisko_rodowe,
                              new.matka,new.ojciec,
                              coalesce(new.data_ur,(null,null,null,false)::custom_date),
                              new.miejsce_ur,
                              coalesce(new.wciaz_zyje,false),
                              new.miejsce_sm,
                              coalesce(new.data_sm,(null,null,null,false)::custom_date),
                              new.plec);
    insert into nazwiska(id_osoby, nazwisko)
    select new.id, unnest(string_to_array(new.nazwiska,' '));
);

create rule osoby_nazwiska_update as
    on update to osoby_nazwiska do instead
    (
    update osoby set (imie,pozostale_imiona,nazwisko_rodowe,matka,ojciec,data_ur,miejsce_ur,wciaz_zyje,miejsce_sm,data_sm,plec)
        = (new.imie,
          new.pozostale_imiona,
          new.nazwisko_rodowe,
          new.matka,new.ojciec,
          coalesce(new.data_ur,(null,null,null,false)::custom_date),
          new.miejsce_ur,
          coalesce(new.wciaz_zyje,false),
          new.miejsce_sm,
          coalesce(new.data_sm,(null,null,null,false)::custom_date),
          new.plec)
    where id = new.id;
    delete from nazwiska where id_osoby = new.id;
    insert into nazwiska(id_osoby, nazwisko)
    select new.id, unnest(string_to_array(new.nazwiska,' '));
    );

-- checki na miejsca

CREATE OR REPLACE FUNCTION check_if_nadmiejsce(child miejsca, ancestor miejsca)
RETURNS BOOLEAN AS
$$
BEGIN
    IF ancestor.id = child.id THEN
        RETURN true;
    END IF;
    IF child.nadmiejsce IS NOT NULL
    THEN
        IF check_if_nadmiejsce((
            SELECT z
            FROM miejsca z
            WHERE z.id = child.nadmiejsce
            )::miejsca, ancestor)
        THEN
            RETURN true;
        END IF;
    END IF;
    RETURN false;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION check_if_nadmiejsce_update()
RETURNS trigger AS
$$
BEGIN
    IF NEW.nadmiejsce IS NULL
    THEN RETURN NEW;
    END IF;
    IF check_if_nadmiejsce((
        SELECT z
        FROM miejsca z
        WHERE z.id = NEW.nadmiejsce
        )::miejsca, NEW)
    THEN
        RAISE EXCEPTION 'UNABLE TO MODIFY PLACE - CYCLE DETECTED';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER dobry_poset_miejsca BEFORE UPDATE
ON miejsca FOR EACH ROW EXECUTE PROCEDURE
check_if_nadmiejsce_update();

-- checki na typy miejsc

CREATE OR REPLACE FUNCTION check_if_nadtyp_m(child typy_miejsc, ancestor typy_miejsc)
RETURNS BOOLEAN AS
$$
BEGIN
    IF ancestor.id = child.id THEN
        RETURN true;
    END IF;
    IF child.nadtyp IS NOT NULL
    THEN
        IF check_if_nadtyp_m((
            SELECT z
            FROM typy_miejsc z
            WHERE z.id = child.nadtyp
            )::typy_miejsc, ancestor)
        THEN
            RETURN true;
        END IF;
    END IF;
    RETURN false;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION check_if_nadtyp_m_update()
RETURNS trigger AS
$$
BEGIN
    IF NEW.nadtyp IS NULL
    THEN RETURN NEW;
    END IF;
    IF check_if_nadtyp_m((
        SELECT z
        FROM typy_miejsc z
        WHERE z.id = NEW.nadtyp
        )::typy_miejsc, NEW)
    THEN
        RAISE EXCEPTION 'UNABLE TO MODIFY PLACE TYPE - CYCLE DETECTED';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER dobry_poset_typy_miejsc BEFORE UPDATE
ON typy_miejsc FOR EACH ROW EXECUTE PROCEDURE
check_if_nadtyp_m_update();

---- powiązanie miejsca i typu

CREATE OR REPLACE FUNCTION check_if_miejsce_matches_typ()
RETURNS TRIGGER AS
$$
DECLARE
second_type INT;
BEGIN
    IF (NEW.typ_miejsca IS NULL) OR (NEW.nadmiejsce IS NULL)
        THEN RETURN NEW;
    END IF;
    second_type = (SELECT typ_miejsca FROM miejsca
        WHERE id = NEW.nadmiejsce
        LIMIT 1
    );
    IF (second_type != NEW.typ_miejsca) AND (
        check_if_nadtyp_m((
        SELECT z
        FROM typy_miejsc z
        WHERE id = NEW.typ_miejsca),
        (SELECT z
        FROM typy_miejsc z
        WHERE id = second_type)
        )
    ) THEN RETURN NEW;
    END IF;
    RAISE EXCEPTION 'UNABLE TO MODIFY/CREATE PLACE - THE SUPERPLACE IS NOT OF SUPERTYPE';
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER miejsce_typ_trigger BEFORE UPDATE OR INSERT
ON miejsca FOR EACH ROW EXECUTE PROCEDURE
check_if_miejsce_matches_typ();

create rule osoby_nazwiska_delete as
on delete to osoby_nazwiska do instead
delete from osoby where id=old.id;

-- funkcja odpowiedzialna za bezpieczne usuwanie osoby
CREATE OR REPLACE FUNCTION safe_person_delete() returns trigger as $$
begin
    delete from relacje_niesymetryczne where (osoba1 = old.id and osoba2 is null) or (osoba2 = old.id and osoba1 is null);
    delete from relacje_symetryczne where (osoba1 = old.id and osoba2 is null) or (osoba2 = old.id and osoba1 is null);
    return old;
end;
$$ language plpgsql;

create trigger safe_person_delete before delete on osoby
for each row execute procedure safe_person_delete();

-- nie modyfikujemy id

CREATE OR REPLACE FUNCTION immutable_id() RETURNS TRIGGER AS
$$
BEGIN
	IF NEW.id != OLD.id
	THEN RAISE EXCEPTION 'CANNOT CHANGE ID OF EXISTING OBJECT';
	END IF;
	RETURN NEW;
END;
$$ language plpgsql;
CREATE OR REPLACE TRIGGER grupy_immutable_id BEFORE UPDATE
ON grupy FOR EACH ROW EXECUTE PROCEDURE immutable_id();
CREATE OR REPLACE TRIGGER miejsca_immutable_id BEFORE UPDATE
ON miejsca FOR EACH ROW EXECUTE PROCEDURE immutable_id();
CREATE OR REPLACE TRIGGER nazwiska_immutable_id BEFORE UPDATE
ON nazwiska FOR EACH ROW EXECUTE PROCEDURE immutable_id();
CREATE OR REPLACE TRIGGER osoby_immutable_id BEFORE UPDATE
ON osoby FOR EACH ROW EXECUTE PROCEDURE immutable_id();
CREATE OR REPLACE TRIGGER relacje_niesymetryczne_immutable_id BEFORE UPDATE
ON relacje_niesymetryczne FOR EACH ROW EXECUTE PROCEDURE immutable_id();
CREATE OR REPLACE TRIGGER relacje_symetryczne_immutable_id BEFORE UPDATE
ON relacje_symetryczne FOR EACH ROW EXECUTE PROCEDURE immutable_id();
CREATE OR REPLACE TRIGGER typy_miejsc_immutable_id BEFORE UPDATE
ON typy_miejsc FOR EACH ROW EXECUTE PROCEDURE immutable_id();
CREATE OR REPLACE TRIGGER typy_rns_immutable_id BEFORE UPDATE
ON typy_rns FOR EACH ROW EXECUTE PROCEDURE immutable_id();
CREATE OR REPLACE TRIGGER typy_rs_immutable_id BEFORE UPDATE
ON typy_rs FOR EACH ROW EXECUTE PROCEDURE immutable_id();
CREATE OR REPLACE TRIGGER typy_uwag_immutable_id BEFORE UPDATE
ON typy_uwag FOR EACH ROW EXECUTE PROCEDURE immutable_id();
CREATE OR REPLACE TRIGGER tytuly_immutable_id BEFORE UPDATE
ON tytuly FOR EACH ROW EXECUTE PROCEDURE immutable_id();
CREATE OR REPLACE TRIGGER uwagi_immutable_id BEFORE UPDATE
ON uwagi FOR EACH ROW EXECUTE PROCEDURE immutable_id();
CREATE OR REPLACE TRIGGER zawody_immutable_id BEFORE UPDATE
ON zawody FOR EACH ROW EXECUTE PROCEDURE immutable_id();

-- indeksy

create index idx_osoby_matka on osoby(matka);
create index idx_osoby_ojciec on osoby(ojciec);
create index idx_rs1 on relacje_symetryczne(osoba1);
create index idx_rs2 on relacje_symetryczne(osoba2);
create index idx_rns1 on relacje_niesymetryczne(osoba1);
create index idx_rns2 on relacje_niesymetryczne(osoba2);
create index idx_nazwiska_id on nazwiska(id_osoby);

--przykładowe dane

COPY typy_miejsc (nazwa, nadtyp) FROM stdin WITH DELIMITER ' ';
kraj \N
województwo 1
miasto 2
dzielnica 3
ulica 4
dom 5
mieszkanie 6
\.

COPY miejsca (nazwa, nadmiejsce, typ_miejsca) FROM stdin WITH DELIMITER ' ';
Polska \N 1
Czechy \N 1
Kraków 1 2
Toruń 1 2
Tymbark \N 2
Wrocław \N 2
Praga 2 2
Ruczaj 3 3
Nowa\ Huta 3 3
Basztowa 3 4
al.\ Mickiewicza 3 4
Kopernika 4 4
Łojasiewicza 3 4
Akacjowa 9 4
8 10 5
6 13 5
4 13 5
100 14 5
1000 5 5
5 18 6
2 15 6
\.

COPY zawody(nazwa) from stdin;
Lekarz
Nauczyciel
Inżynier
Piekarz
Mechanik
Prawnik
Kierowca
Strażak
Policjant
Programista
Fryzjer
Kucharz
Pielęgniarka
Architekt
Księgowy
Ogrodnik
Stolarz
Fotograf
Weterynarz
Bibliotekarz
\.

COPY typy_rs (nazwa) FROM stdin WITH DELIMITER ',';
małżeństwo
narzeczeństwo
związek partnerski
przyjaźń
partnerstwo biznesowe
współlokatorstwo
\.

COPY typy_rns (nazwa) FROM stdin WITH DELIMITER ',';
adopcja
rodzic zastępczy
opiekun prawny
kurator
pracodawca
przełożony
wychowawca
mentor
\.

COPY osoby (imie, pozostale_imiona, nazwisko_rodowe, data_ur, data_sm, plec, matka, ojciec, wciaz_zyje, miejsce_ur, miejsce_sm) FROM stdin WITH (FORMAT csv, DELIMITER ';', NULL '');
Alicja;Sandra Hanna;Sadowski;(1825,8,1,true);(1870,6,7,true);false;;;false;;10
Oliwia;Ewa Olga;Kowalski;(1827,10,16,true);(1832,3,,false);false;;;false;;11
Zdzisław;;Czarnecki;(1826,5,,false);(1883,10,,false);true;;;false;;14
Grażyna;;Sikora;(1829,9,,false);(1904,1,3,true);false;;;false;4;
Ursyn;Zdzisław;Kamiński;(1829,,,false);(1916,4,21,true);true;;;false;;17
Agnieszka;Joanna Roksana;Sienkiewicz;(1830,4,,false);(1895,10,,false);false;;;false;;9
Sławek;Hubert;Wójcik;(1833,7,3,true);(1852,7,14,true);true;;;false;8;14
Leon;;Sikora;(,,,false);(1904,,,false);true;;;false;17;
Natalia;Grażyna;Jankowski;(1836,,,false);(,,,false);false;;;false;20;3
Weronika;Iwona Karolina;Kozłowski;(,,,false);(1918,,,false);false;;;false;;
Kamil;;Bąk;(,,,false);(,,,false);true;;;false;15;
Leon;Zdzisław;Bielecki;(1839,6,,false);(1854,,,false);true;;;false;7;
Cezary;Radek;Wojciechowski;(,,,false);(1894,1,,false);true;1;;false;;
Jakub;Cezary;Czarnecki;(1842,10,,false);(1875,,,false);true;;;false;;18
Wiktor;;Sienkiewicz;(,,,false);(1887,5,4,true);true;1;3;false;15;16
Joanna;;Wysocki;(,,,false);(1861,8,14,false);false;4;;false;;12
Oliwia;Danuta;Sikora;(1845,1,,false);(,,,false);false;6;;false;;19
Tomasz;Franciszek;Kozłowski;(,,,false);(1906,1,1,false);true;1;3;false;20;
Jadwiga;Weronika;Kamiński;(1848,,,false);(1939,8,6,true);false;;5;false;;
Wiktor;Zenon Wiktor;Król;(1851,11,5,true);(1940,1,22,true);true;6;5;false;2;
Jadwiga;;Wojciechowski;(1850,8,22,true);(1909,11,,false);false;10;;false;;
Norbert;Maciej;Kopacz;(1852,7,,false);(1918,9,,false);true;;3;false;13;
Sławek;Bartosz;Wójcik;(1854,,,false);(1864,,,false);true;9;;false;11;2
Tomasz;Szymon;Klimczak;(,,,false);(1873,,,false);true;10;;false;6;
Damian;;Lewandowski;(1856,4,7,true);(,,,false);true;10;8;false;;
Zenon;Maciej;Zalewski;(,,,false);(1928,4,,false);true;1;;false;14;16
Łukasz;Igor;Kozłowski;(1859,2,,false);(,,,false);true;;8;false;;
Adam;;Krawczyk;(1861,,,false);(1900,,,false);true;;15;false;4;13
Celina;;Sikora;(1861,7,,false);(1939,6,19,true);false;;3;false;9;
Igor;;Klimczak;(1864,11,17,true);(1868,1,18,true);true;10;8;false;;
Radek;;Król;(1866,3,16,false);(1898,,,false);true;6;5;false;;1
Celina;Roksana Karolina;Wysocki;(1866,,,false);(1906,3,,false);false;17;11;false;;
Magdalena;;Król;(1868,3,,false);(1948,10,,false);false;;22;false;;
Jadwiga;Ewa;Bąk;(1868,2,,false);(1891,2,,false);false;19;22;false;;
Adam;;Kowalski;(,,,false);(1891,6,,false);true;10;8;false;2;15
Renata;;Kaczmarek;(1871,2,,false);(1952,,,false);false;21;18;false;;12
Michał;;Sikorski;(1874,,,false);(1928,3,,false);true;17;;false;3;14
Natalia;Elżbieta;Kowalik;(1874,10,20,true);(1971,7,6,true);false;9;;false;;8
Tomasz;Wojciech Franciszek;Kowalczyk;(1875,,,false);(1884,4,,false);true;10;8;false;1;19
Tomasz;Witold;Kowalczyk;(1877,,,false);(1936,,,false);true;29;27;false;2;
Zbigniew;Igor;Sienkiewicz;(1879,6,18,true);(1900,10,22,true);true;;28;false;;20
Franciszek;Hubert;Lewandowski;(,,,false);(1923,11,13,true);true;19;22;false;;
Sławek;Kamil;Sadowski;(1881,2,19,true);(1939,,,false);true;6;5;false;;15
Maciej;;Kaczmarek;(1882,,,false);(,,,false);true;33;;false;;6
Danuta;Karolina;Kowalski;(,,,false);(1935,9,25,true);false;19;22;false;9;18
Zbigniew;;Zieliński;(1885,11,11,true);(1968,11,,false);true;33;20;false;15;
Szymon;Kamil;Wysocki;(1885,8,,false);(1928,,,false);true;;15;false;21;3
Sylwia;Franciszka;Walczak;(,,,false);(1889,3,,false);false;21;18;false;;11
Beata;Elżbieta;Kucharski;(,,,false);(1897,3,,false);false;19;22;false;5;
Bartosz;;Kamiński;(,,,false);(1939,,,false);true;32;28;false;18;12
Jakub;Szymon Olek;Walczak;(1891,3,,false);(1932,11,,false);true;10;8;false;12;
Alicja;;Zawadzki;(1894,,,false);(1936,,,false);false;29;27;false;5;7
Grzegorz;Kamil Eryk;Klimczak;(1893,9,,false);(1955,10,4,true);true;29;27;false;7;17
Bartosz;Witold;Zalewski;(1895,1,20,true);(1904,,,false);true;19;22;false;;
Jadwiga;Karolina;Kowalski;(1896,,,false);(1949,,,false);false;29;27;false;9;18
Teresa;Zofia;Majewski;(1897,7,,false);(1982,4,,false);false;36;26;false;2;15
Franciszka;;Wójcik;(1898,,,false);(1941,,,false);false;19;22;false;;3
Witold;;Zalewski;(1902,6,26,true);(1910,1,27,true);true;;42;false;;6
Sylwia;Magdalena Gabriela;Kowal;(1902,2,5,true);(1972,,,false);false;36;26;false;;
Szymon;Maciej;Kowalczyk;(,,,false);(1958,,,false);true;21;18;false;;10
Lidia;Jadwiga;Wysocki;(1905,9,21,false);(1940,8,,false);false;33;;false;;
Kacper;Szymon;Szymański;(1905,8,7,true);(1994,1,5,true);true;19;22;false;;
Danuta;Hanna;Krawczyk;(1907,11,12,true);(1918,10,9,true);false;33;;false;;
Ewa;;Grabowski;(1908,3,4,true);(1984,3,,false);false;33;42;false;3;
Cecylia;;Mazurek;(1910,4,20,true);(1970,4,24,true);false;;47;false;21;6
Alicja;Halina;Bielecki;(1910,10,,false);(1962,8,1,true);false;;50;false;11;6
Felicja;;Majewski;(1914,6,,false);(2006,7,16,true);false;52;40;false;14;2
Kamil;Wiktor;Sikora;(1915,7,5,true);(1935,11,,false);true;55;51;false;;8
Filip;Marek;Krawczyk;(1914,6,13,false);(1973,,,false);true;19;22;false;5;
Igor;;Czarnecki;(1918,2,,false);(1949,,,false);true;52;40;false;2;
Dorota;Iwona Jadwiga;Kozłowski;(1918,2,,false);(2007,3,,false);false;56;60;false;1;21
Rafał;Kacper Maciej;Mazurek;(1920,4,10,true);(2004,5,8,true);true;;51;false;18;
Sandra;Jadwiga;Grabowski;(1920,10,,false);(1941,,,false);false;59;51;false;8;20
Oskar;;Zieliński;(1921,7,,false);(1970,11,8,false);true;57;50;false;15;11
Danuta;Barbara;Kowal;(1923,4,,false);(2001,4,,false);false;33;20;false;;13
Danuta;Oliwia;Kowalski;(1926,,,false);(2013,11,19,false);false;56;60;false;1;13
Wanda;Zuzanna Halina;Kopacz;(1925,,,false);(1994,9,6,true);false;52;40;false;12;
Olek;;Witkowski;(1927,,,false);(1933,,,false);true;64;;false;4;15
Marta;Halina;Czajka;(1929,3,,false);(1948,,,false);false;57;50;false;;
Urszula;Ewa Olga;Lewandowski;(1930,,,false);(2004,6,,false);false;61;62;false;;8
Paulina;Tatiana;Sikorski;(1932,11,,false);(2012,3,15,true);false;57;50;false;;16
Iwona;Alicja Hanna;Zawadzki;(1932,,,false);(2020,7,9,true);false;59;;false;8;
Karolina;Gabriela;Lewandowski;(1935,7,,false);(2007,10,,false);false;65;;false;;20
Joanna;Katarzyna;Kowalik;(1936,7,5,true);(1980,9,16,true);false;33;20;false;7;
Tadeusz;;Klimczak;(1936,5,11,true);(2004,3,23,true);true;56;53;false;8;3
Tomasz;Piotr;Kucharski;(1938,10,6,true);(1959,,,false);true;65;;false;;11
Joanna;;Kowalski;(1939,9,3,true);(1965,,,false);false;56;60;false;;6
Irmina;Wanda;Witkowski;(1942,1,,false);(2010,4,24,false);false;67;70;false;;1
Barbara;;Czarnecki;(1941,8,,false);(2014,10,,false);false;66;69;false;;
Adam;;Król;(1942,,,false);(2004,4,,false);true;76;;false;4;20
Dorota;;Kowalewicz;(1946,2,7,true);(,,,false);false;56;60;true;6;6
Felicja;;Kamiński;(1947,,,false);(1992,11,,false);false;56;60;false;20;1
Piotr;Marek;Kowalewicz;(1947,,,false);(1983,2,9,true);true;81;;false;7;10
Sylwia;Teresa Sandra;Kowalski;(1948,7,17,true);(2020,6,19,true);false;67;70;false;;12
Eryk;Wiktor;Pawlak;(1951,3,7,false);(,,,false);true;56;60;true;15;18
Olga;;Kowalewski;(1951,,,false);(1976,2,17,false);false;76;;false;;
Cezary;Filip;Pietrzak;(1952,,,false);(2004,10,21,true);true;56;60;false;14;
Bartosz;;Sienkiewicz;(1953,6,,false);(2020,3,2,false);true;76;;false;;7
Irmina;Olga;Majewski;(1955,10,13,true);(1988,8,8,true);false;81;;false;1;
Olga;;Wójcik;(1956,1,13,true);(1995,7,,false);false;82;;false;;
Krzysztof;Mikołaj;Wójcik;(1957,3,11,true);(1964,2,27,true);true;76;;false;;7
Magdalena;;Wojtas;(1958,1,7,true);(1969,1,7,true);false;;85;false;;2
Cecylia;Roksana;Wojciechowski;(1960,7,,false);(,,,false);false;;74;true;;8
Hanna;Cecylia;Kozłowski;(1961,4,,false);(1983,10,6,true);false;66;69;false;;
Łukasz;;Czarnecki;(1962,,,false);(2004,9,,false);true;;93;false;13;
Grzegorz;;Król;(1964,11,,false);(2017,4,3,true);true;83;72;false;;
Witold;Kacper;Wojtas;(1967,2,,false);(1990,11,16,true);true;83;72;false;20;
Radek;Jakub Łukasz;Jankowski;(1967,,,false);(,,,false);true;71;72;true;9;
Filip;Eryk;Nowak;(1969,6,1,true);(,,,false);true;;90;true;;15
Zofia;Ewa;Lewandowski;(1970,6,,false);(,,,false);false;83;72;true;16;15
Ewa;;Kowalewski;(1970,6,,false);(,,,false);false;84;95;true;;13
Leon;Krzysztof Krzysztof;Czajka;(1973,4,1,true);(,,,false);true;92;98;true;20;
Lidia;;Szymański;(1975,,,false);(1991,11,4,true);false;82;;false;8;
Hanna;Franciszka;Czajka;(1975,4,24,true);(2020,6,25,true);false;71;72;false;1;19
Eryk;;Borkowski;(1976,2,19,true);(2010,2,,false);true;82;90;false;15;2
Leon;Eryk;Zawadzki;(1977,5,,false);(2002,11,22,true);true;92;98;false;;19
Nikodem;Łukasz;Sikorski;(1979,3,2,true);(2016,1,,false);true;92;98;false;18;4
Igor;Norbert Wojciech;Kucharski;(1980,6,,false);(2019,2,7,true);true;88;93;false;;1
Paulina;;Majewski;(1981,7,,false);(,,,false);false;89;97;true;;
Tomasz;;Wiśniewski;(1984,3,22,false);(,,,false);true;83;72;true;16;18
Piotr;Marek;Dąbrowski;(1986,1,16,true);(,,,false);true;;97;true;6;13
Sandra;Roksana;Szymański;(1985,7,,false);(,,,false);false;91;;true;;
Nikodem;Dawid;Wojtas;(1986,10,27,true);(1997,5,,false);true;99;85;false;;12
Zdzisław;;Walczak;(1989,5,15,false);(2022,3,25,true);true;89;97;false;4;
Kacper;Dawid;Kozłowski;(1991,6,16,true);(,,,false);true;114;115;true;;6
Tomasz;Maciej Eryk;Kaczmarek;(1990,11,27,true);(,,,false);true;83;72;true;7;
Artur;Franciszek;Zawadzki;(1994,9,8,true);(,,,false);true;;106;true;11;
Franciszek;;Borkowski;(1993,5,24,true);(2016,4,,false);true;111;112;false;15;
Kacper;;Kowalczyk;(1995,4,,false);(,,,false);true;76;85;true;13;
Karolina;;Pawlak;(1998,10,,false);(2010,5,,false);false;89;97;false;4;3
Patrycja;;Czarnecki;(1997,3,,false);(,,,false);false;114;115;true;8;19
Eryk;Mikołaj;Mazurek;(1999,1,6,true);(,,,false);true;110;118;true;20;
Zofia;Magdalena Elżbieta;Pawlak;(2001,5,,false);(,,,false);false;;120;true;10;5
Olek;;Kopacz;(2001,7,,false);(,,,false);true;103;108;true;;9
Elżbieta;;Kowalik;(2004,10,23,false);(,,,false);false;;124;true;;
Oliwia;Cecylia;Witkowski;(2005,8,27,true);(,,,false);false;114;115;true;16;1
Mikołaj;;Majewski;(2006,7,14,true);(,,,false);true;119;109;true;;14
Karolina;Jadwiga Zofia;Krawczyk;(2006,1,,false);(2017,2,25,true);false;;121;false;13;2
Grażyna;Dorota;Sienkiewicz;(2008,3,6,true);(,,,false);false;103;108;true;;
Ewa;;Kaczmarek;(2011,3,,false);(,,,false);false;122;121;true;;
Felicja;;Wiśniewski;(2011,7,,false);(,,,false);false;119;109;true;;
Igor;;Nowak;(2012,5,8,true);(,,,false);true;122;;true;4;
Wiktor;;Zieliński;(2014,9,12,true);(,,,false);true;110;118;true;13;
Igor;Maciej;Zawadzki;(2014,10,,false);(,,,false);true;;129;true;21;
Ewa;Hanna;Bąk;(2016,2,13,true);(,,,false);false;;120;true;17;3
Halina;;Kowal;(2017,9,10,true);(,,,false);false;119;109;true;13;12
Celina;Katarzyna Magdalena;Kucharski;(2019,8,7,true);(,,,false);false;131;;true;;
Marek;;Zawadzki;(2021,7,5,true);(,,,false);true;131;;true;;
Roksana;Sylwia;Błaszczyk;(2022,4,6,true);(,,,false);false;111;112;true;5;20
Piotr;Dawid;Kucharski;(2022,3,4,true);(,,,false);true;111;112;true;;
\.
COPY relacje_symetryczne (osoba1, osoba2, typ_rs, data, miejsce) from stdin with (format csv, delimiter ';',null '');
1;3;1;(,,,false);20
7;9;1;(1851,11,13,true);
8;10;1;(,,,false);
15;16;1;(1860,5,9,true);
19;22;1;(,,,false);5
27;29;1;(1876,7,1,false);
28;32;1;(1881,,,false);19
20;33;1;(,,,false);10
31;34;1;(,,,false);
34;35;2;(1886,3,,false);2
26;36;1;(1886,5,,false);21
38;41;1;(,,,false);
33;42;1;(1893,3,26,true);5
31;45;6;(1897,10,24,false);6
40;52;1;(1909,8,26,true);1
51;55;1;(1911,,,false);20
53;56;1;(1912,,,false);2
61;62;1;(1920,3,14,true);
66;69;1;(1929,2,23,true);
67;70;1;(1933,8,16,true);
71;72;1;(1935,9,,false);3
74;79;1;(1944,,,false);
72;83;1;(1950,,,false);
76;85;1;(1951,1,,false);5
82;90;1;(1957,9,,false);
88;93;1;(1962,2,,false);21
84;95;1;(1966,6,12,true);
89;97;1;(1967,9,,false);1
92;98;6;(1968,5,,false);2
85;99;1;(1970,11,,false);19
104;106;1;(1979,9,,false);7
94;107;1;(1982,2,,false);18
103;108;1;(1982,2,,false);
114;115;1;(1991,2,,false);
110;118;1;(1995,11,,false);4
121;122;1;(2001,1,25,true);7
124;131;1;(2012,4,,false);
125;133;1;(2016,6,,false);21
129;135;2;(2019,3,5,false);3
\.
copy nazwiska (id_osoby, nazwisko, data_od, data_do) from stdin with (format csv, delimiter ';',null '');
2;Wójcik;(,,,f);(,,,f)
3;Lewandowski;(1829,,,false);(1876,4,,false)
3;Kopacz;(1869,,,false);(,,,f)
6;Wysocki;(1875,8,,false);(,,,f)
6;Wojtas;(,,,false);(,,,false)
7;Bąk;(1838,,,false);(,,,f)
7;Klimczak;(1847,3,22,true);(,,,f)
9;Wysocki;(1872,6,,false);(,,,f)
10;Witkowski;(1853,2,18,false);(1900,1,13,true)
10;Czarnecki;(1900,5,,false);(,,,f)
11;Szymański;(1849,11,,false);(1880,,,false)
11;Kowalewski;(,,,false);(,,,f)
12;Sikorski;(1844,3,21,true);(,,,f)
14;Piotrowski;(,,,f);(,,,f)
14;Wójcik;(1866,7,,false);(,,,f)
18;Szymański;(1874,5,11,true);(,,,f)
18;Czajka;(1872,6,,false);(1886,,,false)
20;Kopacz;(1866,6,10,true);(,,,false)
20;Kucharski;(1878,7,,false);(,,,f)
21;Krawczyk;(1858,7,3,false);(1893,1,,false)
23;Krawczyk;(1857,11,,false);(,,,f)
23;Kowalski;(1858,,,false);(,,,f)
25;Wiśniewski;(,,,f);(,,,f)
26;Piotrowski;(1883,,,false);(1911,7,,false)
26;Król;(,,,f);(,,,f)
27;Dąbrowski;(1892,11,21,true);(,,,f)
27;Pietrzak;(,,,false);(,,,f)
30;Sikorski;(,,,f);(,,,f)
30;Nowak;(,,,f);(,,,f)
31;Pawlak;(,,,f);(,,,f)
33;Wiśniewski;(,,,f);(,,,f)
35;Wojciechowski;(1881,,,false);(,,,f)
35;Krawczyk;(,,,f);(,,,f)
36;Pietrzak;(1896,7,18,true);(1944,9,12,false)
37;Sadowski;(1879,10,2,false);(1886,3,,false)
37;Klimczak;(,,,false);(,,,f)
39;Wojtas;(,,,f);(,,,f)
39;Borkowski;(1881,,,false);(,,,f)
40;Zieliński;(1893,6,11,true);(,,,f)
42;Król;(1902,3,,false);(,,,f)
42;Grabowski;(1890,1,,false);(1915,,,false)
43;Majewski;(1909,3,,false);(1921,2,,false)
43;Czajka;(1924,10,,false);(,,,f)
44;Wysocki;(1889,,,false);(,,,f)
45;Majewski;(1913,9,,false);(1930,6,5,true)
46;Kaczmarek;(1903,8,,false);(,,,f)
46;Zawadzki;(1950,6,15,true);(,,,f)
47;Sikorski;(1890,,,false);(,,,f)
48;Kozłowski;(,,,f);(,,,f)
50;Grabowski;(,,,f);(,,,f)
50;Sikora;(1926,9,,false);(,,,f)
51;Kamiński;(1925,6,,false);(,,,f)
52;Mazurek;(1897,7,14,true);(1930,5,,false)
52;Piotrowski;(,,,f);(,,,f)
54;Lewandowski;(,,,f);(,,,f)
54;Kozłowski;(,,,f);(,,,f)
55;Pawlak;(1943,,,false);(,,,f)
56;Mazurek;(1974,7,22,false);(,,,f)
57;Zalewski;(1938,,,false);(,,,f)
57;Kozłowski;(1902,7,1,true);(,,,f)
59;Kowalik;(1931,8,1,true);(1966,,,false)
59;Bąk;(1948,,,false);(,,,f)
61;Dąbrowski;(1933,,,false);(,,,f)
61;Sadowski;(1929,,,false);(,,,f)
62;Wysocki;(1943,6,,false);(,,,f)
62;Zalewski;(1959,,,false);(,,,f)
63;Kowalewski;(,,,f);(,,,f)
63;Bąk;(1915,11,12,true);(,,,f)
65;Zieliński;(1938,11,3,true);(1949,,,false)
67;Walczak;(1934,1,,false);(,,,f)
69;Zawadzki;(1923,5,24,true);(1966,9,4,false)
72;Wiśniewski;(1928,7,20,true);(1961,10,10,true)
73;Wójcik;(1934,10,25,true);(,,,f)
73;Sienkiewicz;(1930,2,21,true);(,,,f)
74;Kaczmarek;(1937,11,,false);(1949,9,6,true)
75;Kowalewski;(1981,9,16,true);(,,,f)
76;Kowalski;(1987,7,4,true);(,,,f)
76;Kowalewski;(2007,5,16,true);(,,,f)
80;Wójcik;(,,,f);(,,,f)
81;Czajka;(,,,f);(,,,f)
83;Sikora;(1986,11,10,true);(,,,f)
84;Błaszczyk;(1976,7,,false);(,,,f)
84;Jankowski;(1962,2,11,true);(,,,f)
85;Wysocki;(1941,4,10,true);(1997,6,,false)
86;Klimczak;(1943,5,,false);(,,,f)
89;Kowalewski;(,,,f);(,,,f)
90;Kamiński;(1992,3,4,true);(,,,f)
90;Walczak;(1973,9,20,true);(1995,9,,false)
91;Kucharski;(1995,11,21,true);(,,,f)
91;Pietrzak;(2014,10,,false);(,,,f)
93;Sadowski;(1979,7,8,false);(,,,f)
94;Dąbrowski;(2007,5,,false);(,,,f)
94;Pawlak;(1985,6,8,true);(,,,f)
95;Kucharski;(1978,10,27,true);(2013,2,,false)
97;Zalewski;(1996,6,10,true);(,,,f)
99;Walczak;(,,,f);(,,,f)
99;Zalewski;(1973,4,,false);(,,,f)
100;Kowalski;(1965,5,,false);(1985,1,13,true)
100;Kaczmarek;(1978,11,,false);(,,,f)
101;Sikorski;(1960,6,,false);(,,,f)
103;Sikorski;(2009,8,24,true);(2017,6,27,true)
104;Wójcik;(,,,f);(,,,f)
106;Pietrzak;(2009,4,,false);(,,,f)
106;Czarnecki;(1997,4,13,true);(,,,f)
108;Borkowski;(1987,3,,false);(2017,10,7,true)
109;Wiśniewski;(2008,9,19,true);(,,,f)
109;Bielecki;(2018,5,26,true);(,,,f)
110;Bąk;(2024,3,26,true);(,,,f)
110;Krawczyk;(1991,10,,false);(2044,5,2,true)
114;Kowal;(2015,6,,false);(,,,f)
115;Kowalski;(1991,11,10,false);(,,,f)
117;Wysocki;(2013,7,,false);(,,,f)
118;Sikorski;(1999,6,,false);(2014,10,12,true)
118;Kowal;(2012,9,14,true);(,,,f)
119;Kozłowski;(1994,11,,false);(,,,f)
120;Szymański;(2020,5,17,true);(2039,2,26,true)
122;Kamiński;(2025,9,15,true);(,,,f)
124;Bielecki;(2000,10,,false);(,,,f)
124;Lewandowski;(2012,4,,false);(,,,f)
125;Borkowski;(2024,6,23,true);(,,,f)
128;Piotrowski;(,,,f);(,,,f)
129;Kowalewicz;(2059,2,24,true);(,,,f)
131;Błaszczyk;(2055,10,23,true);(,,,f)
133;Sikora;(2037,2,15,true);(,,,f)
137;Kaczmarek;(2043,1,6,true);(,,,f)
137;Zieliński;(2022,9,7,true);(2034,5,23,true)
138;Zawadzki;(,,,f);(,,,f)
138;Król;(2013,8,,false);(,,,f)
140;Majewski;(2042,1,27,true);(,,,f)
140;Wojtas;(2040,4,9,true);(2084,5,4,true)
142;Krawczyk;(2021,7,23,true);(2062,2,14,true)
142;Pietrzak;(2062,10,16,true);(,,,f)
144;Kowal;(2070,4,13,true);(,,,f)
144;Kopacz;(2052,5,8,true);(,,,f)
147;Jankowski;(,,,f);(,,,f)
148;Kaczmarek;(2038,11,12,true);(2061,2,19,true)
150;Błaszczyk;(2030,5,14,false);(,,,f)
\.
COPY relacje_niesymetryczne (osoba1, osoba2, typ_rns, data, miejsce) from stdin with (format csv, delimiter ';',null '');
15;4;2;(,,,f);
19;5;2;(,,,f);17
22;11;6;(,,,f);9
23;5;3;(,,,f);
29;8;4;(,,,f);4
30;10;6;(,,,f);
32;15;5;(,,,f);13
33;6;1;(,,,f);
39;22;6;(,,,f);2
41;11;3;(,,,f);20
43;21;7;(,,,f);17
52;27;5;(,,,f);19
53;38;3;(,,,f);21
58;45;6;(,,,f);21
66;47;4;(,,,f);3
67;47;4;(,,,f);
69;40;4;(,,,f);
72;47;3;(,,,f);
75;61;3;(,,,f);
76;60;7;(,,,f);15
80;59;1;(,,,f);
81;66;1;(,,,f);9
88;66;2;(,,,f);
94;67;4;(,,,f);
95;84;5;(,,,f);
96;84;4;(,,,f);11
97;86;5;(,,,f);19
98;85;2;(,,,f);
99;72;3;(,,,f);
105;92;6;(,,,f);11
109;96;6;(,,,f);
114;90;7;(,,,f);
115;90;1;(,,,f);
117;92;6;(,,,f);
120;92;5;(,,,f);2
121;94;3;(,,,f);
124;103;1;(,,,f);4
126;97;6;(,,,f);18
128;117;3;(,,,f);7
130;111;2;(,,,f);16
136;124;1;(,,,f);
137;112;5;(,,,f);
140;120;6;(,,,f);
145;121;7;(,,,f);
\.

COPY zawody_osoby (id_osoby, id_zawodu, stanowisko, miejsce) from stdin with (format csv, delimiter ';',null '');
132;5;junior;17
65;7;junior;
82;19;junior;
3;19;junior;5
27;16;senior;
113;15;senior;
148;5;senior;8
54;1;junior;
97;1;junior;10
6;1;junior;
68;10;senior;21
11;20;junior;6
2;14;junior;
50;11;junior;8
126;4;senior;3
98;9;senior;17
3;12;junior;11
41;20;senior;
111;12;senior;
147;5;senior;
19;13;senior;
123;8;junior;6
57;8;junior;12
74;15;senior;
137;14;senior;5
149;8;senior;
61;4;senior;21
9;15;junior;
30;19;senior;7
146;5;senior;
109;11;senior;
58;2;senior;11
28;5;junior;
122;20;junior;14
47;17;senior;4
28;6;junior;
15;14;junior;
118;16;junior;11
121;16;senior;6
74;4;senior;2
74;19;junior;9
8;9;junior;15
68;16;senior;20
123;2;senior;
11;10;senior;10
44;10;senior;
134;16;junior;
68;1;junior;
124;1;senior;20
93;1;senior;
122;4;senior;
42;1;senior;
58;19;senior;4
134;13;junior;15
112;15;senior;
144;1;junior;19
110;17;senior;
68;6;senior;
34;9;junior;16
148;19;junior;
37;19;senior;14
103;12;senior;
49;9;junior;
93;13;junior;10
40;6;junior;
7;11;junior;
150;11;senior;1
75;10;junior;7
91;19;junior;6
53;11;senior;4
\.


COMMIT;
