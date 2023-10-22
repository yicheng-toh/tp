package athleticli.data.activity;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Represents a swimming activity consisting of relevant evaluation data.
 */
public class Swim extends Activity implements Serializable {
    private final int laps;
    private final SwimmingStyle style;
    private final int averageLapTime;

    public enum SwimmingStyle {
        BUTTERFLY,
        BACKSTROKE,
        BREASTSTROKE,
        FREESTYLE
    }

    /**
     * Generates a new swimming activity with swimming specific stats.
     * By default, calories is 0, i.e., not tracked.
     * averageLapTime is calculated automatically based on the movingTime and laps.
     * @param movingTime duration of the activity in minutes
     * @param distance distance covered in meters
     * @param startDateTime start date and time of the activity
     * @param caption a caption of the activity chosen by the user (e.g., "Morning Run")
     * @param style swimming style
     */
    public Swim(String caption, int movingTime, int distance, LocalDateTime startDateTime, SwimmingStyle style) {
        super(caption, movingTime, distance, startDateTime);
        this.laps = this.calculateLaps();
        this.style = style;
        this.averageLapTime = this.calculateAverageLapTime();
    }

    /**
     * Calculates the average lap time in seconds.
     * @return average lap time in seconds
     */
    public int calculateAverageLapTime() {
        int laps = this.calculateLaps();
        return this.getMovingTime() * 60 / laps;
    }

    /**
     * Calculates the number of laps.
     * @return number of laps
     */
    public int calculateLaps() {
        return this.getDistance() / 50;
    }

    public int getLaps() {
        return laps;
    }

    public int getAverageLapTime() {
        return averageLapTime;
    }

    /**
     * Returns a short string representation of the swim.
     * @return a string representation of the swim
     */
    @Override
    public String toString() {
        String result = super.toString();
        result = result.replace("[Activity]", "[Swim]");
        String averageLapTimeOutput = this.averageLapTime + "s";
        result = result.replace("Time: ", "Avg Lap Time: " + averageLapTimeOutput + " | Time: ");
        return result;
    }

    /**
     * Returns a detailed summary of the swim.
     * @return a multiline string representation of the swim
     */
    public String toDetailedString() {
        String startDateTimeOutput = generateStartDateTimeStringOutput();
        String movingTimeOutput = generateMovingTimeStringOutput();
        String distanceOutput = generateDistanceStringOutput();

        int columnWidth = getColumnWidth();
        String header = "[Swim - " + this.getCaption() + " - " + startDateTimeOutput + "]";
        String firstRow = formatTwoColumns("\tDistance: " + distanceOutput, "Moving Time: " +
                movingTimeOutput, columnWidth);
        String secondRow = formatTwoColumns("\tAvg Lap Time: " + averageLapTime + " s", "Calories: " +
                this.getCalories() + " kcal", columnWidth);

        return String.join(System.lineSeparator(), header, firstRow, secondRow);
    }

}
