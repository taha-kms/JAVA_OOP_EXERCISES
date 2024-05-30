package jobOffers;

import java.util.*;
public class Position {
    
    private String positionName;
    private Map<String, Integer> positionSkillMap;
    
    public Position(String name){
        this.positionName = name;
        this.positionSkillMap = new HashMap<>();
    }

    public String getPositionName() {
        return positionName;
    }

    public void addSkill(String skillName, Integer skillLevel){

        this.positionSkillMap.put(skillName, skillLevel);
    }

    public int getSkillAvg(){

        int sum = 0;
        int count = this.positionSkillMap.values().size();
        for(int level: this.positionSkillMap.values()){
            sum = sum + level;
        }

        return sum/count;
    }

    public Collection<String> getSkills(){
        return this.positionSkillMap.keySet();
    }

}
