import java.io.*;
import java.util.List;

public class EventStorage {
    private final String filename;

    public EventStorage(String filename) {
        this.filename = filename;
    }

    public void saveToFile(List<Event> events) {
        try (PrintWriter out = new PrintWriter(new FileWriter(filename))) {
            for (Event e : events) {
                out.println(e.serialize());
            }
        } catch (IOException e) {
            System.out.println("Error saving: " + e.getMessage());
        }
    }

    public void loadFromFile(List<Event> events) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                Event e = Event.deserialize(line);
                if (e != null) events.add(e);
            }
        } catch (IOException ignored) {
        }
    }
}
