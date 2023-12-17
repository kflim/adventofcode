package main

import (
	"testing"

	"github.com/stretchr/testify/assert"
)

func TestPart1(t *testing.T) {
	lines := []string{
		"Time:      7  15   30",
		"Distance:  9  40  200",
	}
	expected := "Total ways: 288\n"
	result := part1(lines)
	assert.Equal(t, expected, result)
}

func TestGetWaysToBeatRecord(t *testing.T) {
	raceTime := 7
	raceDist := 9
	expected := 4
	result := getWaysToBeatRecord(raceTime, raceDist)
	assert.Equal(t, expected, result)
}

func TestGetWaysToBeatRecordNoTime(t *testing.T) {
	raceTime := 0
	raceDist := 1
	expected := 0
	result := getWaysToBeatRecord(raceTime, raceDist)
	assert.Equal(t, expected, result)
}

func TestGetWaysToBeatRecordNoDist(t *testing.T) {
	raceTime := 2
	raceDist := 0
	expected := 1
	result := getWaysToBeatRecord(raceTime, raceDist)
	assert.Equal(t, expected, result)
}

func TestPart2(t *testing.T) {
	lines := []string{
		"Time:      7  15   30",
		"Distance:  9  40  200",
	}
	expected := "Total ways: 71503\n"
	result := part2(lines)
	assert.Equal(t, expected, result)
}
