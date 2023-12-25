package main

import (
	"testing"

	"github.com/stretchr/testify/assert"
)

func TestPart1(t *testing.T) {
	lines := []string{
		"#.##..##.",
		"..#.##.#.",
		"##......#",
		"##......#",
		"..#.##.#.",
		"..##..##.",
		"#.#.##.#.",
		"",
		"#...##..#",
		"#....#..#",
		"..##..###",
		"#####.##.",
		"#####.##.",
		"..##..###",
		"#....#..#",
	}
	expected := "Total sum: 405\n"
	result := part1(lines)
	assert.Equal(t, expected, result)
}

func TestPart1Empty(t *testing.T) {
	lines := []string{}
	expected := "Total sum: 0\n"
	result := part1(lines)
	assert.Equal(t, expected, result)
}

func TestSumNotesCol(t *testing.T) {
	lines := []string{
		"#.##..##.",
		"..#.##.#.",
		"##......#",
		"##......#",
		"..#.##.#.",
		"..##..##.",
		"#.#.##.#.",
	}
	expected := 5
	result := sumNotes(lines, 0)
	assert.Equal(t, expected, result)
}

func TestSumNotesRow(t *testing.T) {
	lines := []string{
		"#...##..#",
		"#....#..#",
		"..##..###",
		"#####.##.",
		"#####.##.",
		"..##..###",
		"#....#..#",
	}
	expected := 400
	result := sumNotes(lines, 0)
	assert.Equal(t, expected, result)
}

func TestSumNotesEmpty(t *testing.T) {
	lines := []string{}
	expected := 0
	result := sumNotes(lines, 0)
	assert.Equal(t, expected, result)
}

func TestCalcForLinesBetweenCols(t *testing.T) {
	notes := [][]string{
		{"#", ".", "#", "#", ".", ".", "#", "#", "."},
		{".", ".", "#", ".", "#", "#", ".", "#", "."},
		{"#", "#", ".", ".", ".", ".", ".", ".", "#"},
		{"#", "#", ".", ".", ".", ".", ".", ".", "#"},
		{".", ".", "#", ".", "#", "#", ".", "#", "."},
		{".", ".", "#", "#", ".", ".", "#", "#", "."},
		{"#", ".", "#", ".", "#", "#", ".", "#", "."},
	}
	expected := 5
	result := calcForLinesBetweenCols(notes, 0)
	assert.Equal(t, expected, result)
}

func TestCalcForLinesBetweenColsEmpty(t *testing.T) {
	notes := [][]string{}
	expected := 0
	result := calcForLinesBetweenCols(notes, 0)
	assert.Equal(t, expected, result)
}

func TestCalcForLinesBetweenRows(t *testing.T) {
	notes := [][]string{
		{"#", ".", ".", ".", "#", "#", ".", ".", "#"},
		{"#", ".", ".", ".", ".", "#", ".", ".", "#"},
		{".", ".", "#", "#", ".", ".", "#", "#", "#"},
		{"#", "#", "#", "#", "#", ".", "#", "#", "."},
		{"#", "#", "#", "#", "#", ".", "#", "#", "."},
		{".", ".", "#", "#", ".", ".", "#", "#", "#"},
		{"#", ".", ".", ".", ".", "#", ".", ".", "#"},
	}
	expected := 400
	result := calcForLinesBetweenRows(notes, 0)
	assert.Equal(t, expected, result)
}

func TestCalcForLinesBetweenRowsEmpty(t *testing.T) {
	notes := [][]string{}
	expected := 0
	result := calcForLinesBetweenRows(notes, 0)
	assert.Equal(t, expected, result)
}

func TestPart2(t *testing.T) {
	lines := []string{
		"#.##..##.",
		"..#.##.#.",
		"##......#",
		"##......#",
		"..#.##.#.",
		"..##..##.",
		"#.#.##.#.",
		"",
		"#...##..#",
		"#....#..#",
		"..##..###",
		"#####.##.",
		"#####.##.",
		"..##..###",
		"#....#..#",
	}
	expected := "Total sum: 400\n"
	result := part2(lines)
	assert.Equal(t, expected, result)
}

func TestPart2Empty(t *testing.T) {
	lines := []string{}
	expected := "Total sum: 0\n"
	result := part2(lines)
	assert.Equal(t, expected, result)
}
