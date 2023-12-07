package main

import (
	"bufio"
	"fmt"
	"log"
	"os"
	"strconv"
	"strings"
	"unicode"

	"github.com/kflim/adventofcode/2023/utility"
)

func main() {
	log.SetFlags(0)

	if len(os.Args) < 2 {
		log.Fatal("Missing filepath argument. Usage: go run day1.go <filepath>")
	}

	filepath := os.Args[1]
	lines := utility.GetLines(filepath)
	fmt.Printf(part1(lines))
	fmt.Printf(part2(lines))
}

// part1 calculates the total sum of calibration values from the given lines.
// It iterates over each line, retrieves the calibration value using getCalibrationVal function,
// and adds it to the running sum. The final sum is returned as a formatted message.
// The format of the message is "Total sum: <sum>".
func part1(lines []string) string {
	sum := int(0)

	for _, line := range lines {
		val := getCalibrationVal(line)
		sum += val
	}

	message := fmt.Sprintf("Total sum: %d\n", sum)

	return message
}

// getCalibrationVal extracts the calibration value from a given string.
// It searches for the first and last digit in the string and concatenates them to form the calibration value.
// If the calibration value cannot be converted to an integer, it will log a fatal error.
// The extracted calibration value is returned as an integer.
func getCalibrationVal(line string) int {
	calibrationVal := getFirstDigit(line) + getSecondDigit(line)

	val, err := strconv.Atoi(calibrationVal)
	if err != nil {
		log.Fatal(err)
	}

	return val
}

// getFirstDigit returns the first digit found in the given string.
// If no digit is found, it returns an empty string.
func getFirstDigit(line string) string {
	for _, char := range line {
		if unicode.IsDigit(char) {
			return string(char)
		}
	}

	return ""
}

// getSecondDigit returns the second digit found in the given line.
// It iterates through the characters in reverse order and returns the first digit encountered.
// If no digit is found, it returns an empty string.
func getSecondDigit(line string) string {
	for i := len(line) - 1; i >= 0; i-- {
		char := rune(line[i])
		if unicode.IsDigit(char) {
			return string(char)
		}
	}

	return ""
}

// part2 calculates the total sum of the calibration values in the given lines.
// It iterates through each line and adds the calibration value to the sum.
// The final sum is returned as a formatted message.
// The format of the message is "Total sum: <sum>".
func part2(lines []string) string {
	sum := int(0)

	for _, line := range lines {
		val := getTrueCalibrationVal(line)
		sum += val
	}

	message := fmt.Sprintf("Total sum: %d\n", sum)

	return message
}

// getTrueCalibrationVal calculates the true calibration value for a given line.
// It adds the true first digit and the true second digit of the line.
// If the calculation fails, it logs the error and exits the program.
// It returns the calculated calibration value as an integer.
func getTrueCalibrationVal(line string) int {
	calibrationVal := getTrueFirstDigit(line) + getTrueSecondDigit(line)

	val, err := strconv.Atoi(calibrationVal)
	if err != nil {
		log.Fatal(err)
	}

	return val
}

// getTrueFirstDigit returns the first digit found in the given line.
// It searches for digits in the line and returns the first digit encountered.
// If a substring of the line matches a key in the digitsMap, it returns the corresponding value as a string.
// If no digit is found or no matching substring is found in the digitsMap, it returns an empty string.
func getTrueFirstDigit(line string) string {
	digitsMap := getDigitMap()
	currentWord := ""
	for _, char := range line {
		if unicode.IsDigit(char) {
			return string(char)
		} else {
			currentWord += string(char)
			if !utility.IsPrefixOfAnyKey(currentWord, digitsMap) {
				currentWord = utility.ReduceToPrefixOfAnyKey(currentWord, digitsMap)
			} else {
				if val, ok := digitsMap[currentWord]; ok {
					return strconv.Itoa(val)
				}
			}
		}
	}

	return ""
}

// getTrueSecondDigit returns the second digit from the end of the given line string.
// It searches for the first digit from the end and returns it as a string.
// If a non-digit character is encountered, it checks if the reversed substring is a key in the digitsMap.
// If it is, it returns the corresponding value as a string.
// If no digit or valid reversed substring is found, it returns an empty string.
func getTrueSecondDigit(line string) string {
	digitsMap := getDigitMap()
	currentWord := ""

	for i := len(line) - 1; i >= 0; i-- {
		char := rune(line[i])
		if unicode.IsDigit(char) {
			return string(char)
		} else {
			currentWord = string(char) + currentWord
			if !utility.IsSuffixOfAnyKey(currentWord, digitsMap) {
				currentWord = utility.ReduceToSuffixOfAnyKey(currentWord, digitsMap)
			} else {
				if val, ok := digitsMap[currentWord]; ok {
					return strconv.Itoa(val)
				}
			}
		}
	}

	return ""
}

// getDigitMap reads a file named "digits.txt" and returns a map of digits.
// The keys of the map are strings representing the digits, and the values are integers.
// Each line in the file should contain a digit followed by a space and a number.
// If there is an error while reading the file or converting the number, it will log a fatal error.
func getDigitMap() map[string]int {
	file, err := os.Open("digits.txt")
	if err != nil {
		log.Fatal(err)
	}
	defer file.Close()

	digitsMap := make(map[string]int)
	scanner := bufio.NewScanner(file)
	for scanner.Scan() {
		line := scanner.Text()
		words := strings.Split(line, " ")
		digitsMap[words[0]], err = strconv.Atoi(words[1])

		if err != nil {
			log.Fatal(err)
		}
	}

	if err := scanner.Err(); err != nil {
		log.Fatal(err)
	}

	return digitsMap
}
