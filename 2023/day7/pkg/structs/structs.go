package structs

import (
	"strings"

	"github.com/kflim/adventofcode/2023/utility"
)

type CamelHand struct {
	Hand     string
	Bid      int
	handType int
}

func (h *CamelHand) GetHandType() int {
	return h.handType
}

// CalculateHandType determines the type of hand based on the cards in the CamelHand.
// It sets the handType field of the CamelHand struct accordingly.
func (h *CamelHand) CalculateHandType() {
	if isFiveOfAKind(h.Hand) {
		h.handType = 1
	} else if isFourOfAKind(h.Hand) {
		h.handType = 2
	} else if isFullHouse(h.Hand) {
		h.handType = 3
	} else if isThreeOfAKind(h.Hand) {
		h.handType = 4
	} else if isTwoPair(h.Hand) {
		h.handType = 5
	} else if isOnePair(h.Hand) {
		h.handType = 6
	} else {
		h.handType = 7
	}
}

func isFiveOfAKind(hand string) bool {
	if len(hand) != 5 {
		return false
	}

	for i := 1; i < len(hand); i++ {
		if hand[i] != hand[0] {
			return false
		}
	}
	return true
}

func isFourOfAKind(hand string) bool {
	if len(hand) != 5 {
		return false
	}

	charCount := make(map[rune]int)
	for _, char := range hand {
		charCount[char]++
	}

	for _, count := range charCount {
		if count == 4 {
			return true
		}
	}

	return false
}

func isFullHouse(hand string) bool {
	if len(hand) != 5 {
		return false
	}

	charCount := make(map[rune]int)
	for _, char := range hand {
		charCount[char]++
	}

	hasTwo := false
	hasThree := false
	for _, count := range charCount {
		if count == 2 {
			hasTwo = true
		} else if count == 3 {
			hasThree = true
		}
	}

	return hasTwo && hasThree
}

func isThreeOfAKind(hand string) bool {
	if len(hand) != 5 {
		return false
	}

	charCount := make(map[rune]int)
	for _, char := range hand {
		charCount[char]++
	}

	for _, count := range charCount {
		if count == 3 {
			return true
		}
	}

	return false
}

func isTwoPair(hand string) bool {
	if len(hand) != 5 {
		return false
	}

	charCount := make(map[rune]int)
	for _, char := range hand {
		charCount[char]++
	}

	pairCount := 0
	for _, count := range charCount {
		if count == 2 {
			pairCount++
		}
	}

	return pairCount == 2
}

func isOnePair(hand string) bool {
	if len(hand) != 5 {
		return false
	}

	charCount := make(map[rune]int)
	for _, char := range hand {
		charCount[char]++
	}

	for _, count := range charCount {
		if count == 2 {
			return true
		}
	}

	return false
}

// Compare compares the current CamelHand with another CamelHand and returns an integer based on the comparison result.
// If the current CamelHand has a lower handType than the other CamelHand, it returns -1.
// If the current CamelHand has a higher handType than the other CamelHand, it returns 1.
// If the handTypes are the same, it calls the compareSameType function to determine the result based on the priorityOrder string.
func (h *CamelHand) Compare(other CamelHand, priorityOrder string) int {
	if h.handType < other.handType {
		return -1
	} else if h.handType > other.handType {
		return 1
	} else {
		return compareSameType(h, other, priorityOrder)
	}
}

// compareSameType compares two CamelHand objects based on a priority order.
// It returns -1 if h is less than other, 1 if h is greater than other, and 0 if they are equal.
// The priorityOrder parameter specifies the order of priority for comparing the cards in the hands.
// The function uses a map to assign priorities to each card based on the priorityOrder.
// It then iterates through the cards in the hands and compares their priorities.
// If the priority of h's card is less than other's card, -1 is returned.
// If the priority of h's card is greater than other's card, 1 is returned.
// If the priorities are equal for all cards, 0 is returned.
func compareSameType(h *CamelHand, other CamelHand, priorityOrder string) int {
	var cardToPriority map[string]int = utility.ArrayToIndiceMap(strings.Split(priorityOrder, ""))

	for i := range h.Hand {
		if cardToPriority[string(h.Hand[i])] < cardToPriority[string(other.Hand[i])] {
			return -1
		} else if cardToPriority[string(h.Hand[i])] > cardToPriority[string(other.Hand[i])] {
			return 1
		}
	}
	return 0
}

// CalculateHandTypeTwo calculates the hand type for the CamelHand.
// If the hand does not contain the letter "J", it calls CalculateHandType.
// Otherwise, it finds the most frequent character in the hand except for "J",
// replaces all occurrences of "J" with that character, and calculates the hand type.
// Finally, it restores the original hand with "J" included.
func (h *CamelHand) CalculateHandTypeTwo() {
	if !strings.Contains(h.Hand, "J") {
		h.CalculateHandType()
	} else {
		mostFrequentCharExceptJ := findMostFrequentCharExceptJ(h.Hand)
		handWithoutJ := strings.ReplaceAll(h.Hand, "J", string(mostFrequentCharExceptJ))
		handWithJ := h.Hand
		h.Hand = handWithoutJ
		h.CalculateHandType()
		h.Hand = handWithJ
	}
}

// findMostFrequentCharExceptJ finds the most frequent character in a given string, excluding the character 'J'.
// It returns the most frequent character as a rune.
func findMostFrequentCharExceptJ(s string) rune {
	charCount := make(map[rune]int)
	for _, char := range s {
		charCount[char]++
	}

	maxCount := 0
	var maxChar rune
	for char, count := range charCount {
		if count > maxCount && char != 'J' {
			maxCount = count
			maxChar = char
		}
	}

	return maxChar
}
