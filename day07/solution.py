def backtrack(numbers, idx, current_value, target_value, is_part2):
    if idx == len(numbers):
        return current_value == target_value
    
    if backtrack(numbers, idx + 1, current_value + numbers[idx], target_value, is_part2):
        return True
    if backtrack(numbers, idx + 1, current_value * numbers[idx], target_value, is_part2):
        return True
    if is_part2:
        concatenated_value = int(str(current_value) + str(numbers[idx]))
        if backtrack(numbers, idx + 1, concatenated_value, target_value, is_part2):
            return True
    
    return False

def solve_with_backtracking(file_path):
    total_calibration_result = 0

    with open(file_path, 'r') as file:
        input_data = file.readlines()

    for line in input_data:
        test_value, numbers = line.split(":")
        test_value = int(test_value.strip())
        numbers = list(map(int, numbers.strip().split()))
        
        if backtrack(numbers, 1, numbers[0], test_value, False):
            total_calibration_result += test_value

    return total_calibration_result

def solve_with_backtracking_and_concatenation(file_path):
    total_calibration_result = 0

    with open(file_path, 'r') as file:
        input_data = file.readlines()

    for line in input_data:
        test_value, numbers = line.split(":")
        test_value = int(test_value.strip())
        numbers = list(map(int, numbers.strip().split()))
        
        if backtrack(numbers, 1, numbers[0], test_value, True):
            total_calibration_result += test_value

    return total_calibration_result

input_file = 'input.txt'

part1 = solve_with_backtracking(input_file)
print("Part 1:", part1)

part2 = solve_with_backtracking_and_concatenation(input_file)
print("Part2:", part2)
