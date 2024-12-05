import re

input_file = "input.txt"

# Check if the file exists
try:
    with open(input_file, "r") as f:
        lines = f.read().strip().split("\n")
except FileNotFoundError:
    print(f"Error: File {input_file} not found!")
    exit(1)

rules = [line for line in lines if re.match(r"^\d+\|\d+$", line)]
updates = [line for line in lines if re.match(r"^\d+(,\d+)+$", line)]

order_rules = {}
for rule in rules:
    a, b = rule.split("|")
    order_rules[(int(a), int(b))] = True

def is_ordered(update):
    for (first, second) in order_rules:
        try:
            first_index = update.index(first)
            second_index = update.index(second)
            if first_index >= second_index:
                return False
        except ValueError:
            continue
    return True

def fix_order(update):
    update_set = set(update)
    sorted_update = []

    while update_set:
        for page in list(update_set):
            can_add = True
            for (first, second) in order_rules:
                if page == second and first in update_set:
                    can_add = False
                    break
            if can_add:
                sorted_update.append(page)
                update_set.remove(page)
    return sorted_update

total = 0

for update_str in updates:
    update = list(map(int, update_str.split(",")))
    if not is_ordered(update):
        fixed_update = fix_order(update)
        middle_index = len(fixed_update) // 2
        total += fixed_update[middle_index]

print(f"Sum of middle page numbers after fixing incorrect updates: {total}")
