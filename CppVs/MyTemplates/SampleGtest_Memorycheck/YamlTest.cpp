#include <gtest/gtest.h>
#ifdef _DEBUG
#define _CRTDBG_MAP_ALLOC
#include <stdlib.h>
#include <crtdbg.h>
#define NEW new ( _NORMAL_BLOCK , __FILE__ , __LINE__ )
// Replace _NORMAL_BLOCK with _CLIENT_BLOCK if you want the
// allocations to be of _CLIENT_BLOCK type

#else
#define NEW new
#endif

int main(int argc, char** argv) {
    ::testing::InitGoogleTest(&argc, argv);
#if _DEBUG
    _CrtMemState memoryState = { 0 };
    _CrtMemCheckpoint(&memoryState);
#endif
    auto res = RUN_ALL_TESTS();
#if _DEBUG
    _CrtMemDumpAllObjectsSince(&memoryState);
#endif
    return res;
}
