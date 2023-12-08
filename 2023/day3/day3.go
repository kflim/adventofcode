package main

import (
	"fmt"
	"log"
	"os"
	"strconv"
	"strings"
	"unicode"

	"github.com/kflim/adventofcode/2023/utility"
)

func main() {
	log.SetFlags(0)

	if len(os.Args) < 2 {
		log.Fatal("Missing filepath argument. Usage: go run day1.go <filepath>")
	}

	filepath := os.Args[1]
	lines := utility.GetLines(filepath)
	fmt.Printf(part1(lines))
	fmt.Printf(part2(lines))
}

// part1 calculates the total sum of numbers in the grid that are adjacent to a symbol.
// It takes a slice of strings representing the lines of the grid as input.
// The grid is parsed and numbers are extracted from it.
// The sum of adjacent numbers is calculated and returned as a formatted message.
func part1(lines []string) string {
	grid := make([][]string, len(lines))
	for i, line := range lines {
		grid[i] = strings.Split(line, "")
	}

	sum := int(0)
	for i := range grid {
		num := int(0)
		start := int(0)
		end := int(0)
		for j := range grid[i] {
			val := rune(grid[i][j][0])
			if unicode.IsDigit(val) {
				if num == 0 {
					start = j
					end = j + 1
				} else {
					end++
				}
				digit, _ := strconv.Atoi(grid[i][j])
				num = num*10 + digit
			} else {
				if num > 0 && isAdjacentToSymbol(grid, i, start, end) {
					sum += num
				}
				num = 0
			}
		}
		if num > 0 && isAdjacentToSymbol(grid, i, start, end) {
			sum += num
		}
	}

	message := fmt.Sprintf("Total sum: %d\n", sum)

	return message
}

// isAdjacentToSymbol checks if there is a symbol adjacent to the given position in the grid.
// A symbol is any character that is not a digit or a period.
// It takes a 2D grid of strings, the row index, and the start and end column indices as parameters.
// It returns true if there is a symbol adjacent to the position, otherwise false.
func isAdjacentToSymbol(grid [][]string, row int, startCol int, endCol int) bool {
	dx := []int{-1, 0, 1, -1, 1, -1, 0, 1}
	dy := []int{-1, -1, -1, 0, 0, 1, 1, 1}

	for j := startCol; j < endCol; j++ {
		for k := range dx {
			x := row + dx[k]
			y := j + dy[k]

			if x < 0 || x >= len(grid) || y < 0 || y >= len(grid[row]) {
				continue
			}

			if !unicode.IsDigit(rune(grid[x][y][0])) && grid[x][y] != "." {
				return true
			}
		}
	}

	return false
}

// part2 calculates the total sum of gear ratios based on the given lines of input.
// It converts the lines into a grid, identifies potential gear locations, and maps gears to part numbers.
// Then, it iterates through the grid to find adjacent part numbers and adds them to the gears map.
// Finally, it calculates the total sum of gear ratios and returns a formatted message.
func part2(lines []string) string {
	grid := make([][]string, len(lines))
	for i, line := range lines {
		grid[i] = strings.Split(line, "")
	}
	gearLocs := getPotentialGearLocs(grid)
	gearsToNumbersMap := make(map[string][]int)
	for _, location := range gearLocs {
		gearsToNumbersMap[location] = []int{}
	}

	for i := range grid {
		num := int(0)
		start := int(0)
		end := int(0)
		for j := range grid[i] {
			val := rune(grid[i][j][0])
			if unicode.IsDigit(val) {
				if num == 0 {
					start = j
					end = j + 1
				} else {
					end++
				}
				digit, _ := strconv.Atoi(grid[i][j])
				num = num*10 + digit
			} else {
				if num > 0 && isAdjacentToPotentialGear(grid, i, start, end) {
					addToGearsMap(gearsToNumbersMap, grid, i, start, end, num)
				}
				num = 0
			}
		}
		if num > 0 && isAdjacentToPotentialGear(grid, i, start, end) {
			addToGearsMap(gearsToNumbersMap, grid, i, start, end, num)
		}
	}

	message := fmt.Sprintf("Total sum: %d\n", getAllGearRatios(gearsToNumbersMap))

	return message
}

// isPotentialGear checks if the given string is a potential gear.
// In this case, a gear is any "*" that is adjacent to exactly two part numbers.
func isPotentialGear(s string) bool {
	return s == "*"
}

// getPotentialGearLocs returns the locations of potential gear in the grid.
// It iterates over each element in the grid and checks if it is a potential gear.
// If a potential gear is found, its location is added to the gearLocs slice.
// The function then returns the gearLocs slice.
func getPotentialGearLocs(grid [][]string) []string {
	gearLocs := []string{}
	for i := range grid {
		for j := range grid[i] {
			if isPotentialGear(grid[i][j]) {
				gearLocs = append(gearLocs, strconv.Itoa(i)+","+strconv.Itoa(j))
			}
		}
	}
	return gearLocs
}

// isAdjacentToPotentialGear checks if there is a potential gear adjacent to a given position in the grid.
// It takes a 2D grid, the row index, the starting column index, and the ending column index as parameters.
// It returns true if there is a potential gear adjacent to the given position, otherwise false.
func isAdjacentToPotentialGear(grid [][]string, row int, startCol int, endCol int) bool {
	dx := []int{-1, 0, 1, -1, 1, -1, 0, 1}
	dy := []int{-1, -1, -1, 0, 0, 1, 1, 1}

	for j := startCol; j < endCol; j++ {
		for k := range dx {
			x := row + dx[k]
			y := j + dy[k]

			if x < 0 || x >= len(grid) || y < 0 || y >= len(grid[row]) {
				continue
			}

			if isPotentialGear(grid[x][y]) {
				return true
			}
		}
	}

	return false
}

// addToGearsMap is a function that adds gear locations and their corresponding part numbers to a map.
// It takes in the following parameters:
// - gearsToNumbersMap: a map[string][]int representing the mapping of gear locations to part numbers.
// - grid: a 2D slice of strings representing the grid.
// - row: an integer representing the current row.
// - startCol: an integer representing the starting column.
// - endCol: an integer representing the ending column(exclusive).
// - num: an integer representing the number to be added to the gear location.
//
// The function iterates through the specified range of columns and checks the neighboring cells
// to determine if they contain a potential gear. If a potential gear is found, it checks if the
// gear location already exists in the gearsToNumbersMap. If not, it adds the gear location and
// the corresponding number to the map.
func addToGearsMap(gearsToNumbersMap map[string][]int, grid [][]string, row int, startCol int, endCol int, num int) {
	dx := []int{-1, 0, 1, -1, 1, -1, 0, 1}
	dy := []int{-1, -1, -1, 0, 0, 1, 1, 1}

	for j := startCol; j < endCol; j++ {
		for k := range dx {
			x := row + dx[k]
			y := j + dy[k]

			if x < 0 || x >= len(grid) || y < 0 || y >= len(grid[row]) {
				continue
			}

			if isPotentialGear(grid[x][y]) {
				if !utility.ValueExistsInMapArray(gearsToNumbersMap, strconv.Itoa(x)+","+strconv.Itoa(y), num) {
					gearLoc := strconv.Itoa(x) + "," + strconv.Itoa(y)
					gearsToNumbersMap[gearLoc] = append(gearsToNumbersMap[gearLoc], num)
				}
			}
		}
	}
}

// getAllGearRatios calculates the total gear ratios for a given map of gears to part numbers.
// A gear ratio is the product of two part umbers.
// It iterates over the numbers in the map and multiplies the pairs of numbers together.
// The function returns the sum of all the calculated gear ratios.
func getAllGearRatios(gearsToNumbersMap map[string][]int) int {
	ratios := int(0)
	for _, numbers := range gearsToNumbersMap {
		if len(numbers) == 2 {
			ratios += numbers[0] * numbers[1]
		}
	}

	return ratios
}
