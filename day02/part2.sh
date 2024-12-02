#!/bin/bash

input_file="input.txt"

# Check if the input file exists
if [ ! -f "$input_file" ]; then
    echo "Error: $input_file not found!"
    exit 1
fi

safe_reports=0

# Function to check if a sequence follows the trend and is safe
is_sequence_safe() {
    local -n seq=$1 # Reference to the first argument
    local is_ascending=true
    local is_descending=true
    local within_diff_limit=true
    local last_diff=0

    for ((i = 1; i < ${#seq[@]}; i++)); do
        diff=$((seq[i] - seq[i-1]))
        abs_diff=${diff#-}

        if ((abs_diff > 3 || abs_diff < 1)); then
            within_diff_limit=false
        fi

        if ((seq[i] < seq[i-1])); then
            is_ascending=false
        fi
        if ((seq[i] > seq[i-1])); then
            is_descending=false
        fi
    done

    $within_diff_limit && ($is_ascending || $is_descending)
}

while IFS=' ' read -r -a levels || [ -n "${levels[*]}" ]; do
    if ((${#levels[@]} == 0)); then
        continue
    fi

    if is_sequence_safe levels; then
        ((safe_reports++))
        continue
    fi

    # Try skipping one level at a time and check if the sequence becomes safe
    for ((skip_idx = 0; skip_idx < ${#levels[@]}; skip_idx++)); do
        # Create a new sequence with the skipped level
        temp_levels=("${levels[@]:0:$skip_idx}" "${levels[@]:$((skip_idx + 1))}")
        
        # Check if the sequence becomes safe after skipping the level
        if is_sequence_safe temp_levels; then
            ((safe_reports++))
            break
        fi
    done
done < "$input_file"

echo "Number of safe reports: $safe_reports"

# Pause at the end
read -p "Press Enter to exit..."
