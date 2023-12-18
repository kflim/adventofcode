package main

import (
	"fmt"
	"log"
	"os"
	"strings"

	"github.com/kflim/adventofcode/2023/day7/structs"
	"github.com/kflim/adventofcode/2023/utility"
)

func main() {
	log.SetFlags(0)

	if len(os.Args) < 2 {
		log.Fatal("Missing filepath argument. Usage: go run day7.go <filepath>")
	}

	filepath := os.Args[1]
	lines := utility.GetLines(filepath)
	fmt.Printf(part1(lines))
	fmt.Printf(part2(lines))
}

// part1 calculates the total sum of winnings for a given set of lines.
// It takes a slice of strings as input, where each string represents a line of information.
// The function iterates over each line, splits it into individual components, and creates a CamelHand struct.
// The CamelHand struct contains information about the hand and its bid.
// The CalculateHandType method is called on each CamelHand to determine its type.
// The hands are then sorted in descending order based on their hand type.
// The function calculates the winnings for each hand based on its position in the sorted list.
// Finally, the function returns a formatted message with the total sum of winnings.
func part1(lines []string) string {
	sum := 0
	var hands []structs.CamelHand

	for _, line := range lines {
		info := strings.Split(line, " ")
		hand := structs.CamelHand{Hand: info[0], Bid: utility.ConvertAsciiToInt(info[1])}
		hand.CalculateHandType()
		hands = append(hands, hand)
	}

	sortHands(hands, "AKQJT98765432")

	for i, hand := range hands {
		sum += getWinnings(i+1, hand)
	}

	message := fmt.Sprintf("Total sum: %d\n", sum)

	return message
}

// sortHands sorts the given hands slice in ascending order based on the hand type and priority order.
// It uses the quicksort algorithm to recursively partition the slice and sort the subarrays.
// The hands are sorted based on the hand type first, and if two hands have the same type,
// they are further sorted based on the priority order.
// The function modifies the original hands slice in place.
func sortHands(hands []structs.CamelHand, priorityOrder string) {
	if len(hands) < 2 {
		return
	}

	left, right := 0, len(hands)-1

	pivot := len(hands) / 2

	hands[pivot], hands[right] = hands[right], hands[pivot]

	for i := range hands {
		if hands[i].GetHandType() > hands[right].GetHandType() {
			hands[left], hands[i] = hands[i], hands[left]
			left++
		} else if hands[i].GetHandType() == hands[right].GetHandType() {
			if hands[i].Compare(hands[right], priorityOrder) > 0 {
				hands[left], hands[i] = hands[i], hands[left]
				left++
			}
		}
	}

	hands[left], hands[right] = hands[right], hands[left]

	sortHands(hands[:left], priorityOrder)
	sortHands(hands[left+1:], priorityOrder)
}

// getWinnings calculates the winnings based on the rank and bid of a camel hand.
// It multiplies the rank by the bid to determine the winnings.
// The rank parameter represents the rank of the camel hand.
// The hand parameter represents the camel hand with the bid.
// The function returns the calculated winnings as an integer.
func getWinnings(rank int, hand structs.CamelHand) int {
	return rank * hand.Bid
}

// part2 calculates the total sum of winnings for a given set of lines.
// It takes a slice of strings as input, where each string represents a line of information.
// The function iterates over each line, splits it into separate pieces of information,
// creates a CamelHand struct with the extracted information, calculates the hand type,
// and appends it to the hands slice.
// The hands slice is then sorted in a specific order.
// Finally, the function calculates the winnings for each hand and returns the total sum as a formatted message.
func part2(lines []string) string {
	sum := 0
	var hands []structs.CamelHand

	for _, line := range lines {
		info := strings.Split(line, " ")
		hand := structs.CamelHand{Hand: info[0], Bid: utility.ConvertAsciiToInt(info[1])}
		hand.CalculateHandTypeTwo()
		hands = append(hands, hand)
	}

	sortHands(hands, "AKQT98765432J")

	for i, hand := range hands {
		sum += getWinnings(i+1, hand)
	}

	message := fmt.Sprintf("Total sum: %d\n", sum)

	return message
}
