package tcs.familytree.core.person;

import javafx.util.StringConverter;

public enum Gender {
    MALE, FEMALE, OTHER;
    public static Gender fromBoolean(Boolean gender){
        if(gender == null) return OTHER;
        else if(gender) return MALE;
        else return FEMALE;
    }
    public Boolean toBoolean(){
        if(this == MALE) return true;
        else if(this == FEMALE) return false;
        else return null;
    }
    public static StringConverter<Gender> getConverter(){
        return new StringConverter<Gender>() {
            public Gender fromString(String gender) {
                if(gender == null) return OTHER;
                else if(gender.equals("Mężczyzna")) return MALE;
                else if(gender.equals("Kobieta")) return FEMALE;
                else return OTHER;
            }
            public String toString(Gender gender) {
                if(gender == null) return OTHER.toString();
                else if(gender.equals(MALE)) return "Mężczyzna";
                else if(gender.equals(FEMALE)) return "Kobieta";
                else return "Inna";
            }
        };
    }
}
