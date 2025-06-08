package tcs.familytree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Generator {
    static Random random = new Random();
    enum Gender {
        MALE,
        FEMALE
    }
    static String i2s(Integer i) {
        if(i == null)
            return "";
        return i.toString();
    }
    static String s2s(String s) {
        if(s == null)
            return "";
//        return "'" + s + "'";
        return s;
    }
    static String g2s(Gender g) {
        if(g == null)
            return "";
        return g==Gender.MALE?"true":"false";
    }
    static String b2s(Boolean b) {
        if (b == null) {
            return "";
        }
        return b?"true":"false";
    }

    /**
     * Standardowa data. Wcześniejsze daty mają większe prawdopodobieństwo bycia niekomplentnymi.
     */
    static String randPlace(){
        if(random.nextBoolean()) return i2s(null);
        else return i2s(random.nextInt(21)+1);
    }

    static class Date {
        static Date about(int year) {
            final Date date = new Date();
            int dev = 1+(int)Math.sqrt(2025 - year);
            int accuracy = 3 - random.nextInt(0, 1+dev/4) % 4;
            switch (accuracy) {
                case 3:
                    date.day = random.nextInt(1, 28);
                case 2:
                    date.month = random.nextInt(1, 12);
                case 1:
                    date.year = year;
                    if(accuracy==3)date.isAccurate = random.nextDouble() >= 0.1;
                    else date.isAccurate = false;
            }
            return date;
        }
        Integer year, month, day;
        boolean isAccurate;
        @Override
        public String toString() {
            StringBuffer buffer = new StringBuffer();
            buffer.append("(");
            if(year == null) {
                buffer.append(",,,").append(isAccurate).append(")");
                return buffer.toString();
            }
            if(month == null) {
                buffer.append(year).append(",,,").append(isAccurate).append(")");
                return buffer.toString();
            }
            if(day == null) {
                buffer.append(year).append(",").append(month).append(",,").
                        append(isAccurate).append(")");
                return buffer.toString();
            }
            buffer.append(year).append(",").append(month).append(",").append(day).
                    append(",").append(isAccurate).append(")");
            return buffer.toString();
        }
    }
    static class Person {
        Integer id;
        String name, surname;
        String otherNames;
        List<String> otherSurnames;
        Integer mother, father;
        Gender gender;
        Date dob, dod;
        int yob, yod;
        boolean alive;
        int marriages = 0;
        @Override
        public String toString() {
            StringBuffer buffer = new StringBuffer();
            buffer.append(s2s(name)).
                    append(";").append(s2s(otherNames)).append(";").append(s2s(surname)).append(";").append(dob).
                    append(";").append(dod).append(";").append(g2s(gender)).
                    append(";").append(i2s(mother)).append(";").append(i2s(father)).
                    append(";").append(b2s(alive)).append(";").append(randPlace()).append(";").append(randPlace());
            return buffer.toString();
        }
    }
    static class Marriage {
        Person man, woman;
        int activeFrom;
        int activeTo;

        @Override
        public String toString() {
            StringBuffer buffer = new StringBuffer();
            buffer.append(Math.min(man.id, woman.id)).append(";").append(Math.max(man.id, woman.id)).
                    append(";").append(random.nextDouble() >= 0.1 ? 1 : random.nextInt(2,7)).append(";").append(Date.about(activeFrom)).append(";").append(randPlace());
            return buffer.toString();
        }
    }

    public static List<Integer> getRandomInts(int ileLiczb, int zakres){
        List<Integer> liczby = new ArrayList<>();
        for (int i = 0; i < zakres; i++) {
            liczby.add(i);
        }
        Collections.shuffle(liczby);
        return liczby.subList(0, ileLiczb);
    }

    public static void main(String[] args) {
        String[] maleNames = new String[]{"Adam", "Bartosz", "Cezary", "Dawid", "Eryk", "Filip", "Grzegorz", "Hubert", "Igor", "Jakub", "Kacper", "Łukasz", "Marek", "Norbert", "Oskar", "Paweł", "Rafał", "Sebastian", "Tomasz", "Ursyn", "Wojciech", "Zbigniew", "Michał", "Szymon", "Krzysztof", "Witold", "Leon", "Mikołaj", "Artur", "Damian", "Emil", "Franciszek", "Kamil", "Maciej", "Nikodem", "Olek", "Piotr", "Radek", "Sławek", "Tadeusz", "Wiktor", "Zdzisław", "Zenon"};
        String[] femaleNames = new String[]{"Agnieszka", "Barbara", "Cecylia", "Dorota", "Ewa", "Felicja", "Gabriela", "Hanna", "Iwona", "Joanna", "Karolina", "Lidia", "Magdalena", "Natalia", "Oliwia", "Patrycja", "Renata", "Sandra", "Teresa", "Urszula", "Wanda", "Zofia", "Alicja", "Beata", "Celina", "Danuta", "Elżbieta", "Franciszka", "Grażyna", "Halina", "Irmina", "Jadwiga", "Katarzyna", "Lena", "Marta", "Nadia", "Olga", "Paulina", "Roksana", "Sylwia", "Tatiana", "Weronika", "Zuzanna"};
        String[] surnames = new String[]{"Nowak", "Kowalski", "Wiśniewski", "Wójcik", "Kowalczyk", "Zieliński", "Szymański", "Dąbrowski", "Lewandowski", "Kamiński", "Kozłowski", "Jankowski", "Witkowski", "Pawlak", "Krawczyk", "Piotrowski", "Grabowski", "Zawadzki", "Majewski", "Król", "Walczak", "Sikora", "Bąk", "Czarnecki", "Kaczmarek", "Kucharski", "Borkowski", "Sadowski", "Wysocki", "Mazurek", "Klimczak", "Błaszczyk", "Czajka", "Kopacz", "Sienkiewicz", "Wojciechowski", "Bielecki", "Kowal", "Kowalewski", "Sikorski", "Wojtas", "Zalewski", "Kowalewicz", "Pietrzak", "Kowalik"};

        System.out.println("COPY osoby (imie, pozostale_imiona, nazwisko_rodowe, data_ur, data_sm, plec, matka, ojciec, wciaz_zyje, miejsce_ur, miejsce_sm) FROM stdin WITH (FORMAT csv, DELIMITER ';', NULL '');");
        int N = 150;

        final int from = 1825;
        final int to = 2025;

        List<Person> personList = new ArrayList<>();
        List<Marriage> marriageList = new ArrayList<>();

        /**
        Generowanie N osób i małżeństw między nimi.
         */

        for (int i = 0; i < N; i++) {
//            if (i != 0) {
//                System.out.print(", ");
//            } else {
//                System.out.print("  ");
//            }
            Person p = new Person();
            personList.add(p);
            p.id = i + 1;
            p.gender = random.nextBoolean() ? Gender.MALE : Gender.FEMALE;
            p.name = p.gender == Gender.MALE ? maleNames[random.nextInt(maleNames.length)] :
                    femaleNames[random.nextInt(femaleNames.length)];
            StringBuilder otherNamesBuilder = new StringBuilder();
            for(int j=0;j<random.nextInt(0,3);j++){
                if(j>0)otherNamesBuilder.append(" ");
                otherNamesBuilder.append(p.gender == Gender.MALE ? maleNames[random.nextInt(maleNames.length)] :
                        femaleNames[random.nextInt(femaleNames.length)]);
            }
            p.otherNames = otherNamesBuilder.toString();
            p.otherSurnames = new ArrayList<>();
            for(int x : getRandomInts(random.nextInt(0,3),surnames.length)){
                p.otherSurnames.add(surnames[x]);
            }
            p.surname = surnames[random.nextInt(surnames.length)];
            int yob = (to - from) * i / N + from + random.nextInt(-1, 2);
            p.dob = Date.about(yob);
            int yod = yob + random.nextInt(90) + random.nextInt(10);
            if (yod <= 2025) {
                p.dod = Date.about(yod);
                p.alive = false;
            } else {
                p.dod = new Date();
                p.alive = true;
            }
            p.yob = yob;
            p.yod = yod;
            /**
             * próba znalezienia małżonka
             */
            if (yob < 2005) {
                for (int j = 0; j < 6; j++) {
                    int partner = i - random.nextInt(N * 20 / (to - from));
                    if (partner >= 0 && !personList.get(partner).gender.equals(p.gender)) {
                        if(personList.get(partner).marriages > 0) {
                            if(random.nextInt(100) < 92)
                                continue;
                        }
                        Marriage marriage;
                        if (p.gender == Gender.MALE) {
                            marriage = new Marriage();
                            marriage.man = p;
                            marriage.woman = personList.get(partner);
                        } else {
                            marriage = new Marriage();
                            marriage.woman = p;
                            marriage.man = personList.get(partner);
                        }
                        marriage.activeFrom = Math.max(marriage.man.yob, marriage.woman.yob) + 15;
                        marriage.activeTo = Math.min(marriage.man.yod, marriage.woman.yod);
                        if (marriage.activeFrom < marriage.activeTo) {
                            marriage.man.marriages++;
                            marriage.woman.marriages++;
                            marriageList.add(marriage);
                            break;
                        }
                    }
                }
            }
            /**
             * próba znalezienia rodziców
             */
            for (int j = 0; j < 5; j++) {
                int mid = marriageList.size() - random.nextInt(35) - 1;
                if (mid < 0) {
                    continue;
                }
                Marriage marriage = marriageList.get(mid);
                if (marriage.activeFrom <= yob && marriage.activeTo > yob) {
                    p.mother = marriage.woman.id;
                    p.father = marriage.man.id;
                    break;
                }
            }
            /**
             * próba znalezienia chociaż jednego rodzica
             */
            if (p.mother == null) {
                for (int j = 0; j < 6; j++) {
                    int pid = i - random.nextInt(N * 15 / (to - from), N * 45 / (to - from));
                    if (pid >= 0 && personList.get(pid).yod > p.yob) {
                        if (personList.get(pid).gender == Gender.MALE) {
                            p.father = pid+1;
                        } else {
                            p.mother = pid+1;
                        }
                        break;
                    }
                }
            }
            System.out.println(p);
        }
        System.out.println("\\.");
        System.out.println("COPY relacje_symetryczne (osoba1, osoba2, typ_rs, data, miejsce) from stdin with (format csv, delimiter ';',null '');");
        boolean firstMarriage = true;
        for(Marriage m : marriageList) {
            if(random.nextInt(100) < 17) {
                continue;
            }
            System.out.println(m);
        }
        System.out.println("\\.");

        System.out.println("copy nazwiska (id_osoby, nazwisko, data_od, data_do) from stdin with (format csv, delimiter ';',null '');");

        for(Person p : personList) {
            for(String nazwisko : p.otherSurnames){
                int y1 = 3+random.nextInt(p.yob, p.yod);
                int y2 = 7 + random.nextInt(0, p.yod-p.yob);
                System.out.println(p.id + ";" + nazwisko + ";" + (y1 < p.yod-2 ? Date.about(y1) : "(,,,f)") + ";" + (y1+y2 < p.yod-2 ? Date.about(y1+y2) : "(,,,f)"));
            }
        }

        System.out.println("\\.");

        System.out.println("COPY relacje_niesymetryczne (osoba1, osoba2, typ_rns, data, miejsce) from stdin with (format csv, delimiter ';',null '');");

        for(Person p : personList) {
            if(random.nextInt(100) > 30) continue;
            for (int j = 0; j < 6; j++) {
                int pid = p.id - random.nextInt(N * 15 / (to - from), N * 45 / (to - from));
                if (pid >= 0 && pid < N && personList.get(pid).yod > p.yob) {
//                    int y1 = p.yob + + 2 + random.nextInt(5);
                    System.out.println(p.id + ";" + (pid+1) + ";" + (random.nextInt(7)+1) + ";" +  "(,,,f)" + ";" + randPlace());
                    break;
                }
            }
        }

        System.out.println("\\.");

        System.out.println("COPY zawody_osoby (id_osoby, id_zawodu, stanowisko, miejsce) from stdin with (format csv, delimiter ';',null '');");

        for(int i=0;i<70;i++){
            System.out.println((random.nextInt(N)+1) + ";" + (random.nextInt(20)+1) + ";" + ((random.nextBoolean() ? "senior" : "junior") + ";" + randPlace()));
        }

        System.out.println("\\.");
    }
}