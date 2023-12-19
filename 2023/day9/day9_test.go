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
		"0 3 6 9 12 15",
		"1 3 6 10 15 21",
		"10 13 16 21 30 45",
	}
	expected := "Total sum: 114\n"
	result := part1(lines)
	assert.Equal(t, expected, result)
}

// TestPart1Empty is a unit test function that tests the behavior of the part1 function when the input lines are empty.
// It verifies that the expected total sum is 0.
func TestPart1Empty(t *testing.T) {
	lines := []string{}
	expected := "Total sum: 0\n"
	result := part1(lines)
	assert.Equal(t, expected, result)
}

// TestPredictNextValue tests the predictNextValue function by providing a line of numbers
// and asserting that the predicted next value matches the expected value.
func TestPredictNextValue(t *testing.T) {
	line := "0 3 6 9 12 15"
	expected := 18
	result := predictNextValue(line)
	assert.Equal(t, expected, result)
}

// TestPart2 is a unit test function that tests the functionality of the part2 function.
// It takes an array of strings representing lines of numbers and compares the expected result with the actual result.
// The expected result is a string representing the total sum, and the actual result is the result of calling the part2 function.
// It uses the assert.Equal function from the testing package to compare the expected and actual results.
func TestPart2(t *testing.T) {
	lines := []string{
		"0 3 6 9 12 15",
		"1 3 6 10 15 21",
		"10 13 16 21 30 45",
	}
	expected := "Total sum: 2\n"
	result := part2(lines)
	assert.Equal(t, expected, result)
}

// TestPredictNextValueTwo tests the predictNextValueTwo function.
// It verifies that the function correctly predicts the next value in a given line of numbers.
func TestPredictNextValueTwo(t *testing.T) {
	line := "0 3 6 9 12 15"
	expected := -3
	result := predictNextValueTwo(line)
	assert.Equal(t, expected, result)
}
