#include <tuple>
#include <iostream>
#include <format>
#include <fmt/core.h>

using namespace std;

int main()
{
    const tuple<string, int> checks[] = {
        {"abcd", 1},
        {"xyz", 2},
    };
    for (const auto& [arg, ret]: checks) {
        cout << format("{}: {}\n", arg, ret);
    }
    fmt::print("{:#x} {:#b}\n", 0x15, 0b1100);
}
