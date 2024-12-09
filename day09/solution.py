input_file = "input.txt"

try:
    with open(input_file, "r") as f:
        line = f.read().strip()
except FileNotFoundError:
    print(f"Error: File {input_file} not found!")
    exit(1)

def part1():
    parsed = []
    file_id = 0
    for i in range(0, len(line), 2):
        file_length = int(line[i])
        free_length = int(line[i + 1]) if i + 1 < len(line) else 0
        parsed.extend([file_id] * file_length + ['.'] * free_length)
        file_id += 1

    front, back = 0, len(parsed) - 1
    while front < back:
        while front < len(parsed) and parsed[front] != '.':
            front += 1

        while back >= 0 and parsed[back] == '.':
            back -= 1

        if front < back:
            parsed[front], parsed[back] = parsed[back], '.'

    checksum = sum(idx * block for idx, block in enumerate(parsed) if block != '.')
    return checksum

def part2():
    parsed = []
    file_id = 0
    files = []
    for i in range(0, len(line), 2):
        file_length = int(line[i])
        free_length = int(line[i + 1]) if i + 1 < len(line) else 0
        if file_length > 0:
            files.append((file_id, file_length))
        parsed.extend([file_id] * file_length + ['.'] * free_length)
        file_id += 1

    for file_id, file_length in sorted(files, reverse=True):
        span_start = -1
        for i in range(len(parsed) - file_length + 1):
            if all(parsed[j] == '.' for j in range(i, i + file_length)):
                if i + file_length <= parsed.index(file_id):
                    span_start = i
                    break

        if span_start != -1:
            parsed = [block if block != file_id else '.' for block in parsed]
            for j in range(file_length):
                parsed[span_start + j] = file_id

    checksum = sum(idx * block for idx, block in enumerate(parsed) if block != '.')
    return checksum

print(f"Part 1: {part1()}")
# print(f"Part 2: {part2()}")
