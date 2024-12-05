#!/bin/bash

input_file="input.txt"

# Check if the file exists
if [[ ! -f "$input_file" ]]; then
    echo "Error: File $input_file not found!"
    exit 1
fi

page_orders=()
updates=()

reading_rules=true
while IFS= read -r line || [ -n "$line" ]; do
    if [[ -z "$line" ]]; then
        reading_rules=false
        continue
    fi
    
    if $reading_rules; then
        page_orders+=("$line")
    else
        updates+=("$line")
    fi
done < "$input_file"

is_ordered() {
    local update=$1
    for order in "${page_orders[@]}"; do
        IFS='|' read -r first second <<< "$order"
        local first_index=-1
        local second_index=-1
        for i in "${update[@]}"; do
            IFS=',' read -r -a split_values <<< "$i"
            for index in "${!split_values[@]}"; do
                value="${split_values[$index]}"
                if [[ "$value" == "$first" ]]; then
                    first_index=$index
                elif [[ "$value" == "$second" ]]; then
                    second_index=$index
                fi
            done
        done

        if [[ $first_index -eq -1 || $second_index -eq -1 ]]; then
            continue
        fi

        if [[ $first_index -ge $second_index ]]; then
            return 1
        fi
    done
    return 0
}

# part1=0
part2=0
for update in "${updates[@]}"; do
    #if is_ordered $update; then
    #    IFS=',' read -r -a split_update <<< "$i"
    #    middle_index=$(( ${#split_update[@]} / 2 ))
    #    middle_page="${split_update[middle_index]}"
    #    if [[ -n "$middle_page" ]]; then
    #        part1=$(( part1 + middle_page ))
    #    fi
    #fi
    if ! is_ordered "$update"; then
        # Split the update into an array
        IFS=',' read -r -a split_update <<< "$i"
        
        # Initialize the sorted update array and the update_set array
        sorted_update=()
        update_set=("${split_update[@]}")  # Copy the array to update_set
        
        # Continue adding pages until all are ordered
        while [ ${#update_set[@]} -gt 0 ]; do
            for page in "${update_set[@]}"; do
                can_add=true
                
                # Check if all rules for `page` are satisfied
                for order in "${page_orders[@]}"; do
                    IFS='|' read -r first second <<< "$order"
                    
                    if [[ "$page" == "$second" ]] && [[ " ${update_set[@]} " =~ " $first " ]]; then
                        can_add=false
                        break
                    fi
                done
                
                # If the page is allowed, add it to the sorted update
                if $can_add; then
                    sorted_update+=("$page")
                    
                    # Remove the page from update_set by creating a new array without it
                    new_update_set=()
                    for elem in "${update_set[@]}"; do
                        if [[ "$elem" != "$page" ]]; then
                            new_update_set+=("$elem")
                        fi
                    done
                    update_set=("${new_update_set[@]}")  # Reassign the updated set
                fi
            done
        done
        
        # Output the sorted update
        middle_index=$(( ${#sorted_update[@]} / 2 ))
        middle_page="${sorted_update[middle_index]}"
        if [[ -n "$middle_page" ]]; then
            part2=$(( part2 + middle_page ))
        fi
    fi
done

# echo "Part 1: $part1"
echo "Part 2: $part2"

# Pause at the end
read -p "Press Enter to exit..."
