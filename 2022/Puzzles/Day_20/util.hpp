#ifndef UTIL_H
#define UTIL_H

#include <list>
#include <vector>

namespace kflim_adventofcode_2022_day20::util
{
    extern const long DECRYPTION_KEY;

    int floorModulo(const int &n, const int &m);
    int floorModulo(const long long &n, const int &m);
    std::vector<std::list<long long>::iterator> copyVec(
        std::vector<std::list<long long>::iterator> &);
}

#endif