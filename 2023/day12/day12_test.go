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
