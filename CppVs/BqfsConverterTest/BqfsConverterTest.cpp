#include <gtest/gtest.h>

import <span>;
import <algorithm>;
import <ranges>;
import <string>;
import <iostream>;
import <map>;
import <fstream>;
import <utility>;
import <set>;
import <filesystem>;
import <cstdlib>;
//import <string>;
import BqfsConverterLib;
import bqfs_cmd_type;

namespace fs = std::filesystem;
using namespace std;
using bc = BqfsConverterLib;
#if 0
namespace std {

void PrintTo(const bqfs_cmd_t& point, std::ostream* os) {
	*os << point.to_string();
}
}
#endif
TEST(Converter, checkFile) {
    GTEST_SKIP() << "only for test file";
    const auto path{ "test/input1.fs" };
    ifstream ifs{ path };
    if (!ifs) {
        cout << format("Failed to open file: {} at {}\n", path, __LINE__);
    }
    std::set<char> first_chars;
    size_t size{};
    for (string line; getline(ifs, line);) {
        if (line.starts_with(';')) continue;
        ++size;
        first_chars.insert(line.at(0));
    }
    for (const auto v : first_chars) {
        cout << v << " ";
    }
    cout << endl;
    cout << format("size: {}\n", size); // 171
}
TEST(Converter, basic) {
    // GTEST_SKIP() << "Skipping single test";
    const auto path{ "test/input1.fs" };
    const BqfsConverterLib bq{ path };
    constexpr size_t cmd_size{ 171 };
    ASSERT_EQ(cmd_size, bq.size());
    const auto out_path{ "test/input1.h" };
    if (fs::is_regular_file(out_path)) {
        ASSERT_TRUE(fs::remove(out_path));
    }
    bq.write_to(out_path);
    ASSERT_TRUE(fs::is_regular_file(out_path));
	ASSERT_TRUE(fs::remove(out_path));

    const auto cmd = format("BqfsConverterMain.exe -i {} -o {}", path, out_path);
    ASSERT_EQ(0, std::system(cmd.c_str()));
    ASSERT_TRUE(fs::is_regular_file(out_path));
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
TEST(Converter, parseValidLines) {
    //GTEST_SKIP() << "dev...";
    const pair<string_view, bqfs_cmd_t> checks[]{
        {"W: AA 00 01 00",
			{CMD_W, 0xAA, 0x00,
				{.bytes{0x01, 0x00}},
				2,
            },
        },
        {"C: AA 00 21 06",
			{CMD_C, 0xAA, 0x00,
				{.bytes{0x21, 0x06}},
				2,
            },
        },
        {"C: AA 60 A5",
			{CMD_C, 0xAA, 0x60,
				{.bytes{0xa5}},
				1,
            },
        },
        {"X: 2000",
            {CMD_X, 0, 0, {.delay=2000}}
        }
    };
    for (const auto& [line, expect] : checks) {
        const auto result = bc::parse_line(line);
        ASSERT_TRUE(result) << format("Failed to check line: {}", line);
        ASSERT_EQ(expect, result.value());
    }
#if 0
    const auto w1 = bc::parse_line("W: AA 00 01 00");
    const bqfs_cmd_t w1_expect{CMD_W, 0xAA, 0x00,
        {.bytes{0x01, 0x00}},
        2,
    };
    ASSERT_TRUE(w1);
    ASSERT_EQ(w1_expect, w1.value());
#endif
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

TEST(Converter, str2uint16_t) {
    const pair<string_view, optional<uint16_t>> checks[]{
        {"00", {00}},
        {"1000", {1000}},
        {"10", {10}},
    };
    for (const auto [s, value] : checks) {
        const auto result = bc::str2uint16_t(s);
        if (value) {
            ASSERT_TRUE(result) << format("Failed to convert uint16_t: \"{}\"", s);;
        }
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
    const bqfs_cmd_t not_equals[][2]{
        {
            {
			.cmd_type = CMD_W,
			.addr = 0XAA,
			.reg = 0X0,
			.data = {.bytes = {0x00, 0x00}},
			.data_len = 2,
			},
            {
            .cmd_type = CMD_W,
            .addr = 0XAA,
            .reg = 0X0,
            .data = {.bytes = {0x01, 0x00} },
            .data_len = 2,
            },
        },
        {
            {CMD_X, 0, 0, {.delay=2000}},
            {CMD_X, 0, 0, {.delay=1000}},
		},
        {
            {
			.cmd_type = CMD_W,
			.addr = 0XAA,
			.reg = 0X0,
			.data = {.bytes = {0x00, 0x00}},
			.data_len = 2,
			},
            {CMD_X, 0, 0, {.delay=2000}},
		},
    };
    for (const auto& [left, right] : not_equals) {
        ASSERT_NE(left, right);
    }
}
int main(int argc, char** argv) {
    ::testing::InitGoogleTest(&argc, argv);
    const auto res = RUN_ALL_TESTS();
    return res;
}
