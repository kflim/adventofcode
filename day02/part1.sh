#!/bin/bash

input_file="input.txt"

# Check if the input file exists
if [ ! -f "$input_file" ]; then
    echo "Error: $input_file not found!"
    exit 1
fi

safe_reports=0

while IFS=' ' read -r -a levels || [ -n "$lv1" ]; do
    is_ascending=true
    is_descending=true
    within_diff_limit=true

    for ((i = 0; i < ${#levels[@]} - 1; i++)); do
        diff=$((levels[i+1] - levels[i]))
        abs_diff=${diff#-}

        if ((abs_diff > 3 || abs_diff < 1)); then
            within_diff_limit=false
        fi

        if ((levels[i+1] < levels[i])); then
            is_ascending=false
        fi
        if ((levels[i+1] > levels[i])); then
            is_descending=false
        fi
    done

    if $within_diff_limit && { $is_ascending || $is_descending; }; then
        ((safe_reports++))
    fi
done < "$input_file"

echo "Number of safe reports: $safe_reports"

# Pause at the end
read -p "Press Enter to exit..."
