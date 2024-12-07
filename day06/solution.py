from collections import defaultdict
from bisect import insort, bisect

input_file = "input.txt"

# Check if the file exists
try:
    with open(input_file, "r") as f:
        lines = f.read().strip().split("\n")
except FileNotFoundError:
    print(f"Error: File {input_file} not found!")
    exit(1)

grid = [list(row) for row in lines]
rows = len(grid)
cols = len(grid[0])

for i in range(rows):
    for j in range(cols):
        if grid[i][j] == "^":
            start_x = i
            start_y = j
            break

def count_positions(curr_x=0, curr_y=0):
    positions = set()
    directions = [[-1, 0], [0, 1], [1, 0], [0, -1]]
    direction_index = 0

    while True:
        positions.add((curr_x, curr_y))
        next_x = curr_x + directions[direction_index][0]
        next_y = curr_y + directions[direction_index][1]
        if next_x < 0 or next_x >= rows or next_y < 0 or next_y >= cols:
            break
        if grid[next_x][next_y] == "#":
            direction_index = (direction_index + 1) % 4
            continue
        curr_x = next_x
        curr_y = next_y

    return len(positions)

# Credits to https://github.com/mgtezak, tried to not brute force but couldn't find a solution
def count_obstructions(grid):
    m, n = len(grid), len(grid[0])
    obstacles = {
        'rows': defaultdict(list),
        'cols': defaultdict(list),
    }
    for r in range(m):
        for c in range(n):
            if grid[r][c] == '#':
                insort(obstacles['rows'][r], c)
                insort(obstacles['cols'][c], r)
            if grid[r][c] == '^':
                start = (r, c, 'up')

    def move(r, c, d, obstacles):
        r_obs = obstacles['rows'][r]
        c_obs = obstacles['cols'][c]

        if d == 'up':
            if not c_obs or c_obs[0] > r:
                new_r = -1
            else:
                i = bisect(c_obs, r)
                new_r = c_obs[i-1] + 1
            return new_r, c, 'right'

        if d == 'right':
            if not r_obs or r_obs[-1] < c:
                new_c = n
            else:
                i = bisect(r_obs, c)
                new_c = r_obs[i] - 1
            return r, new_c, 'down'
        
        if d == 'down':
            if not c_obs or c_obs[-1] < r:
                new_r = m
            else:
                i = bisect(c_obs, r)
                new_r = c_obs[i] - 1
            return new_r, c, 'left'

        if d == 'left':
            if not r_obs or r_obs[0] > c:
                new_c = -1
            else:
                i = bisect(r_obs, c)
                new_c = r_obs[i-1] + 1
            return r, new_c, 'up'

    candidates = set()
    r, c, d = start
    while r in range(m) and c in range(n):
        new_r, new_c, new_d = move(r, c, d, obstacles)
        if d == 'up':
            candidates |= set((i, c) for i in range(new_r+1, r+1))
        elif d == 'right':
            candidates |= set((r, j) for j in range(c, new_c))
        elif d == 'down':
            candidates |= set((i, c) for i in range(r, new_r))
        elif d == 'left':
            candidates |= set((r, j) for j in range(new_c+1, c+1))
        r, c, d = new_r, new_c, new_d

    def is_looping(obstacles):
        r, c, d = start
        visited = set([start])
        while r in range(m) and c in range(n):
            r, c, d = move(r, c, d, obstacles)
            if (r, c, d) in visited:
                return True
            visited.add((r, c, d))
        return False
    
    loop_count = 0
    for r, c in candidates:
        insort(obstacles['rows'][r], c)
        insort(obstacles['cols'][c], r)
        loop_count += is_looping(obstacles)
        obstacles['rows'][r].remove(c)    
        obstacles['cols'][c].remove(r)

    return loop_count

print(f"Number of unique positions visited: {count_positions(start_x, start_y)}")
print(f"Number of unique obstructions possible: {count_obstructions(grid)}")
