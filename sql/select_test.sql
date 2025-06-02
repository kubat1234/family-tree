select * from osoby o;
update osoby 
set 
	matka = 2
where id = 1;
select * from osoby o order by id;
select * from tytuly t order by id;
insert into tytuly(id, nazwa, skrot, nadtytul)
VALUES (1000, 'general', 'gen', null);
insert into tytuly(id, nazwa, skrot, nadtytul)
VALUES (1001, 'starszy oficer', 'str of', 1000);
insert into tytuly(id, nazwa, skrot, nadtytul)
VALUES (1002,'starszy oficer', 'of', 1001);
select * from tytuly t order by id;
update tytuly t
set 
	nadtytul = 1002
where id = 4;
select * from tytuly t order by id;
DELETE FROM tytuly 
WHERE id > 1000;


select * from praca;