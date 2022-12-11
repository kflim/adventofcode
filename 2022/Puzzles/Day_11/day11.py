from Util import *
import Monkey


def main():
    # partOne()
    partTwo()


def partOne():
    monkeys = Util.read_monkeys("input.txt")
    for _ in range(Util.PART_ONE_ROUNDS):
        __runMonkeyInspectionOne(monkeys)
    res = __findHighestMonkeyBusiness(monkeys)
    print("Level of monkey business after " +
          Util.PART_ONE_ROUNDS + " rounds is: " + str(res))


def partTwo():
    monkeys = Util.read_monkeys("input.txt")
    for x in range(Util.PART_TWO_ROUNDS):
        print(x)
        __runMonkeyInspectionTwo(monkeys)
    res = __findHighestMonkeyBusiness(monkeys)
    print("Level of monkey business after " +
          Util.PART_TWO_ROUNDS + " rounds is: " + str(res))


def __runMonkeyInspectionOne(monkeys):
    for monkey in monkeys:
        monkey.inspectItems(True)
        monkey.throwItems(monkeys)


def __findHighestMonkeyBusiness(monkeys):
    sortedMonkeys = sorted(
        monkeys, key=lambda monkey: monkey.getInspections(), reverse=True)
    twoMostActiveMonkeys = sortedMonkeys[:2]
    highestInspections = twoMostActiveMonkeys[0].getInspections()
    secondHighestInspections = twoMostActiveMonkeys[1].getInspections()
    return highestInspections * secondHighestInspections


def __runMonkeyInspectionTwo(monkeys):
    for monkey in monkeys:
        monkey.inspectItems(False)
        monkey.throwItems(monkeys)


if __name__ == '__main__':
    main()
