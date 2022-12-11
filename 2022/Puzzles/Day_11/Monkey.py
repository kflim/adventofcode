import re


class Monkey:
    def __init__(self, idx, itemsInfo, opInfo, testInfo):
        self.idx = idx
        self.items = self.initItems(itemsInfo)
        self.op = self.initOp(opInfo)
        self.test = self.initTest(testInfo)
        self.monkeysToThrowAt = []
        self.inspections = 0

    def initItems(self, itemsInfo):
        itemsDesc = itemsInfo.split(": ")
        itemsStr = itemsDesc[1]
        return list(map(int, itemsStr.split(", ")))

    def initOp(self, opInfo):
        opDesc = opInfo.split("= ")
        opOperands = opDesc[1].split(" ")
        return lambda input: self.__parseOp(input, opOperands)

    def __parseOp(self, input, opOperands):
        op = opOperands[1]
        thirdOperand = int(
            opOperands[2]) if opOperands[2].isnumeric() else opOperands[2]
        if isinstance(thirdOperand, str):
            if op == "+":
                return input + input
            elif op == "-":
                return input - input
            elif op == "*":
                return input * input
            elif op == "/":
                return input / input
        else:
            if op == "+":
                return input + thirdOperand
            elif op == "-":
                return input - thirdOperand
            elif op == "*":
                return input * thirdOperand
            elif op == "/":
                return input / thirdOperand

    def initTest(self, testInfo):
        num_regex = re.compile(r"\d+")
        test_numbers = list(
            map(lambda info: num_regex.findall(info), testInfo)
        )
        divisible_test_number = int(test_numbers[0][0])
        return lambda input: self.__determineMonkeyToThrowAt(input, divisible_test_number, test_numbers)

    def __determineMonkeyToThrowAt(self, input, divisible_test_number, test_numbers):
        if input % divisible_test_number == 0:
            self.monkeysToThrowAt.append(int(test_numbers[1][0]))
        else:
            self.monkeysToThrowAt.append(int(test_numbers[2][0]))

    def inspectItems(self, hasRelief):
        newItems = []
        while self.items:
            nextItem = self.items.pop(0)
            newItem = self.__performOp(nextItem, hasRelief)
            newItems.append(newItem)
            self.__findNextMonkeyToThrowAt(newItem)
            self.inspections += 1
        self.items = newItems.copy()

    def __performOp(self, item, hasRelief):
        return self.op(item) // 3 if hasRelief else self.op(item)

    def __findNextMonkeyToThrowAt(self, item):
        self.test(item)

    def throwItems(self, monkeys):
        for idx, item in enumerate(self.items):
            nextMonkeyIdx = self.monkeysToThrowAt[idx]
            nextMonkey = monkeys[nextMonkeyIdx]
            nextMonkey.addItem(item)
        self.items.clear()
        self.monkeysToThrowAt.clear()

    def addItem(self, item):
        self.items.append(item)

    def getInspections(self):
        return self.inspections

    def __str__(self):
        if self.items:
            return "Monkey " + str(self.idx) + ": " + " ".join(list(map(lambda item: str(item), self.items))) + ", Inspections: " + str(self.inspections)
        else:
            return "Monkey " + str(self.idx) + ": Inspections: " + str(self.inspections)

    def __repr__(self):
        return self.__str__()
