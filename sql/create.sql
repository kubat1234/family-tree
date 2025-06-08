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

create or replace function date_to_custom_date(data date) returns custom_date as $$
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

create or replace function czy_osoba_zyla(osoba int, data custom_date) returns boolean as $$
begin
    return data < (select data_sm from osoby where id = osoba) and (select data_ur from osoby where id = osoba) < data;
end;
$$ language plpgsql;

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
  pozostale_imiona varchar,
  nazwisko_rodowe varchar,
  matka int REFERENCES osoby(id),
  ojciec int REFERENCES osoby(id),
  data_ur custom_date not null default row(null,null,null,false) check(date_check(data_ur)),
  miejsce_ur int REFERENCES miejsca(id),
  wciaz_zyje boolean NOT NULL default false,
  miejsce_sm int REFERENCES miejsca(id),
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
  osoba1 int REFERENCES osoby(id),
  osoba2 int REFERENCES osoby(id),
  typ_rs int NOT NULL REFERENCES typy_rs(id),
  miejsce int REFERENCES miejsca(id),
  data custom_date not null default row(null,null,null,false) check(date_check(data)),
  CHECK(osoba1 is not null or osoba2 is not null),
  check(czy_osoba_zyla(osoba1,data))
);

CREATE TABLE relacje_niesymetryczne (
  id serial PRIMARY KEY,
  osoba1 int REFERENCES osoby(id),
  osoba2 int REFERENCES osoby(id),
  typ_rns int NOT NULL REFERENCES typy_rns(id),
  miejsce int REFERENCES miejsca(id),
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
  data_od custom_date not null default row(null,null,null,false) check(date_check(data_od)),
  data_do custom_date not null default row(null,null,null,false) check(date_check(data_do)),
    CHECK ( data_od < data_do ),
      check(czy_osoba_zyla(id_osoby,data_do) and czy_osoba_zyla(id_osoby,data_od))
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
  data_od custom_date not null default row(null,null,null,false) check(date_check(data_od)),
  data_do custom_date not null default row(null,null,null,false) check(date_check(data_do)),
  kolejnosc int NOT NULL DEFAULT nextval('nazwiska_kolejnosc_seq'),
  PRIMARY KEY (id_osoby, nazwisko),
    check(czy_osoba_zyla(id_osoby,data_od) and czy_osoba_zyla(id_osoby,data_do)),
    check(data_od < data_do)
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

create or replace function check_if_ancestor_update() 
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
			WHERE o.id = child.nadtytul
		)::tytuly, ancestor)
		THEN 
			RETURN true;
		END IF;
    END IF;
	RETURN false;
END;
$$ LANGUAGE plpgsql;

create or replace function check_if_nadtytul_update() 
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

create or replace view osoby_nazwiska as
select osoby.*, string_agg(coalesce(nazwiska.nazwisko,''),' ' order by nazwiska.kolejnosc) as "nazwiska"
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

--przykładowe dane

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
Toruń 1 2
Kopernika 5 3
al.\ Mickiewicza 2 3
4 6 4
19 7 4
Tymbark \N 2
1000 10 4
26 7 4
48 7 4
\.

COPY osoby (imie,pozostale_imiona,nazwisko_rodowe,matka,ojciec,data_ur, miejsce_ur, wciaz_zyje, miejsce_sm, data_sm, plec) FROM stdin WITH DELIMITER ' ';
Stefania \N \N \N \N (,,,f) \N 0 \N (,,,f) 1
Jan \N Nowak 1 \N (,,,f) \N 0 \N (1949,,,f) 0
Genowefa \N Wilkoryj \N \N (1899,,,f) \N 0 \N (1956,4,,f) 1
Stanisław \N Nowak 3 2 (1925,1,,f) \N 0 \N (2009,4,22,t) 0
Dorota \N Nowak 3 2 (1935,9,19,f) \N 0 \N (2005,1,1,t) 1
Krzysztof \N Kowal \N \N (1933,12,12,f) \N 0 \N (2013,11,13,t) 0
Adam \N Kowal 5 6 (1975,3,3,t) \N 0 \N (2003,6,9,t) 0
Antoni \N Kowal 5 6 (1975,3,3,t) \N 0 \N (2003,6,9,t) 0
Konrad \N \N \N \N (1985,,,f) \N 1 \N (,,,f) 0
Martyna Weronika Wielka \N \N (1983,5,5,t) 2 1 \N (,,,f) 1
Jakub Piotr Kowal 10 9 (2008,11,17,t) 3 1 \N (,,,f) 0
Maria Martyna Kowal 10 9 (2015,12,18,t) 4 1 \N (,,,f) 1
\.

COPY typy_rs (nazwa) FROM stdin WITH DELIMITER ' ';
małżeństwo
narzeczeństwo
\.

COPY typy_rns (nazwa) FROM stdin WITH DELIMITER ' ';
adopcja
\.

COPY relacje_symetryczne(osoba1, osoba2, typ_rs, miejsce, data) FROM stdin WITH DELIMITER ' ';
2 3 1 \N (1921,,,f)
5 6 1 2 (1966,7,18,t)
9 10 2 2 (2010,3,3,t)
\.

COPY relacje_niesymetryczne(osoba1, osoba2, typ_rns, miejsce, data) FROM stdin WITH DELIMITER ' ';
5 9 1 \N (1988,3,,f)
6 9 1 \N (1988,3,,f)
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

COPY nazwiska(id_osoby, nazwisko, data_od, data_do) FROM stdin WITH DELIMITER ' ';
1 Nowak (,,,f) (,,,f)
3 Nowak (1950,11,13,t) (,,,f)
5 Kowal (,,,f) (1966,7,18,t)
5 Nowak-Kowal (1966,7,18,t) (,,,f)
9 Kowal (1988,3,,f) (,,,f)
10 Kowal (2010,3,3,t) (,,,f)
\.

COMMIT;
