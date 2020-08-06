package wooteco.subway.maps.map.domain;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.stream.Collectors;

public class SubwayPath {
    private List<LineStationEdge> lineStationEdges;

    public SubwayPath(List<LineStationEdge> lineStationEdges) {
        this.lineStationEdges = lineStationEdges;
    }

    public List<LineStationEdge> getLineStationEdges() {
        return lineStationEdges;
    }

    public List<Long> extractStationId() {
        List<Long> stationIds = Lists.newArrayList(lineStationEdges.get(0).getLineStation().getPreStationId());
        stationIds.addAll(lineStationEdges.stream()
                .map(it -> it.getLineStation().getStationId())
                .collect(Collectors.toList()));

        return stationIds;
    }

    public int getMaxExtraCharge() {
        return lineStationEdges.stream()
            .mapToInt(LineStationEdge::getLineExtraCharge)
            .sum();
    }

    public int calculateDuration() {
        return lineStationEdges.stream().mapToInt(it -> it.getLineStation().getDuration()).sum();
    }

    public int calculateDistance() {
        return lineStationEdges.stream().mapToInt(it -> it.getLineStation().getDistance()).sum();
    }

    public int calculateFare() {
        final int distance = calculateDistance();
        final int extraLineCharge = getMaxExtraCharge();

        return calculateFareWithDistance(distance) + extraLineCharge;
    }

    public int calculateFareWithDistance(int distance) {
        if (distance <= 10) {
            return 1250;
        }
        if (distance <= 50) {
            return 1250 + (distance / 5) * 100;
        } else {
            return 1250 + (distance / 8) * 100;
        }
    }
}
