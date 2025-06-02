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

