package utility

import (
	"bufio"
	"log"
	"math/big"
	"os"
	"strconv"
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

// GetFileContent reads the content of a file specified by the filepath parameter and returns it as a string.
// If the file cannot be opened or read, it will log a fatal error.
func GetFileContent(filepath string) string {
	file, err := os.Open(filepath)
	if err != nil {
		log.Fatal(err)
	}
	defer file.Close()

	var content string
	scanner := bufio.NewScanner(file)
	for scanner.Scan() {
		content += scanner.Text() + "\n"
	}

	if err := scanner.Err(); err != nil {
		log.Fatal(err)
	}

	return content
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

// Create2DStrArray takes a slice of strings and returns a 2D slice of strings.
// Each string in the input slice is split into individual characters and stored in the resulting 2D slice.
// The input slice represents the rows of the 2D slice.
// The returned 2D slice represents a grid of strings.
func Create2DStrArray(lines []string) [][]string {
	grid := make([][]string, len(lines))
	for i, line := range lines {
		grid[i] = strings.Split(line, "")
	}
	return grid
}

// ValueExistsInMapArray checks if a given value exists in the array associated with a specific key in a map.
// It takes a map m, a key string, and a value int as parameters.
// It returns true if the value exists in the array, and false otherwise.
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

// ConvertAsciiToInt converts a string representation of an ASCII value to an integer.
// It returns the converted integer value.
func ConvertAsciiToInt(ascii string) int {
	res, err := strconv.Atoi(ascii)

	if err != nil {
		log.Fatal(err)
	}

	return res
}

// ConvertIntToAscii converts an integer to its corresponding ASCII character.
func ConvertIntToAscii(num int) string {
	return strconv.Itoa(num)
}

// SplitStringByColon splits a string by colon and returns a slice of strings.
// It takes a string as input and uses the strings.Split function to split the string by colon.
// The resulting substrings are returned as a slice of strings.
func SplitStringByColon(s string) []string {
	return strings.Split(s, ":")
}

// SplitStringSeparatedByWhitespace splits a string into a slice of substrings separated by whitespace.
// It takes a string as input and returns a slice of strings.
func SplitStringSeparatedByWhitespace(s string) []string {
	return strings.Split(s, " ")
}

// GetSeparatedNumbersAfterColon takes a string as input and returns a slice of integers.
// It expects the input string to be in the format "key: value1 value2 value3 ...",
// where the values are separated by whitespace after the colon.
// The function extracts the values after the colon, splits them by whitespace,
// converts each value from ASCII to integer, and returns the resulting slice of integers.
func GetSeparatedNumbersAfterColon(line string) []int {
	nums := SplitStringSeparatedByWhitespace(strings.TrimSpace(SplitStringByColon(line)[1]))

	var res []int

	for _, time := range nums {
		trimmedTime := strings.TrimSpace(time)
		if trimmedTime != "" {
			num := ConvertAsciiToInt(trimmedTime)
			res = append(res, num)
		}
	}

	return res
}

// ArrayToIndiceMap converts an array of strings into a map where the keys are the elements of the array
// and the values are the corresponding indices of the elements in the array.
func ArrayToIndiceMap(arr []string) map[string]int {
	m := make(map[string]int)
	for index, value := range arr {
		m[value] = index
	}
	return m
}

func GCD(a, b int64) int64 {
	return new(big.Int).GCD(nil, nil, big.NewInt(a), big.NewInt(b)).Int64()
}

func LCM(a, b int64) int64 {
	return a / GCD(a, b) * b
}

// LCMArray calculates the least common multiple (LCM) of an array of integers.
// It takes an array of int64 as input and returns the LCM as an int64.
func LCMArray(arr []int64) int64 {
	if len(arr) == 0 {
		return 0
	}
	if len(arr) == 1 {
		return arr[0]
	}

	result := arr[0]
	for _, num := range arr[1:] {
		result = LCM(result, num)
	}
	return result
}

// IsAllZeroes checks if all elements in the given slice are zero.
// It returns true if all elements are zero, otherwise false.
func IsAllZeroes(row []int) bool {
	for _, value := range row {
		if value != 0 {
			return false
		}
	}

	return true
}
