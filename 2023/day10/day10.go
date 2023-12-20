package main

import (
	"fmt"
	"log"
	"os"

	"github.com/kflim/adventofcode/2023/day10/structs"
	"github.com/kflim/adventofcode/2023/utility"
)

func main() {
	log.SetFlags(0)

	if len(os.Args) < 2 {
		log.Fatal("Missing filepath argument. Usage: go run day10.go <filepath>")
	}

	filepath := os.Args[1]
	lines := utility.GetLines(filepath)
	fmt.Printf(part1(lines))
	fmt.Printf(part2(lines))
}

func part1(lines []string) string {
	grid := utility.Create2DStrArray(lines)

	start := []int{}
	for i, line := range grid {
		for j, tile := range line {
			if tile == "S" {
				start = []int{i, j}
				break
			}
		}
	}

	visited := make([][]bool, len(grid))
	for i := range visited {
		visited[i] = make([]bool, len(grid[0]))
	}
	deltas := [][]int{{0, 1}, {1, 0}, {0, -1}, {-1, 0}}

	steps := 0
	queue := []structs.Node{{X: start[0], Y: start[1], DirX: 0, DirY: 0}}
outer:
	for len(queue) > 0 {
		length := len(queue)
		for i := 0; i < length; i++ {
			current := queue[0]
			queue = queue[1:]

			if visited[current.X][current.Y] {
				break outer
			}

			visited[current.X][current.Y] = true

			for _, delta := range deltas {
				nextX := current.X + delta[0]
				nextY := current.Y + delta[1]
				if nextX >= 0 && nextX < len(grid) && nextY >= 0 && nextY < len(grid[0]) && !visited[nextX][nextY] {
					nextCanonicalDir := getCanonicalDir(grid[nextX][nextY])
					if nextCanonicalDir[0][0] == 0 && nextCanonicalDir[0][1] == 0 {
						continue
					}
					var dirX int
					var dirY int
					if current.DirX == 0 && current.DirY == 0 {
						dirX = delta[0]
						dirY = delta[1]
					} else {
						dirX = current.DirX
						dirY = current.DirY
					}
					if dirX != delta[0] || dirY != delta[1] {
						continue
					}
					if len(nextCanonicalDir) == 1 {
						if pathLeadsToTile(grid, dirX, dirY, nextCanonicalDir[0]) {
							queue = append(queue, structs.Node{X: nextX, Y: nextY, DirX: nextCanonicalDir[0][0], DirY: nextCanonicalDir[0][1]})
						} else if pathLeadsToTile(grid, dirX, dirY, negateArray(nextCanonicalDir[0])) {
							queue = append(queue, structs.Node{X: nextX, Y: nextY, DirX: -nextCanonicalDir[0][0], DirY: -nextCanonicalDir[0][1]})
						}
					} else {
						if pathLeadsToTile(grid, dirX, dirY, nextCanonicalDir[0]) {
							queue = append(queue, structs.Node{X: nextX, Y: nextY, DirX: nextCanonicalDir[1][0], DirY: nextCanonicalDir[1][1]})
						} else if pathLeadsToTile(grid, dirX, dirY, negateArray(nextCanonicalDir[1])) {
							queue = append(queue, structs.Node{X: nextX, Y: nextY, DirX: -nextCanonicalDir[0][0], DirY: -nextCanonicalDir[0][1]})
						}
					}
				}
			}
		}
		steps++
	}

	message := fmt.Sprintf("Total steps: %d\n", steps)

	return message
}

func getCanonicalDir(tile string) [][]int {
	switch tile {
	case "|":
		return [][]int{{1, 0}}
	case "-":
		return [][]int{{0, 1}}
	case "L":
		return [][]int{{1, 0}, {0, 1}}
	case "J":
		return [][]int{{1, 0}, {0, -1}}
	case "7":
		return [][]int{{0, 1}, {1, 0}}
	case "F":
		return [][]int{{0, -1}, {1, 0}}
	case "S":
	case ".":
		return [][]int{{0, 0}}
	default:
		log.Panicln("Invalid tile:", tile)
	}
	return [][]int{{0, 0}}
}

func pathLeadsToTile(grid [][]string, dirX int, dirY int, nextDir []int) bool {
	return dirX == nextDir[0] && dirY == nextDir[1]
}

func negateArray(array []int) []int {
	return []int{-array[0], -array[1]}
}

// incomplete
func part2(lines []string) string {
	grid := utility.Create2DStrArray(lines)

	start := []int{}
	for i, line := range grid {
		for j, tile := range line {
			if tile == "S" {
				start = []int{i, j}
				break
			}
		}
	}

	visited := make([][]bool, len(grid))
	for i := range visited {
		visited[i] = make([]bool, len(grid[0]))
	}
	deltas := [][]int{{0, 1}, {1, 0}, {0, -1}, {-1, 0}}
	queue := []structs.Node{{X: start[0], Y: start[1], DirX: 0, DirY: 0}}

outer:
	for len(queue) > 0 {
		length := len(queue)
		for i := 0; i < length; i++ {
			current := queue[0]
			queue = queue[1:]

			if visited[current.X][current.Y] {
				break outer
			}

			visited[current.X][current.Y] = true

			for _, delta := range deltas {
				nextX := current.X + delta[0]
				nextY := current.Y + delta[1]
				if nextX >= 0 && nextX < len(grid) && nextY >= 0 && nextY < len(grid[0]) && !visited[nextX][nextY] {
					nextCanonicalDir := getCanonicalDir(grid[nextX][nextY])
					if nextCanonicalDir[0][0] == 0 && nextCanonicalDir[0][1] == 0 {
						continue
					}
					var dirX int
					var dirY int
					if current.DirX == 0 && current.DirY == 0 {
						dirX = delta[0]
						dirY = delta[1]
					} else {
						dirX = current.DirX
						dirY = current.DirY
					}
					if dirX != delta[0] || dirY != delta[1] {
						continue
					}
					if len(nextCanonicalDir) == 1 {
						if pathLeadsToTile(grid, dirX, dirY, nextCanonicalDir[0]) {
							queue = append(queue, structs.Node{X: nextX, Y: nextY, DirX: nextCanonicalDir[0][0], DirY: nextCanonicalDir[0][1]})
						} else if pathLeadsToTile(grid, dirX, dirY, negateArray(nextCanonicalDir[0])) {
							queue = append(queue, structs.Node{X: nextX, Y: nextY, DirX: -nextCanonicalDir[0][0], DirY: -nextCanonicalDir[0][1]})
						}
					} else {
						if pathLeadsToTile(grid, dirX, dirY, nextCanonicalDir[0]) {
							queue = append(queue, structs.Node{X: nextX, Y: nextY, DirX: nextCanonicalDir[1][0], DirY: nextCanonicalDir[1][1]})
						} else if pathLeadsToTile(grid, dirX, dirY, negateArray(nextCanonicalDir[1])) {
							queue = append(queue, structs.Node{X: nextX, Y: nextY, DirX: -nextCanonicalDir[0][0], DirY: -nextCanonicalDir[0][1]})
						}
					}
				}
			}
		}
	}

	sum := 0

	for i, line := range grid {
		for j, tile := range line {
			if tile == "." && isInsideLoop(grid, visited, i, j) {
				fmt.Println(i, j)
				sum++
			}
		}
	}

	message := fmt.Sprintf("Total enclosed tiles: %d\n", sum)

	return message
}

func isInsideLoop(grid [][]string, visited [][]bool, i int, j int) bool {
	deltas := [][]int{{0, 1}, {1, 0}, {0, -1}, {-1, 0}}

	for _, delta := range deltas {
		currX := i + delta[0]
		currY := j + delta[1]
		loopIntersections := 0
		for true {
			if currX < 0 || currX >= len(grid) || currY < 0 || currY >= len(grid[0]) {
				break
			}
			if !visited[currX][currY] {
				currX += delta[0]
				currY += delta[1]
				continue
			}
			currCanonicalDir := getCanonicalDir(grid[currX][currY])
			if crossesMultipleWay(currCanonicalDir, delta) {
				loopIntersections++
			}
			currX += delta[0]
			currY += delta[1]
		}
		if loopIntersections%2 == 1 {
			fmt.Println("Intersections: ", loopIntersections)
			return true
		}
	}

	return false
}

func crossesMultipleWay(currCanonicalDir [][]int, delta []int) bool {
	for _, dir := range currCanonicalDir {
		crossesX := dir[0] != delta[0] && -dir[0] != delta[0]
		crossesY := dir[1] != delta[1] && -dir[1] != delta[1]
		if crossesX || crossesY {
			return true
		}
	}
	return false
}
