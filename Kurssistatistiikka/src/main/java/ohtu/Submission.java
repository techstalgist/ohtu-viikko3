package ohtu;

import java.util.Arrays;

public class Submission {
    private int week;
    private int hours;
    private int[] exercises;

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int[] getExercises() {
        return exercises;
    }

    public void setExercises(int[] exercises) {
        this.exercises = exercises;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getWeek() {
        return week;
    }
    
    private String exercisesToString() {
        String exercisesAsString = Arrays.toString(exercises);
        return exercisesAsString.substring(1, exercisesAsString.length()-1);
    }

    public String toString(int max) {
        return " viikko " + week + 
                ": tehtyjä tehtäviä yhteensä: " + exercises.length + 
                " (maksimi " + max + ")" +
                ", aikaa kului " + hours + 
                " tuntia, tehdyt tehtävät: " + exercisesToString();
    }
    
}