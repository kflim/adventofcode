#include "Cuboid.hpp"
#include "util.hpp"

namespace kflim_adventofcode_2021_day22::Cuboid
{

    Cuboid::Cuboid(std::string_view line)
    {
        using namespace kflim_adventofcode_2021_day22::util;

        std::string onPrefix = "on";
        bool isOn = (line.rfind(onPrefix, 0) == 0);
        this->activeStatus = isOn;

        std::string originalLine{line.begin(), line.end()};
        std::string coordinates = isOn ? originalLine.substr(3) : originalLine.substr(4);
        std::vector<std::string> splitCoords = splitString(coordinates, ",");

        std::string xPrefix = "x=";
        std::string yPrefix = "y=";
        std::string zPrefix = "z=";

        for (std::string coord : splitCoords)
        {
            std::string numberRange = coord.substr(2);
            if (coord.rfind(xPrefix, 0) == 0)
            {
                std::vector<std::string> xCoords = splitString(numberRange, "..");
                this->xStart = std::stoi(xCoords.at(0));
                this->xEnd = std::stoi(xCoords.at(1));
            }
            else if (coord.rfind(yPrefix, 0) == 0)
            {
                std::vector<std::string> yCoords = splitString(numberRange, "..");
                this->yStart = std::stoi(yCoords.at(0));
                this->yEnd = std::stoi(yCoords.at(1));
            }
            else if (coord.rfind(zPrefix, 0) == 0)
            {
                std::vector<std::string> zCoords = splitString(numberRange, "..");
                this->zStart = std::stoi(zCoords.at(0));
                this->zEnd = std::stoi(zCoords.at(1));
            }
        }
    }

    bool Cuboid::containsPoint(int px, int py, int pz)
    {
        return xStart <= px && px <= xEnd && yStart <= py && py <= yEnd 
            && zStart <= pz && pz <= zEnd;
    }

    bool Cuboid::isActive()
    {
        return this->activeStatus;
    }

    std::ostream &operator<<(std::ostream &s, const Cuboid &cuboid)
    {
        return s << "{xStart: " << cuboid.xStart << ", xEnd: " << cuboid.xEnd
                 << ", yStart: " << cuboid.yStart << ", yEnd: " << cuboid.yEnd
                 << ", zStart: " << cuboid.zStart << ", zEnd: " << cuboid.zEnd
                 << ", isActive: " << cuboid.activeStatus << "}";
    }
}
