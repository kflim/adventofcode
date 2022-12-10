import sys


class Util:
    SPECIAL_CYCLE_START_IDX = 20
    SPECIAL_CYCLE_INCREMENT = 40
    CRT_ROW_INITIAL_ROW_END = 40

    def read_lines():
        input_file = sys.argv[1] if len(sys.argv) > 1 else 'input.txt'
        data = open(input_file).read().strip()
        return [x for x in data.split('\n')]
