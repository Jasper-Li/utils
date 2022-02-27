#include "QxCrypt.h"
#include <openssl/bio.h>
#include <openssl/pem.h>

namespace QxCrypt {
void check_file(fs::path path) {
  if(!fs::exists(path)) {
    throw runtime_error("FileNotFound " + path.string());
  }
}
void encrypt(fs::path pubkey, fs::path plain_text, fs::path encrypted_file) {
  check_file(pubkey);
  check_file(plain_text);
  BIO* bp_pubkey = BIO_new_file(pubkey.string().c_str(), "r");
  if(!bp_pubkey) {
    throw runtime_error("Failed to open pubkey file " + pubkey.string());
  }
  RSA* rsa_pubkey =
      PEM_read_bio_RSA_PUBKEY(bp_pubkey, nullptr, nullptr, nullptr);
  if(!rsa_pubkey) {
    throw runtime_error("Failed to pem read pubkey file " + pubkey.string());
  }

  int ret = RSA_print_fp(stdout, rsa_pubkey, 0);
  RSA_free(rsa_pubkey);
  BIO_free(bp_pubkey);
}
}