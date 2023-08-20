module BqfsConverterLib;
import <iostream>;
import <format>;
import <fstream>;
import <stdexcept>;
import <string>;
import <filesystem>;

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
optional<bqfs_cmd_t> bc::parse_line(const string_view line) {
	if (line.starts_with(";")) {
		return {};
	}
	return {};

}
