from functools import cmp_to_key
from math import prod
from Util import *


def main():
    # partOne()
    partTwo()


def partOne():
    packets = read_packets("input.txt")
    right_order_pair_idxs = []
    packets_len = len(packets)

    for i in range(0, packets_len, 2):
        if __check_signals(packets[i:i + 2]) < 0:
            right_order_pair_idxs.append((i // 2) + 1)

    print(*right_order_pair_idxs, sep=",")
    ans = sum(right_order_pair_idxs)
    print("The sum of the right order pairs is: " + str(ans))


def __check_signals(signals):
    first = signals[0]
    second = signals[1]

    if isinstance(first, int) and isinstance(second, int):
        return first - second
    elif isinstance(first, list) and isinstance(second, list):
        signal_one_len = len(first)
        signal_two_len = len(second)
        i = 0

        while i < signal_one_len and i < signal_two_len:
            check_res = __check_signals([first[i], second[i]])
            if check_res > 0 or check_res < 0:
                return check_res
            i += 1

        if i == signal_one_len and signal_one_len < signal_two_len:
            return -1
        elif i == signal_one_len and signal_one_len == signal_two_len:
            return 0
        else:
            return 1
    elif isinstance(first, int) and isinstance(second, list):
        return __check_signals([[first], second])
    elif isinstance(first, list) and isinstance(second, int):
        return __check_signals([first, [second]])


def partTwo():
    packets = read_packets("input.txt")
    for packet in DIVIDER_PACKETS:
        packets.append(packet)
    packets.sort(
        key=cmp_to_key(
            lambda packet1, packet2: __check_signals([packet1, packet2])
        )
    )
    divider_idxs = []
    for idx, packet in enumerate(packets):
        if packet in DIVIDER_PACKETS:
            divider_idxs.append(idx + 1)
    print(*divider_idxs, sep=",")
    print("The decoder key is: " + str(prod(divider_idxs)))


if __name__ == '__main__':
    main()
