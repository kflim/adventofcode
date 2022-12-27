class Util:
    DELTAS = ((0, 1), (1, 0), (0, -1), (-1, 0))

    def read_grid(input_file):
        data = open(input_file).read().strip()
        rows = [x for x in data.split('\n')]
        return [list(row) for row in rows]

    def findSPos(grid):
        grid_len = len(grid)
        grid_depth = len(grid[0])

        for x in range(grid_len):
            for y in range(grid_depth):
                if grid[x][y] == 'S':
                    return x, y
