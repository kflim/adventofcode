package utility

import (
	"bufio"
	"log"
	"os"
	"strings"
)

// parseTextFile parses each line of a text file and converts them into 2D arrays of characters.
func parseTextFile(filepath string) ([]string, error) {
	file, err := os.Open(filepath)
	if err != nil {
		return nil, err
	}
	defer file.Close()

	scanner := bufio.NewScanner(file)
	var lines []string
	for scanner.Scan() {
		line := scanner.Text()
		lines = append(lines, line)
	}

	if err := scanner.Err(); err != nil {
		return nil, err
	}

	return lines, nil
}

// GetLines reads a text file from the specified filepath and returns its content as a slice of strings.
// If there is an error while reading the file, it will log the error and terminate the program.
func GetLines(filepath string) []string {
	lines, err := parseTextFile(filepath)
	if err != nil {
		log.Fatal(err)
	}

	return lines
}

// IsPrefixOfAnyKey checks if the given string 's' is a prefix of any key in the provided map 'm'.
// It returns true if there is a key in 'm' that starts with 's', otherwise it returns false.
func IsPrefixOfAnyKey(s string, m map[string]int) bool {
	for key := range m {
		if strings.HasPrefix(key, s) {
			return true
		}
	}
	return false
}

// ReduceToPrefixOfAnyKey reduces the given string 's' to the prefix of any key in the map 'm'.
// It iterates through the string 's' and checks if any substring starting from index 'i' is a prefix of any key in the map 'm'.
// If a prefix is found, it returns the substring. If no prefix is found, it returns an empty string.
func ReduceToPrefixOfAnyKey(s string, m map[string]int) string {
	for i := 1; i <= len(s); i++ {
		substring := s[i:]
		if IsPrefixOfAnyKey(substring, m) {
			return substring
		}
	}
	return ""
}

// IsSuffixOfAnyKey checks if the given string 's' is a suffix of any key in the provided map 'm'.
// It returns true if there is a key in 'm' that ends with 's', otherwise it returns false.
func IsSuffixOfAnyKey(s string, m map[string]int) bool {
	for key := range m {
		if strings.HasSuffix(key, s) {
			return true
		}
	}
	return false
}

// ReduceToSuffixOfAnyKey reduces the given string 's' to the longest suffix that matches any key in the provided map 'm'.
// It iterates through the string from the end and checks if each substring is a suffix of any key in the map.
// If a matching suffix is found, it returns that substring. If no matching suffix is found, it returns an empty string.
func ReduceToSuffixOfAnyKey(s string, m map[string]int) string {
	for i := len(s) - 1; i >= 0; i-- {
		substring := s[:i]
		if IsSuffixOfAnyKey(substring, m) {
			return substring
		}
	}
	return ""
}

func Create2DStrArray(lines []string) [][]string {
	grid := make([][]string, len(lines))
	for i, line := range lines {
		grid[i] = strings.Split(line, "")
	}
	return grid
}

func ValueExistsInMapArray(m map[string][]int, key string, value int) bool {
	if arr, ok := m[key]; ok {
		for _, v := range arr {
			if v == value {
				return true
			}
		}
	}
	return false
}
