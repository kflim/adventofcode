#include "util.hpp"

namespace kflim_adventofcode_2021_day22::util
{
    std::vector<std::string> splitString(std::string str, std::string delimiter)
    {
        std::vector<std::string> strings;
        int delSize = delimiter.size();
        int start = 0;
        int end = -delSize;

        do
        {
            start = end + delSize;
            end = str.find(delimiter, start);
            strings.push_back(str.substr(start, end - start));
        } while (end != -1);

        return strings;
    }
}
