# Isochrone Map

A desktop app to produce an isochrone map, implemented in Java with Dijkstra's shortest path algorithm. An isochrone map like a travel time map shows us how far we can reach within a certain period. Such map is extremely handy when trying to decide where to live in a new city. In city planning, it is useful in demonstrating the connectivity and accessibility of transit and active transportation network.

## Features

Produces an isochrone map. If vertices are reachable with a specified threshold, they're displayed with a light blue fill. Else, they're displayed with a white fill. A source vertex is marked with dark blue. Edges which are used as shortest paths to the reachable vertices are displayed with a solid line whereas others are displayed with a dashed line.

![Screenshot of a sample isochrone map](/img/sample-isochrone-map.png "Sample isochrone map")

## Implementation

Dijkstra's algorithm is used as a shortest path algorithm and is implemented using priority queues in Java. This implementation of Dijkstra's algorithm has the worst case performance of `Ο((|V|+|E|)log|V|)` where `V` is the number of vertices and `E` is the number of edges. This is better than the implementation without priority queues with `Ο(|V|²)`.

## Setup

Clone this project:

```bash
$ git clone https://github.com/shunhiguchi/isochrone-map.git
```

Change directory to the source directory:

```bash
$ cd src
```

Build the project:

```bash
$ javac *.java
```

## Usage

To produce an isochrone map with a source vertex of 6 and a threshold cost of 7

```bash
$ java IsochroneMap vertices.csv edges.csv 6 7
```

## License

See [LICENSE](/LICENSE).
