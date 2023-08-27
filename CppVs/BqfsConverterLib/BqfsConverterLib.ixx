#pragma once
export module BqfsConverterLib;
export import bqfs_cmd_type;

export import <string_view>;
export import <string>;
export import <optional>;
export import <vector>;
using namespace std;

export class BqfsConverterLib
{
public:
	BqfsConverterLib(const string_view path);
	size_t size() const { return values.size(); }
	void write_to(const string_view path) const;
	static optional<bqfs_cmd_t> parse_line(const string_view line);
	static optional<cmd_type_t> guess_type(const char c);
	static optional<uint8_t> str2uint8_t(const string_view s);
	static optional<uint16_t> str2uint16_t(const string_view s);
private:
	const string path;
	std::vector<bqfs_cmd_t> values;
};

