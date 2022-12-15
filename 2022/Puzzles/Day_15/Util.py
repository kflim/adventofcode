from collections import defaultdict

PART_ONE_ROW = 2000000
PART_TWO_XY_SIZE = 4000000


def read_lines(input_file):
    data = open(input_file).read().strip()
    return [x for x in data.split('\n')]


def get_manhattan_dist(point_x, point_y):
    return abs(point_x[0] - point_y[0]) + abs(point_x[1] - point_y[1])
