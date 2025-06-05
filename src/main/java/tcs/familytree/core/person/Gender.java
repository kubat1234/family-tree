package tcs.familytree.core.person;

public enum Gender {
    MALE, FEMALE, OTHER;
    public static Gender fromBoolean(Boolean gender){
        if(gender == null) return OTHER;
        else if(gender) return FEMALE;
        else return MALE;
    }
    public Boolean toBoolean(){
        if(this == MALE) return false;
        else if(this == FEMALE) return true;
        else return null;
    }
    public String toString(){
        if(this == MALE) return "Mężczyzna";
        else if(this == FEMALE) return "Kobieta";
        else return "Inna";
    }
}
