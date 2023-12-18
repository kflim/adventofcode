package main

import (
	"fmt"
	"log"
	"os"
	"slices"
	"strings"

	"github.com/kflim/adventofcode/2023/day8/structs"
	"github.com/kflim/adventofcode/2023/utility"
)

func main() {
	log.SetFlags(0)

	if len(os.Args) < 2 {
		log.Fatal("Missing filepath argument. Usage: go run day8.go <filepath>")
	}

	filepath := os.Args[1]
	lines := utility.GetLines(filepath)
	fmt.Printf(part1(lines))
	fmt.Printf(part2(lines))
}

// part1 calculates the total number of steps needed to reach the end of the instructions.
// It takes a slice of strings representing the lines of instructions as input.
// It returns a string message with the total number of steps.
func part1(lines []string) string {
	if len(lines) < 3 {
		return "Total steps: 0\n"
	}

	steps := 0

	instructions := lines[0]

	nodes := map[string]*structs.Node{}

	setupNodes(nodes, lines[2:])

	if isPossibleToReachEnd(nodes) {
		steps = getStepsNeeded(instructions, nodes)
	}

	message := fmt.Sprintf("Total steps: %d\n", steps)

	return message
}

// setupNodes is a function that populates a map of nodes with the given lines of instructions.
// It expects a map of nodes and a slice of strings as input parameters.
// Each line of the instructions is processed to extract the current node, left node, and right node.
// If the left or right node does not exist in the map, a new node is created and added to the map.
// Finally, the current node is updated with the left and right nodes.
func setupNodes(nodes map[string]*structs.Node, lines []string) {
	for _, line := range lines {
		instructionSegments := strings.Split(line, " = ")
		currentNode := instructionSegments[0]
		elementInstructions := strings.TrimSpace(instructionSegments[1])
		leftRightNodes := strings.Split(elementInstructions[1:len(elementInstructions)-1], ", ")
		_, ok := nodes[leftRightNodes[0]]
		if !ok {
			nodes[leftRightNodes[0]] = &structs.Node{Name: leftRightNodes[0]}
		}
		_, ok = nodes[leftRightNodes[1]]
		if !ok {
			nodes[leftRightNodes[1]] = &structs.Node{Name: leftRightNodes[1]}
		}
		leftNode := nodes[leftRightNodes[0]]
		rightNode := nodes[leftRightNodes[1]]
		_, ok = nodes[currentNode]
		if !ok {
			nodes[currentNode] = &structs.Node{Name: currentNode, Left: leftNode, Right: rightNode}
		} else {
			node, _ := nodes[currentNode]
			node.Left = leftNode
			node.Right = rightNode
		}
	}
}

// isPossibleToReachEnd checks if it is possible to reach the end node "ZZZ" from the start node "AAA" in a graph represented by the given nodes map.
// It uses a breadth-first search algorithm to explore the graph and returns true if the end node is reachable, false otherwise.
// For the purposes of the challenge, it is assumed that the start node "AAA" always exists in the graph.
// However, for practical purposes, the function should check if the start node exists in the graph before performing the search.
func isPossibleToReachEnd(nodes map[string]*structs.Node) bool {
	_, ok := nodes["AAA"]

	if !ok {
		return false
	}

	visited := []string{}
	queue := []string{"AAA"}

	for len(queue) > 0 {
		currentNode := queue[0]
		queue = queue[1:]
		if currentNode == "ZZZ" {
			return true
		}
		if !slices.Contains(visited, currentNode) {
			visited = append(visited, currentNode)
			node := nodes[currentNode]
			if node.Left != nil {
				queue = append(queue, node.Left.Name)
			}
			if node.Right != nil {
				queue = append(queue, node.Right.Name)
			}
		}
	}

	return false
}

// getStepsNeeded calculates the number of steps needed to traverse a path of instructions.
// It takes a string of instructions and a map of nodes as input.
// The function starts at node "AAA" and follows the instructions to move left or right through the nodes.
// The traversal stops when the current node reaches "ZZZ".
// The function returns the total number of steps taken to reach "ZZZ".
func getStepsNeeded(instructions string, nodes map[string]*structs.Node) int {
	_, ok := nodes["AAA"]

	if !ok {
		return 0
	}

	steps := 0
	currentNode := "AAA"
	for i := 0; i < len(instructions); {
		if currentNode == "ZZZ" {
			break
		} else if instructions[i] == 'L' {
			currentNode = nodes[currentNode].Left.Name
		} else if instructions[i] == 'R' {
			currentNode = nodes[currentNode].Right.Name
		}
		steps++
		if i == len(instructions)-1 && currentNode != "ZZZ" {
			i = 0
		} else {
			i++
		}
	}

	return steps
}

// part2 calculates the total steps needed to reach the end from nodes with names ending in "A".
// It takes a slice of strings representing the input lines and returns a string message with the total steps.
// The function uses a map of nodes and their connections to determine the steps needed.
// It calls helper functions to set up the nodes, check if it is possible to reach the end from a node,
// and calculate the steps needed from a specific node.
// Finally, it calculates the least common multiple (LCM) of the steps and returns a formatted message with the total steps needed for all paths to end with Z at the same time.
func part2(lines []string) string {
	pathSteps := []int64{}

	instructions := lines[0]

	nodes := map[string]*structs.Node{}

	setupNodes(nodes, lines[2:])

	for _, node := range nodes {
		if strings.HasSuffix(node.Name, "A") {
			if isPossibleToReachEndFromNode(nodes, node.Name) {
				pathSteps = append(pathSteps, getStepsNeededFromNode(instructions, nodes, node.Name))
			}
		}
	}

	message := fmt.Sprintf("Total steps: %d\n", utility.LCMArray(pathSteps))

	return message
}

// isPossibleToReachEndFromNode checks if it is possible to reach the end node from the given start node.
// It uses a breadth-first search algorithm to traverse the graph represented by the nodes map.
// The function returns true if the end node is reachable, and false otherwise.
func isPossibleToReachEndFromNode(nodes map[string]*structs.Node, startNode string) bool {
	_, ok := nodes[startNode]

	if !ok {
		return false
	}

	visited := []string{}
	queue := []string{startNode}

	for len(queue) > 0 {
		currentNode := queue[0]
		queue = queue[1:]
		if strings.HasSuffix(currentNode, "Z") {
			return true
		}
		if !slices.Contains(visited, currentNode) {
			visited = append(visited, currentNode)
			node := nodes[currentNode]
			if node.Left != nil {
				queue = append(queue, node.Left.Name)
			}
			if node.Right != nil {
				queue = append(queue, node.Right.Name)
			}
		}
	}

	return false
}

// getStepsNeededFromNode calculates the number of steps needed to traverse a sequence of instructions
// starting from a given node in a graph.
// It takes the following parameters:
// - instructions: a string representing the sequence of instructions (L for left, R for right)
// - nodes: a map of nodes in the graph, where each node has a left and right neighbor
// - startNode: the name of the node to start from
// It returns the number of steps needed to traverse the instructions.
func getStepsNeededFromNode(instructions string, nodes map[string]*structs.Node, startNode string) int64 {
	_, ok := nodes[startNode]

	if !ok {
		return 0
	}

	steps := int64(0)
	currentNode := startNode
	for i := 0; i < len(instructions); {
		if strings.HasSuffix(currentNode, "Z") {
			break
		} else if instructions[i] == 'L' {
			currentNode = nodes[currentNode].Left.Name
		} else if instructions[i] == 'R' {
			currentNode = nodes[currentNode].Right.Name
		}
		steps++
		if i == len(instructions)-1 && !strings.HasSuffix(currentNode, "Z") {
			i = 0
		} else {
			i++
		}
	}
	return steps
}
