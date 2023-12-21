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
		log.Fatal("Missing filepath argument. Usage: go run day11.go <filepath>")
	}

	filepath := os.Args[1]
	lines := utility.GetLines(filepath)
	fmt.Printf(part1(lines))
	fmt.Printf(part2(lines))
}

func part1(lines []string) string {
	sum := 0

	for _, line := range lines {
		sum += getWaysToArrangeRow(line)
	}

	message := fmt.Sprintf("Total sum: %d\n", sum)

	return message
}

func getWaysToArrangeRow(row string) int {
	parts := strings.Split(row, " ")
	springs := parts[0]
	groupsInfo := parts[1]
	groupSizes := []int{}
	for _, groupInfo := range strings.Split(groupsInfo, ",") {
		groupSizes = append(groupSizes, utility.ConvertAsciiToInt(groupInfo))
	}
	return backtrackArrangements(springs, groupSizes, 0, 0, true)
}

func backtrackArrangements(springs string, groupSizes []int, springsIndex int, groupIndex int, isNewGroup bool) int {
	// When we reach the end of the springs and the end of the group sizes, we have found a valid arrangement.
	if groupIndex >= len(groupSizes) {
		if groupSizes[len(groupSizes)-1] <= 0 && springsIndex >= len(springs) {
			return 1
		}
		// However, if not all the springs have been checked, then we need to continue checking the springs.
		if springsIndex < len(springs) {
			// If there is an additional spring, then this is an invalid arrangement.
			if string(springs[springsIndex]) == "#" {
				return 0
			} else {
				// Otherwise, we can continue checking the springs.
				return backtrackArrangements(springs, groupSizes, springsIndex+1, groupIndex, true)
			}
		}
	}

	// If we have reached the end of the springs before the end of the group sizes, then this is an invalid arrangement.
	if springsIndex >= len(springs) {
		if groupIndex == len(groupSizes)-1 && groupSizes[groupIndex] <= 0 {
			return 1
		} else {
			return 0
		}
	}

	// If we have finished one group, then we need to check that the next character is not a spring.
	if groupSizes[groupIndex] <= 0 {
		if string(springs[springsIndex]) == "#" {
			return 0
		} else {
			return backtrackArrangements(springs, groupSizes, springsIndex+1, groupIndex+1, true)
		}
	}

	if isNewGroup {
		if string(springs[springsIndex]) == "." {
			return backtrackArrangements(springs, groupSizes, springsIndex+1, groupIndex, true)
		} else {
			newGroupSizes := make([]int, len(groupSizes))
			copy(newGroupSizes, groupSizes)
			newGroupSizes[groupIndex]--

			if string(springs[springsIndex]) == "#" {
				return backtrackArrangements(springs, newGroupSizes, springsIndex+1, groupIndex, false)
			}

			sum := 0
			sum += backtrackArrangements(springs, groupSizes, springsIndex+1, groupIndex, true)
			sum += backtrackArrangements(springs, newGroupSizes, springsIndex+1, groupIndex, false)
			return sum
		}
	}

	if string(springs[springsIndex]) == "." {
		return 0
	} else {
		newGroupSizes := make([]int, len(groupSizes))
		copy(newGroupSizes, groupSizes)
		newGroupSizes[groupIndex]--
		return backtrackArrangements(springs, newGroupSizes, springsIndex+1, groupIndex, false)
	}
}

func part2(lines []string) string {
	sum := 0

	for _, line := range lines {
		unfoldedLine := unfoldLine(line)
		sum += getWaysToArrangeRow(unfoldedLine)
	}

	message := fmt.Sprintf("Total sum: %d\n", sum)

	return message
}

func unfoldLine(line string) string {
	parts := strings.Split(line, " ")
	springs := parts[0]
	groupsInfo := parts[1]

	newSprings := ""
	for i := 0; i < 5; i++ {
		if i > 0 {
			newSprings += "?"
		}
		newSprings += springs
	}

	newGroupSizes := ""
	for i := 0; i < 5; i++ {
		if i > 0 {
			newGroupSizes += ","
		}
		newGroupSizes += groupsInfo
	}

	return newSprings + " " + newGroupSizes
}
