#include "crt_debug.h"
#include <gtest/gtest.h>

#include <iostream>

using namespace std;
#if 1
int main()
{
#if _DEBUG
    _CrtSetDbgFlag(_CRTDBG_ALLOC_MEM_DF | _CRTDBG_LEAK_CHECK_DF);
#endif

    auto p = malloc(10);
    auto p1 = NEW int[15];
    struct S{
        int* pn{};
    };
    S s;
    int* pn1{};
    
    cout << "pn " << hex << s.pn << " "
        << "pn1 " << hex << pn1 << endl;
}
#else
int add(int a, int b) { return a + b; }
TEST(TestSuiteName, TestName) {
    auto p = NEW int[10];
    auto pa = malloc(3);
    ASSERT_EQ(3, add(1, 2));
}
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
#endif
