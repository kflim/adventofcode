package main

import (
	"os"
	"testing"

	"github.com/kflim/adventofcode/2023/utility"
)

// TestEmptyFile tests the behavior of the part1 function when given an empty file.
func TestPart1EmptyFile(t *testing.T) {
	// Create empty file
	file, err := os.Create("empty.txt")
	if err != nil {
		t.Fatalf("Failed to create empty file: %v", err)
	}
	defer func() {
		file.Close()
		os.Remove("empty.txt") // Delete the file
	}()

	// Run GetLines on the empty file
	lines := utility.GetLines("empty.txt")
	if err != nil {
		t.Fatalf("Failed to parse empty file: %v", err)
	}

	// Assert the result
	message := part1(lines, 12, 13, 14)

	if message != "Total sum: 0\n" {
		t.Fatalf("Expected: Total sum: 0\n, got: %s", message)
	}
}

// TestPart1 is a unit test function that tests the functionality of the part1 function.
// It takes an array of strings as input and an expected string result.
// It iterates over the test cases, calls the part1 function with the input and compares the result with the expected value.
// If the result does not match the expected value, it reports an error.
func TestPart1(t *testing.T) {
	// test cases and expected results
	testCases := []struct {
		input    []string
		expected string
	}{
		{[]string{
			"Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green",
			"Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue",
			"Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red",
			"Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red",
			"Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green"}, "Total sum: 8\n"},
	}

	// iterate over test cases
	for _, tc := range testCases {
		// call part1 function with input and expected values
		result := part1(tc.input, 12, 13, 14)
		// compare result with expected value
		if result != tc.expected {
			// report error if result does not match expected value
			t.Errorf("Expected: %s, got: %s", tc.expected, result)
		}
	}
}

func TestPart2(t *testing.T) {
	// TestPart2 tests the part2 function.
	// It verifies that the function returns the expected output for the given input.
	testCases := []struct {
		input    []string
		expected string
	}{
		{[]string{
			"Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green",
			"Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue",
			"Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red",
			"Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red",
			"Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green"}, "Total sum: 2286\n"},
	}

	for _, tc := range testCases {
		result := part2(tc.input)
		if result != tc.expected {
			t.Errorf("Expected: %s, got: %s", tc.expected, result)
		}
	}
}
