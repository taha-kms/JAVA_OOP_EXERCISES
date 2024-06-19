package it.polito.project;

public class Review {

    private String id;
    private String title;
    private String group;
    private String topic;
    
    public Review(String id, String title, String topic, String group){
        this.title = title;
        this.topic = topic;
        this.id = id;
        this.group = group;
    }

    public String getGroup() {
        return group;
    }
    
    public String getId() {
        return id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getTopic() {
        return topic;
    }
    
}
