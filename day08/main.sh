#!/bin/bash

input_file="input.txt"

if [[ ! -f "$input_file" ]]; then
    echo "Error: File $input_file not found!"
    exit 1
fi

mapfile -t lines < "$input_file"

rows=${#lines[@]}
cols=${#lines[0]}

solve() {
    is_part2=$1
    declare -A freq_positions
    unique_locations=()

    for ((i = 0; i < rows; i++)); do
        for ((j = 0; j < cols; j++)); do
            tile="${lines[i]:j:1}"
            if [[ "$tile" == "." ]]; then
                continue
            fi
            freq_positions["$tile"]+="$i,$j "
        done
    done

    for tile in "${!freq_positions[@]}"; do
        positions=(${freq_positions[$tile]})
        n=${#positions[@]}
        for ((p1 = 0; p1 < n; p1++)); do
            for ((p2 = p1 + 1; p2 < n; p2++)); do
                IFS=',' read -r x1 y1 <<< "${positions[p1]}"
                IFS=',' read -r x2 y2 <<< "${positions[p2]}"
                x_diff=$((x2 - x1))
                y_diff=$((y2 - y1))

                if [[ "$is_part2" -eq 1 ]]; then
                    temp_x1=$x1
                    temp_y1=$y1
                    while ((temp_x1 + x_diff >= 0 && temp_x1 + x_diff < rows &&
                            temp_y1 + y_diff >= 0 && temp_y1 + y_diff < cols)); do
                        temp_x1=$((temp_x1 + x_diff))
                        temp_y1=$((temp_y1 + y_diff))
                        unique_locations+=("$temp_x1,$temp_y1")
                    done

                    temp_x2=$x2
                    temp_y2=$y2
                    while ((temp_x2 - x_diff >= 0 && temp_x2 - x_diff < rows &&
                            temp_y2 - y_diff >= 0 && temp_y2 - y_diff < cols)); do
                        temp_x2=$((temp_x2 - x_diff))
                        temp_y2=$((temp_y2 - y_diff))
                        unique_locations+=("$temp_x2,$temp_y2")
                    done
                else
                    [[ $((x1 - x_diff)) -ge 0 && $((x1 - x_diff)) -lt $rows && $((y1 - y_diff)) -ge 0 && $((y1 - y_diff)) -lt $cols ]] &&
                        unique_locations+=("$((x1 - x_diff)),$((y1 - y_diff))")
                    [[ $((x2 + x_diff)) -ge 0 && $((x2 + x_diff)) -lt $rows && $((y2 + y_diff)) -ge 0 && $((y2 + y_diff)) -lt $cols ]] &&
                        unique_locations+=("$((x2 + x_diff)),$((y2 + y_diff))")
                fi
            done
        done
    done

    printf "%s\n" "${unique_locations[@]}" | sort -u | wc -l
}

echo "Part 1: $(solve 0)"
echo "Part 2: $(solve 1)"

# Pause at the end
read -p "Press Enter to exit..."