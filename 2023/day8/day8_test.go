package main

import (
	"testing"
	"time"

	"github.com/kflim/adventofcode/2023/day8/structs"
	"github.com/stretchr/testify/assert"
)

// TestPart1 tests the part1 function.
// It verifies that the function returns the expected result for a given input.
func TestPart1(t *testing.T) {
	lines := []string{
		"RL",
		"",
		"AAA = (BBB, CCC)",
		"BBB = (DDD, EEE)",
		"CCC = (ZZZ, GGG)",
		"DDD = (DDD, DDD)",
		"EEE = (EEE, EEE)",
		"GGG = (GGG, GGG)",
		"ZZZ = (ZZZ, ZZZ)",
	}
	expected := "Total steps: 2\n"
	result := part1(lines)
	assert.Equal(t, expected, result)
}

// TestPart1Empty tests the part1 function with an empty input.
// It verifies that the expected result is "Total steps: 0\n".
func TestPart1Empty(t *testing.T) {
	lines := []string{}
	expected := "Total steps: 0\n"
	result := part1(lines)
	assert.Equal(t, expected, result)
}

// TestPart1NoStart tests the part1 function when there is no starting point.
// It verifies that the expected result is obtained.
func TestPart1NoStart(t *testing.T) {
	lines := []string{
		"RL",
		"",
		"BBB = (DDD, EEE)",
		"CCC = (ZZZ, GGG)",
		"DDD = (DDD, DDD)",
		"EEE = (EEE, EEE)",
		"GGG = (GGG, GGG)",
		"ZZZ = (ZZZ, ZZZ)",
	}
	expected := "Total steps: 0\n"
	result := part1(lines)
	assert.Equal(t, expected, result)
}

// TestPart1NoEnd tests the part1 function when there is no end node.
func TestPart1NoEnd(t *testing.T) {
	lines := []string{
		"RL",
		"",
		"AAA = (BBB, CCC)",
		"BBB = (DDD, EEE)",
		"DDD = (DDD, DDD)",
		"EEE = (EEE, EEE)",
		"GGG = (GGG, GGG)",
	}
	expected := "Total steps: 0\n"
	result := part1(lines)
	assert.Equal(t, expected, result)
}

// TestSetupNodes is a unit test function that tests the setupNodes function.
// It verifies that the setupNodes function correctly sets up the nodes map and establishes the correct relationships between nodes.
func TestSetupNodes(t *testing.T) {
	lines := []string{
		"LLR",
		"",
		"AAA = (BBB, BBB)",
		"BBB = (AAA, ZZZ)",
		"ZZZ = (ZZZ, ZZZ)",
	}
	nodes := map[string]*structs.Node{}
	setupNodes(nodes, lines[2:])
	endNode := nodes["ZZZ"]
	startNode, _ := nodes["AAA"]
	bNode, _ := nodes[startNode.Left.Name]
	assert.Equal(t, endNode, bNode.Right)
}

// TestIsPossibleToReachEnd tests the isPossibleToReachEnd function.
func TestIsPossibleToReachEnd(t *testing.T) {
	nodes := map[string]*structs.Node{}
	setupNodes(nodes, []string{
		"AAA = (BBB, CCC)",
		"BBB = (DDD, EEE)",
		"CCC = (ZZZ, GGG)",
		"DDD = (DDD, DDD)",
		"EEE = (EEE, EEE)",
		"GGG = (GGG, GGG)",
		"ZZZ = (ZZZ, ZZZ)",
	})
	assert.True(t, isPossibleToReachEnd(nodes))
}

// TestIsPossibleToReachEndNoStart tests the isPossibleToReachEnd function when there is no start node.
func TestIsPossibleToReachEndNoStart(t *testing.T) {
	nodes := map[string]*structs.Node{}
	setupNodes(nodes, []string{
		"BBB = (DDD, EEE)",
		"CCC = (ZZZ, GGG)",
		"DDD = (DDD, DDD)",
		"EEE = (EEE, EEE)",
		"GGG = (GGG, GGG)",
		"ZZZ = (ZZZ, ZZZ)",
	})
	assert.False(t, isPossibleToReachEnd(nodes))
}

// Test case to check if it is possible to reach the end without an end node.
func TestIsPossibleToReachEndNoEnd(t *testing.T) {
	nodes := map[string]*structs.Node{}
	setupNodes(nodes, []string{
		"AAA = (BBB, CCC)",
		"BBB = (DDD, EEE)",
		"DDD = (DDD, DDD)",
		"EEE = (EEE, EEE)",
		"GGG = (GGG, GGG)",
	})
	assert.False(t, isPossibleToReachEnd(nodes))
}

// Test case for calculating the steps needed based on instructions
func TestGetStepsNeeded(t *testing.T) {
	instructions := "RL"
	nodes := map[string]*structs.Node{}
	setupNodes(nodes, []string{
		"AAA = (BBB, CCC)",
		"BBB = (DDD, EEE)",
		"CCC = (ZZZ, GGG)",
		"DDD = (DDD, DDD)",
		"EEE = (EEE, EEE)",
		"GGG = (GGG, GGG)",
		"ZZZ = (ZZZ, ZZZ)",
	})
	assert.Equal(t, 2, getStepsNeeded(instructions, nodes))
}

// Test case to verify the steps needed to complete a set of instructions without a starting node.
func TestGetStepsNeededNoStart(t *testing.T) {
	instructions := "RL"
	nodes := map[string]*structs.Node{}
	setupNodes(nodes, []string{
		"BBB = (DDD, EEE)",
		"CCC = (ZZZ, GGG)",
		"DDD = (DDD, DDD)",
		"EEE = (EEE, EEE)",
		"GGG = (GGG, GGG)",
		"ZZZ = (ZZZ, ZZZ)",
	})
	assert.Equal(t, 0, getStepsNeeded(instructions, nodes))
}

// Test case to verify the behavior of getStepsNeeded function when there is no end node.
func TestGetStepsNeededNoEnd(t *testing.T) {
	instructions := "RL"
	nodes := map[string]*structs.Node{}
	setupNodes(nodes, []string{
		"AAA = (BBB, CCC)",
		"BBB = (DDD, EEE)",
		"DDD = (DDD, DDD)",
		"EEE = (EEE, EEE)",
		"GGG = (GGG, GGG)",
	})
	assert.Panics(t, func() { getStepsNeeded(instructions, nodes) })
}

// TestPart2 is a unit test function that tests the functionality of the part2 function.
// It verifies that the expected output is returned when the part2 function is called with the given input.
// The input consists of a slice of strings representing lines of code.
// The function asserts that the result of calling part2 with the input matches the expected output.
func TestPart2(t *testing.T) {
	lines := []string{
		"LR",
		"",
		"11A = (11B, XXX)",
		"11B = (XXX, 11Z)",
		"11Z = (11B, XXX)",
		"22A = (22B, XXX)",
		"22B = (22C, 22C)",
		"22C = (22Z, 22Z)",
		"22Z = (22B, 22B)",
		"XXX = (XXX, XXX)",
	}
	expected := "Total steps: 6\n"
	result := part2(lines)
	assert.Equal(t, expected, result)
}

// TestPart2NoStart tests the part2 function when there is no starting point.
// It verifies that the expected result is obtained.
func TestPart2NoStart(t *testing.T) {
	lines := []string{
		"LR",
		"",
		"11B = (XXX, 11Z)",
		"11Z = (11B, XXX)",
		"22D = (22B, XXX)",
		"22B = (22C, 22C)",
		"22C = (22Z, 22Z)",
		"22Z = (22B, 22B)",
		"XXX = (XXX, XXX)",
	}
	expected := "Total steps: 0\n"
	result := part2(lines)
	assert.Equal(t, expected, result)
}

// TestPart2NoEnd tests the part2 function when there is no end point.
// It verifies that the expected result is returned.
func TestPart2NoEnd(t *testing.T) {
	lines := []string{
		"LR",
		"",
		"11A = (11B, XXX)",
		"11B = (XXX, 11D)",
		"22A = (22B, XXX)",
		"22B = (22C, 22C)",
		"22C = (22D, 22D)",
		"22D = (22B, 22B)",
		"XXX = (XXX, XXX)",
	}
	expected := "Total steps: 0\n"
	result := part2(lines)
	assert.Equal(t, expected, result)
}

// TestIsPossibleToReachEndFromNode tests the isPossibleToReachEndFromNode function.
// It verifies if it is possible to reach the end from a given node.
func TestIsPossibleToReachEndFromNode(t *testing.T) {
	nodes := map[string]*structs.Node{}
	setupNodes(nodes, []string{
		"11A = (11B, XXX)",
		"11B = (XXX, 11Z)",
		"11Z = (11B, XXX)",
		"22A = (22B, XXX)",
		"22B = (22C, 22C)",
		"22C = (22Z, 22Z)",
		"22Z = (22B, 22B)",
		"XXX = (XXX, XXX)",
	})
	node, _ := nodes["11A"]
	assert.True(t, isPossibleToReachEndFromNode(nodes, node.Name))
}

// TestIsPossibleToReachEndFromUnknownNode tests the isPossibleToReachEndFromNode function
// to check if it returns false when trying to reach the end from an unknown node.
func TestIsPossibleToReachEndFromUnknownNode(t *testing.T) {
	nodes := map[string]*structs.Node{}
	setupNodes(nodes, []string{
		"11A = (11B, XXX)",
		"11B = (XXX, 11Z)",
		"11Z = (11B, XXX)",
		"22A = (22B, XXX)",
		"22B = (22C, 22C)",
		"22C = (22Z, 22Z)",
		"22Z = (22B, 22B)",
		"XXX = (XXX, XXX)",
	})
	assert.False(t, isPossibleToReachEndFromNode(nodes, "11C"))
}

// TestIsPossibleToReachEndFromNodeNoEnd tests the isPossibleToReachEndFromNode function when there is no end node.
func TestIsPossibleToReachEndFromNodeNoEnd(t *testing.T) {
	nodes := map[string]*structs.Node{}
	setupNodes(nodes, []string{
		"11A = (11B, XXX)",
		"11B = (XXX, 11Z)",
		"22A = (22B, XXX)",
		"22B = (22C, 22C)",
		"22C = (22D, 22D)",
		"22D = (22B, 22B)",
		"XXX = (XXX, XXX)",
	})
	node, _ := nodes["22D"]
	assert.False(t, isPossibleToReachEndFromNode(nodes, node.Name))
}

// TestGetStepsNeededFromNode tests the getStepsNeededFromNode function.
// It verifies that the correct number of steps is returned when navigating from a given node using the provided instructions.
func TestGetStepsNeededFromNode(t *testing.T) {
	instructions := "LR"
	nodes := map[string]*structs.Node{}
	setupNodes(nodes, []string{
		"11A = (11B, XXX)",
		"11B = (XXX, 11Z)",
		"11Z = (11B, XXX)",
		"22A = (22B, XXX)",
		"22B = (22C, 22C)",
		"22C = (22Z, 22Z)",
		"22Z = (22B, 22B)",
		"XXX = (XXX, XXX)",
	})
	assert.Equal(t, int64(2), getStepsNeededFromNode(instructions, nodes, "11A"))
}

// TestGetStepsNeededFromUnknownNode tests the getStepsNeededFromNode function
// when the starting node is unknown.
func TestGetStepsNeededFromUnknownNode(t *testing.T) {
	instructions := "LR"
	nodes := map[string]*structs.Node{}
	setupNodes(nodes, []string{
		"11A = (11B, XXX)",
		"11B = (XXX, 11Z)",
		"11Z = (11B, XXX)",
		"22A = (22B, XXX)",
		"22B = (22C, 22C)",
		"22C = (22Z, 22Z)",
		"22Z = (22B, 22B)",
		"XXX = (XXX, XXX)",
	})
	assert.Equal(t, int64(0), getStepsNeededFromNode(instructions, nodes, "33Z"))
}

// Test case for getting steps needed from a node with no end node.
// Test will take a while to timeout.
func TestGetStepsNeededFromNodeNoEnd(t *testing.T) {
	instructions := "LR"
	nodes := map[string]*structs.Node{}
	setupNodes(nodes, []string{
		"11A = (11B, XXX)",
		"11B = (XXX, 11Z)",
		"22A = (22B, XXX)",
		"22B = (22C, 22C)",
		"22C = (22D, 22D)",
		"22D = (22B, 22B)",
		"XXX = (XXX, XXX)",
	})

	// Run the function in a separate goroutine
	done := make(chan bool)
	go func() {
		getStepsNeededFromNode(instructions, nodes, "22D")
		done <- true
	}()

	// Wait for the function to finish or for the timeout
	select {
	case <-done:
		t.Log("Function finished successfully")
	case <-time.After(29 * time.Second):
		// If we're here, it means the timeout occurred before the function finished
		t.Error("Function timed out")
	}
}
