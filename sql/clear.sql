BEGIN;

DROP TABLE IF EXISTS osoby CASCADE;
DROP TABLE IF EXISTS relacje_symetryczne CASCADE;
DROP TABLE IF EXISTS relacje_niesymetryczne CASCADE;
DROP TABLE IF EXISTS typy_rs CASCADE;
DROP TABLE IF EXISTS typy_rns CASCADE;
DROP TABLE IF EXISTS miejsca CASCADE;
DROP TABLE IF EXISTS typy_miejsc CASCADE;
DROP TABLE IF EXISTS uwagi_osoby CASCADE;
DROP TABLE IF EXISTS uwagi CASCADE;
DROP TABLE IF EXISTS typy_uwag CASCADE;
DROP TABLE IF EXISTS zawody CASCADE;
DROP TABLE IF EXISTS zawody_osoby CASCADE;
DROP TABLE IF EXISTS miejsce_pracy CASCADE;
DROP TABLE IF EXISTS tytuly CASCADE;
DROP TABLE IF EXISTS tytuly_osoby CASCADE;
DROP TABLE IF EXISTS grupy CASCADE;
DROP TABLE IF EXISTS grupy_osoby CASCADE;
DROP TABLE IF EXISTS uwagi_grupy CASCADE;
DROP TABLE IF EXISTS nazwiska CASCADE;

DROP VIEW IF EXISTS praca CASCADE;
DROP VIEW IF EXISTS osoby_nazwiska CASCADE;

DROP SEQUENCE IF EXISTS nazwiska_kolejnosc_seq CASCADE;
drop table if exists swiadkowie CASCADE;
drop table IF EXISTS dokumenty CASCADE;
drop table if EXISTS instytucje CASCADE;
drop table IF EXISTS typy_dokumentow CASCADE;

DROP FUNCTION IF EXISTS date_check(d custom_date) CASCADE;
DROP FUNCTION IF EXISTS date_comp(a custom_date, b custom_date) CASCADE;
DROP FUNCTION IF EXISTS date_to_custom_date(date data) CASCADE;
DROP FUNCTION IF EXISTS dni_w_miesiacu CASCADE;
DROP FUNCTION IF EXISTS czy_rok_przestepny CASCADE;
DROP FUNCTION IF EXISTS czy_rok_przestepny(p_rok INT) CASCADE;
DROP FUNCTION IF EXISTS czy_osoba_zyla(osoba int, data custom_date) CASCADE;
DROP FUNCTION IF EXISTS check_if_ancestor(child OSOBY, ancestor OSOBY) CASCADE;
DROP FUNCTION IF EXISTS check_if_ancestor_update() CASCADE;
DROP FUNCTION IF EXISTS check_if_nadtytul(child tytuly, ancestor tytuly) CASCADE;
DROP FUNCTION IF EXISTS check_if_nadtytul_update() CASCADE;
DROP FUNCTION IF EXISTS check_if_nadmiejsce(child miejsca, ancestor miejsca) CASCADE;
DROP FUNCTION IF EXISTS check_if_nadmiejsce_update() CASCADE;
DROP FUNCTION IF EXISTS check_if_nadtyp_m(child typy_miejsc, ancestor typy_miejsc) CASCADE;
DROP FUNCTION IF EXISTS check_if_nadtyp_m_update() CASCADE;
DROP FUNCTION IF EXISTS check_if_miejsce_matches_typ() CASCADE;
DROP FUNCTION IF EXISTS safe_person_delete() CASCADE;
DROP FUNCTION IF EXISTS immutable_id() CASCADE;

DROP TYPE IF EXISTS custom_date CASCADE;

COMMIT;
