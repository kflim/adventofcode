package main

import (
	"testing"

	"github.com/kflim/adventofcode/2023/utility"
	"github.com/stretchr/testify/assert"
)

// TestPart1 is a unit test function that tests the functionality of the part1 function.
// It verifies that the correct total sum is returned when given a set of lines.
func TestPart1(t *testing.T) {
	lines := []string{
		"467..114..",
		"...*......",
		"..35..633.",
		"......#...",
		"617*......",
		".....+.58.",
		"..592.....",
		"......755.",
		"...$.*....",
		".664.598..",
	}
	expected := "Total sum: 4361\n"
	result := part1(lines)
	assert.Equal(t, expected, result)
}

func TestPart1EmptyGrid(t *testing.T) {
	// TestPart1EmptyGrid tests the part1 function with an empty grid.
	// It expects the total sum to be 0.
	lines := []string{}
	expected := "Total sum: 0\n"
	result := part1(lines)
	assert.Equal(t, expected, result)
}

// TestIsAdjacentToSymbolTrue tests the IsAdjacentToSymbol function when the symbol is present in the grid.
// It verifies that the function correctly identifies if there is a symbol adjacent to the specified row and column range.
// The expected result is true.
func TestIsAdjacentToSymbolTrue(t *testing.T) {
	grid := [][]string{
		{"1", "#", "#"},
		{".", ".", "."},
		{"#", ".", "2"},
	}
	row := 0
	startCol := 0
	endCol := 1
	expected := true
	result := isAdjacentToSymbol(grid, row, startCol, endCol)
	assert.True(t, expected, result)
}

// TestIsAdjacentToSymbolFalse tests the IsAdjacentToSymbol function when the symbol is not adjacent to the given range of columns.
// It creates a grid with a symbol and empty spaces, and checks if the function returns false as expected.
func TestIsAdjacentToSymbolFalse(t *testing.T) {
	grid := [][]string{
		{"1", ".", "."},
		{".", ".", "."},
		{".", ".", "."},
	}
	row := 0
	startCol := 0
	endCol := 1
	expected := false
	result := isAdjacentToSymbol(grid, row, startCol, endCol)
	assert.False(t, expected, result)
}

// TestPart2EmptyGrid tests the part2 function with an empty grid.
// It expects the total sum to be 0.
func TestPart2EmptyGrid(t *testing.T) {
	lines := []string{}
	expected := "Total sum: 0\n"
	result := part2(lines)
	assert.Equal(t, expected, result)
}

// TestPart2GridNoSymbols tests the part2 function with a grid containing no symbols.
// It verifies that the expected total sum is 0.
func TestPart2GridNoSymbols(t *testing.T) {
	lines := []string{
		"123",
		"456",
		"789",
	}
	expected := "Total sum: 0\n"
	result := part2(lines)
	assert.Equal(t, expected, result)
}

// TestPart2GridNoNumbers tests the part2 function with a grid that contains no numbers.
// It verifies that the expected result is "Total sum: 0\n".
func TestPart2GridNoNumbers(t *testing.T) {
	lines := []string{
		"#..",
		".*.",
		"..$",
	}
	expected := "Total sum: 0\n"
	result := part2(lines)
	assert.Equal(t, expected, result)
}

// TestPart2GridWithSymbolsAndNumbers tests the part2 function with a grid containing symbols and numbers.
func TestPart2GridWithSymbolsAndNumbers(t *testing.T) {
	lines := []string{
		"467..114..",
		"...*......",
		"..35..633.",
		"......#...",
		"617*......",
		".....+.58.",
		"..592.....",
		"......755.",
		"...$.*....",
		".664.598..",
	}
	expected := "Total sum: 467835\n"
	result := part2(lines)
	assert.Equal(t, expected, result)
}

// Test case for getPotentialGearLocs function with an empty grid.
// It expects an empty slice as the result.
func TestGetPotentialGearLocsEmptyGrid(t *testing.T) {
	grid := [][]string{}
	expected := []string{}
	result := getPotentialGearLocs(grid)
	assert.Equal(t, expected, result)
}

// Test case to verify the behavior of getPotentialGearLocs function when there are no symbols in the grid.
func TestGetPotentialGearLocsNoSymbols(t *testing.T) {
	lines := []string{
		"123",
		"456",
		"789",
	}
	grid := utility.Create2DStrArray(lines)
	expected := []string{}
	result := getPotentialGearLocs(grid)
	assert.Equal(t, expected, result)
}

// TestGetPotentialGearLocsWithSymbols tests the getPotentialGearLocs function with symbols.
func TestGetPotentialGearLocsWithSymbols(t *testing.T) {
	lines := []string{
		"467..114..",
		"...*......",
		"..35..633.",
		"......#...",
		"617*......",
		".....+.58.",
		"..592.....",
		"......755.",
		"...$.*....",
		".664.598..",
	}
	grid := utility.Create2DStrArray(lines)
	expected := []string{
		"1,3",
		"4,3",
		"8,5",
	}
	result := getPotentialGearLocs(grid)
	assert.Equal(t, expected, result)
}

// Test case to check if a position in the grid is adjacent to a potential gear
func TestIsAdjacentToPotentialGearTrue(t *testing.T) {
	lines := []string{
		"467..114..",
		"...*......",
		"..35..633.",
		"......#...",
		"617*......",
		".....+.58.",
		"..592.....",
		"......755.",
		"...$.*....",
		".664.598..",
	}
	grid := utility.Create2DStrArray(lines)
	row := 0
	startCol := 0
	endCol := 4
	expected := true
	assert.True(t, expected, isAdjacentToPotentialGear(grid, row, startCol, endCol))
}

// TestIsAdjacentToPotentialGearFalse tests the isAdjacentToPotentialGear function when the expected result is false.
// It creates a grid from the given lines and checks if the gear at the specified row and column range is adjacent to a potential gear.
// The expected result is false.
func TestIsAdjacentToPotentialGearFalse(t *testing.T) {
	lines := []string{
		"467..114..",
		"...*......",
		"..35..633.",
		"......#...",
		"617*......",
		".....+.58.",
		"..592.....",
		"......755.",
		"...$.*....",
		".664.598..",
	}
	grid := utility.Create2DStrArray(lines)
	row := 0
	startCol := 5
	endCol := 8
	expected := false
	assert.False(t, expected, isAdjacentToPotentialGear(grid, row, startCol, endCol))
}
