package main

import (
	"fmt"
	"log"
	"os"
	"strings"

	"github.com/kflim/adventofcode/2023/day5/structs"
	"github.com/kflim/adventofcode/2023/utility"
)

func main() {
	log.SetFlags(0)

	if len(os.Args) < 2 {
		log.Fatal("Missing filepath argument. Usage: go run day5.go <filepath>")
	}

	filepath := os.Args[1]
	file := utility.GetFileContent(filepath)
	fmt.Printf(part1(file))
	fmt.Printf(part2(file))
}

func part1(file string) string {
	lowestLoc := 0
	rangeMaps := make([]structs.RangeMap, 7)
	maps := strings.Split(file, "\n\n")
	seeds := []int{}

	for i, mapData := range maps {
		if i == 0 {
			seedData := strings.Split(strings.Split(mapData, ": ")[1], " ")
			for _, seed := range seedData {
				if seed == "" {
					continue
				}
				seeds = append(seeds, utility.ConvertAsciiToInt(seed))
			}
			continue
		}
		addRanges(mapData, rangeMaps, i)
	}

	for _, seed := range seeds {
		val := seed
		for i := range rangeMaps {
			val = rangeMaps[i].GetDest(val)
		}
		if val < lowestLoc || lowestLoc == 0 {
			lowestLoc = val
		}
	}

	message := fmt.Sprintf("Lowest location number: %d\n", lowestLoc)

	return message
}

func addRanges(mapData string, rangeMaps []structs.RangeMap, i int) {
	lines := strings.Split(mapData, "\n")[1:]
	for _, line := range lines {
		if line == "" {
			continue
		}
		convertInfo := strings.Split(strings.TrimSpace(line), " ")
		dest := utility.ConvertAsciiToInt(convertInfo[0])
		src := utility.ConvertAsciiToInt(convertInfo[1])
		length := utility.ConvertAsciiToInt(convertInfo[2])
		rangeMaps[i-1].AddRange(dest, src, length)
	}
}

// incomplete
func part2(file string) string {
	lowestLoc := 0
	rangeMaps := make([]structs.RangeMap, 7)
	maps := strings.Split(file, "\n\n")
	seedRanges := structs.RangeMap{}

	for i, mapData := range maps {
		if i == 0 {
			seedData := strings.Split(strings.Split(mapData, ": ")[1], " ")
			for j := 0; j < len(seedData); {
				if seedData[j] == "" {
					j++
					continue
				}
				seedRanges.AddRange(0, utility.ConvertAsciiToInt(seedData[j]), utility.ConvertAsciiToInt(seedData[j+1]))
				j += 2
			}
			continue
		}
		addRanges(mapData, rangeMaps, i)
	}

	for _, seedRange := range seedRanges.GetRanges() {
		for i := 0; i < seedRange.GetLength(); i++ {
			val := seedRange.GetSrcStart() + i
			for i := range rangeMaps {
				val = rangeMaps[i].GetDest(val)
			}
			if val < lowestLoc || lowestLoc == 0 {
				lowestLoc = val
			}
		}
	}

	message := fmt.Sprintf("Lowest location number: %d\n", lowestLoc)

	return message
}
