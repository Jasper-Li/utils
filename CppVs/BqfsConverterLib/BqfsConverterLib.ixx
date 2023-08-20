#pragma once
export module BqfsConverterLib;
export import bqfs_cmd_type;

export import <string_view>;
export import <string>;
export import <optional>;

using namespace std;

export class BqfsConverterLib
{
public:
	BqfsConverterLib(const string_view path);
	static optional<bqfs_cmd_t> parse_line(const string_view line);
private:
	string path;
};

