#include <gtest/gtest.h>

#include <cstdlib>
#include <filesystem>

#include "QxCrypt.h"
#include "openssl/opensslv.h"
#include "fmt/core.h"

namespace fs = std::filesystem;
using namespace std;
using namespace QxCrypt;

TEST(QxCrypt, Init) {
  fs::path test_dir = getenv("TESTDIR");
  ASSERT_TRUE(fs::is_directory(test_dir));
  fmt::print("openssl version: {}\n", OPENSSL_VERSION_TEXT);

}
TEST(QxCrypt, Encrypt) {
  fs::path test_dir = getenv("TESTDIR");
  fs::path pubkey = test_dir / "2048_pub.pem";
  fs::path plain_text = test_dir / "plain.txt";
  fs::path encrypted_file = "encrypted.txt";

  if(fs::exists(encrypted_file)) {
    fs::remove(encrypted_file);
  }
  encrypt(pubkey, plain_text, encrypted_file);
  //ASSERT_TRUE(fs::is_regular_file(encrypted_file));
}