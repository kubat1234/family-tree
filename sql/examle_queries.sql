--Wiek osob

select imie,pozostale_imiona, nazwisko_rodowe, nazwiska,
       coalesce((data_sm).rok, extract(year from now())) - (data_ur).rok::int  as wiek
from osoby_nazwiska;

-- relacje niesymatryczne

select
    (
        select imie || ' ' || nazwisko_rodowe from osoby
        where id=rns.osoba1
    ) as osoba1,
    (
        select imie || ' ' || nazwisko_rodowe from osoby
        where id=rns.osoba2
    ) as osoba2,
    nazwa as "typ relacji"
from relacje_niesymetryczne rns inner join typy_rns t on rns.typ_rns = t.id;

-- uwagi

insert into typy_uwag values (100, 'ilość nazwisk');
insert into uwagi values(100,'miała 2 nazwiska', 100);
insert into uwagi_osoby
select 100, id
from osoby
where (
    select count(*) from nazwiska where id_osoby = id
          ) = 2;

select zawartosc, imie, nazwiska
from uwagi_osoby inner join uwagi u on id_uwagi = u.id inner join osoby_nazwiska o on id_osoby = o.id;

--usuwanie osob

delete from osoby_nazwiska where nazwiska = 'Bielecki Lewandowski';

select * from relacje_symetryczne where typ_rs = 1;

delete from osoby where id = 1;

delete from osoby o where
(
    select count(*) from relacje_symetryczne where typ_rs = 1 and ((osoba1 = o.id and osoba2 is null) or (osoba2 = o.id and osoba1 is null))
) > 0;

-- grupy i miejsca

select id, imie, nazwisko_rodowe, miejsce_ur from osoby o
where check_if_nadmiejsce((
    select m from miejsca m where id = miejsce_ur
                              ),
    (select m from miejsca m inner join typy_miejsc t on m.typ_miejsca = t.id
    where t.nazwa = 'kraj' and m.nazwa <> 'Polska'));

insert into grupy values(100, 'obcokrajowcy');

insert into grupy_osoby
select 100, id from osoby o
where check_if_nadmiejsce((
      select m from miejsca m where id = miejsce_ur
  ),
  (select m from miejsca m inner join typy_miejsc t on m.typ_miejsca = t.id
   where t.nazwa = 'kraj' and m.nazwa <> 'Polska'));

select imie, nazwiska, nazwa from osoby_nazwiska o inner join grupy_osoby on o.id = id_osoby inner join grupy g on id_grupy=g.id;

