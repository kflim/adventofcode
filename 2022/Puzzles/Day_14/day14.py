from Util import *


def main():
    # partOne()
    partTwo()


def partOne():
    rock_paths = read_lines("input.txt")
    rock_points = setup(rock_paths)
    max_sand_capacity = find_max_sand_capacity_one(rock_points.copy())
    print("Maximum units of sand is %d" % max_sand_capacity)


def setup(rock_paths):
    rock_points = set()

    for path in rock_paths:
        points = list(map(lambda coords: eval(coords), path.split(' -> ')))
        point_len = len(points)
        for i in range(point_len - 1):
            start = points[i]
            end = points[i + 1]
            if start[0] == end[0]:
                smaller_y = start[1] if start[1] < end[1] else end[1]
                y_diff = abs(start[1] - end[1])
                for i in range(y_diff + 1):
                    rock_points.add((start[0], smaller_y + i))
            else:
                smaller_x = start[0] if start[0] < end[0] else end[0]
                x_diff = abs(start[0] - end[0])
                for i in range(x_diff + 1):
                    rock_points.add((smaller_x + i, start[1]))

    return rock_points


def find_max_sand_capacity_one(occupied_points):
    left_most_x = get_left_most_x(occupied_points)
    right_most_x = get_right_most_x(occupied_points)
    bottom_most_y = get_bottom_most_y(occupied_points)
    sand_count = 0
    curr_sand_pos = SAND_START
    can_fall = False

    while left_most_x <= curr_sand_pos[0] <= right_most_x and curr_sand_pos[1] < bottom_most_y:
        for dir in SAND_FALL_DIRECTIONS:
            next_pos = (curr_sand_pos[0] + dir[0], curr_sand_pos[1] + dir[1])
            if not next_pos in occupied_points:
                curr_sand_pos = next_pos
                can_fall = True
                break
        if not can_fall:
            sand_count += 1
            occupied_points.add(curr_sand_pos)
            curr_sand_pos = SAND_START
        can_fall = False

    return sand_count


def get_left_most_x(rock_points):
    rocks_sorted_bottom_left = sorted(
        rock_points, key=lambda point: (point[0]))
    return rocks_sorted_bottom_left[0][0]


def get_right_most_x(rock_points):
    rocks_sorted_bottom_left = sorted(
        rock_points, key=lambda point: (-point[0]))
    return rocks_sorted_bottom_left[0][0]


def get_bottom_most_y(rock_points):
    rocks_sorted_bottom_left = sorted(
        rock_points, key=lambda point: (-point[1]))
    return rocks_sorted_bottom_left[0][1]


def partTwo():
    rock_paths = read_lines("input.txt")
    rock_points = setup(rock_paths)
    max_sand_capacity = find_max_sand_capacity_two(rock_points.copy())
    print("Maximum units of sand is %d" % max_sand_capacity)


def find_max_sand_capacity_two(occupied_points):
    bottom_most_y = get_bottom_most_y(occupied_points) + 2
    sand_count = 0
    curr_sand_pos = SAND_START
    can_fall = False

    while True:
        for dir in SAND_FALL_DIRECTIONS:
            next_pos = (curr_sand_pos[0] + dir[0], curr_sand_pos[1] + dir[1])
            if not next_pos in occupied_points and next_pos[1] < bottom_most_y:
                curr_sand_pos = next_pos
                can_fall = True
                break

        if can_fall:
            can_fall = False
            continue

        sand_count += 1
        occupied_points.add(curr_sand_pos)

        if curr_sand_pos == SAND_START:
            break

        curr_sand_pos = SAND_START

    return sand_count


if __name__ == '__main__':
    main()
