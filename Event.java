public class Event {
    private String title, desc, date;
    private boolean isCompleted;

    public Event(String title, String desc, String date) {
        this.title = title.trim();
        this.desc = desc.trim();
        this.date = date.trim();
        this.isCompleted = false;
    }

    public String getTitle() { return title; }
    public String getDesc() { return desc; }
    public String getDate() { return date; }
    public boolean isCompleted() { return isCompleted; }

    public void toggleStatus() { isCompleted = !isCompleted; }

    @Override
    public String toString() {
        return title + " (" + date + ") - " + (isCompleted ? "✔ Completed" : "⏳ Pending");
    }

    // Save format
    public String serialize() {
        return title + "|" + desc + "|" + date + "|" + (isCompleted ? "1" : "0");
    }

    // Load format
    public static Event deserialize(String line) {
        String[] parts = line.split("\\|");
        if (parts.length < 4) return null;
        Event e = new Event(parts[0], parts[1], parts[2]);
        if (parts[3].equals("1")) e.toggleStatus();
        return e;
    }
}
