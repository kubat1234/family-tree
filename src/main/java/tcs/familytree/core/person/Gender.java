package tcs.familytree.core.person;

public enum Gender {
    MALE, FEMALE, OTHER;
    public static Gender fromBoolean(Boolean gender){
        if(gender == null) return OTHER;
        else if(gender) return MALE;
        else return FEMALE;
    }
}
