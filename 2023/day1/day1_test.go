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
	message := part1(lines)

	if message != "Total sum: 0\n" {
		t.Fatalf("Expected: Total sum: 0\n, got: %s", message)
	}
}

// TestMultipleLines tests the behavior of the part1 function when given multiple lines.
func TestPart1MultipleLines(t *testing.T) {
	lines := []string{"1", "2", "3", "1"}
	message := part1(lines)
	if message != "Total sum: 77\n" {
		t.Fatalf("Expected: Total sum: 77\n, got: %s", message)
	}
}

// TestPart2EmptyLines tests the behavior of the part2 function when given an empty slice.
func TestPart2EmptyLines(t *testing.T) {
	lines := []string{}
	message := part2(lines)
	if message != "Total sum: 0\n" {
		t.Fatalf("Expected: Total sum: 0\n, got: %s", message)
	}
}

// TestPart2SingleLine tests the behavior of the part2 function when given a single line.
func TestPart2SingleLineWithDigits(t *testing.T) {
	lines := []string{"615"}
	message := part2(lines)
	if message != "Total sum: 65\n" {
		t.Fatalf("Expected: Total sum: 65\n, got: %s", message)
	}
}

// TestPart2MultipleLines tests the behavior of the part2 function when given multiple lines.
func TestPart2MultipleLinesWithDigits(t *testing.T) {
	lines := []string{"1", "2", "3", "1"}
	message := part2(lines)
	if message != "Total sum: 77\n" {
		t.Fatalf("Expected: Total sum: 77\n, got: %s", message)
	}
}

// TestPart2SingleLine tests the behavior of the part2 function when given a single line.
func TestPart2SingleLineWithAscii(t *testing.T) {
	lines := []string{"1nineeightsevenkc8sixfour"}
	message := part2(lines)
	if message != "Total sum: 14\n" {
		t.Fatalf("Expected: Total sum: 14\n, got: %s", message)
	}
}

// TestPart2MultipleLines tests the behavior of the part2 function when given multiple lines.
func TestPart2MultipleLinesWithAscii(t *testing.T) {
	lines := []string{"two1nine", "eightwothree", "abcone2threexyz", "xtwone3four", "4nineeightseven2", "zoneight234", "7pqrstsixteen"}
	message := part2(lines)
	if message != "Total sum: 281\n" {
		t.Fatalf("Expected: Total sum: 281\n, got: %s", message)
	}
}
