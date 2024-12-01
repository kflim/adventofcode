#!/bin/bash

input_file="input.txt"

if [ ! -f "$input_file" ]; then
    echo "Error: $input_file not found!"
    exit 1
fi

list1=()
list2=()

while IFS=' ' read -r num1 num2; do
    list1+=("$num1")
    list2+=("$num2")
done < "$input_file"

# Verify both lists are the same length
if [ "${#list1[@]}" -ne "${#list2[@]}" ]; then
    echo "Error: Lists are of unequal length!"
    exit 1
fi

# Sort both lists
sorted_list1=($(printf "%s\n" "${list1[@]}" | sort -n))
sorted_list2=($(printf "%s\n" "${list2[@]}" | sort -n))

sum_of_differences=0

for i in "${!sorted_list1[@]}"; do
    diff=$((sorted_list1[i] - sorted_list2[i]))
    abs_diff=${diff#-}
    sum_of_differences=$((sum_of_differences + abs_diff))
done

echo "Sum of absolute differences: $sum_of_differences"

# Pause at the end
read -p "Press Enter to exit..."
