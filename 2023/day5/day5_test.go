package main

import (
	"testing"

	"github.com/stretchr/testify/assert"
)

// TestPart1 is a unit test function that tests the functionality of the part1 function.
// It verifies that the lowest location number returned by the part1 function matches the expected value.
func TestPart1(t *testing.T) {
	file := "seeds: 79 14 55 13\n\nseed-to-soil map:\n50 98 2\n52 50 48\n\nsoil-to-fertilizer map:\n0 15 37\n37 52 2\n39 0 15\n\nfertilizer-to-water map:\n49 53 8\n0 11 42\n42 0 7\n57 7 4\n\nwater-to-light map:\n8 18 7\n18 25 70\n\nlight-to-temperature map:\n45 77 23\n81 45 19\n68 64 13\n\ntemperature-to-humidity map:\n0 69 1\n1 0 69\n\nhumidity-to-location map:\n60 56 37\n56 93 4"
	expected := "Lowest location number: 35\n"
	result := part1(file)
	assert.Equal(t, expected, result)
}

// TestPart2 is a unit test function that tests the functionality of the part2 function.
// It verifies if the expected output matches the actual output.
// The test case includes a file with seed-to-soil, soil-to-fertilizer, fertilizer-to-water,
// water-to-light, light-to-temperature, temperature-to-humidity, and humidity-to-location maps.
// The function expects the lowest location number to be 46.
func TestPart2(t *testing.T) {
	file := "seeds: 79 14 55 13\n\nseed-to-soil map:\n50 98 2\n52 50 48\n\nsoil-to-fertilizer map:\n0 15 37\n37 52 2\n39 0 15\n\nfertilizer-to-water map:\n49 53 8\n0 11 42\n42 0 7\n57 7 4\n\nwater-to-light map:\n8 18 7\n18 25 70\n\nlight-to-temperature map:\n45 77 23\n81 45 19\n68 64 13\n\ntemperature-to-humidity map:\n0 69 1\n1 0 69\n\nhumidity-to-location map:\n60 56 37\n56 93 4"
	expected := "Lowest location number: 46\n"
	result := part2(file)
	assert.Equal(t, expected, result)
}
