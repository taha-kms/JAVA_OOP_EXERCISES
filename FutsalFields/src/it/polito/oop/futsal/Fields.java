package it.polito.oop.futsal;

import java.util.List;
import java.util.Map;

/**
 * Represents a infrastructure with a set of playgrounds, it allows teams
 * to book, use, and  leave fields.
 *
 */
public class Fields {
	    
    public static class Features {
        public final boolean indoor; // otherwise outdoor
        public final boolean heating;
        public final boolean ac;
        public Features(boolean i, boolean h, boolean a) {
            this.indoor=i; this.heating=h; this.ac = a;
        }
    }
    
	
    public void defineFields(Features... features) throws FutsalException {

    }
    
    public long countFields() {
        return -1;
    }

    public long countIndoor() {
        return -1;
    }
    
    public String getOpeningTime() {
        return null;
    }
    
    public void setOpeningTime(String time) {
    }
    
    public String getClosingTime() {
        return null;
    }
    
    public void setClosingTime(String time) {
    }

    public int newAssociate(String first, String last, String mobile) {
        return -1;
    }
    
    public String getFirst(int associate) throws FutsalException {
        return null;
    }
    
    public String getLast(int associate) throws FutsalException {
        return null;
    }
    
    public String getPhone(int associate) throws FutsalException {
        return null;
    }
    
    public int countAssociates() {
        return -1;
    }
    
    public void bookField(int field, int associate, String time) throws FutsalException {
    }

    public boolean isBooked(int field, String time) {
        return false;
    }
    

    public int getOccupation(int field) {
        return -1;
    }
    
    public List<FieldOption> findOptions(String time, Features required){
        return null;
    }
    
    public long countServedAssociates() {
        return -1;
    }
    
    public Map<Integer,Long> fieldTurnover() {
        return null;
    }
    
    public double occupation() {
        return -1;
    }
    
 }
