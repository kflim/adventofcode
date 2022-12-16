from Util import *


def main():
    # partOne()
    partTwo()


def partOne():
    instructions = Util.read_lines()
    signal_strengths = execute_and_analyze(instructions)
    print(signal_strengths)
    print("Sum of the interesting signal strengths is: {:d}"
          .format(sum(signal_strengths)))


def execute_and_analyze(instructions):
    current_cycle = 1
    cycles_remaining = 0
    x = 1
    special_idx = Util.SPECIAL_CYCLE_START_IDX
    special_signal_strengths = []

    for instruction in instructions:
        cycles_increment, x_increment = parseInstruction(instruction)
        cycles_remaining += cycles_increment

        while cycles_remaining > 0:
            if current_cycle == special_idx:
                special_signal_strengths.append(x * current_cycle)
                special_idx += Util.SPECIAL_CYCLE_INCREMENT
            cycles_remaining -= 1
            current_cycle += 1

        x += x_increment

    return special_signal_strengths


def parseInstruction(instruction):
    x_increment = 0
    instruction_details = instruction.split(" ")
    if len(instruction_details) == 1:
        cycles_increment = 1
    else:
        cycles_increment = 2
        x_increment = int(instruction_details[1])
    return cycles_increment, x_increment


def partTwo():
    instructions = Util.read_lines()
    imageRendered = executeWithCRT(instructions)
    print(*imageRendered, sep="\n")


def executeWithCRT(instructions):
    current_cycle = 1
    cycles_remaining = 0
    x = 1
    end_of_row_idx = Util.CRT_ROW_INITIAL_ROW_END
    image = []
    curr_pixels = []

    for instruction in instructions:
        cycles_increment, x_increment = parseInstruction(instruction)
        cycles_remaining += cycles_increment

        while cycles_remaining > 0:
            add_pixel(current_cycle, x, curr_pixels)
            cycles_remaining -= 1
            current_cycle += 1

            if current_cycle - 1 == end_of_row_idx:
                end_of_row_idx += Util.SPECIAL_CYCLE_INCREMENT
                image.append(curr_pixels.copy())
                curr_pixels.clear()

        x += x_increment

    return image


def add_pixel(current_cycle, x, curr_pixels):
    if is_beside_sprite(current_cycle, x):
        curr_pixels.append('#')
    else:
        curr_pixels.append('.')


def is_beside_sprite(current_cycle, x):
    cycle_x_pos = (current_cycle - 1) % 40
    return abs(cycle_x_pos - x) <= 1


if __name__ == '__main__':
    main()
