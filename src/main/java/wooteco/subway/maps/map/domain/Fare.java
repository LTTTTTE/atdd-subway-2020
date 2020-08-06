package wooteco.subway.maps.map.domain;

public class Fare {
    private static final int DEFAULT_FARE = 1250;
    private static final int EXTRA_DISTANCE_FARE_MULTIPLE = 100;
    private static final int FIRST_EXTRA_DISTANCE_PIVOT = 10;
    private static final int FIRST_EXTRA_DISTANCE_DIVIDER = 5;
    private static final int SECOND_EXTRA_DISTANCE_PIVOT = 50;
    private static final int SECOND_EXTRA_DISTANCE_DIVIDER = 8;

    private int fare;

    public Fare(int distance) {
        if (distance <= FIRST_EXTRA_DISTANCE_PIVOT) {
            this.fare = DEFAULT_FARE;
            return;
        }
        if (distance <= SECOND_EXTRA_DISTANCE_PIVOT) {
            this.fare = DEFAULT_FARE
                + (distance / FIRST_EXTRA_DISTANCE_DIVIDER) * EXTRA_DISTANCE_FARE_MULTIPLE;
        } else {
            this.fare = DEFAULT_FARE
                + (distance / SECOND_EXTRA_DISTANCE_DIVIDER) * EXTRA_DISTANCE_FARE_MULTIPLE;
        }
    }

    public int getFare() {
        return fare;
    }
}
