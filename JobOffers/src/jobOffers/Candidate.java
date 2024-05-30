package jobOffers;


import java.util.*;
import java.util.stream.Collectors;
public class Candidate {
    
    private String name;
    private Map<String, Integer> candidateSkillsMap;
    private Set<Position> candidatePositionSet;

    public Candidate(String name){
        this.name = name;
        this.candidateSkillsMap = new HashMap<>();
        this.candidatePositionSet = new HashSet<>();

    }

    public Set<String> getCandidatePositionSet() {
        return candidatePositionSet.stream().map(p -> p.getPositionName()).collect(Collectors.toSet());
    }
    public Set<String> getCandidateSkillsSet() {
        return candidateSkillsMap.keySet();
    }
    public String getName() {
        return name;
    }

    public void addAplication(Position pos){
        this.candidatePositionSet.add(pos);
    }

    public void addSkill(String skill){
        this.candidateSkillsMap.put(skill, 0);
    }

    public void addPosition(Position pos){
        this.candidatePositionSet.add(pos);
    }

    public int getNumberOfSkills(){
        return this.candidateSkillsMap.size();
    }

    public List<String> getApplicationsString(){

        List<String> result = new ArrayList<>();

        for (Position position : candidatePositionSet) {
            result.add(String.format("%s:%s", this.name, position.getPositionName()));
        }

        return result;
    }

    public void addSkillRate(String name,int rate){
        this.candidateSkillsMap.put(name, rate);
    }

    public int getSkillAvg(){
        int sum = 0;
        int count = this.candidateSkillsMap.values().size();
        for(int level: this.candidateSkillsMap.values()){
            sum = sum + level;
        }

        return sum/count;
    }
    
}
