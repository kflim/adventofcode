#include <fstream>
#include <iostream>
#include <list>
#include <string>
#include <vector>
#include "util.hpp"

using namespace kflim_adventofcode_2022_day20::util;

void partOne(char *);
std::list<int> parseFileToInt(char *);
void mixNumbers(std::list<int> &);
void moveNode(std::list<int> &, std::list<int>::iterator &);
int getZeroPos(std::list<int> &);
int sumGroveCoordinates(std::list<int> &, int zeroPos);

void partTwo(char *, char *);
std::list<long long> parseFileToLongLong(char *);
void applyDecryptionKey(std::list<long long> &);
std::vector<std::list<long long>::iterator> getNodes(std::list<long long> &);
void initMix(std::list<long long> &,
             std::vector<std::list<long long>::iterator>,
             int);
void mixNumbers(std::list<long long> &,
                std::vector<std::list<long long>::iterator> &,
                std::vector<std::list<long long>::iterator>);
std::list<long long>::iterator moveNode(std::list<long long> &,
                                        std::list<long long>::iterator &);
int getZeroPos(std::list<long long> &);
long long sumGroveCoordinates(std::list<long long> &, int zeroPos);

int main(int argc, char **argv)
{
    if (argc < 2)
    {
        std::cout << "Insufficient arguments." << '\n';
        return -1;
    }

    if (argc == 2)
    {
        partOne(argv[1]);
        return 0;
    }

    partTwo(argv[1], argv[2]);
    return 0;
}

void partOne(char *filename)
{
    std::list<int> nums = parseFileToInt(filename);
    mixNumbers(nums);
    int z = getZeroPos(nums);
    int sum = sumGroveCoordinates(nums, z);
    std::cout << "Sum of grove coordinates is: " << sum << ".\n";
}

std::list<int> parseFileToInt(char *filename)
{
    std::list<int> lst;
    std::fstream file(filename);

    if (file.is_open())
    {
        std::string line;
        while (std::getline(file, line))
        {
            lst.push_back(std::stoi(line));
        }
    }

    return lst;
}

void mixNumbers(std::list<int> &lst)
{
    std::vector<std::list<int>::iterator> nodes;

    for (std::list<int>::iterator node = lst.begin(); node != lst.end(); node++)
    {
        nodes.push_back(node);
    }

    for (std::list<int>::iterator node : nodes)
    {
        moveNode(lst, node);
    }
}

void moveNode(std::list<int> &lst, std::list<int>::iterator &node)
{
    int n = *node;

    if (n == 0)
    {
        return;
    }

    int modulus = lst.size() - 1;
    int steps = floorModulo(n, modulus);

    for (int i = 0; i < steps; i++)
    {
        std::list<int>::iterator currPos = lst.erase(node);
        if (currPos == lst.end())
        {
            currPos = lst.begin();
        }
        currPos++;
        node = lst.insert(currPos, n);
    }
}

int getZeroPos(std::list<int> &lst)
{
    int pos = 0;

    for (std::list<int>::iterator node = lst.begin(); node != lst.end(); node++)
    {
        if (*node == 0)
        {
            return pos;
        }
        pos++;
    }

    std::cerr << "Element 0 position not found. Exiting program." << '\n';
    exit(1);
}

int sumGroveCoordinates(std::list<int> &lst, int zeroPos)
{
    std::vector<int> numsVec;

    for (int num : lst)
    {
        numsVec.push_back(num);
    }

    int n = numsVec.size();
    int first = numsVec[(zeroPos + 1000) % n];
    int second = numsVec[(zeroPos + 2000) % n];
    int third = numsVec[(zeroPos + 3000) % n];

    return first + second + third;
}

void partTwo(char *filename, char *times)
{
    std::list<long long> nums = parseFileToLongLong(filename);
    applyDecryptionKey(nums);
    initMix(nums, getNodes(nums), std::stoi(times));
    int z = getZeroPos(nums);
    long long sum = sumGroveCoordinates(nums, z);
    std::cout << "Sum of grove coordinates is: " << sum << ".\n";
}

std::list<long long> parseFileToLongLong(char *filename)
{
    std::list<long long> lst;
    std::fstream file(filename);

    if (file.is_open())
    {
        std::string line;
        while (std::getline(file, line))
        {
            lst.push_back(std::stoll(line));
        }
    }

    return lst;
}

void applyDecryptionKey(std::list<long long> &nums)
{
    for (std::list<long long>::iterator node = nums.begin(); node != nums.end(); node++)
    {
        *node *= DECRYPTION_KEY;
    }
}

std::vector<std::list<long long>::iterator> getNodes(std::list<long long> &lst)
{
    std::vector<std::list<long long>::iterator> nodes;

    for (std::list<long long>::iterator node = lst.begin(); node != lst.end(); node++)
    {
        nodes.push_back(node);
    }

    return nodes;
}

void initMix(std::list<long long> &lst,
             std::vector<std::list<long long>::iterator> nodes,
             int times)
{
    for (int i = 0; i < times; i++)
    {
        mixNumbers(lst, nodes, copyVec(nodes));
    }
}

void mixNumbers(std::list<long long> &lst,
                std::vector<std::list<long long>::iterator> &newNodes,
                std::vector<std::list<long long>::iterator> currNodes)
{
    newNodes.clear();

    for (std::list<long long>::iterator node : currNodes)
    {
        newNodes.push_back(moveNode(lst, node));
    }
}

std::list<long long>::iterator moveNode(std::list<long long> &lst,
                                        std::list<long long>::iterator &node)
{
    long long n = *node;

    if (n == 0)
    {
        return node;
    }

    int modulus = lst.size() - 1;
    int steps = floorModulo(n, modulus);

    for (int i = 0; i < steps; i++)
    {
        std::list<long long>::iterator currPos = lst.erase(node);
        if (currPos == lst.end())
        {
            currPos = lst.begin();
        }
        currPos++;
        node = lst.insert(currPos, n);
    }

    return node;
}

int getZeroPos(std::list<long long> &lst)
{
    int pos = 0;

    for (std::list<long long>::iterator node = lst.begin(); node != lst.end(); node++)
    {
        if (*node == 0)
        {
            return pos;
        }
        pos++;
    }

    std::cerr << "Element 0 position not found. Exiting program." << '\n';
    exit(1);
}

long long sumGroveCoordinates(std::list<long long> &lst, int zeroPos)
{
    std::vector<long long> numsVec;

    for (long long num : lst)
    {
        numsVec.push_back(num);
    }

    int n = numsVec.size();
    long long first = numsVec[(zeroPos + 1000) % n];
    long long second = numsVec[(zeroPos + 2000) % n];
    long long third = numsVec[(zeroPos + 3000) % n];

    return first + second + third;
}