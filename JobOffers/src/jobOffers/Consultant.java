package jobOffers;

import java.util.*;

public class Consultant {
    
    private String name;
    private Set<String> consultantSkillSet;

    public Consultant(String name){
        this.name = name;
        this.consultantSkillSet = new HashSet<>();
    }
    public Set<String> getConsultantSkillSet() {
        return consultantSkillSet;
    }
    public String getName() {
        return name;
    }
    public void addSkill(String skill){
        this.consultantSkillSet.add(skill);
    }
    public int getNumberOfSkills(){
        return this.consultantSkillSet.size();
    }
}
