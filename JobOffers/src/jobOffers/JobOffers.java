package jobOffers; 
import java.util.*;
import java.util.stream.Collectors;


public class JobOffers  {

	private Set<String> skillsSet;
	private Map<String, Position> positionMap;
	private Map<String, Candidate> candidateMap;
	private Map<String, Consultant> consultantMap;

	public JobOffers(){

		this.skillsSet = new HashSet<>();
		this.positionMap = new HashMap<>();
		this.candidateMap = new HashMap<>();
		this.consultantMap = new HashMap<>();
	}
//R1
	public int addSkills (String... skills) {
		for (String string : skills) {
			this.skillsSet.add(string);
		}
		return this.skillsSet.size();
	}
	
	public int addPosition (String position, String... skillLevels) throws JOException {
		
		if(this.positionMap.containsKey(position)) throw new JOException("addPosition ERROR");


		this.positionMap.put(position, new Position(position));
		for(String skillLevel: skillLevels){

			String skill = skillLevel.split(":")[0];
			Integer level = Integer.parseInt(skillLevel.split(":")[1]);

			if(!this.skillsSet.contains(skill) || (level < 4 || level > 8)) throw new JOException("addPosition IF ERROR");

			else{

				this.positionMap.get(position).addSkill(skill, level);
			}
		}
		return this.positionMap.get(position).getSkillAvg();
	}
//R2	
	public int addCandidate (String name, String... skills) throws JOException {

		if(this.candidateMap.containsKey(name)) throw new JOException("addCandidate");


		this.candidateMap.put(name, new Candidate(name));

		for (String skill : skills) {
			if(!this.skillsSet.contains(skill)) throw new JOException("addCandidate <not in skillsSet> ");

			this.candidateMap.get(name).addSkill(skill);
			
		}

		return this.candidateMap.get(name).getNumberOfSkills();
	}
	
	public List<String> addApplications (String candidate, String... positions) throws JOException {
		if(!this.candidateMap.containsKey(candidate)) throw new JOException("addApplications 1112 ");

		for (String position : positions) {
			if(!this.positionMap.containsKey(position)) throw new JOException("addApplications 1113");
			if(!this.candidateMap.get(candidate).getCandidateSkillsSet().containsAll(this.positionMap.get(position).getSkills())) throw new JOException("addApplications 1114");
			
			
			this.candidateMap.get(candidate).addPosition(this.positionMap.get(position));
		}


		return this.candidateMap.get(candidate).getApplicationsString();
	} 
	
	public TreeMap<String, List<String>> getCandidatesForPositions() {
		TreeMap<String, List<String>> result = new TreeMap<>();

		for (Position position : this.positionMap.values()) {
			result.computeIfAbsent(position.getPositionName(), k -> new ArrayList<>());
			
			List<String> candidates = this.candidateMap.values().stream()
																.filter(c -> c.getCandidatePositionSet().contains(position.getPositionName()))
																.map(c -> c.getName())
																.collect(Collectors.toList());

			result.put(position.getPositionName(), candidates);
		}
		return result;
	}
		
//R3
	public int addConsultant (String name, String... skills) throws JOException {
		if(this.consultantMap.containsKey(name)) throw new JOException("addConsultant");


		this.consultantMap.put(name, new Consultant(name));
		for (String skill : skills) {
			this.consultantMap.get(name).addSkill(skill);
		}
		return this.consultantMap.get(name).getNumberOfSkills();
	}
	
	public Integer addRatings (String consultant, String candidate, String... skillRatings)  throws JOException {
		if(
			!this.candidateMap.containsKey(candidate) ||
			!this.consultantMap.containsKey(consultant) ||
			skillRatings.length != this.candidateMap.get(candidate).getNumberOfSkills()
		) throw new JOException("addRatings 1");

		
		for (String string : skillRatings) {
			String skill = string.split(":")[0];
			Integer rate = Integer.parseInt(string.split(":")[1]);

			if(
				!this.candidateMap.get(candidate).getCandidateSkillsSet().contains(skill) ||
				(rate < 4 || rate > 10)
			  ) throw new JOException("addRatings 2");

			else this.candidateMap.get(candidate).addSkillRate(skill, rate);
		}
		return this.candidateMap.get(candidate).getSkillAvg();
	}
	
//R4
	public List<String> discardApplications() {
		return null;
	}
	
	 
	public List<String> getEligibleCandidates(String position) {
		return null;
	}
	

	
}

		
