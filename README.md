# Isochrone Map

Project under construction.

A desktop app to produce an isochrone map, implemented in Java with Dijkstra's shortest path algorithm using priority queues. An isochrone map like a travel time map shows us how far we can reach within a certain period. Such map is extremely handy when trying to decide where to live in a new city. In the infrastucture planning industry, it is useful for transit and active transportation planning because it demonstrates how accessibl or convenient the transportation network is.

## Features

Produces an isochrone map. If vertices are reachable with a specified threshold, they're displayed with a light blue fill. Else, they're displayed with a white fill. A source vertex is indicated with dark blue. Edges which are used as shortest paths to the reachable vertices are displayed with a solid line whereas others are displayed with a dashed line.

![Screenshot of a sample isochrone map](/img/sample-isochrone-map.png "Sample isochrone map")

## Implementation

Lorem.

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
