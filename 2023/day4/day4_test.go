package main

import (
	"testing"

	"github.com/stretchr/testify/assert"
)

func TestPart1(t *testing.T) {
	lines := []string{
		"Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53",
		"Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19",
		"Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1",
		"Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83",
		"Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36",
		"Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11",
	}
	expected := "Total sum: 13\n"
	result := part1(lines)
	assert.Equal(t, expected, result)
}

func TestPart1NoCards(t *testing.T) {
	lines := []string{}
	expected := "Total sum: 0\n"
	result := part1(lines)
	assert.Equal(t, expected, result)
}

func TestGetMatchCountEmptyLine(t *testing.T) {
	line := ""
	expected := 0
	result := getMatchCount(line)
	assert.Equal(t, expected, result)
}

func TestGetMatchNoMatch(t *testing.T) {
	line := "Card 1: 41 48 83 86 17 | 2 3 4 5 6"
	expected := 0
	result := getMatchCount(line)
	assert.Equal(t, expected, result)
}

func TestGetMatchAllMatch(t *testing.T) {
	line := "Card 1: 41 48 83 86 17 | 41 48 83 86 17"
	expected := 5
	result := getMatchCount(line)
	assert.Equal(t, expected, result)
}

func TestGetScoreLessThanTwo(t *testing.T) {
	matchCount := 1
	expected := 1
	result := getScore(matchCount)
	assert.Equal(t, expected, result)
}

func TestGetScoreGreaterThanTwo(t *testing.T) {
	matchCount := 5
	expected := 16
	result := getScore(matchCount)
	assert.Equal(t, expected, result)
}

func TestPart2(t *testing.T) {
	lines := []string{
		"Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53",
		"Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19",
		"Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1",
		"Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83",
		"Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36",
		"Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11",
	}
	expected := "Total sum: 30\n"
	result := part2(lines)
	assert.Equal(t, expected, result)
}

func TestPart2NoCards(t *testing.T) {
	lines := []string{}
	expected := "Total sum: 0\n"
	result := part2(lines)
	assert.Equal(t, expected, result)
}
