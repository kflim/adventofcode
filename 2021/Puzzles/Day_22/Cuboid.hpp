#ifndef CUBOID_H
#define CUBOID_H

#include <iostream>
#include <string>

namespace kflim_adventofcode_2021_day22::Cuboid
{
    class Cuboid
    {
    private:
        int xStart;
        int xEnd;
        int yStart;
        int yEnd;
        int zStart;
        int zEnd;
        bool activeStatus;

    public:
        Cuboid(std::string_view line);
        bool containsPoint(int px, int py, int pz);
        bool isActive();
        friend std::ostream &operator<<(std::ostream &s, const Cuboid &cuboid);
    };
}

#endif