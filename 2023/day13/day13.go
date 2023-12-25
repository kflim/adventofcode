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
		log.Fatal("Missing filepath argument. Usage: go run day11.go <filepath>")
	}

	filepath := os.Args[1]
	lines := utility.GetLines(filepath)
	fmt.Printf(part1(lines))
	fmt.Printf(part2(lines))
}

// part1 summarizes the notes based on the given lines.
// It iterates through the lines and calls the sumNotes function for each group of notes, with smudges value of 0.
// The sum of all the notes is returned as a formatted message.
func part1(lines []string) string {
	sum := 0

	start := 0
	for i, line := range lines {
		if line == "" {
			sum += sumNotes(lines[start:i], 0)
			start = i + 1
		}
	}

	sum += sumNotes(lines[start:], 0)

	message := fmt.Sprintf("Total sum: %d\n", sum)

	return message
}

// sumNotes summarizes notes based on the given lines and smudges.
// It converts the lines into a 2D slice of strings and calculates the sum
// by considering both vertical and horizontal lines of symmetry.
// If the sum for vertical lines is greater than 0, it returns that value.
// Otherwise, it calculates the sum for horizontal lines and returns it.
// Important to note that this assumes that there will only be one type of symmetry.
func sumNotes(lines []string, smudges int) int {
	if len(lines) == 0 {
		return 0
	}

	rows := len(lines)
	cols := len(lines[0])
	notes := make([][]string, rows)
	for i, line := range lines {
		notes[i] = make([]string, cols)
		for j, char := range line {
			notes[i][j] = string(char)
		}
	}

	// Vertical lines - line of symmetry in between columns
	res := calcForLinesBetweenCols(notes, smudges)

	if res > 0 {
		return res
	}

	// Horizontal lines - line of symmetry in between rows
	return calcForLinesBetweenRows(notes, smudges)
}

// calcForLinesBetweenCols calculates the number of columns to the left of the line of symmetry in a 2D string array.
// It iterates through each column and compares the elements in each row that are equidistant from the target column.
// If the elements are different, the difference count is incremented.
// If the difference count exceeds the specified smudges value, the count is reset and the iteration continues.
// If the difference count equals the smudges value, the function returns the number of columns to the left of the line of symmetry.
// If no column satisfies the smudges condition, the function returns 0.
func calcForLinesBetweenCols(notes [][]string, smudges int) int {
	if len(notes) < 1 {
		return 0
	}

	cols := len(notes[0])
	diff := 0

verticalOuter:
	for j := 1; j < cols; j++ {
		left := j - 1
		k := j
		for left >= 0 && k < cols {
			for _, row := range notes {
				if row[left] != row[k] {
					diff++
					if diff > smudges {
						diff = 0
						continue verticalOuter
					}
				}
			}
			left--
			k++
		}
		if diff == smudges {
			return j
		}
	}

	return 0
}

// calcForLinesBetweenRows calculates the number of rows above the line of symmetry in a 2D string array
// and returns the result. It iterates through each row and compares the elements in each column that are equidistant from the target row.
// If the number of differences exceeds the given smudges value, it resets the count and continues to the next row.
// If the count reaches the smudges value, it returns 100 times the number of rows above the line of symmetry.
// If no match is found, it returns 0.
func calcForLinesBetweenRows(notes [][]string, smudges int) int {
	if len(notes) < 1 {
		return 0
	}

	rows := len(notes)
	diff := 0

horizontalOuter:
	for i := 1; i < rows; i++ {
		top := i - 1
		k := i
		for top >= 0 && k < rows {
			for j := 0; j < len(notes[0]); j++ {
				if notes[top][j] != notes[k][j] {
					diff++
					if diff > smudges {
						diff = 0
						continue horizontalOuter
					}
				}
			}
			top--
			k++
		}
		if diff == smudges {
			return 100 * i
		}
	}
	return 0
}

// part2 summarizes the notes based on the given lines.
// It iterates through the lines and calls the sumNotes function for each group of notes, with smudges value of 1.
// The sum of all the notes is returned as a formatted message.
func part2(lines []string) string {
	sum := 0

	start := 0
	for i, line := range lines {
		if line == "" {
			sum += sumNotes(lines[start:i], 1)
			start = i + 1
		}
	}

	sum += sumNotes(lines[start:], 1)

	message := fmt.Sprintf("Total sum: %d\n", sum)

	return message
}
