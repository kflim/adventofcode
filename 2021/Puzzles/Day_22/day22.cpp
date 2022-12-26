#include <algorithm>
#include <iostream>
#include <fstream>
#include <string>
#include <vector>
#include "Cuboid.hpp"

using namespace kflim_adventofcode_2021_day22::Cuboid;

void solvePartOne(char *filename);
void solvePartTwo(char *filename);
std::vector<Cuboid> parseFile(char *filename);
int countCubesOne(std::vector<Cuboid> &cuboids);
bool canContribute(std::vector<Cuboid> &cuboids, int x, int y, int z);

int main(int argc, char **argv)
{
    if (argc < 2)
    {
        std::cout << "Missing input file.\n";
        return -1;
    }

    // solvePartOne(argv[1]);
    solvePartTwo(argv[1]);

    return 0;
}

void solvePartOne(char *filename)
{
    std::vector<Cuboid> cuboids = parseFile(filename);
    int cubeCount = countCubesOne(cuboids);
    std::cout << "Cube count: " << cubeCount << '\n';
}

std::vector<Cuboid> parseFile(char *filename)
{
    std::vector<Cuboid> cuboids;
    std::fstream file(filename);

    if (file.is_open())
    {
        std::string line;
        while (std::getline(file, line))
        {
            cuboids.push_back(Cuboid(line));
        }
    }

    return cuboids;
}

int countCubesOne(std::vector<Cuboid> &cuboids)
{
    int count = 0;

    for (int i = -50; i <= 50; i++)
    {
        for (int j = -50; j <= 50; j++)
        {
            for (int k = -50; k <= 50; k++)
            {
                count = (canContribute(cuboids, i, j, k)) ? count + 1 : count;
            }
        }
    }

    return count;
}

bool canContribute(std::vector<Cuboid> &cuboids, int i, int j, int k)
{
    bool ans = false;
    for (Cuboid c : cuboids)
    {
        if (c.containsPoint(i, j, k))
        {
            ans = c.isActive();
        }
    }

    return ans;
}

void solvePartTwo(char *filename)
{
    std::vector<Cuboid> cuboids = parseFile(filename);
}