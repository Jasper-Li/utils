#pragma once
#include <filesystem>

namespace QxCrypt {
using namespace std;
namespace fs = std::filesystem;

void encrypt(fs::path pubkey, fs::path plain_text, fs::path encrypted_file);

}  // namespace QxCrypt
