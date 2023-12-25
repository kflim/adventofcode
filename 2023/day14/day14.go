package main

import (
	"fmt"
	"log"
	"os"

	"github.com/kflim/adventofcode/2023/utility"
)

const ROUND_ROCK = "O"
const CUBE_ROCK = "#"

func main() {
	log.SetFlags(0)

	if len(os.Args) < 2 {
		log.Fatal("Missing filepath argument. Usage: go run day11.go <filepath>")
	}

	filepath := os.Args[1]
	lines := utility.GetLines(filepath)
	fmt.Printf(part1(lines))
	// fmt.Printf(part2(lines))
}

// part1 calculates the total sum of the load on the north support beams from the given lines.
// It returns a formatted message with the total sum.
func part1(lines []string) string {
	message := fmt.Sprintf("Total sum: %d\n", getLoad(lines))
	return message
}

// getLoad calculates the load value based on the given lines.
// It initializes a 2D slice dp to store the steps towards the north possible for each position.
// It then calls findStepsToMove and sumStepsToMove functions to calculate the possible number of steps towards the north.
// Finally, it returns the sum of the steps to move as the load value.
func getLoad(lines []string) int {
	if len(lines) == 0 {
		return 0
	}

	dp := make([][]int, len(lines))

	for i := range dp {
		dp[i] = make([]int, len(lines[0]))
	}

	for j, char := range lines[0] {
		if string(char) == "." {
			dp[0][j] = 1
		} else {
			dp[0][j] = 0
		}
	}

	findStepsToMove(lines, dp)

	return sumStepsToMove(lines, dp)
}

// findStepsToMove calculates the number of steps towards the north possible through a grid of rocks.
// It takes in a slice of strings representing the grid and a 2D slice of integers representing the dynamic programming table.
// The function iterates through the grid and updates the dynamic programming table based on the type of rock encountered.
// The ROUND_ROCK type maintains the same number of steps as the previous row, the CUBE_ROCK type has 0 steps, and other types increment the steps by 1.
func findStepsToMove(lines []string, dp [][]int) {
	for i := 1; i < len(lines); i++ {
		for j, char := range lines[i] {
			switch string(char) {
			case ROUND_ROCK:
				dp[i][j] = dp[i-1][j]
				break
			case CUBE_ROCK:
				dp[i][j] = 0
				break
			default:
				dp[i][j] = dp[i-1][j] + 1
			}
		}
	}
}

// sumStepsToMove calculates the sum of steps towards the north possible based on the given lines and dp matrix.
// It iterates through each element of the dp matrix and checks if the corresponding element in the lines slice is equal to ROUND_ROCK.
// If it is, it adds the difference between the number of rows and the current row index, plus the value in the current column of the dp matrix, to the sum.
// This amount is equal to the final position relative to the south.
// Finally, it returns the sum of steps.
func sumStepsToMove(lines []string, dp [][]int) int {
	rows := len(dp)
	sum := 0
	for i, row := range dp {
		for j, col := range row {
			if string(lines[i][j]) == ROUND_ROCK {
				sum += rows - i + col
			}
		}
	}
	return sum
}
