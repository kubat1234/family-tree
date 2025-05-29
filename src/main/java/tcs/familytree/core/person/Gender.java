package tcs.familytree.core.person;

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
}
