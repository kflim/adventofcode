input_file = "input.txt"

try:
    with open(input_file, "r") as f:
        lines = f.read().strip().split("\n")
except FileNotFoundError:
    print(f"Error: File {input_file} not found!")
    exit(1)

def solve(is_part2):
    grid = [list(row) for row in lines]
    rows = len(grid)
    cols = len(grid[0])
    freq_positions = dict()

    for i in range(rows):
        for j in range(cols):
            tile = grid[i][j]
            if tile == ".":
                continue
            if tile not in freq_positions:
                freq_positions[tile] = []
            freq_positions[tile].append((i, j))

    unique_locations = set()

    for tile in freq_positions:
        positions = freq_positions[tile]
        n = len(positions)
        for i in range(n):
            for j in range(i + 1, n):
                x1, y1 = positions[i]
                x2, y2 = positions[j]
                x_diff = x2 - x1
                y_diff = y2 - y1
                if is_part2:
                    temp_x1, temp_y1, temp_x2, temp_y2 = x1, y1, x2, y2
                    while temp_x1 + x_diff >= 0 and temp_x1 + x_diff < rows and temp_y1 + y_diff >= 0 and temp_y1 + y_diff < cols:
                        temp_x1 += x_diff
                        temp_y1 += y_diff
                        unique_locations.add((temp_x1, temp_y1))
                    temp_x1, temp_y1, temp_x2, temp_y2 = x1, y1, x2, y2
                    while temp_x2 - x_diff >= 0 and temp_x2 - x_diff < rows and temp_y2 - y_diff >= 0 and temp_y2 - y_diff < cols:
                        temp_x2 -= x_diff
                        temp_y2 -= y_diff
                        unique_locations.add((temp_x2, temp_y2))
                else:
                    if x1 - x_diff >= 0 and x1 - x_diff < rows and y1 - y_diff >= 0 and y1 - y_diff < cols:
                        unique_locations.add((x1 - x_diff, y1 - y_diff))
                    if x2 + x_diff >= 0 and x2 + x_diff < rows and y2 + y_diff >= 0 and y2 + y_diff < cols:
                        unique_locations.add((x2 + x_diff, y2 + y_diff))

    return len(unique_locations)

print("Part 1:", solve(False))
print("Part 2:", solve(True))
