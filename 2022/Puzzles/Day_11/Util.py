import re
from Monkey import *


class Util:
    PART_ONE_ROUNDS = 20
    PART_TWO_ROUNDS = 10000

    def read_monkeys(input_file):
        data = open(input_file).read().strip()
        allLines = [x for x in data.split("\n")]
        monkey_regex = re.compile(r"(Monkey \d+)")
        monkey_matches = monkey_regex.findall(data)

        monkeys = []
        for idx, _ in enumerate(monkey_matches):
            infoStartIdx = idx * 7 + 1
            itemsInfo = allLines[infoStartIdx: infoStartIdx + 1][0]
            opInfo = allLines[infoStartIdx + 1: infoStartIdx + 2][0]
            testInfo = allLines[infoStartIdx + 2: infoStartIdx + 5]
            monkeys.append(Monkey(idx, itemsInfo, opInfo, testInfo))

        return monkeys
