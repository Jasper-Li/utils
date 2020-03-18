#include<iostream>
#include <sys/types.h>
#include <dirent.h>
#include <errno.h>
#include <string.h>

using std::cin;
using std::cout;
using std::endl;
void check_errno() {
    if (errno) {
        cout << "error no: " << strerror(errno) << endl;
    }
}
static inline bool is_dot_dotdot(const char *name)
{
	if (name[0] != '.')
		return false;

	return name[1] == '\0' || (name[1] == '.' && name[2] == '\0');
}
void listDir(char* path) {
    DIR * pDir = opendir(path);
    int res = 0;
    char cPath[512];
    if (!pDir) {
        cout << path << endl;
        check_errno();
    }
    while(1) {
        struct dirent * dp = nullptr;
        errno = 0;

        dp = readdir(pDir);
        if (dp == nullptr) {
            check_errno();
            break;
        }

        if (is_dot_dotdot(dp->d_name) ||
		    !strncmp(dp->d_name, "lost+found", strlen("lost+found")))
			continue;
        cout << dp->d_name << endl;

    }

    res = closedir(pDir);
}
int main(void) {
    char path[] = "test";
    listDir(path);
    return 0;
}
