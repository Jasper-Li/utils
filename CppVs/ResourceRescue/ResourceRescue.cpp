#include "crt_debug.h"
#include <gtest/gtest.h>
#include <memory>
#include <iostream>
#include <format>
using namespace std;

struct ResultSet{

    ResultSet() :p{ NEW int }{}
    ResultSet(const string& msg):p{NEW int}, msg{msg}{}
    void print() { cout << msg << endl; }
    void Close() { delete p; }
    string msg{};
    
private:
    int* p{ nullptr };
};
struct Statement{
    ResultSet* query(const string& msg) {
        return NEW ResultSet(msg);
    }
    Statement():p(NEW int){}
    void CloseResultSet(ResultSet* r) { r->Close(); delete r; }
    int* p{ nullptr };
};
struct Connection {
    Statement* createStatement() {
        return NEW Statement();
    }
    Connection():p(NEW int){}
    void CloseStatement(Statement* s) { delete s->p; delete s; }
    friend class Environment;
    int* p{ nullptr };
};
struct Environment {
    static Environment* create() {
        return NEW Environment();
    }
    Connection* creatConnection() {
        return NEW Connection();
    }
    Environment():p(NEW int){}
    void Close() { delete p; }
    void CloseConnection(Connection* c) {
        delete c->p;
        delete c;
    }
    int* p{ nullptr };
};
struct EnvironmentDeleter {
    void operator()(Environment*& e) {
        e->Close();
        delete e;
    }
};

TEST(ResourceRescue, shared) {
    ResultSet rs;
    rs.Close();
    Environment env;
    env.Close();
    shared_ptr<Environment> env1{ Environment::create(), EnvironmentDeleter() };
    shared_ptr<Environment> env2{ NEW Environment(), EnvironmentDeleter() };
    cout << env2.get() << " " << env2.use_count() << endl;
    auto env3{ env2 };
    cout << env3.get() << " " << env3.use_count() << endl;

}
TEST(ResourceRescue, create) {
    auto env  = Environment::create();
    auto conn = env->creatConnection();
    auto stmt = conn->createStatement();
    auto msg = "test111"s;
    auto msg2 = "test222"s;
    auto rs = stmt->query(msg);
    ASSERT_EQ(msg, rs->msg);
    //rs->Close();

    auto rs2 = stmt->query(msg2);
    ASSERT_EQ(msg2, rs2->msg);
    //rs2->Close();

    stmt->CloseResultSet(rs);
    stmt->CloseResultSet(rs2);
    conn->CloseStatement(stmt);
    env->CloseConnection(conn);
    env->Close();
    delete env;
}

int main(int argc, char** argv) {
    ::testing::InitGoogleTest(&argc, argv);
#if _DEBUG
    _CrtMemState memoryState { 0 };
    _CrtMemCheckpoint(&memoryState);
#endif
    auto res = RUN_ALL_TESTS();
#if _DEBUG
    _CrtMemDumpAllObjectsSince(&memoryState);
#endif
    return res;
}
