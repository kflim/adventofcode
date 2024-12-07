#!/bin/bash

input_file="input.txt"

# Check if the file exists
if [[ ! -f $input_file ]]; then
    echo "Error: File $input_file not found!"
    exit 1
fi

# Read the grid from the file into an array
mapfile -t grid < "$input_file"
rows=${#grid[@]}
cols=${#grid[0]}

# Find the starting position
start_x=-1
start_y=-1
for ((i = 0; i < rows; i++)); do
    for ((j = 0; j < cols; j++)); do
        if [[ ${grid[i]:j:1} == "^" ]]; then
            start_x=$i
            start_y=$j
            break 2
        fi
    done
done

if ((start_x == -1 || start_y == -1)); then
    echo "Error: Starting position not found!"
    exit 1
fi

# Function to count unique positions visited
count_positions() {
    local curr_x=$1
    local curr_y=$2
    local direction_index=0
    local directions=("-1 0" "0 1" "1 0" "0 -1")
    declare -A visited

    while true; do
        visited["$curr_x,$curr_y"]=1
        read -r dx dy <<< "${directions[direction_index]}"
        next_x=$((curr_x + dx))
        next_y=$((curr_y + dy))

        if ((next_x < 0 || next_x >= rows || next_y < 0 || next_y >= cols)); then
            break
        fi

        cell=${grid[next_x]:next_y:1}
        if [[ $cell == "#" ]]; then
            direction_index=$(((direction_index + 1) % 4))
            continue
        fi

        curr_x=$next_x
        curr_y=$next_y
    done

    echo "${#visited[@]}"
}

# Function to count unique obstructions possible
count_obstructions() {
    local obstacles_rows=()
    local obstacles_cols=()
    declare -A obstacles_map

    # Populate obstacle lists
    for ((r = 0; r < rows; r++)); do
        for ((c = 0; c < cols; c++)); do
            if [[ ${grid[r]:c:1} == "#" ]]; then
                obstacles_rows+=("$r:$c")
                obstacles_cols+=("$c:$r")
                obstacles_map["$r,$c"]=1
            fi
        done
    done

    local candidates=()
    local curr_x=$start_x
    local curr_y=$start_y
    local direction_index=0
    local directions=("-1 0" "0 1" "1 0" "0 -1")

    # Determine candidate positions for obstacles
    while ((curr_x >= 0 && curr_x < rows && curr_y >= 0 && curr_y < cols)); do
        read -r dx dy <<< "${directions[direction_index]}"
        next_x=$((curr_x + dx))
        next_y=$((curr_y + dy))

        if ((next_x < 0 || next_x >= rows || next_y < 0 || next_y >= cols)); then
            break
        fi

        if [[ ${grid[next_x]:next_y:1} == "#" ]]; then
            direction_index=$(((direction_index + 1) % 4))
        else
            candidates+=("$next_x,$next_y")
            curr_x=$next_x
            curr_y=$next_y
        fi
    done

    # Check for looping behavior with added obstacles
    local loop_count=0
    for candidate in "${candidates[@]}"; do
        if [[ -z ${obstacles_map["$candidate"]} ]]; then
            obstacles_map["$candidate"]=1
            if is_looping "${obstacles_map[@]}"; then
                ((loop_count++))
            fi
            unset obstacles_map["$candidate"]
        fi
    done

    echo "$loop_count"
}

# Helper function to check if a loop is formed
is_looping() {
    local obstacles=("$@")
    local curr_x=$start_x
    local curr_y=$start_y
    local direction_index=0
    local directions=("-1 0" "0 1" "1 0" "0 -1")
    declare -A visited

    while ((curr_x >= 0 && curr_x < rows && curr_y >= 0 && curr_y < cols)); do
        key="$curr_x,$curr_y,$direction_index"
        if [[ -n ${visited["$key"]} ]]; then
            return 0 # Loop detected
        fi
        visited["$key"]=1

        read -r dx dy <<< "${directions[direction_index]}"
        next_x=$((curr_x + dx))
        next_y=$((curr_y + dy))

        if ((next_x < 0 || next_x >= rows || next_y < 0 || next_y >= cols)); then
            break
        fi

        if [[ -n ${obstacles["$next_x,$next_y"]} ]]; then
            direction_index=$(((direction_index + 1) % 4))
        else
            curr_x=$next_x
            curr_y=$next_y
        fi
    done

    return 1 # No loop
}

# Run the functions
visited_count=$(count_positions "$start_x" "$start_y")
obstruction_count=$(count_obstructions)
echo "Number of unique positions visited: $visited_count"
echo "Number of unique obstructions possible: $obstruction_count"

# Pause at the end
read -p "Press Enter to exit..."