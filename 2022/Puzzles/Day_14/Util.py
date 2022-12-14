SAND_START = (500, 0)
SAND_FALL_DIRECTIONS = ((0, 1), (-1, 1), (1, 1))


def read_lines(input_file):
    data = open(input_file).read().strip()
    return [x for x in data.split('\n')]
