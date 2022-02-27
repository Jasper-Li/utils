cmake_minimum_required(VERSION 3.20.0)
project(QxCrypt VERSION 0.1.0)

set (CMAKE_CXX_FLAGS_DEBUG "${CMAKE_CXX_FLAGS_DEBUG} -fno-omit-frame-pointer -fsanitize=address -fno-optimize-sibling-calls")
set (CMAKE_LINKER_FLAGS_DEBUG "${CMAKE_LINKER_FLAGS_DEBUG} -fno-omit-frame-pointer -fsanitize=address -fno-optimize-sibling-calls")
set(CMAKE_CXX_STANDARD 17)
add_compile_options("-g")
include(CTest)
enable_testing()

set(srcs QxCrypt.cpp)
add_library(QxCrypt ${srcs})
target_link_libraries(QxCrypt fmt crypto)

add_executable(testQxCrypt TestQxCrypt.cpp ${srcs})
target_link_libraries(testQxCrypt gtest gtest_main pthread QxCrypt)

include(GoogleTest)
gtest_discover_tests(testQxCrypt PROPERTIES 
    ENVIRONMENT
    TESTDIR=${CMAKE_CURRENT_SOURCE_DIR}/test_data
    )
set(CPACK_PROJECT_NAME ${PROJECT_NAME})
set(CPACK_PROJECT_VERSION ${PROJECT_VERSION})
include(CPack)
