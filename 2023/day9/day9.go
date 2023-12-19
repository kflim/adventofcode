package main

import (
	"fmt"
	"log"
	"os"
	"strings"

	"github.com/kflim/adventofcode/2023/utility"
)

func main() {
	log.SetFlags(0)

	if len(os.Args) < 2 {
		log.Fatal("Missing filepath argument. Usage: go run day9.go <filepath>")
	}

	filepath := os.Args[1]
	lines := utility.GetLines(filepath)
	fmt.Printf(part1(lines))
	fmt.Printf(part2(lines))
}

// part1 calculates the total sum of predicted values for each line in the input.
// It iterates over the lines and calls the predictNextValue function to get the value for each line.
// The sum of all predicted values is returned as a formatted message.
func part1(lines []string) string {
	sum := 0

	for _, line := range lines {
		sum += predictNextValue(line)
	}

	message := fmt.Sprintf("Total sum: %d\n", sum)

	return message
}

// predictNextValue calculates the next value in a sequence based on the given line.
// The line is expected to be a space-separated string of integers.
// It uses a subtraction triangle algorithm to generate a sequence of differences between adjacent numbers,
// and then sums up the last elements of each row to determine the next value.
// The function returns the predicted next value as an integer.
func predictNextValue(line string) int {
	subtractionTriangle := [][]int{}

	initialValues := strings.Split(line, " ")
	firstRow := []int{}

	for _, value := range initialValues {
		firstRow = append(firstRow, utility.ConvertAsciiToInt(value))
	}

	subtractionTriangle = append(subtractionTriangle, firstRow)

	for true {
		currRow := subtractionTriangle[len(subtractionTriangle)-1]
		if utility.IsAllZeroes(currRow) {
			break
		}

		newRow := []int{}
		for i := 0; i < len(currRow)-1; i++ {
			newRow = append(newRow, currRow[i+1]-currRow[i])
		}
		subtractionTriangle = append(subtractionTriangle, newRow)
	}

	nextValue := 0

	for i := len(subtractionTriangle) - 1; i >= 0; i-- {
		currRow := subtractionTriangle[i]
		nextValue += currRow[len(currRow)-1]
	}

	return nextValue
}

// part2 calculates the total sum of predicted next values for each line in the given slice of strings.
// It iterates over each line and calls the predictNextValueTwo function to calculate the next value.
// The sum of all predicted next values is returned as a formatted string.
func part2(lines []string) string {
	sum := 0

	for _, line := range lines {
		sum += predictNextValueTwo(line)
	}

	message := fmt.Sprintf("Total sum: %d\n", sum)

	return message
}

// predictNextValueTwo predicts the next value in a sequence based on the given line.
// It takes a string parameter 'line' representing the initial values of the sequence.
// The function converts the initial values to integers and creates a subtraction triangle.
// It iteratively generates new rows in the subtraction triangle until all rows are filled with zeroes.
// Finally, it calculates the next value by traversing the subtraction triangle from bottom to top.
// The predicted next value is returned as an integer.
func predictNextValueTwo(line string) int {
	subtractionTriangle := [][]int{}

	initialValues := strings.Split(line, " ")
	firstRow := []int{}

	for _, value := range initialValues {
		firstRow = append(firstRow, utility.ConvertAsciiToInt(value))
	}

	subtractionTriangle = append(subtractionTriangle, firstRow)

	for true {
		currRow := subtractionTriangle[len(subtractionTriangle)-1]
		if utility.IsAllZeroes(currRow) {
			break
		}

		newRow := []int{}
		for i := 0; i < len(currRow)-1; i++ {
			newRow = append(newRow, currRow[i+1]-currRow[i])
		}
		subtractionTriangle = append(subtractionTriangle, newRow)
	}

	nextValue := 0

	for i := len(subtractionTriangle) - 1; i >= 0; i-- {
		currRow := subtractionTriangle[i]
		nextValue = currRow[0] - nextValue
	}

	return nextValue
}
