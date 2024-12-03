#!/bin/bash

input_file="input.txt"

# Check if the input file exists
if [ ! -f "$input_file" ]; then
    echo "Error: $input_file not found!"
    exit 1
fi

sum=0
enabled=true

while IFS= read -r line; do
    # Extract all `mul(x,x)`, `do()`, `don't()` patterns from the current line
    instructions=$(echo "$line" | grep -oP '(mul\(\d+,\d+\)|do\(\)|don'\''t\(\))')

    while IFS= read -r instruction; do
        case $instruction in
            "do()")
                enabled=true
                ;;
            "don't()")
                enabled=false
                ;;
            mul\(*,*\))
                if $enabled; then
                    nums=$(echo "$instruction" | grep -oP '\d+,\d+')
                    IFS=',' read -r num1 num2 <<< "$nums"
                    product=$((num1 * num2))
                    sum=$((sum + product))
                fi
                ;;
        esac
    done <<< "$instructions"
done < "$input_file"

echo "Total sum is: $sum"

# Pause at the end
read -p "Press Enter to exit..."