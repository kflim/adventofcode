from collections import deque

from Util import Util


def main():
    # partOne()
    partTwo()


def partOne():
    areaGrid, elevations, visited, queue = __setup(1)
    grid_len = len(areaGrid)
    grid_depth = len(areaGrid[0])

    while queue:
        (row, col), steps = queue.popleft()
        if (row, col) in visited:
            continue

        visited.add((row, col))
        if areaGrid[row][col] == 'E':
            print("Minimum steps is : " + str(steps))
            break

        for (deltaX, deltaY) in Util.DELTAS:
            nextRow = row + deltaX
            nextCol = col + deltaY
            if 0 <= nextRow < grid_len and 0 <= nextCol < grid_depth and elevations[nextRow][nextCol] - elevations[row][col] <= 1:
                queue.append(((nextRow, nextCol), steps + 1))


def __setup(part):
    areaGrid = Util.read_grid("input.txt")
    grid_len = len(areaGrid)
    grid_depth = len(areaGrid[0])
    elevations = [[0 for _ in range(grid_depth)] for _ in range(grid_len)]
    for i in range(grid_len):
        for j in range(grid_depth):
            if areaGrid[i][j] == 'S':
                elevations[i][j] = 1
            elif areaGrid[i][j] == 'E':
                elevations[i][j] = 26
            else:
                elevations[i][j] = ord(areaGrid[i][j]) - ord('a') + 1
    visited = set()
    queue = __get_queue(areaGrid, part, elevations)

    return areaGrid, elevations, visited, queue


def __get_queue(areaGrid, part, elevations):
    queue = deque()

    if part == 1:
        Sx, Sy = Util.findSPos(areaGrid)
        queue.append(((Sx, Sy), 0))
        return queue
    elif part == 2:
        grid_len = len(areaGrid)
        grid_depth = len(areaGrid[0])
        for i in range(grid_len):
            for j in range(grid_depth):
                if elevations[i][j] == 1:
                    queue.append(((i, j), 0))
        return queue


def partTwo():
    areaGrid, elevations, visited, queue = __setup(2)
    grid_len = len(areaGrid)
    grid_depth = len(areaGrid[0])

    while queue:
        (row, col), steps = queue.popleft()
        if (row, col) in visited:
            continue

        visited.add((row, col))
        if areaGrid[row][col] == 'E':
            print("Minimum steps is : " + str(steps))
            break

        for (deltaX, deltaY) in Util.DELTAS:
            nextRow = row + deltaX
            nextCol = col + deltaY
            if 0 <= nextRow < grid_len and 0 <= nextCol < grid_depth and elevations[nextRow][nextCol] - elevations[row][col] <= 1:
                queue.append(((nextRow, nextCol), steps + 1))


if __name__ == '__main__':
    main()
