# Isochrone Map

Project under construction.

A desktop app to produce an isochrone map, implemented in Java with Dijkstra's shortest path algorithm using priority queues.

## Getting started

Clone this repository

```bash
$ git clone https://github.com/shunhiguchi/isochrone-map.git
```

Change directory to the source directory

```bash
$ cd src
```

Build the project

```bash
$ javac *.java
```

## Usage

To produce an isochrone map with a source vertex of 6 and a threshold cost of 7

```bash
$ java IsochroneMap vertices.csv edges.csv 6 7
```

## License

See [LICENSE](LICENSE).
