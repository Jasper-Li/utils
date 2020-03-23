#include<iostream>
#include<experimental/filesystem>
#include<fstream>
using std::cin;
using std::cout;
using std::endl;
namespace fs = std::experimental::filesystem;
int main(int argc, char *argv[]) {
    std::experimental::filesystem::path src_path {argv[1]};  
    if (fs::exists(src_path)) {
        cout << "file: " << src_path 
        << " size: " <<fs::file_size(src_path) 
        << endl;

        std::ifstream src_file(src_path);
        char a[15];
        src_file.read(a, 14);
        cout << "content: " << a << endl;

    } else {
        cout << "file " << src_path << " doesn't exist" << endl;
    }
    return 0;
}
