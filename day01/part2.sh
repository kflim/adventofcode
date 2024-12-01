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

declare -A count_arr

# Count occurrences of each number in the second list
for num in "${list2[@]}"; do
  ((count_arr[$num]++))
done

similarity_score=0

for num in "${list1[@]}"; do
  similarity_score=$(( similarity_score + num * ${count_arr[$num]:-0} )) # Use 0 as default if not found
done

echo "Total similarity score: $similarity_score"

# Pause at the end
read -p "Press Enter to exit..."
