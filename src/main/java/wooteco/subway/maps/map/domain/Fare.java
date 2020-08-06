package wooteco.subway.maps.map.domain;

public class Fare {

    private static final int DEFAULT_FARE = 1250;
    private static final int EXTRA_DISTANCE_FARE_MULTIPLE = 100;
    private static final int FIRST_EXTRA_DISTANCE_PIVOT = 10;
    private static final int FIRST_EXTRA_DISTANCE_DIVIDER = 5;
    private static final int SECOND_EXTRA_DISTANCE_PIVOT = 50;
    private static final int SECOND_EXTRA_DISTANCE_DIVIDER = 8;
    private static final int YOUNG_START_AGE = 6;
    private static final int STUDENT_START_AGE = 13;
    private static final int STUDENT_END_AGE = 19;
    private static final int DEFAULT_DISCOUNT_GONGJAE = 350;
    private static final double YOUNG_DISCOUNT_RATIO = 0.5;
    private static final double STUDENT_DISCOUNT_RATIO = 0.2;

    private int fare;

    public Fare(int distance, int lineExtraCharge, int age) {
        if (distance <= FIRST_EXTRA_DISTANCE_PIVOT) {
            this.fare = DEFAULT_FARE;
        }
        if (distance <= SECOND_EXTRA_DISTANCE_PIVOT && distance > FIRST_EXTRA_DISTANCE_PIVOT) {
            this.fare = DEFAULT_FARE
                + (distance / FIRST_EXTRA_DISTANCE_DIVIDER) * EXTRA_DISTANCE_FARE_MULTIPLE;
        } else {
            this.fare = DEFAULT_FARE
                + (distance / SECOND_EXTRA_DISTANCE_DIVIDER) * EXTRA_DISTANCE_FARE_MULTIPLE;
        }
        this.fare += lineExtraCharge;
        this.fare = calculateFareWithAge(this.fare, age);
    }

    private int calculateFareWithAge(int fare, int age) {
        if (age >= YOUNG_START_AGE && age < STUDENT_START_AGE) {
            return (int) (fare - (fare - DEFAULT_DISCOUNT_GONGJAE) * YOUNG_DISCOUNT_RATIO);
        }
        if (age >= STUDENT_START_AGE && age < STUDENT_END_AGE) {
            return (int) (fare - (fare - DEFAULT_DISCOUNT_GONGJAE) * STUDENT_DISCOUNT_RATIO);
        } else {
            return fare;
        }
    }

    public int getFare() {
        return fare;
    }
}
