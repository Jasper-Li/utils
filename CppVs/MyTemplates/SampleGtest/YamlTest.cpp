#include <gtest/gtest.h>

int main(int argc, char** argv) {
    ::testing::InitGoogleTest(&argc, argv);
    const auto res = RUN_ALL_TESTS();
    return res;
}
