package main

import (
	"testing"

	"github.com/stretchr/testify/assert"
)

// TestPart1 is a unit test function that tests the functionality of the part1 function.
// It verifies that the part1 function returns the expected result when given a specific input.
// The input consists of a slice of strings representing lines of data.
// The expected result is a string representing the total number of ways calculated by the part1 function.
// The function uses the assert.Equal function from the testing package to compare the expected result with the actual result.
func TestPart1(t *testing.T) {
	lines := []string{
		"Time:      7  15   30",
		"Distance:  9  40  200",
	}
	expected := "Total ways: 288\n"
	result := part1(lines)
	assert.Equal(t, expected, result)
}

// TestGetWaysToBeatRecord is a unit test function that tests the getWaysToBeatRecord function.
// It verifies that the function returns the expected result when given a specific race time and distance.
// Parameters:
//
//	t: The testing.T object for running the test.
//
// Returns: None.
func TestGetWaysToBeatRecord(t *testing.T) {
	raceTime := 7
	raceDist := 9
	expected := 4
	result := getWaysToBeatRecord(raceTime, raceDist)
	assert.Equal(t, expected, result)
}

// TestGetWaysToBeatRecordNoTime is a unit test function that tests the getWaysToBeatRecord function
// when the race time is 0 and the race distance is 1. It expects the result to be 0.
func TestGetWaysToBeatRecordNoTime(t *testing.T) {
	raceTime := 0
	raceDist := 1
	expected := 0
	result := getWaysToBeatRecord(raceTime, raceDist)
	assert.Equal(t, expected, result)
}

// Test case to verify the behavior of getWaysToBeatRecord function when race distance is 0.
// It expects the result to be 1, indicating that there is 1 way to beat the record when the race distance is 0.
func TestGetWaysToBeatRecordNoDist(t *testing.T) {
	raceTime := 2
	raceDist := 0
	expected := 1
	result := getWaysToBeatRecord(raceTime, raceDist)
	assert.Equal(t, expected, result)
}

// TestPart2 is a unit test function that tests the functionality of the part2 function.
// It verifies that the correct result is returned when the function is called with the given input.
// The input consists of an array of strings representing lines of data.
// The expected output is a string representing the total number of ways.
// The function uses the assert.Equal function from the testing package to compare the expected and actual results.
func TestPart2(t *testing.T) {
	lines := []string{
		"Time:      7  15   30",
		"Distance:  9  40  200",
	}
	expected := "Total ways: 71503\n"
	result := part2(lines)
	assert.Equal(t, expected, result)
}
