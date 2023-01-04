#include "util.hpp"

namespace kflim_adventofcode_2022_day20::util
{
    extern const long DECRYPTION_KEY = 811589153;

    int floorModulo(const int &n, const int &m)
    {
        return (n % m + m) % m;
    }

    int floorModulo(const long long &n, const int &m)
    {
        return (n % m + m) % m;
    }

    std::vector<std::list<long long>::iterator> copyVec(
        std::vector<std::list<long long>::iterator> &lst)
    {
        std::vector<std::list<long long>::iterator> vec;

        for (int i = 0; i < lst.size(); i++)
        {
            vec.push_back(lst[i]);
        }

        return vec;
    }
}