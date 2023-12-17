package main

import (
	"fmt"
	"log"
	"os"

	"github.com/kflim/adventofcode/2023/utility"
)

func main() {
	log.SetFlags(0)

	if len(os.Args) < 2 {
		log.Fatal("Missing filepath argument. Usage: go run day6.go <filepath>")
	}

	filepath := os.Args[1]
	lines := utility.GetLines(filepath)
	fmt.Printf(part1(lines))
	fmt.Printf(part2(lines))
}

// part1 calculates the total number of ways to beat the race record for each race time and distance pair.
// It takes a slice of strings representing the race times and distances as input.
// It returns a string message with the total number of ways.
func part1(lines []string) string {
	product := 1

	raceTimes := utility.GetSeparatedNumbersAfterColon(lines[0])
	raceDist := utility.GetSeparatedNumbersAfterColon(lines[1])

	for i, raceTime := range raceTimes {
		ways := getWaysToBeatRecord(raceTime, raceDist[i])
		fmt.Println(ways)
		product *= ways
	}

	message := fmt.Sprintf("Total ways: %d\n", product)

	return message
}

// getWaysToBeatRecord calculates the number of ways a race can be completed within a given time and distance,
// such that the record is beaten. It returns the count of such ways.
// Parameters:
// - raceTime: The total time available for the race.
// - raceDist: The total distance of the race.
// Returns:
// - The number of ways to beat the record.
func getWaysToBeatRecord(raceTime, raceDist int) int {
	low := 1
	high := raceTime - 1

	for low <= high {
		if low*high > raceDist {
			return high - low + 1
		}
		low++
		high--
	}

	return 0
}

// part2 calculates the total number of ways to beat a race record based on the given race times and distances.
// It takes a slice of strings as input, where each string represents a line of input data.
// The first line contains the race times separated by colons, and the second line contains the race distances separated by colons.
// It returns a string message indicating the total number of ways to beat the record.
func part2(lines []string) string {
	raceTimes := utility.GetSeparatedNumbersAfterColon(lines[0])
	raceDist := utility.GetSeparatedNumbersAfterColon(lines[1])

	totalRaceTimeAscii := ""
	for _, time := range raceTimes {
		totalRaceTimeAscii += utility.ConvertIntToAscii(time)
	}
	totalRaceTime := utility.ConvertAsciiToInt(totalRaceTimeAscii)

	totalDistAscii := ""
	for _, dist := range raceDist {
		totalDistAscii += utility.ConvertIntToAscii(dist)
	}
	totalDist := utility.ConvertAsciiToInt(totalDistAscii)

	ways := getWaysToBeatRecord(totalRaceTime, totalDist)

	message := fmt.Sprintf("Total ways: %d\n", ways)

	return message
}
