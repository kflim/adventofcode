#!/bin/bash

# Function to perform backtracking
backtrack() {
    local numbers=("${!1}")
    local idx=$2
    local current_value=$3
    local target_value=$4
    local is_part2=$5

    # Base case: if we've reached the end, check if the current value equals the target
    if [ $idx -eq ${#numbers[@]} ]; then
        if [ $current_value -eq $target_value ]; then
            echo 1
        else
            echo 0
        fi
        return
    fi

    # Try addition
    local next_value=$((current_value + numbers[idx]))
    if [ "$(backtrack numbers[@] $((idx + 1)) $next_value $target_value $is_part2)" -eq 1 ]; then
        echo 1
        return
    fi

    # Try multiplication
    next_value=$((current_value * numbers[idx]))
    if [ "$(backtrack numbers[@] $((idx + 1)) $next_value $target_value $is_part2)" -eq 1 ]; then
        echo 1
        return
    fi

    # Try concatenation if is_part2 is true
    if [ "$is_part2" -eq 1 ]; then
        next_value=$(echo "$current_value${numbers[idx]}" | bc)
        if [ "$(backtrack numbers[@] $((idx + 1)) $next_value $target_value $is_part2)" -eq 1 ]; then
            echo 1
            return
        fi
    fi

    echo 0
}

# Function to solve the puzzle with backtracking
solve_with_backtracking() {
    local file_path=$1
    local is_part2=$2
    local total_calibration_result=0

    while IFS=: read -r test_value rest; do
        test_value=$(echo $test_value | xargs) # Trim whitespace
        numbers=($(echo $rest | xargs | tr ' ' '\n')) # Convert numbers to array

        # Use backtracking
        if [ "$(backtrack numbers[@] 1 ${numbers[0]} $test_value $is_part2)" -eq 1 ]; then
            total_calibration_result=$((total_calibration_result + test_value))
        fi
    done < "$file_path"

    echo $total_calibration_result
}

# File path to the input file
input_file="input.txt"

# Solve Part 1
part1=$(solve_with_backtracking "$input_file" 0)
echo "Part 1: $part1"

# Solve Part 2 (with concatenation)
part2=$(solve_with_backtracking "$input_file" 1)
echo "Part 2: $part2"
