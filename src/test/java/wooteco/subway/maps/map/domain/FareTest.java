package wooteco.subway.maps.map.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class FareTest {

    private static Stream<Arguments> generateFare() {
        return Stream.of(
            Arguments.of(1, 500, 6, 1050),
            Arguments.of(1, 500, 17, 1470),
            Arguments.of(1, 500, 30, 1750),
            Arguments.of(18, 500, 6, 1200),
            Arguments.of(18, 500, 17, 1710),
            Arguments.of(18, 500, 30, 2050),
            Arguments.of(77, 500, 6, 1500),
            Arguments.of(77, 500, 17, 2190),
            Arguments.of(77, 500, 30, 2650)
        );
    }

    @DisplayName("통합요금테스트")
    @ParameterizedTest
    @MethodSource("generateFare")
    void fareTest(int distance, int lineExtraCharge, int age, int expectedFare) {
        Fare fare = new Fare(distance, lineExtraCharge, age);

        assertThat(fare.getFare()).isEqualTo(expectedFare);
    }
}