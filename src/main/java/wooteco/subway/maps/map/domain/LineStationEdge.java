package wooteco.subway.maps.map.domain;

import java.util.Optional;
import wooteco.subway.maps.line.domain.LineStation;
import org.jgrapht.graph.DefaultWeightedEdge;

public class LineStationEdge extends DefaultWeightedEdge {
    private LineStation lineStation;
    private Long lineId;
    private Integer lineExtraCharge;

    public LineStationEdge(LineStation lineStation, Long lineId) {
        this.lineStation = lineStation;
        this.lineId = lineId;
    }

    public LineStationEdge(LineStation lineStation, Long lineId, Integer lineExtraCharge) {
        this.lineStation = lineStation;
        this.lineId = lineId;
        this.lineExtraCharge = lineExtraCharge;
    }

    public LineStation getLineStation() {
        return lineStation;
    }

    public Long getLineId() {
        return lineId;
    }

    public Integer getLineExtraCharge() {
        return Optional.ofNullable(lineExtraCharge).orElse(0);
    }

    @Override
    protected Object getSource() {
        return this.lineStation.getPreStationId();
    }

    @Override
    protected Object getTarget() {
        return this.lineStation.getStationId();
    }

    public Long extractTargetStationId(Long preStationId) {
        if (lineStation.getStationId().equals(preStationId)) {
            return lineStation.getPreStationId();
        } else if (lineStation.getPreStationId().equals(preStationId)) {
            return lineStation.getStationId();
        } else {
            throw new RuntimeException();
        }
    }
}
