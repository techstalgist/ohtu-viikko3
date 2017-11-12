package ohtu;

import com.google.gson.Gson;
import java.io.IOException;
import org.apache.http.client.fluent.Request;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Main {

    public static void main(String[] args) throws IOException {
        // vaihda oma opiskelijanumerosi seuraavaan, ÄLÄ kuitenkaan laita githubiin omaa opiskelijanumeroasi
        String studentNr = "";
        if (args.length>0) {
            studentNr = args[0];
        }

        Submission[] subs = getSubmissionsForStudent(studentNr);
        Course course = getCourse();
        getTotalsForCourse(course);
        printCourseData(studentNr, subs, course);
    }
    
    private static Submission[] getSubmissionsForStudent(String studentNr) throws IOException {
        String url = "https://studies.cs.helsinki.fi/ohtustats/students/"+studentNr+"/submissions";

        String bodyText = Request.Get(url).execute().returnContent().asString();
        Gson mapper = new Gson();
        return mapper.fromJson(bodyText, Submission[].class);
    }
    
    private static void printCourseData(String studentNr, Submission[] subs, Course course) {
        System.out.println("Kurssi: " + course.toString() + "\n");
        System.out.println("opiskelijanumero: " + studentNr + "\n");
        int totalHours = 0;
        int exercisesCount = 0;
        for (Submission submission : subs) {
            int max = course.getExercises()[submission.getWeek()-1];
            System.out.println(submission.toString(max));
            totalHours += submission.getHours();
            exercisesCount += submission.getExercises().length;
        }
        System.out.println("\nyhteensä: " + exercisesCount + " tehtävää, " + totalHours + " tuntia.");
        System.out.println("kurssilla yhteensä "+ course.getSubmissionsTotal()+ " palautusta, palautettuja tehtäviä " + course.getExercisesTotal() + " kpl");
    } 

    private static Course getCourse() throws IOException {
        String url = "https://studies.cs.helsinki.fi/ohtustats/courseinfo";

        String bodyText = Request.Get(url).execute().returnContent().asString();

        Gson mapper = new Gson();
        return mapper.fromJson(bodyText, Course.class);
    }
    
    private static void getTotalsForCourse(Course course) throws IOException {
        String url = "https://studies.cs.helsinki.fi/ohtustats/stats";

        String bodyText = Request.Get(url).execute().returnContent().asString();
        
        JsonParser parser = new JsonParser();
        JsonObject json = parser.parse(bodyText).getAsJsonObject();
        for (String k : json.keySet()) {
            course.incrementSubmissionsTotal(json.get(k).getAsJsonObject().get("students").getAsInt());
            course.incrementExercisesTotal(json.get(k).getAsJsonObject().get("exercise_total").getAsInt());
        }
                
    }
}