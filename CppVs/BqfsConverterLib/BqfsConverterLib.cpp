module BqfsConverterLib;

import <iostream>;
import <format>;
import <fstream>;
import <stdexcept>;
import <string>;
import <filesystem>;
import <map>;
import <ranges>;
import <vector>;
import <charconv>;
import <system_error>;


using namespace std;
namespace fs = std::filesystem;
using bc = BqfsConverterLib;

BqfsConverterLib::BqfsConverterLib(const string_view path)
	:path{ path } {
	std::ifstream ifs{ this->path };
	if (!ifs) {
		throw runtime_error(format("Failed to open file: {} at {}", path, fs::current_path().string()));
	}
	string line;
	while (getline(ifs, line)) {
#if 0
		cout << line ;
		cout << format(" {:#x}", line.at(line.size() - 1));
		cout << endl;
#endif	
	}
}
optional<cmd_type_t> bc::guess_type(const char c) {
	const static map<char, cmd_type_t> char_2_cmd_type_t{
		{'R', CMD_R},
		{'W', CMD_W},
		{'C', CMD_C},
		{'X', CMD_X},
	};
	try {
		return char_2_cmd_type_t.at(c);
	}
	catch (const std::out_of_range& ) {
		return {};
	}
	return {};
}
optional<uint8_t> bc::str2uint8_t(const string_view s) {
	if (s.size() != 2) { return {}; }
	uint8_t value;
	const auto p = s.data();
	const auto result = std::from_chars(p, p + 2, value, 16);
	if (result.ec == std::errc::invalid_argument ||
		result.ec == std::errc::result_out_of_range) {
		return {};
	}
//	cout << format("debug: \"{}\" -> {:#x}\n", s, value);
	return { value };
}

optional<bqfs_cmd_t> bc::parse_line(const string_view line) {
	if (line.starts_with(";")) {
		return {};
	}
	try {
		const auto first{ line.at(0) };
		const cmd_type_t type = bc::guess_type(first).value();
		const auto buf{ line.substr(1, 2) };
		if (buf != ": ") { return {}; }
		const auto body{ line.substr(3) };

		using namespace std::literals;
		auto elements = body
			| std::views::split(" "sv);
		if (type != CMD_X) {
			vector<string_view> bufs{ elements.cbegin(), elements.cend() };
			const size_t size = bufs.size();
			if (size <= 3) { 
				cout << format(R"(elements size({}) <= 3 for line "{}")",
					size, line);
				return {}; 
			}
			vector<uint8_t> values; 
			for (const auto b : bufs) {
				const auto op_value = str2uint8_t(b);
				if (!op_value) {
					cout << format(R"(Failed to convert "{}" in line "{}")", b, line);
					return {};
				}
				values.push_back(op_value.value());
			}
			try {
				bqfs_cmd_t result{
					.cmd_type = type,
					.addr = values.at(0),
					.reg = values.at(1),
					.data{.bytes = {}},
					.data_len = static_cast<uint8_t>(size - 2),
				};
				std::ranges::copy(values.cbegin() + 2, values.cend(),
					std::begin(result.data.bytes));
				return result;
			}
			catch (std::exception const& e) {
				cout << "get exception: " << e.what() << endl;
				return {};
			}

#if 0
			for (const auto e : bufs) {
				cout << format("debug: e \"{}\"\n", string_view{ e });
			}
#endif
		}
	}
	catch (const std::bad_optional_access&) {
		return {};
	}
	return {};
}

