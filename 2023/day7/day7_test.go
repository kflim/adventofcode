package main

import (
	"testing"

	"github.com/kflim/adventofcode/2023/day7/structs"
	"github.com/stretchr/testify/assert"
)

// TestPart1 is a unit test function that tests the functionality of the part1 function.
// It verifies that the correct total sum is returned when given a list of lines.
// The expected total sum is compared with the actual result using the assert.Equal function from the testing package.
// If the expected and actual results are equal, the test passes. Otherwise, it fails.
func TestPart1(t *testing.T) {
	lines := []string{
		"32T3K 765",
		"T55J5 684",
		"KK677 28",
		"KTJJT 220",
		"QQQJA 483",
	}
	expected := "Total sum: 6440\n"
	result := part1(lines)
	assert.Equal(t, expected, result)
}

// TestPart1Empty tests the part1 function with an empty input.
// It expects the total sum to be 0.
func TestPart1Empty(t *testing.T) {
	lines := []string{}
	expected := "Total sum: 0\n"
	result := part1(lines)
	assert.Equal(t, expected, result)
}

// TestSortHands is a unit test function that tests the sortHands function.
// It verifies that the hands are sorted correctly based on the specified hand type order.
// The expected result is compared against the actual result using the assert.Equal function.
func TestSortHands(t *testing.T) {
	hands := []structs.CamelHand{
		{Hand: "32T3K", Bid: 765},
		{Hand: "T55J5", Bid: 684},
		{Hand: "KK677", Bid: 28},
		{Hand: "KTJJT", Bid: 220},
		{Hand: "QQQJA", Bid: 483},
	}
	expected := []structs.CamelHand{
		{Hand: "32T3K", Bid: 765},
		{Hand: "KTJJT", Bid: 220},
		{Hand: "KK677", Bid: 28},
		{Hand: "T55J5", Bid: 684},
		{Hand: "QQQJA", Bid: 483},
	}
	for i := range hands {
		hands[i].CalculateHandType()
		expected[i].CalculateHandType()
	}
	sortHands(hands, "AKQJT98765432")
	assert.Equal(t, expected, hands)
}

// TestPart2 is a unit test function that tests the functionality of the part2 function.
// It verifies that the part2 function returns the expected result when given a set of input lines.
// The expected result is "Total sum: 5905\n".
// The test uses the assert.Equal function from the testify package to compare the expected result with the actual result.
func TestPart2(t *testing.T) {
	lines := []string{
		"32T3K 765",
		"T55J5 684",
		"KK677 28",
		"KTJJT 220",
		"QQQJA 483",
	}
	expected := "Total sum: 5905\n"
	result := part2(lines)
	assert.Equal(t, expected, result)
}

// TestSortHandsWithJ is a unit test function that tests the sorting of CamelHands with the letter 'J'.
// It initializes a slice of CamelHands and an expected sorted slice of CamelHands.
// Then it calculates the hand type for each CamelHand and sorts the hands using the sortHands function.
// Finally, it asserts that the sorted hands match the expected sorted hands using the assert.Equal function.
func TestSortHandsWithJ(t *testing.T) {
	hands := []structs.CamelHand{
		{Hand: "32T3K", Bid: 765},
		{Hand: "T55J5", Bid: 684},
		{Hand: "KK677", Bid: 28},
		{Hand: "KTJJT", Bid: 220},
		{Hand: "QQQJA", Bid: 483},
	}
	expected := []structs.CamelHand{
		{Hand: "32T3K", Bid: 765},
		{Hand: "KK677", Bid: 28},
		{Hand: "T55J5", Bid: 684},
		{Hand: "QQQJA", Bid: 483},
		{Hand: "KTJJT", Bid: 220},
	}
	for i := range hands {
		hands[i].CalculateHandTypeTwo()
		expected[i].CalculateHandTypeTwo()
	}
	sortHands(hands, "AKQT98765432J")
	assert.Equal(t, expected, hands)
}
