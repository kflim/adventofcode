package main

import (
	"testing"

	"github.com/stretchr/testify/assert"
)

func TestPart1(t *testing.T) {
	lines := []string{
		"O....#....",
		"O.OO#....#",
		".....##...",
		"OO.#O....O",
		".O.....O#.",
		"O.#..O.#.#",
		"..O..#O..O",
		".......O..",
		"#....###..",
		"#OO..#....",
	}
	expected := "Total sum: 136\n"
	result := part1(lines)
	assert.Equal(t, expected, result)
}

func TestPart1Empty(t *testing.T) {
	lines := []string{}
	expected := "Total sum: 0\n"
	result := part1(lines)
	assert.Equal(t, expected, result)
}

func TestGetLoad(t *testing.T) {
	lines := []string{
		"O....#....",
		"O.OO#....#",
		".....##...",
		"OO.#O....O",
		".O.....O#.",
		"O.#..O.#.#",
		"..O..#O..O",
		".......O..",
		"#....###..",
		"#OO..#....",
	}
	expected := 136
	result := getLoad(lines)
	assert.Equal(t, expected, result)
}

func TestGetLoadEmpty(t *testing.T) {
	lines := []string{}
	expected := 0
	result := getLoad(lines)
	assert.Equal(t, expected, result)
}
