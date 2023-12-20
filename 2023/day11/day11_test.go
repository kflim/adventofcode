package main

import (
	"testing"

	"github.com/stretchr/testify/assert"
)

// TestPart1 is a unit test function that tests the functionality of the part1 function.
// It verifies that the correct total sum is returned when given a slice of lines.
// The expected total sum is compared with the actual result using the assert.Equal function.
// If the expected and actual results are equal, the test passes; otherwise, it fails.
func TestPart1(t *testing.T) {
	lines := []string{
		"...#......",
		".......#..",
		"#.........",
		"..........",
		"......#...",
		".#........",
		".........#",
		"..........",
		".......#..",
		"#...#.....",
	}
	expected := "Total sum: 374\n"
	result := part1(lines)
	assert.Equal(t, expected, result)
}

// TestPart1Empty is a unit test function that tests the behavior of the part1 function when the input lines are empty.
// It verifies that the expected result is "Total sum: 0\n".
func TestPart1Empty(t *testing.T) {
	lines := []string{}
	expected := "Total sum: 0\n"
	result := part1(lines)
	assert.Equal(t, expected, result)
}

// Test case for the getEmptyRows function.
// It checks if the function correctly identifies the empty rows in the grid.
func TestGetEmptyRows(t *testing.T) {
	grid := [][]string{
		{".", ".", "."},
		{"#", ".", "#"},
		{"#", "#", "#"},
	}
	expected := []int{0}
	result := getEmptyRows(grid)
	assert.Equal(t, expected, result)
}

// Test case for the getEmptyRows function when the grid is empty.
// It should return an empty slice of integers.
func TestGetEmptyRowsEmptyGrid(t *testing.T) {
	grid := [][]string{}
	expected := []int{}
	result := getEmptyRows(grid)
	assert.Equal(t, expected, result)
}

// Test case for the getEmptyCols function.
// It checks if the function correctly returns the indices of empty columns in the grid.
func TestGetEmptyCols(t *testing.T) {
	grid := [][]string{
		{".", ".", "."},
		{".", ".", "#"},
		{".", "#", "#"},
	}
	expected := []int{0}
	result := getEmptyCols(grid)
	assert.Equal(t, expected, result)
}

// Test case for the getEmptyCols function when the grid is empty.
// It should return an empty slice of integers.
func TestGetEmptyColsEmptyGrid(t *testing.T) {
	grid := [][]string{}
	expected := []int{}
	result := getEmptyCols(grid)
	assert.Equal(t, expected, result)
}

// TestGetDistanceNoEmptyRowsNoEmptyCols tests the getDistance function when there are no empty rows or empty columns.
// It verifies that the distance between two galaxies is calculated correctly.
func TestGetDistanceNoEmptyRowsNoEmptyCols(t *testing.T) {
	galaxy1 := []int{0, 0}
	galaxy2 := []int{2, 2}
	emptyRows := []int{}
	emptyCols := []int{}
	expected := 4
	result := getDistance(galaxy1, galaxy2, emptyRows, emptyCols)
	assert.Equal(t, expected, result)
}

// TestGetDistanceNoEmptyRows tests the getDistance function when there are no empty rows.
// It verifies that the distance between two galaxies is calculated correctly, taking into account the empty rows and columns.
// The expected result is compared with the actual result using the assert.Equal function from the testing package.
func TestGetDistanceNoEmptyRows(t *testing.T) {
	galaxy1 := []int{0, 0}
	galaxy2 := []int{2, 2}
	emptyRows := []int{}
	emptyCols := []int{1}
	expected := 5
	result := getDistance(galaxy1, galaxy2, emptyRows, emptyCols)
	assert.Equal(t, expected, result)
}

// TestGetDistanceNoEmptyCols tests the getDistance function when there are no empty columns.
// It verifies that the distance between two galaxies is calculated correctly, taking into account the empty rows and columns.
func TestGetDistanceNoEmptyCols(t *testing.T) {
	galaxy1 := []int{0, 0}
	galaxy2 := []int{2, 2}
	emptyRows := []int{1}
	emptyCols := []int{}
	expected := 5
	result := getDistance(galaxy1, galaxy2, emptyRows, emptyCols)
	assert.Equal(t, expected, result)
}

// TestGetDistance is a unit test function that tests the getDistance function.
// It verifies that the distance between two galaxies is calculated correctly,
// taking into account any empty rows and columns.
func TestGetDistance(t *testing.T) {
	galaxy1 := []int{0, 0}
	galaxy2 := []int{2, 2}
	emptyRows := []int{1}
	emptyCols := []int{1}
	expected := 6
	result := getDistance(galaxy1, galaxy2, emptyRows, emptyCols)
	assert.Equal(t, expected, result)
}

// TestPart2 tests the part2 function with a specific input and expected output.
// It verifies that the function returns the correct result.
func TestPart2(t *testing.T) {
	lines := []string{
		"...#......",
		".......#..",
		"#.........",
		"..........",
		"......#...",
		".#........",
		".........#",
		"..........",
		".......#..",
		"#...#.....",
	}
	expected := "Total sum: 82000210\n"
	result := part2(lines)
	assert.Equal(t, expected, result)
}

// TestPart2Empty tests the part2 function with an empty input.
// It expects the total sum to be 0.
func TestPart2Empty(t *testing.T) {
	lines := []string{}
	expected := "Total sum: 0\n"
	result := part2(lines)
	assert.Equal(t, expected, result)
}

// TestGetDistanceTwoNoEmptyRowsNoEmptyCols tests the getDistanceTwo function when there are no empty rows or empty columns.
// It verifies that the distance between two galaxies is calculated correctly.
func TestGetDistanceTwoNoEmptyRowsNoEmptyCols(t *testing.T) {
	galaxy1 := []int{0, 0}
	galaxy2 := []int{2, 2}
	emptyRows := []int{}
	emptyCols := []int{}
	expected := 4
	result := getDistanceTwo(galaxy1, galaxy2, emptyRows, emptyCols)
	assert.Equal(t, expected, result)
}

// TestGetDistanceTwoNoEmptyRows tests the getDistanceTwo function when there are no empty rows.
// It verifies that the function returns the expected distance between two galaxies.
func TestGetDistanceTwoNoEmptyRows(t *testing.T) {
	galaxy1 := []int{0, 0}
	galaxy2 := []int{2, 2}
	emptyRows := []int{}
	emptyCols := []int{1}
	expected := 1000003
	result := getDistanceTwo(galaxy1, galaxy2, emptyRows, emptyCols)
	assert.Equal(t, expected, result)
}

// Test case to calculate the distance between two galaxies when there are no empty columns.
// The first galaxy is located at coordinates (0, 0) and the second galaxy is located at coordinates (2, 2).
// There are no empty columns in the galaxy.
// The expected result is 1000003.
func TestGetDistanceTwoNoEmptyCols(t *testing.T) {
	galaxy1 := []int{0, 0}
	galaxy2 := []int{2, 2}
	emptyRows := []int{1}
	emptyCols := []int{}
	expected := 1000003
	result := getDistanceTwo(galaxy1, galaxy2, emptyRows, emptyCols)
	assert.Equal(t, expected, result)
}

// Test case for calculating the distance between two galaxies.
// The galaxies are represented by their coordinates (x, y).
// The emptyRows and emptyCols represent the empty rows and columns in the galaxy.
// The expected result is the calculated distance between the two galaxies.
func TestGetDistanceTwo(t *testing.T) {
	galaxy1 := []int{0, 0}
	galaxy2 := []int{2, 2}
	emptyRows := []int{1}
	emptyCols := []int{1}
	expected := 2000002
	result := getDistanceTwo(galaxy1, galaxy2, emptyRows, emptyCols)
	assert.Equal(t, expected, result)
}
