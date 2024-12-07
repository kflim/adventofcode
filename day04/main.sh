#!/bin/bash

input_file="input.txt"

# Check if the file exists
if [ ! -f "$input_file" ]; then
    echo "Error: File $input_file not found!"
    exit 1
fi

# Read the grid into an array
mapfile -t grid < "$input_file"

rows=${#grid[@]}
cols=${#grid[0]}

word="XMAS"
word_length=${#word}

check_direction() {
    local r=$1 c=$2 dr=$3 dc=$4
    local i char match=1

    for ((i = 0; i < word_length; i++)); do
        local row=$((r + i * dr))
        local col=$((c + i * dc))
        
        if ((row < 0 || row >= rows || col < 0 || col >= cols)); then
            match=0
            break
        fi

        char=${grid[row]:col:1}
        if [[ $char != ${word:i:1} ]]; then
            match=0
            break
        fi
    done

    echo $match
}

check_x_mas() {
    local r=$1 c=$2
    local count=0

    # Top-left "M", Bottom-left "M"
    if ((r >= 1 && r < rows - 1 && c >= 1 && c < cols - 1)) &&
       [[ ${grid[r-1]:c-1:1} == "M" && ${grid[r+1]:c+1:1} == "S" ]] &&
       [[ ${grid[r]:c:1} == "A" ]] &&
       [[ ${grid[r+1]:c-1:1} == "M" && ${grid[r-1]:c+1:1} == "S" ]]; then
        count=$((count + 1))
    fi

    # Top-right "M", Bottom-right "M"
    if ((r >= 1 && r < rows - 1 && c >= 1 && c < cols - 1)) &&
       [[ ${grid[r-1]:c+1:1} == "M" && ${grid[r+1]:c-1:1} == "S" ]] &&
       [[ ${grid[r]:c:1} == "A" ]] &&
       [[ ${grid[r+1]:c+1:1} == "M" && ${grid[r-1]:c-1:1} == "S" ]]; then
        count=$((count + 1))
    fi

    # "M" above
    if ((r >= 1 && r < rows - 1)) &&
       [[ ${grid[r-1]:c-1:1} == "M" && ${grid[r+1]:c+1:1} == "S" ]] &&
       [[ ${grid[r]:c:1} == "A" ]] &&
       [[ ${grid[r-1]:c+1:1} == "M" && ${grid[r+1]:c-1:1} == "S" ]]; then
        count=$((count + 1))
    fi

    # "M" below
    if ((r >= 1 && r < rows - 1)) &&
       [[ ${grid[r+1]:c-1:1} == "M" && ${grid[r-1]:c+1:1} == "S" ]] &&
       [[ ${grid[r]:c:1} == "A" ]] &&
       [[ ${grid[r+1]:c+1:1} == "M" && ${grid[r-1]:c-1:1} == "S" ]]; then
        count=$((count + 1))
    fi

    echo $count
}

xmas_count=0
for ((r = 0; r < rows; r++)); do
    for ((c = 0; c < cols; c++)); do
        if [[ ${grid[r]:c:1} == ${word:0:1} ]]; then
            for dr in -1 0 1; do
                for dc in -1 0 1; do
                    if ((dr != 0 || dc != 0)); then
                        match=$(check_direction "$r" "$c" "$dr" "$dc")
                        xmas_count=$((xmas_count + match))
                    fi
                done
            done
        fi
    done
done

echo "Part 1: $xmas_count"

x_mas_count=0

for ((r = 0; r < rows; r++)); do
    for ((c = 0; c < cols; c++)); do
        x_mas_count=$((x_mas_count + $(check_x_mas "$r" "$c")))
    done
done

echo "Part 2: $x_mas_count"

# Pause at the end
read -p "Press Enter to exit..."
