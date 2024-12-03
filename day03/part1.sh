#!/bin/bash

input_file="input.txt"

# Check if the input file exists
if [ ! -f "$input_file" ]; then
    echo "Error: $input_file not found!"
    exit 1
fi

sum=0

while IFS= read -r line; do
    # Extract all `mul(x,x)` patterns from the current line
    matches=$(echo "$line" | grep -oP 'mul\(\d+,\d+\)')
    
    while IFS= read -r match; do
        nums=$(echo "$match" | grep -oP '\d+,\d+')
        IFS=',' read -r num1 num2 <<< "$nums"
        product=$((num1 * num2))
        sum=$((sum + product))
    done <<< "$matches"
done < "$input_file"

echo "Total sum is: $sum"

# Pause at the end
read -p "Press Enter to exit..."