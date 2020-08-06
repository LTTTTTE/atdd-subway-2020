package wooteco.subway.maps.map.domain;

import wooteco.subway.maps.line.domain.LineStation;

import java.util.function.Function;

public enum PathType {
    DISTANCE(lineStation -> lineStation.getDistance()),

    DURATION(lineStation -> lineStation.getDuration()),

    ARRIVAL_TIME(lineStation -> lineStation.getDuration());

    private Function<LineStation, Integer> expression;

    PathType(Function<LineStation, Integer> expression) {
        this.expression = expression;
    }

    public boolean isArrivalTimeType() {
        return this.equals(ARRIVAL_TIME);
    }

    public int findWeightOf(LineStation lineStation) {
        return expression.apply(lineStation);
    }
}
