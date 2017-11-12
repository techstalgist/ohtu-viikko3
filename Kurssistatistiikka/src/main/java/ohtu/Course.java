
package ohtu;


public class Course {
    private String name;
    private String term;
    private int[] exercises;
    private int submissionsTotal;
    private int exercisesTotal;
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public int[] getExercises() {
        return exercises;
    }

    public void setExercises(int[] exercises) {
        this.exercises = exercises;
    }
    
    public void incrementSubmissionsTotal(int toAdd) {
        submissionsTotal += toAdd;
    }
    
    public void incrementExercisesTotal(int toAdd) {
        exercisesTotal += toAdd;
    }
    
    public int getSubmissionsTotal() {
        return submissionsTotal;
    }
    
    public int getExercisesTotal() {
        return exercisesTotal;
    }
    
    @Override
    public String toString() {
        return name + ", " + term;
    }
}
