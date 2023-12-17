package main

import (
	"fmt"
	"log"
	"os"
	"strconv"
	"strings"

	"github.com/kflim/adventofcode/2023/utility"
)

func main() {
	log.SetFlags(0)

	if len(os.Args) < 2 {
		log.Fatal("Missing filepath argument. Usage: go run day2.go <filepath>")
	}

	filepath := os.Args[1]
	lines := utility.GetLines(filepath)
	fmt.Printf(part1(lines, 12, 13, 14))
	fmt.Printf(part2(lines))
}

// part1 calculates the total sum of game IDs based on the given lines and color counts.
// It iterates over each line, checks if the game is possible with the given color counts,
// and if so, adds the game ID to the sum. Finally, it returns a formatted message with the total sum.
func part1(lines []string, redCount int, greenCount int, blueCount int) string {
	sum := int(0)

	for _, line := range lines {
		possible := checkGame(line, redCount, greenCount, blueCount)

		if possible {
			val := getGameId(line)
			sum += val
		}
	}

	message := fmt.Sprintf("Total sum: %d\n", sum)

	return message
}

// checkGame checks if the given line satisfies the maximum count constraints for each color.
// It takes the line string and the maximum count for red, green, and blue colors as input.
// It returns true if the line satisfies the constraints, otherwise false.
func checkGame(line string, redCount int, greenCount int, blueCount int) bool {
	maxValues := getMaxValues(line)

	for key, value := range maxValues {
		if key == "red" && value > redCount {
			return false
		} else if key == "green" && value > greenCount {
			return false
		} else if key == "blue" && value > blueCount {
			return false
		}
	}

	return true
}

// getMaxValues takes a line of input and returns a map containing the maximum values for each color.
// The input line should be in the format "color: value1, value2, ...; color: value1, value2, ...; ...".
// The function splits the line into rounds, where each round represents a set of color-value pairs.
// It then iterates over each round, splitting it into individual color-value pairs.
// For each pair, it extracts the value and color, converts the value to an integer, and updates the maximum value for the corresponding color in the map.
// If the color is not present in the map, a new entry is created with the extracted value.
// If the extracted value is greater than the current maximum value for the color, it is updated in the map.
// The function returns the map containing the maximum values for each color.
func getMaxValues(line string) map[string]int {
	maxValues := make(map[string]int)

	rounds := strings.Split(strings.Split(line, ": ")[1], "; ")
	for _, round := range rounds {
		colorValues := strings.Split(round, ", ")
		for _, colorValue := range colorValues {
			parts := strings.Split(colorValue, " ")
			value, err := strconv.Atoi(parts[0])

			if err != nil {
				log.Fatal(err)
			}

			color := parts[1]
			if currentValue, ok := maxValues[color]; ok {
				if value > currentValue {
					maxValues[color] = value
				}
			} else {
				maxValues[color] = value
			}
		}
	}

	return maxValues
}

// getGameId extracts the game ID from a given line.
// It expects the line to be in the format "Game ID: <id>".
// If the line is not in the expected format or if the ID cannot be converted to an integer, it will log a fatal error.
// It returns the extracted game ID as an integer.
func getGameId(line string) int {
	parts := strings.Split(line, ": ")
	gameId, err := strconv.Atoi(strings.Split(parts[0], " ")[1])

	if err != nil {
		log.Fatal(err)
	}

	return gameId
}

// part2 calculates the total sum of the minimum power values obtained from each line in the input.
// It iterates over the lines and calls the getGameMinimumPower function to get the minimum power value for each line.
// The minimum power values are then added to the sum.
// Finally, it returns a formatted message with the total sum.
func part2(lines []string) string {
	sum := int(0)

	for _, line := range lines {
		val := getGameMinimumPower(line)
		sum += val
	}

	message := fmt.Sprintf("Total sum: %d\n", sum)

	return message
}

// getGameMinimumPower calculates the minimum power for a game based on the given line.
// It multiplies all the maximum values obtained from getMaxValues function.
// The result is the minimum power required for the game.
func getGameMinimumPower(line string) int {
	minimumPower := int(1)
	maxValues := getMaxValues(line)

	for _, value := range maxValues {
		minimumPower *= value
	}

	return minimumPower
}
