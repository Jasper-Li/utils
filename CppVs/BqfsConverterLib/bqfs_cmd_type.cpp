module bqfs_cmd_type;
import <format>;
import <string>;
import <ranges>;
import <algorithm>;
import <iterator>;
import <vector>;
import <iostream>;

using namespace std;
bool bqfs_cmd_t::operator==(bqfs_cmd_t const& o) const {
    if (cmd_type != o.cmd_type ||
        reg != o.reg ||
        reg != o.reg ||
        data_len != o.data_len ||
        line_num != o.line_num) {
        return false;
    }
    else if(cmd_type == CMD_X) { return data.delay == o.data.delay; }
    else {
		return ranges::equal(std::cbegin(data.bytes), std::cbegin(data.bytes) + data_len,
			std::cbegin(o.data.bytes), std::cbegin(o.data.bytes) + data_len);
    }
}
std::string bqfs_cmd_t::bytes_to_string(const std::span<const uint8_t>bytes) {
	using namespace std;
	vector<string> buf1;
	ranges::transform(bytes, std::back_inserter(buf1),
		[](const auto v) {return format("{:#04x}", v); });
	return buf1
		| views::join_with(string{ ", "s})
		|ranges::to<string>()
		;
}
std::string bqfs_cmd_t::to_string(std::string fronter) const {
	if (cmd_type == CMD_X) {
		return format(
			"{}{{\n"
			"{}\t.cmd_type = {},\n"
			"{}\t.data     = {{.delay = {}}},\n"
			"{}}}", fronter,
			fronter, cmd_type_t_to_string(cmd_type),
			fronter, data.delay,
			fronter);
	}
	else {
		return format(
			"{}{{\n"
			"{}\t.cmd_type = {},\n"
			"{}\t.addr     = {:#04X},\n"
			"{}\t.reg      = {:#04X},\n"
			"{}\t.data     = {{.bytes = {{{}}}}},\n"
			"{}\t.data_len = {},\n"
			"{}}}", fronter,
			fronter, cmd_type_t_to_string(cmd_type),
			fronter, addr,
			fronter, reg,
			fronter, bytes_to_string({ this->data.bytes, this->data_len }),
			fronter, data_len,
			fronter);
	}
}
namespace std {
	void PrintTo(const bqfs_cmd_t& point, std::ostream* os) {
		*os << point.to_string();
	}
}
