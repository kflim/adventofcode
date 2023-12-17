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
		log.Fatal("Missing filepath argument. Usage: go run day4.go <filepath>")
	}

	filepath := os.Args[1]
	lines := utility.GetLines(filepath)
	fmt.Printf(part1(lines))
	fmt.Printf(part2(lines))
}

func part1(lines []string) string {
	sum := 0

	for _, line := range lines {
		matchCount := getMatchCount(line)
		sum += getScore(matchCount)
	}

	message := fmt.Sprintf("Total sum: %d\n", sum)

	return message
}

func getMatchCount(line string) int {
	if !strings.Contains(line, ":") && !strings.Contains(line, "|") {
		return 0
	}

	lsts := strings.Split(strings.Split(line, ": ")[1], " | ")
	winningNums := strings.Split(lsts[0], " ")
	myNums := strings.Split(lsts[1], " ")

	winningMap := make(map[string]bool)
	for _, s := range winningNums {
		if s != "" {
			winningMap[strings.TrimSpace(s)] = true
		}
	}

	count := 0
	for _, s := range myNums {
		if winningMap[strings.TrimSpace(s)] {
			count++
		}
	}

	return count
}

func getScore(matchCount int) int {
	if matchCount <= 2 {
		return matchCount
	}

	score := 2
	for i := 3; i <= matchCount; i++ {
		score *= 2
	}

	return score
}

func part2(lines []string) string {
	dp := make([]int, len(lines))

	sum := 0
	for i := len(lines) - 1; i >= 0; i-- {
		sum++
		dp[i] = getMatchCount(lines[i])
		if i < len(lines)-1 {
			numCopies := dp[i]
			dp[i] = 0
			for j, k := i+1, 0; j < len(lines) && k < numCopies; j, k = j+1, k+1 {
				dp[i] += dp[j]
				sum += dp[j]
			}
		}
		dp[i]++
	}

	message := fmt.Sprintf("Total sum: %d\n", sum)

	return message
}
