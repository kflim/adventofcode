package main

import (
	"fmt"
	"log"
	"os"

	"github.com/kflim/adventofcode/2023/utility"
)

func main() {
	log.SetFlags(0)

	if len(os.Args) < 2 {
		log.Fatal("Missing filepath argument. Usage: go run day11.go <filepath>")
	}

	filepath := os.Args[1]
	lines := utility.GetLines(filepath)
	fmt.Printf(part1(lines))
	fmt.Printf(part2(lines))
}

// part1 calculates the total steps required to traverse all galaxies in the grid.
// It takes a slice of strings representing the grid as input.
// It returns a string message containing the total steps.
func part1(lines []string) string {
	grid := utility.Create2DStrArray(lines)
	emptyRols := getEmptyRows(grid)
	emptyCols := getEmptyCols(grid)
	sum := 0

	galaxies := [][]int{}
	for i, line := range grid {
		for j, tile := range line {
			if tile != "." {
				galaxies = append(galaxies, []int{i, j})
			}
		}
	}

	for i, galaxy := range galaxies {
		for j := i + 1; j < len(galaxies); j++ {
			sum += getDistance(galaxy, galaxies[j], emptyRols, emptyCols)
		}
	}

	message := fmt.Sprintf("Total sum: %d\n", sum)

	return message
}

func getEmptyRows(grid [][]string) []int {
	emptyRows := []int{}
	for i, row := range grid {
		isEmpty := true
		for _, tile := range row {
			if tile != "." {
				isEmpty = false
				break
			}
		}
		if isEmpty {
			emptyRows = append(emptyRows, i)
		}
	}
	return emptyRows
}

func getEmptyCols(grid [][]string) []int {
	if len(grid) == 0 {
		return []int{}
	}

	emptyCols := []int{}
	for i := range grid[0] {
		isEmpty := true
		for j := range grid {
			if grid[j][i] != "." {
				isEmpty = false
				break
			}
		}
		if isEmpty {
			emptyCols = append(emptyCols, i)
		}
	}
	return emptyCols
}

// getDistance calculates the Manhattan distance between two points in a galaxy.
// It takes two slices of integers representing the coordinates of the two points,
// as well as two slices of integers representing the empty rows and empty columns in the galaxy.
// The function returns the sum of the absolute differences between the x-coordinates and y-coordinates of the two points,
// taking into account any empty rows or empty columns that lie between the points.
func getDistance(galaxy1 []int, galaxy2 []int, emptyRows []int, emptyCols []int) int {
	deltaX := galaxy1[0] - galaxy2[0]
	deltaY := galaxy1[1] - galaxy2[1]
	if deltaX < 0 {
		deltaX = -deltaX
	}
	if deltaY < 0 {
		deltaY = -deltaY
	}

	for i := range emptyRows {
		if emptyRows[i] > galaxy1[0] && emptyRows[i] < galaxy2[0] || emptyRows[i] > galaxy2[0] && emptyRows[i] < galaxy1[0] {
			deltaX++
		}
	}

	for i := range emptyCols {
		if emptyCols[i] > galaxy1[1] && emptyCols[i] < galaxy2[1] || emptyCols[i] > galaxy2[1] && emptyCols[i] < galaxy1[1] {
			deltaY++
		}
	}

	return deltaX + deltaY
}

// part2 calculates the total steps required to traverse all galaxies in the grid.
// It takes a slice of strings representing the grid as input.
// It returns a string message containing the total steps.
func part2(lines []string) string {
	grid := utility.Create2DStrArray(lines)
	emptyRols := getEmptyRows(grid)
	emptyCols := getEmptyCols(grid)
	sum := 0

	galaxies := [][]int{}
	for i, line := range grid {
		for j, tile := range line {
			if tile != "." {
				galaxies = append(galaxies, []int{i, j})
			}
		}
	}

	for i, galaxy := range galaxies {
		for j := i + 1; j < len(galaxies); j++ {
			sum += getDistanceTwo(galaxy, galaxies[j], emptyRols, emptyCols)
		}
	}

	message := fmt.Sprintf("Total sum: %d\n", sum)

	return message
}

// getDistanceTwo calculates the Manhattan distance between two points in a galaxy, taking into account empty rows and columns.
// It takes four parameters:
// - galaxy1: a slice of integers representing the coordinates of the first point in the galaxy.
// - galaxy2: a slice of integers representing the coordinates of the second point in the galaxy.
// - emptyRows: a slice of integers representing the coordinates of the empty rows in the galaxy.
// - emptyCols: a slice of integers representing the coordinates of the empty columns in the galaxy.
// It returns an integer representing the Manhattan distance between the two points, considering the larger empty rows and columns.
func getDistanceTwo(galaxy1 []int, galaxy2 []int, emptyRows []int, emptyCols []int) int {
	deltaX := galaxy1[0] - galaxy2[0]
	deltaY := galaxy1[1] - galaxy2[1]
	if deltaX < 0 {
		deltaX = -deltaX
	}
	if deltaY < 0 {
		deltaY = -deltaY
	}

	for i := range emptyRows {
		if emptyRows[i] > galaxy1[0] && emptyRows[i] < galaxy2[0] || emptyRows[i] > galaxy2[0] && emptyRows[i] < galaxy1[0] {
			deltaX += 999999
		}
	}

	for i := range emptyCols {
		if emptyCols[i] > galaxy1[1] && emptyCols[i] < galaxy2[1] || emptyCols[i] > galaxy2[1] && emptyCols[i] < galaxy1[1] {
			deltaY += 999999
		}
	}

	return deltaX + deltaY
}
