import re
from Util import *


def main():
    # partOne()
    partTwo()


def partOne():
    points_info = read_lines("input.txt")
    points_dict = parse_points(points_info, 1)
    closest_points = filter_points_by_dist(points_dict, PART_ONE_ROW)
    invalid_beacon_count = get_invalid_beacon_count(
        PART_ONE_ROW, closest_points, points_dict)
    print("Number of invalid positions at y = %d: %d" %
          (PART_ONE_ROW, invalid_beacon_count))


def parse_points(points_info, part):
    points_dict = defaultdict(int)

    for line in points_info:
        point_regex = re.compile(r"(x=-?\d+, y=-?\d+)")
        point_matches = point_regex.findall(line)
        sensor_coords = point_matches[0]
        sensor_point = tuple(
            map(lambda str: int(str.split("=")[1]), sensor_coords.split(",", 1)))
        beacon_coords = point_matches[1]
        beacon_point = tuple(
            map(lambda str: int(str.split("=")[1]), beacon_coords.split(",", 1)))
        points_diff = get_manhattan_dist(
            sensor_point, beacon_point
        )
        if part == 1:
            points_dict[sensor_point] = ((beacon_point,) + ((points_diff),))
        elif part == 2:
            points_dict[sensor_point] = points_diff

    return points_dict


def filter_points_by_dist(points_dict, y_dest):
    filtered_points = []

    for point, value in points_dict.items():
        vertical_dist_to_y = abs(point[1] - y_dest)
        if vertical_dist_to_y < value[1]:
            filtered_points.append(point)

    return filtered_points


def get_invalid_beacon_count(y, closest_points, points_dict):
    beacon_positions = [value[0] for _, value in points_dict.items()]
    sensor_positions = [key for key, _ in points_dict.items()]
    invalid_beacons = set()

    for point in closest_points:
        dist_to_closest_beacon = points_dict[point][1]
        dist_to_y = abs(y - point[1])
        max_x_dist = dist_to_closest_beacon - dist_to_y
        for i in range(max_x_dist + 1):
            next_left_point = (point[0] - i, y)
            next_right_point = (point[0] + i, y)
            if not next_left_point in beacon_positions and not next_left_point in sensor_positions:
                invalid_beacons.add(next_left_point)
            if not next_right_point in beacon_positions and not next_right_point in sensor_positions:
                invalid_beacons.add(next_right_point)

    return len(invalid_beacons)


def partTwo():
    points_info = read_lines("input.txt")
    points_dict = parse_points(points_info, 2)
    distress_beacon_point = find_undetected_beacon(points_dict)
    print(distress_beacon_point)


def find_undetected_beacon(points_dict):
    for i in range(PART_TWO_XY_SIZE):
        for j in range(PART_TWO_XY_SIZE):
            curr_point = (i, j)
            print(curr_point)
            if cannot_be_detected(curr_point, points_dict):
                return curr_point


def cannot_be_detected(curr_point, points_dict):
    for point, dist in points_dict.items():
        shortest_dist_to_point = get_manhattan_dist(curr_point, point)
        if shortest_dist_to_point <= dist:
            return False
    return True


if __name__ == '__main__':
    main()
