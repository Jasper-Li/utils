#include <gtest/gtest.h>

import <span>;
import <algorithm>;
import <ranges>;
import <string>;
import <iostream>;
import <map>;
import <utility>;
//import <string>;
import BqfsConverterLib;
import bqfs_cmd_type;

using namespace std;
using bc = BqfsConverterLib;
#if 0
namespace std {

void PrintTo(const bqfs_cmd_t& point, std::ostream* os) {
	*os << point.to_string();
}
}
#endif
TEST(Converter, init) {
    GTEST_SKIP() << "Skipping single test";
    auto path{ "test/input1.fs" };
    BqfsConverterLib bq{ path };
}
TEST(Converter, parseCommentLine) {
    const vector<string_view> lines{
        ";--------------------------------------------------------",
        "; Verify Existing Firmware Version",
        "; --------------------------------------------------------" };
    for (const auto line : lines) {
        const auto result = bc::parse_line(line);
        ASSERT_FALSE(result);
    }
}
TEST(Converter, parseWriteLine) {
    //GTEST_SKIP() << "dev...";
    const auto w1 = bc::parse_line("W: AA 00 01 00");
    const bqfs_cmd_t w1_expect{CMD_W, 0xAA, 0x00, 
        {.bytes{0x01, 0x00}},
        2,
    };
    ASSERT_TRUE(w1);
    const auto value = w1.value();
    cout << value.to_string() << endl
        << w1_expect.to_string() << endl;
    ASSERT_EQ(w1_expect, w1.value());
}
TEST(Converter, guessType) {
    const pair<char, optional<cmd_type_t>> checks[]{
        {'R', {CMD_R}},
        {'W', {CMD_W}},
        {'C', {CMD_C}},
        {'X', {CMD_X}},
        {'A', {}},
        {'r', {}},
    };
    for (const auto [ch, type_expect] : checks) {
        const auto result = bc::guess_type(ch);
        ASSERT_EQ(type_expect, result);
    }
}
TEST(Converter, str2uint_8) {
    const pair<string_view, optional<uint8_t>> checks[]{
        {"00", {0x00}},
        {"01", {0x01}},
        {"0a", {0x0a}},
        {"0A", {0x0A}},
    };
    for (const auto [s, value] : checks) {
        const auto result = bc::str2uint8_t(s);
        ASSERT_EQ(value, result) 
            << format("Failed to convert \"{}\", {} != {}", s, value.value(), result.value());
    }
}
TEST(print_string, array_span) {
    uint8_t bytes[10]{ 0x01, 0x02, 0x03 };
    auto result = bqfs_cmd_t::bytes_to_string({ bytes, 3 });
    ASSERT_EQ("0x01, 0x02, 0x03", result);
}
TEST(print_string, bqfs_cmd_t_to_string) {
    bqfs_cmd_t empty{};
    cout << empty.to_string() << endl;
    GTEST_SKIP() << "Skipping single test";
    const bqfs_cmd_t values[]{
		{
			.cmd_type = CMD_W,
			.addr = 0xAA,
			.reg = 0x00,
			.data = {.bytes = {0x01, 0x00}},
			.data_len = 2,
		},
		{
			.cmd_type = CMD_C,
			.addr = 0xAA,
			.reg = 0x00,
			.data = {.bytes = {0x21, 0x06}},
			.data_len = 2,
		},
		{
			.cmd_type = CMD_X,
			.data = {.delay = 1100},
		},
	};
    for (const auto& v : values) {
        cout << v.to_string() << endl;
    }
}
TEST(print_string, cmd_type_t_to_string) {
    ASSERT_EQ("CMD_R", cmd_type_t_to_string(CMD_R));
    ASSERT_EQ("CMD_W", cmd_type_t_to_string(CMD_W));
    ASSERT_EQ("CMD_C", cmd_type_t_to_string(CMD_C));
    ASSERT_EQ("CMD_X", cmd_type_t_to_string(CMD_X));

}
TEST(bqfs_cmd_t, cmp) {
    const bqfs_cmd_t a{
        .cmd_type = CMD_W,
        .addr = 0XAA,
        .reg = 0X0,
        .data = {.bytes = {0x00, 0x00}},
        .data_len = 2,
    };
    const bqfs_cmd_t b{
        .cmd_type = CMD_W,
            .addr = 0XAA,
            .reg = 0X0,
            .data = {.bytes = {0x01, 0x00} },
            .data_len = 2,
    };
    ASSERT_NE(a, b);
}
int main(int argc, char** argv) {
    ::testing::InitGoogleTest(&argc, argv);
    const auto res = RUN_ALL_TESTS();
    return res;
}
