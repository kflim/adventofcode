from ast import literal_eval

DIVIDER_PACKETS = [[[2]], [[6]]]


def read_packets(input_file):
    data = open(input_file).read().strip()
    packet_exprs = list(filter(lambda row: len(row) > 0,
                        [x for x in data.split('\n')]))
    return list(map(lambda packet_expr: literal_eval(packet_expr), packet_exprs))
