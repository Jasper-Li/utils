#pragma once
export module bqfs_cmd_type;

import <cstdint>;
import <map>;
import <span>;
import <cstring>;
import <string>;

constexpr size_t CMD_MAX_DATA_SIZE = 110;
#define RETRY_LIMIT		3
#define CMD_RETRY_DELAY		100 /* in ms */

#define ELEMENTS(ACTION) \
	ACTION(CMD_INVALID), \
	ACTION(CMD_R), 	/* Read */ \
	ACTION(CMD_W), 	/* Write */ \
	ACTION(CMD_C), 	/* Compare */ \
	ACTION(CMD_X), 	/* Delay */

export enum cmd_type_t {
#define	ELE_DEFINE(ELE) ELE
	ELEMENTS(ELE_DEFINE)
#undef	ELE_DEFINE
};
export std::string cmd_type_t_to_string(const cmd_type_t t) {
	static const std::map<cmd_type_t, std::string> cmd_type_t_map{
	#define ELE_MAP(ELE) {ELE, #ELE}
		ELEMENTS(ELE_MAP)
	#undef	ELE_MAP
	};
	return cmd_type_t_map.at(t);
}
/*
 * DO NOT change the order of fields - particularly reg
 * should be immediately followed by data
 */
export struct bqfs_cmd_t {
	cmd_type_t cmd_type{};
	uint8_t addr{};
	uint8_t reg{};
	union {
		uint8_t bytes[CMD_MAX_DATA_SIZE + 1];
		uint16_t delay{};
	} data;
	uint8_t data_len{};
	uint32_t line_num{};

	static std::string bytes_to_string(const std::span<const uint8_t> bytes);
	bool operator==(bqfs_cmd_t const& o) const;
	std::string to_string(std::string fronter = "") const;
};
export namespace std {
	void PrintTo(const bqfs_cmd_t& point, std::ostream* os);
}
