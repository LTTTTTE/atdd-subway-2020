package wooteco.subway.maps.map.application;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.alg.shortestpath.KShortestPaths;
import org.springframework.stereotype.Service;
import wooteco.subway.maps.line.domain.Line;
import wooteco.subway.maps.map.domain.LineStationEdge;
import wooteco.subway.maps.map.domain.PathType;
import wooteco.subway.maps.map.domain.SubwayGraph;
import wooteco.subway.maps.map.domain.SubwayPath;

@Service
public class PathService {

    public SubwayPath findPath(List<Line> lines, Long source, Long target, PathType type) {
        SubwayGraph graph = new SubwayGraph(LineStationEdge.class);
        graph.addVertexWith(lines);
        graph.addEdge(lines, type);

        if (type.isArrivalTimeType()) {
            // K-최단경로
            List<GraphPath> paths = new KShortestPaths(graph, 200).getPaths(source, target);
            GraphPath kShortestPath = findKShortestPath(paths);

            return convertSubwayPath(kShortestPath);
        } else {
            // 다익스트라 최단 경로 찾기
            DijkstraShortestPath dijkstraShortestPath = new DijkstraShortestPath(graph);
            GraphPath<Long, LineStationEdge> path = dijkstraShortestPath.getPath(source, target);

            return convertSubwayPath(path);
        }
    }

    private SubwayPath convertSubwayPath(GraphPath graphPath) {
        return new SubwayPath(
            (List<LineStationEdge>) graphPath.getEdgeList().stream().collect(Collectors.toList()));
    }

    private GraphPath findKShortestPath(List<GraphPath> paths) {
        Collections.shuffle(paths);//gg
        return paths.get(0);
    }
}
